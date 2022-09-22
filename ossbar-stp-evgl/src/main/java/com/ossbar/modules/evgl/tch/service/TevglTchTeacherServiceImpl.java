package com.ossbar.modules.evgl.tch.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.dto.SaveTeacherDTO;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: 教师管理</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchteacher")
public class TevglTchTeacherServiceImpl implements TevglTchTeacherService {

    @Autowired
    private TevglTchTeacherMapper tevglTchTeacherMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private UploadFileUtils uploadPathUtils;

    @Override
    @SysLog(value="查询列表(返回List<Bean>)")
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<TevglTchTeacher> tevglTchTeacherList = tevglTchTeacherMapper.selectListByMap(query);
        convertUtil.convertOrgId(tevglTchTeacherList, "orgId"); // 转换机构
        convertUtil.convertDict(tevglTchTeacherList, "state", "teacher_state"); // 教师状态(Y有效N无效)
        convertUtil.convertDict(tevglTchTeacherList, "showIndex", "state1"); // 是否推荐到首页(Y是N否)
        tevglTchTeacherList.forEach(a -> {
            a.setTeacherPic(uploadPathUtils.stitchingPath(a.getTeacherPic(), "7"));
        });
        PageUtils pageUtil = new PageUtils(tevglTchTeacherList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 查询列表(返回List<Map<String, Object>)
     * @param map
     * @return R
     */
    @Override
    @SysLog(value="查询列表(返回List<Map<String, Object>)")
    public R queryForMap(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<Map<String, Object>> tevglTchTeacherList = tevglTchTeacherMapper.selectListMapByMap(query);
        PageUtils pageUtil = new PageUtils(tevglTchTeacherList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    @Override
    public R save(TevglTchTeacher tevglTchTeacher) {
        return null;
    }

    @Override
    public R update(TevglTchTeacher tevglTchTeacher) {
        return null;
    }

    @Override
    public R delete(String id) {
        return null;
    }

    @Override
    public R deleteBatch(String[] ids) {
        return null;
    }

    @Override
    public R view(String id) {
        return null;
    }

    @Override
    public TevglTchTeacher selectObjectById(Object id) {
        return tevglTchTeacherMapper.selectObjectById(id);
    }

    @Override
    public TevglTchTeacher selectObjectByTraineeId(Object id) {
        return tevglTchTeacherMapper.selectObjectByTraineeId(id);
    }

    @Override
    public List<TevglTchTeacher> selectListByMap(Map<String, Object> map) {
        Query query = new Query(map);
        return tevglTchTeacherMapper.selectListByMap(query);
    }

    /**
     * 管理端新增教师
     *
     * @param dto
     * @return
     */
    @Override
    public R saveTeacherInfo(SaveTeacherDTO dto) {
        return null;
    }

    /**
     * 管理端修改教师
     *
     * @param dto
     * @return
     */
    @Override
    public R updateTeacherInfo(SaveTeacherDTO dto) {
        return null;
    }

    @Override
    public R updateStateOrShowIndex(TevglTchTeacher tevglTchTeacher) {
        return null;
    }

    /**
     * 根据条件查询记录，返回List<T>，关联学员表
     *
     * @param map
     * @return
     */
    @Override
    public List<TevglTchTeacher> selectListByMapInnerJoinTraineeTable(Map<String, Object> map) {
        map.put("state", "Y");
        map.put("sidx", "t1.create_time");
        map.put("order", "desc");
        Query query = new Query(map);
        List<TevglTchTeacher> list = tevglTchTeacherMapper.selectListByMapInnerJoinTraineeTable(query);
        list.stream().forEach(a -> {
            a.setTeacherPic(uploadPathUtils.stitchingPath(a.getTeacherPic(), "7"));
        });
        return list;
    }
}
