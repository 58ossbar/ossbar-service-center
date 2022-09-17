package com.ossbar.modules.evgl.book.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookMajorService;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.domain.TevglBookSubperiod;
import com.ossbar.modules.evgl.book.persistence.TevglBookMajorMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubperiodMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huj
 * @create 2022-09-16 9:40
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
public class TevglBookMajorServiceImpl implements TevglBookMajorService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TevglBookMajorServiceImpl.class);
    @Autowired
    private TevglBookMajorMapper tevglBookMajorMapper;
    @Autowired
    private TevglBookSubperiodMapper tevglBookSubperiodMapper;
    @Autowired
    private TevglBookSubjectMapper tevglBookSubjectMapper;
    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private UploadFileUtils uploadPathUtils;

    @Value("${com.creatorblue.file-access-path}")
    public String creatorblueFieAccessPath;

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
        List<TevglBookMajor> tevglBookMajorList = tevglBookMajorMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tevglBookMajorList, "createUserId", "updateUserId");
        convertUtil.convertOrgId(tevglBookMajorList, "orgId");
        PageUtils pageUtil = new PageUtils(tevglBookMajorList,query.getPage(),query.getLimit());
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
        List<Map<String, Object>> tevglBookMajorList = tevglBookMajorMapper.selectListMapByMap(query);
        convertUtil.convertUserId2RealName(tevglBookMajorList, "create_user_id", "update_user_id");
        PageUtils pageUtil = new PageUtils(tevglBookMajorList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 新增
     *
     * @param tevglBookMajor
     * @return
     */
    @Override
    @SysLog(value="新增")
    public R save(TevglBookMajor tevglBookMajor) throws CreatorblueException {
        tevglBookMajor.setMajorId(Identities.uuid());
        tevglBookMajor.setCreateUserId(serviceLoginUtil.getLoginUserId());
        tevglBookMajor.setCreateTime(DateUtils.getNowTimeStamp());
        //ValidatorUtils.check(tevglBookMajor);
        tevglBookMajorMapper.insert(tevglBookMajor);
        return R.ok();
    }

    /**
     * 修改
     *
     * @param tevglBookMajor
     * @return
     */
    @Override
    @SysLog(value="修改")
    public R update(TevglBookMajor tevglBookMajor) throws CreatorblueException  {
        tevglBookMajor.setUpdateUserId(serviceLoginUtil.getLoginUserId());
        tevglBookMajor.setUpdateTime(DateUtils.getNowTimeStamp());
        //ValidatorUtils.check(tevglBookMajor);
        tevglBookMajorMapper.update(tevglBookMajor);
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
        tevglBookMajorMapper.delete(id);
        return R.ok();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    @SysLog(value="批量删除")
    public R deleteBatch(String[] ids) {
        if (ids == null || ids.length == 0) {
            return R.ok();
        }
        List<String> majorIds = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            majorIds.add(ids[i]);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("majorIds", majorIds);
        List<TevglBookSubperiod> list = tevglBookSubperiodMapper.selectListByMap(map);
        if (list != null && list.size() > 0) {
            return R.error("该职业课程路径下还存在课程，请先删除课程");
        }
        tevglBookMajorMapper.deleteBatch(ids);
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
        TevglBookMajor major = tevglBookMajorMapper.selectObjectById(id);
        if (major == null) {
            return R.ok();
        }
        major.setMajorLogo(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("11") + "/" + major.getMajorLogo());
        convertUtil.convertOrgId(major, "orgId");
        // resultList 存储分组后的数据
        List<List<TevglBookSubperiod>> resultList = new ArrayList<>();
        // 查询该职业课程路径下的课程
        Map<String, Object> map = new HashMap<>();
        map.put("majorId", major.getMajorId());
        List<TevglBookSubperiod> tevglBookSubperiodList = tevglBookSubperiodMapper.selectListByMap(map);
        // 根据所属学期分组
        tevglBookSubperiodList.stream().collect(Collectors.groupingBy(TevglBookSubperiod::getTerm, Collectors.toList()))
                .forEach((name, dataList) -> {
                    // 查询课程信息
                    dataList.forEach(a -> {
                        TevglBookSubject subject = tevglBookSubjectMapper.selectObjectById(a.getSubjectId());
                        if (subject != null) {
                            // 图片处理
                            if (subject.getSubjectLogo() != null && !"".equals(subject.getSubjectLogo())) {
                                int i = subject.getSubjectLogo().indexOf(creatorblueFieAccessPath);
                                if (i == -1) {
                                    subject.setSubjectLogo(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("10") + "/" + subject.getSubjectLogo());
                                }
                            }
                        }
                        a.setTevglBookSubject(subject);
                    });
                    resultList.add(dataList);
                });

        // 进一步处理数据，便于前端识别
        List<Map<String, Object>> ok = new ArrayList<>();
        resultList.forEach(list -> {
            Map<String, Object> mm = new HashMap<>();
            List<TevglBookSubject> subjects = new ArrayList<>();
            list.forEach(a -> { // a此时是实体TevglBookSubperiod，利用map的key不能重复，取值
                mm.put("subperiodId", a.getSubperiodId());
                mm.put("majorId", a.getMajorId());
                mm.put("subjectId", a.getSubjectId());
                mm.put("sortNum", a.getSortNum());
                mm.put("term", a.getTerm());
                mm.put("subjectProperty", a.getSubjectProperty());
                mm.put("classHour", a.getClassHour());
                mm.put("classScore", a.getClassScore());
                // 将课程加入集合
                subjects.add(a.getTevglBookSubject());
            });
            mm.put("subjectList", subjects); // 课程集合
            ok.add(mm);
        });

        // 计算总课时、总学分
        BigDecimal totalClassHour = new BigDecimal(0);
        BigDecimal totalClassScore = new BigDecimal(0);
        for (Map<String, Object> k : ok) {
            if (k.get("subjectList") != null) {
                @SuppressWarnings("unchecked")
                List<TevglBookSubject> list = (List<TevglBookSubject>) k.get("subjectList");
                for (TevglBookSubject subject : list) {
                    if (subject.getClassHour() != null) {
                        totalClassHour = totalClassHour.add(subject.getClassHour());
                    }
                    if (subject.getClassScore() != null) {
                        totalClassScore = totalClassScore.add(subject.getClassScore());
                    }
                }
            }
        }
        convertUtil.convertDict(ok, "term", "term"); // 所属学期
        return R.ok().put(Constant.R_DATA, major)
                .put("more", ok) // 学期下的课程
                .put("totalClassHour", totalClassHour)
                .put("totalClassScore", totalClassScore);
    }

    /**
     * <p>根据条件查询记录，无分页</p>
     *
     * @param map
     * @return
     * @author huj
     * @data 2019年8月20日
     */
    @Override
    public List<TevglBookMajor> selectListByMap(Map<String, Object> map) {
        Query query = new Query(map);
        return tevglBookMajorMapper.selectListByMap(query);
    }

    /**
     * <p>职业课程路径列表</p>
     *
     * @param map
     * @return
     * @author huj
     * @data 2019年7月11日
     */
    @Override
    public List<Map<String, Object>> selectListMapByMapForWeb(Map<String, Object> map) {
        String type = (String)map.get("type");
        map.put("sidx", "hot");
        map.put("order", "desc,sort_num asc");
        map.put("state", "Y");
        List<Map<String, Object>> majorList = tevglBookMajorMapper.selectListMapByMapForWeb(map);
        // 获取所有有效的课堂
        if (StrUtils.isNotEmpty(type) && "countClassroomNum".equals(type)) {
            map.clear();
            map.put("state", "Y");
            List<Map<String, Object>> classroomList = tevglTchClassroomMapper.selectListMapForCommon(map);
            majorList.stream().forEach(major -> {
                // 计算此专业下的课堂数量
                long classroomNum = classroomList.stream().filter(a -> a.get("majorId").equals(major.get("majorId"))).count();
                major.put("classroomNum", classroomNum);
            });
        }
        return majorList;
    }
}
