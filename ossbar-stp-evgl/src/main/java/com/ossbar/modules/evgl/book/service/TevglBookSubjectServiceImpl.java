package com.ossbar.modules.evgl.book.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.*;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.domain.TevglBookSubperiod;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookMajorMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubperiodMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> Title: 课程教材</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
public class TevglBookSubjectServiceImpl implements TevglBookSubjectService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TevglBookSubjectServiceImpl.class);
    @Autowired
    private TevglBookSubjectMapper tevglBookSubjectMapper;
    @Autowired
    private TevglBookChapterMapper tevglBookChapterMapper;
    @Autowired
    private TevglBookSubperiodMapper tevglBookSubperiodMapper;
    @Autowired
    private TevglBookMajorMapper tevglBookMajorMapper;
    @Autowired
    private TsysAttachService tsysAttachService;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private DictUtil dictUtil;
    @Autowired
    private UploadFileUtils uploadFileUtils;

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    @SysLog(value="查询列表(返回List<Bean>)")
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<TevglBookSubject> tevglBookSubjectList = tevglBookSubjectMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tevglBookSubjectList, "createUserId", "updateUserId");
        convertUtil.convertOrgId(tevglBookSubjectList, "orgId");
        if (tevglBookSubjectList.size() > 0) {
            tevglBookSubjectList.forEach(subject -> {
                subject.setSubjectLogo(uploadFileUtils.stitchingPath(subject.getSubjectLogo(), "10"));
            });
        }
        PageUtils pageUtil = new PageUtils(tevglBookSubjectList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    @SysLog(value="查询列表(返回List<Map<String, Object>)")
    public R queryForMap(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<Map<String, Object>> tevglBookSubjectList = tevglBookSubjectMapper.selectListMapByMap(query);
        convertUtil.convertUserId2RealName(tevglBookSubjectList, "create_user_id", "update_user_id");
        PageUtils pageUtil = new PageUtils(tevglBookSubjectList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 新增
     *
     * @param tevglBookSubject
     * @return
     */
    @Override
    @SysLog(value="新增")
    public R save(TevglBookSubject tevglBookSubject) throws CreatorblueException {
        if (StrUtils.isEmpty(tevglBookSubject.getMajorId())) {
            return R.error("职业课程路径不能为空！");
        }
        String uuid = Identities.uuid();
        tevglBookSubject.setSubjectId(uuid);
        tevglBookSubject.setCreateUserId(serviceLoginUtil.getLoginUserId());
        tevglBookSubject.setCreateTime(DateUtils.getNowTimeStamp());
        tevglBookSubjectMapper.insert(tevglBookSubject);
        // 如果上传了资源文件
        String attachId = tevglBookSubject.getAttachId();
        if (attachId != null && !"undefined".equals(attachId) && !"".equals(attachId)) {
            tsysAttachService.updateAttachForAdd(attachId, uuid,"10");
        }
        // 保存职业路径和课程的关系
        String majorId = tevglBookSubject.getMajorId(); // 前端，多个的话，会用英文逗号分隔
        if (StrUtils.isNotEmpty(majorId)) {
            String[] majorIdArray = majorId.split(",");
            for (int i = 0; i < majorIdArray.length; i++) {
                TevglBookSubperiod t = new TevglBookSubperiod();
                t.setSubperiodId(Identities.uuid());
                t.setSubjectId(tevglBookSubject.getSubjectId());
                t.setMajorId(majorIdArray[i]);
                t.setTerm(StrUtils.isEmpty(tevglBookSubject.getTerm()) ? "1" : tevglBookSubject.getTerm()); // 1为第一学期
                t.setSubjectProperty(StrUtils.isEmpty(tevglBookSubject.getSubjectProperty()) ? "1" : tevglBookSubject.getSubjectProperty()); // 1为必修
                tevglBookSubperiodMapper.insert(t);
            }
        }
        return R.ok().put("subjectId", tevglBookSubject.getSubjectId());
    }

    /**
     * 修改
     *
     * @param tevglBookSubject
     * @return
     */
    @Override
    @SysLog(value="修改")
    public R update(TevglBookSubject tevglBookSubject) throws CreatorblueException {
        if (StrUtils.isEmpty(tevglBookSubject.getMajorId())) {
            return R.error("职业课程路径不能为空！");
        }
        tevglBookSubject.setUpdateUserId(serviceLoginUtil.getLoginUserId());
        tevglBookSubject.setUpdateTime(DateUtils.getNowTimeStamp());
        //ValidatorUtils.check(tevglBookSubject);
        tevglBookSubjectMapper.update(tevglBookSubject);
        // 如果上传了资源文件
        String attachId = tevglBookSubject.getAttachId();
        if (attachId != null && !"undefined".equals(attachId) && !"".equals(attachId)) {
            tsysAttachService.updateAttachForEdit(attachId, tevglBookSubject.getAttachId(),"10");
        }
        // 保存职业路径和课程的关系
        String majorId = tevglBookSubject.getMajorId(); // 前端，多个的话，会用英文逗号分隔
        if (StrUtils.isNotEmpty(majorId)) {
            // 先删除课程教材与职业课程路径的关系
            Map<String, Object> map = new HashMap<>();
            map.put("subjectId", tevglBookSubject.getSubjectId());
            List<TevglBookSubperiod> list = tevglBookSubperiodMapper.selectListByMap(map);
            if (list.size() > 0) {
                list.forEach(a -> {
                    tevglBookSubperiodMapper.delete(a.getSubperiodId());
                });
            }
            // 再保存
            String[] majorIdArray = majorId.split(",");
            for (int i = 0; i < majorIdArray.length; i++) {
                TevglBookSubperiod t = new TevglBookSubperiod();
                t.setSubperiodId(Identities.uuid());
                t.setSubjectId(tevglBookSubject.getSubjectId());
                t.setMajorId(majorIdArray[i]);
                t.setTerm(StrUtils.isEmpty(tevglBookSubject.getTerm()) ? "1" : tevglBookSubject.getTerm()); // 1为第一学期
                t.setSubjectProperty(StrUtils.isEmpty(tevglBookSubject.getSubjectProperty()) ? "1" : tevglBookSubject.getSubjectProperty()); // 1为必修
                tevglBookSubperiodMapper.insert(t);
            }
        }
        return R.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @SysLog(value="单条删除")
    public R delete(String id) throws CreatorblueException {
        tevglBookSubjectMapper.delete(id);
        return R.ok();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public R deleteBatch(String[] ids) throws CreatorblueException {
        tevglBookSubjectMapper.deleteBatch(ids);
        return R.ok();
    }

    /**
     * 查看明细
     *
     * @param id
     * @return
     */
    @Override
    @SysLog(value="查看明细")
    public R view(String id) {
        // 课程教材
        Map<String, Object> data = new HashMap<>();
        TevglBookSubject subject = tevglBookSubjectMapper.selectObjectById(id);
        if (subject == null) {
            return R.ok().put(Constant.R_DATA, new TevglBookSubject());
        }
        subject.setSubjectLogo(uploadFileUtils.stitchingPath(subject.getSubjectLogo(), "10"));
        data.put("subject", subject);
        // 课程教材与职业路径的关系
        Map<String, Object> map = new HashMap<>();
        map.put("subjectId", subject.getSubjectId());
        List<TevglBookSubperiod> list = tevglBookSubperiodMapper.selectListByMap(map);
        List<String> subperiodIds = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(a -> {
                subperiodIds.add(a.getSubperiodId());
            });
        }
        map.clear();
        map.put("subperiodIds", subperiodIds);
        List<TevglBookMajor> majorList = tevglBookMajorMapper.selectListByMap2(map);
        data.put("majorList", majorList);
        return R.ok().put(Constant.R_DATA, data);
    }


    /**
     * 课程下拉列表，注意此方法只会查询subject_ref为空的记录
     *
     * @param params
     * @return
     */
    @Override
    public R listSelectSubject(Map<String, Object> params) {
        List<TevglBookSubject> tevglBookSubjectList = new ArrayList<TevglBookSubject>();
        params.put("sidx", "t1.create_time");
        params.put("order", "desc");
        Query query = new Query(params);
        if (params.get("majorIds") != null && !"".equals(params.get("majorIds"))) {
            String ids = (String) params.get("majorIds");
            List<String> list = new ArrayList<>();
            String[] majorIds = ids.split(",");
            for (String string : majorIds) {
                list.add(string);
            }
            params.put("majorIds", list);
        }
        tevglBookSubjectList = tevglBookSubjectMapper.selectListByMapForCommon(query);
        if (tevglBookSubjectList != null && tevglBookSubjectList.size() > 0) {
            tevglBookSubjectList.forEach(subject -> {
                subject.setSubjectLogo(uploadFileUtils.stitchingPath(subject.getSubjectLogo(), "10")); // 图片处理
            });
        }
        List<Map<String,Object>> collect = tevglBookSubjectList.stream().map(this::converToSubjectMap).collect(Collectors.toList());
        return R.ok().put(Constant.R_DATA, collect);
    }

    /**
     * 取部分属性，若需要额外，自行补充
     * @param tevglBookSubject
     * @return
     */
    private Map<String, Object> converToSubjectMap(TevglBookSubject tevglBookSubject){
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("subjectId", tevglBookSubject.getSubjectId());
        info.put("subjectName", tevglBookSubject.getSubjectName());
        info.put("subjectLogo", tevglBookSubject.getSubjectLogo());
        return info;
    }

}
