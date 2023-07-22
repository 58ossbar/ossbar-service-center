package com.ossbar.modules.evgl.web.controller.threefeetplatform.pkg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.common.exception.OssbarException;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.pkg.api.ExportPackageService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;

/**
 * @author huj
 * @create 2022-10-09 8:41
 * @email hujun@ossbar.com
 */
@RestController
@RequestMapping("/pkginfo-api/export/")
public class ExportPkgController {

    @Reference(version = "1.0.0")
    private TevglBookSubjectService tevglBookSubjectService;
    @Reference(version = "1.0.0")
    private ExportPackageService exportPackageService;

    /**
     * 导出
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/toexport")
    @CheckSession
    public void toexport(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            throw new OssbarException(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        String pkgId = params.get("pkgId") == null ? "" : params.get("pkgId").toString();
        String ctId = params.get("ctId") == null ? "" : params.get("ctId").toString();
        String subjectId = params.get("subjectId") == null ? "" : params.get("subjectId").toString();
        TevglBookSubject subject = tevglBookSubjectService.selectObjectById(subjectId);
        if(subject == null){
            throw new OssbarException("无效的教材");
        }
        try{
            POIFSFileSystem fs = new POIFSFileSystem();
            String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"+
                    "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\""+
                    "xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\""+
                    "xmlns=\"http://www.w3.org/TR/REC-html40\">"+
                    "<head>"+
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body>" + exportPackageService.initWordDataBySubjectId(pkgId, subjectId, ctId,traineeInfo.getTraineeId()) + "</body></html>";
            InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
            fs.createDocument(is, "WordDocument");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(subject.getSubjectName()+".doc", "UTF-8"))));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-word");
            fs.writeFilesystem(response.getOutputStream());
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
