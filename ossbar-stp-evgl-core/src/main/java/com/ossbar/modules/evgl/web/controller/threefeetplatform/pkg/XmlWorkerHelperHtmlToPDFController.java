package com.ossbar.modules.evgl.web.controller.threefeetplatform.pkg;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.dubbo.config.annotation.Reference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.common.CbUploadUtils;
import com.ossbar.modules.evgl.common.pdf.AsianFontProvider;
import com.ossbar.modules.evgl.pkg.api.ExportPackageService;
import com.ossbar.utils.tool.StrUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * 导出为pdf文档
 * @author huj
 * @create 2022-10-12 10:02
 * @email hujun@ossbar.com
 */
@RestController
@RequestMapping("/pkginfo-api/pdf/")
public class XmlWorkerHelperHtmlToPDFController {

    private Logger log = LoggerFactory.getLogger(XmlWorkerHelperHtmlToPDFController.class);

    // 生成PDF路径
    public static final String PDF_PATH = "s:\\pdf\\1.pdf";

    private static String FONT_PATH = "/fonts/simsunb.ttf";

    @Reference(version = "1.0.0")
    private ExportPackageService exportPackageService;
    @Reference(version = "1.0.0")
    private TevglBookSubjectService tevglBookSubjectService;
    @Autowired
    private CbUploadUtils uploadUtils;


    /**
     * 测试成功，但该方式较慢!!!
     * http://localhost:9999/pkginfo-api/pdf/exportPdf?subjectId=6beb02a96d6c4d0b98455c5ff2118d32&pkgId=dd83adfdd2a741dd82a541462d3e2e72
     * http://localhost:9999/pkginfo-api/pdf/exportPdf?subjectId=e5957323d4cb423c88729ed31b93e738&pkgId=de736e94bde24e4ba1b70a047082d904
     * @param response
     * @param params
     * @throws IOException
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(HttpServletResponse response, @RequestParam Map<String, Object> params) throws IOException {
        String subjectId = params.get("subjectId") == null ? null : params.get("subjectId").toString();
        String pkgId = params.get("pkgId") == null ? null : params.get("pkgId").toString();
        if (StrUtils.isEmpty(subjectId) || StrUtils.isEmpty(pkgId)) {
            log.debug("必传参数为空");
            return;
        }
        TevglBookSubject tevglBookSubject = tevglBookSubjectService.selectObjectById(subjectId);
        if (tevglBookSubject == null) {
            return;
        }
        // TODO 权限校验

        String htmlStr = exportPackageService.getHtmlString(pkgId, subjectId);
        String htmlStart = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title></title></head><body>";
        String htmlEnd = "</body><style>body{font-family:SimSun}@page{size:240mm 360mm;}</style></html>";
        htmlStr = formatHtml(htmlStart + htmlStr + htmlEnd);
        // 生成pdf
        String pdfPath = uploadUtils.getUploadPath() + "/pdf/";
        File f = new File(pdfPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        String abPath = pdfPath + pkgId + ".pdf";
        XMLWorkerHelperHtmlToPDF(response, pkgId, htmlStr, abPath);
        log.debug("pdf文件已生成至 {}", abPath);
        // 下载
        downloadFile(response, pkgId, tevglBookSubject.getSubjectName());
    }

    /**
     * 生成pdf
     * @param response
     * @param pkgId
     * @param htmlString html字符串
     * @param pdfPath 磁盘上绝对路径，示例：d:/pdf/布道师.pdf
     */
    public static void XMLWorkerHelperHtmlToPDF(HttpServletResponse response, String pkgId, String htmlString, String pdfPath) {
        Document document = null;
        try {
            Long startTime = System.currentTimeMillis();
            document = new Document(PageSize.A4);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            // 添加水印
            pdfWriter.setPageEvent(new CustomEvent("布道师"));
            document.open();
            document.addAuthor("布道师");
            document.addCreator("布道师");
            document.addSubject("pdf主题");
            document.addCreationDate();
            document.addTitle("pdf标题,可在html中指定title");
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            InputStream inputStream = null;
            // 方式一/文件生成PDF
            //worker.parseXHtml(pdfWriter, document, new FileInputStream(HTML), inputStream, Charset.forName("UTF-8"), new AsianFontProvider());
            // 方式二/HTML代码字符串生成PDF
            worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(htmlString.getBytes("UTF-8")),inputStream, Charset.forName("UTF-8"),new AsianFontProvider());
            Long endTime = System.currentTimeMillis();
            System.out.print("XMLWorkerHelper parse Html to Pdf End -> " + (endTime -startTime));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private static String formatHtml(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        // jsoup标准化标签，生成闭合标签
        doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
        return doc.html();
    }

    /**
     * 事件处理类, 用于监听pdf页码增加时, 每页增加水印
     */
    static class CustomEvent extends PdfPageEventHelper {

        private String waterMark;

        public CustomEvent(String waterMark) {
            this.waterMark = waterMark;
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                // 加入水印
                PdfContentByte waterMar = writer.getDirectContentUnder();
                // 开始设置水印
                waterMar.beginText();
                // 设置水印透明度
                PdfGState gs = new PdfGState();
                // 设置填充字体不透明度为0.2f
                gs.setFillOpacity(0.1f);
                // 设置水印字体参数及大小
                BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
                waterMar.setFontAndSize(baseFont,60);
                // 设置透明度
                waterMar.setGState(gs);
                // 设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
                waterMar.showTextAligned(Element.ALIGN_CENTER, waterMark , 300, 500, 45);
                //结束设置
                waterMar.endText();
                waterMar.stroke();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadFile(HttpServletResponse response, String pkgId, String fileName) {
        log.debug("开始下");
        String absolutePath = uploadUtils.getUploadPath() + "/pdf/" + pkgId + ".pdf";
        log.debug("pdf文件磁盘路径为：" + absolutePath);
        if (absolutePath == null) {
            return;
        }
        fileName = fileName + ".pdf";
        // 实现文件下载
        byte[] buffer = new byte[1024];
        File f = new File(absolutePath);
        try(
                FileInputStream fis = new FileInputStream(f);
                BufferedInputStream bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
        ){
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //resp.setCharacterEncoding("UTF-8");
        //resp.setHeader("content-Type", "application/pdf");
        //resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("test" + ".pdf", "UTF-8"));
    }
}
