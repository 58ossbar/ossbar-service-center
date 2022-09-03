package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;

import java.util.Map;

/**
 * 附件管理
 * @author huj
 * @create 2019-05-09 15:56
 * @email hujun@creatorblue.com
 */
public interface TsysAttachService {

    /**
     * 根据条件分页查询记录
     * @param params
     * @return
     */
    R query(Map<String, Object> params);

    /**
     * 批量删除
     * @author huj
     * @data 2019年5月9日
     * @param ids
     * @return
     */
    R deleteBatch(String[] ids);

    /**
     * 查看明细
     * @author huj
     * @data 2019年5月9日
     * @param id
     * @return
     */
    R view(String id);

    /**
     * 文件上传时候保存附件记录，根据前端组件的特性,需要在实际点击保存的时候获取attach对象进行id的绑定。
     *
     * @author huangwb
     * @data 2019年5月30日
     * @param contentType     原始名称，示例值：image/jpeg、image/jpg、image/png
     * @param fileSize       文件大小
     * @param newFileName    链接地址，uuid与后缀名组成的新文件名称，示例值：151254efdc8d4c08865d17a4862b1739.jpeg
     * @param longNamePc     文件名称
     * @param fileType       文件类型 1、字典
     * @return attachId 附件id
     */
    String uploadFileInsertAttach(String contentType, Long fileSize, String newFileName, String longNamePc, String fileType);

    /**
     * （绑定操作）资源上传成功后保存时修改文件绑定关联状态
     * @param attachId 附件表t_sys_attach主键id
     * @param pkId 关联id，被关联对象表的主键id，示例：假如是新增学员时，且上传了附件，该值就是学员表主键id
     * @param fileType 下标位置，属性文件中，com.creatorblue.cb-upload-paths对应的下标
     */
    void updateAttachForAdd(String attachId, String pkId, String fileType);

    /**
     * （绑定新的，解绑旧的）资源上传成功后保存时修改文件绑定关联状态
     * @param attachId 附件表t_sys_attach主键id
     * @param pkId 关联id，被关联对象表的主键id，示例：假如是新增学员时，且上传了附件，该值就是学员表主键id
     * @param fileType 下标位置，属性文件中，com.creatorblue.cb-upload-paths对应的下标
     */
    void updateAttachForEdit(String attachId, String pkId, String fileType);

}
