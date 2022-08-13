package com.ossbar.modules.sys.service;

import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.modules.sys.domain.TsysAttach;
import com.ossbar.modules.sys.persistence.TsysAttachMapper;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private TsysAttachMapper tsysAttachMapper;

    /**
     * 文件上传时候保存附件记录，根据前端组件的特性,需要在实际点击保存的时候获取attach对象进行id的绑定。
     *
     * @param oldNamePc      原始名称
     * @param fileSize       文件大小
     * @param extensionPc    文件后缀
     * @param imgNamePc      链接地址
     * @param longNamePc     文件名称
     * @param fileType       文件类型 1、字典
     * @param uploadUserId 上传人名称
     * @return attachId 附件id
     * @author huangwb
     * @data 2019年5月30日
     */
    @Override
    public String uploadFileInsertAttach(String oldNamePc, String fileSize, String extensionPc, String imgNamePc, String longNamePc, String fileType, String uploadUserId) {
        try {
            TsysAttach tsysAttach = new TsysAttach();
            // 文件后缀
            tsysAttach.setFileSuffix(extensionPc);
            // 文件名称
            tsysAttach.setFileUrl(longNamePc);
            // 链接地址
            tsysAttach.setLjUrl(imgNamePc);
            // 文件大小
            tsysAttach.setFileSize(fileSize);
            // 文件状态 0未绑定
            tsysAttach.setFileState("0");
            // 文件原始名称
            tsysAttach.setFileName(oldNamePc);
            // 设置文件为字典类型
            tsysAttach.setFileType(fileType);
            // 创建用户ID
            tsysAttach.setCreateUserId(uploadUserId);
            tsysAttach.setCreateTime(DateUtils.getNowTimeStamp());
            String uuid = Identities.uuid();
            tsysAttach.setAttachId(uuid);
            tsysAttachMapper.insert(tsysAttach);
            return uuid;
        } catch (Exception e) {
        }
        return null;
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

    }
}
