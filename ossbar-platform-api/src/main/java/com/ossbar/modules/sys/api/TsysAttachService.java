package com.ossbar.modules.sys.api;

/**
 * 附件管理
 * @author huj
 * @create 2019-05-09 15:56
 * @email hujun@creatorblue.com
 */
public interface TsysAttachService {

    /**
     * 文件上传时候保存附件记录，根据前端组件的特性,需要在实际点击保存的时候获取attach对象进行id的绑定。
     *
     * @author huangwb
     * @data 2019年5月30日
     * @param oldNamePc      原始名称
     * @param extensionPc    文件后缀
     * @param fileSize       文件大小
     * @param imgNamePc      链接地址
     * @param longNamePc     文件名称
     * @param fileType       文件类型 1、字典
     * @param uploadUserId 上传人
     * @return attachId 附件id
     */
    String uploadFileInsertAttach(String oldNamePc, String fileSize, String extensionPc, String imgNamePc,
                                  String longNamePc, String fileType, String uploadUserId);

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