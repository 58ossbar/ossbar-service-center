package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.modules.sys.domain.TsysAttach;
import com.ossbar.modules.sys.persistence.TsysAttachMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件管理
 * @author huj
 * @create 2019-05-09 15:56
 * @email hujun@creatorblue.com
 */
@Api(tags="附件管理接口")
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/attach")
public class TsysAttachServiceImpl implements TsysAttachService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TsysAttachMapper tsysAttachMapper;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private ConvertUtil convertUtil;

    /**
     * 根据条件分页查询记录
     *
     * @param params
     * @return
     */
    @Override
    @RequestMapping("/query")
    @SentinelResource("/sys/attach/query")
    public R query(@RequestParam Map<String, Object> params) {
        params.put("sidx", "create_time");
        params.put("order", "desc");
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TsysAttach> tsysDictList = tsysAttachMapper.selectListByMap(params);
        Map<String, String> map = new HashMap<String, String>();
        map.put("fileType", "fileType");
        map.put("fileState", "fileState");
        convertUtil.convertParam(tsysDictList, map);
        convertUtil.convertUserId2RealName(tsysDictList, "createUserId");
        PageUtils pageUtil = new PageUtils(tsysDictList, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 查看明细
     *
     * @param id
     * @return
     * @author huj
     * @data 2019年5月9日
     */
    @Override
    @RequestMapping("/view/{id}")
    @SentinelResource("/sys/attach/view")
    public R view(@PathVariable("id") String id) {
        return R.ok().put(Constant.R_DATA, tsysAttachMapper.selectObjectById(id));
    }

    /**
     * 文件上传时候保存附件记录，根据前端组件的特性,需要在实际点击保存的时候获取attach对象进行id的绑定。
     *
     * @param contentType     原始名称
     * @param fileSize       文件大小
     * @param newFileName    链接地址，存新生成的uuid名称，示例值：296096de-dbd5-4c32-b897-d46fb91dc885.jpeg
     * @param longNameAbsolutePath     文件名称，完整的路径，示例值：D:/uploads/avd-img/296096de-dbd5-4c32-b897-d46fb91dc885.jpeg /mnt/cbstp/uploads/teacher-img/912e6cae-13b6-46c3-b6b6-12981f1c6370.jpeg
     * @param fileType       文件类型 对应属性文件中com.creatorblue.cb-upload-paths的下标
     * @return attachId 附件id
     * @author huangwb
     * @data 2019年5月30日
     */
    @Override
    public String uploadFileInsertAttach(String contentType, Long fileSize, String newFileName, String longNameAbsolutePath, String fileType) {
        try {
            TsysAttach tsysAttach = new TsysAttach();
            String uuid = Identities.uuid();
            tsysAttach.setAttachId(uuid);
            // 文件原始名称类型
            tsysAttach.setFileName(contentType);
            // 文件后缀
            tsysAttach.setFileSuffix(handleFileSuffixByContentType(contentType));
            // 文件名称，绝对路径
            tsysAttach.setFileUrl(longNameAbsolutePath);
            // 链接地址，uuid新文件名称
            tsysAttach.setLjUrl(newFileName);
            // 文件大小
            tsysAttach.setFileSize(String.valueOf(fileSize));
            // 文件状态 0未绑定
            tsysAttach.setFileState("0");
            // 设置文件为字典类型
            tsysAttach.setFileType(fileType);
            // 创建用户ID
            tsysAttach.setCreateUserId(serviceLoginUtil.getLoginUserId());
            tsysAttach.setCreateTime(DateUtils.getNowTimeStamp());
            // 入库
            tsysAttachMapper.insert(tsysAttach);
            return uuid;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     * @author huj
     * @data 2019年5月9日
     */
    @Override
    @RequestMapping("/remove")
    @SentinelResource("/sys/attach/remove")
    @Transactional
    public R deleteBatch(@RequestBody String[] ids) {
        Arrays.stream(ids).forEach(a -> {
            TsysAttach attach = tsysAttachMapper.selectObjectById(a);
            if (attach.getFileUrl() != null && StrUtils.isNotEmpty(attach.getFileUrl())) {
                new File(attach.getFileUrl()).delete();
            }
        });
        tsysAttachMapper.deleteBatch(ids);
        return R.ok();
    }

    /**
     * 根据这种值：image/jpeg、image/jpg、image/png等获取后缀名
     * @param contentType 必传参数，示例值：image/jpeg、image/jpg、image/png
     * @return
     */
    private String handleFileSuffixByContentType(String contentType) {
        if (contentType == null || "".equals(contentType)) {
            return "";
        }
        // 获取图片后缀格式
        String extensionPc = "." + contentType.substring(contentType.lastIndexOf("/") + 1);
        return extensionPc;
    }

    /**
     * （绑定操作）资源上传成功后保存时修改文件绑定关联状态
     *
     * @param attachId 附件表t_sys_attach主键id
     * @param pkId     关联id，被关联对象表的主键id，示例：假如是新增学员时，且上传了附件，该值就是学员表主键id
     * @param fileType 下标位置，属性文件中，com.creatorblue.cb-upload-paths对应的下标
     */
    @Override
    public void updateAttachForAdd(String attachId, String pkId, String fileType) {
        if (StrUtils.isEmpty(attachId) || StrUtils.isEmpty(pkId) || StrUtils.isEmpty(fileType)) {
            return;
        }
        TsysAttach attach = tsysAttachMapper.selectObjectById(attachId);
        if (attach == null) {
            return;
        }
        // 修改attach链接的id
        attach.setPkid(pkId);
        // 设置成绑定状态，0未绑定状态，1绑定状态
        attach.setFileState("1");
        // 更新入库
        attach.setUpdateTime(DateUtils.getNowTimeStamp());
        tsysAttachMapper.update(attach);
    }

    /**
     * （绑定新的，解绑旧的）资源上传成功后保存时修改文件绑定关联状态
     *
     * @param attachId 附件表t_sys_attach主键id
     * @param pkId     关联id，被关联对象表的主键id，示例：假如是新增学员时，且上传了附件，该值就是学员表主键id
     * @param fileType 下标位置，属性文件中，com.creatorblue.cb-upload-paths对应的下标
     */
    @Override
    public void updateAttachForEdit(String attachId, String pkId, String fileType) {
        if (StrUtils.isEmpty(attachId) || StrUtils.isEmpty(pkId) || StrUtils.isEmpty(fileType)) {
            return;
        }
        TsysAttach attach = tsysAttachMapper.selectObjectById(attachId);
        if (attach == null) {
            return;
        }
        // 修改attach链接的id
        attach.setPkid(pkId);
        // 设置成绑定状态，0未绑定状态，1绑定状态
        attach.setFileState("1");
        // 更新入库
        attach.setUpdateTime(DateUtils.getNowTimeStamp());
        tsysAttachMapper.update(attach);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pkid", pkId);
        map.put("fileType", fileType);
        // 文件状态为绑定状态
        map.put("fileState", "1");
        List<TsysAttach> attachs = tsysAttachMapper.selectListByMap(map);
        attachs.stream().forEach(a -> {
            if (a.getAttachId().equals(attachId)) {
                return;
            }
            log.debug("被删除的文件：" + a.getFileUrl());
            if (StrUtils.isNotEmpty(a.getFileUrl())) {
                File file = new File(a.getFileUrl());
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
            tsysAttachMapper.delete(a.getAttachId());
        });
    }
}
