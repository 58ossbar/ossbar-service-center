package com.ossbar.modules.evgl.tch.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.api.TevglTchClassService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClass;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
public class TevglTchClassServiceImpl implements TevglTchClassService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TevglTchClassServiceImpl.class);
    @Autowired
    private TevglTchClassMapper tevglTchClassMapper;
    @Autowired
    private ConvertUtil convertUtil;
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
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<TevglTchClass> tevglTchClassList = tevglTchClassMapper.selectListByMap(query);
        convertUtil.convertOrgId(tevglTchClassList, "orgId");
        convertUtil.convertUserId2RealName(tevglTchClassList, "createUserId", "updateUserId");
        convertUtil.convertUserId2RealName(tevglTchClassList, "createUserId", "updateUserId");
        convertUtil.convertDict(tevglTchClassList, "classState", "class_state");
        convertUtil.convertDict(tevglTchClassList, "type", "class_type");
        handleDatas(tevglTchClassList);
        PageUtils pageUtil = new PageUtils(tevglTchClassList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }


    private void handleDatas(List<TevglTchClass> tevglTchClassList) {
        if (tevglTchClassList == null || tevglTchClassList.size() == 0) {
            return;
        }
        tevglTchClassList.forEach(a -> {
            // 处理班级图片
            handleClassPic(a);
            // 处理教师头像
            if (StrUtils.isNotEmpty(a.getTeacherPic())) {
                a.setTeacherPic(uploadPathUtils.stitchingPath(a.getTeacherPic(), "7"));
            }
            // 处理助教头像
            if (StrUtils.isNotEmpty(a.getTeachingAssistantPic())) {
                a.setTeachingAssistantPic(uploadPathUtils.stitchingPath(a.getTeachingAssistantPic(), "7"));
            }
            // 去掉日期间的杠
            a.setRegistrationStartTime(getYearMonthDay(a.getRegistrationStartTime()));
            a.setExpectTime(getYearMonthDay(a.getExpectTime()));
        });
    }

    private String getYearMonthDay(String sourceStr) {
        if (StrUtils.isEmpty(sourceStr)) {
            return sourceStr;
        }
        String yearMonthDay = "";
        String[] split = sourceStr.split("-");
        if (split != null) {
            for (int i = 0; i < split.length; i++) {
                yearMonthDay += split[i];
            }
        }
        return yearMonthDay;
    }

    private void handleClassPic(TevglTchClass a) {
        if (a == null) {
            return;
        }
        // 如果自定义上传了
        if (StrUtils.isNotEmpty(a.getClassImg())) {
            a.setClassPic(uploadPathUtils.stitchingPath(a.getClassImg(), "13"));
            a.setClassImg(uploadPathUtils.stitchingPath(a.getClassImg(), "13"));
        } else {
            // 否则使用的是默认图片
            if (StrUtils.isNotEmpty(a.getClassPic())) {
                // 如果不是网络头像,则拼接地址
                if (a.getClassPic().indexOf("https") == -1 && a.getClassPic().indexOf("http") == -1) {
                    // 如果没有uploads才拼接
                    if (a.getClassPic().indexOf("uploads/dict") == -1) {
                        String val = creatorblueFieAccessPath + "/dict/" + a.getClassPic();
                        a.setClassPic(val);
                    }
                }
            }
        }
    }


    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    public R queryForMap(Map<String, Object> map) {
        return null;
    }

    /**
     * 新增
     *
     * @param tevglTchClass
     * @return
     */
    @Override
    public R save(TevglTchClass tevglTchClass) {
        return null;
    }

    /**
     * 修改
     *
     * @param tevglTchClass
     * @return
     */
    @Override
    public R update(TevglTchClass tevglTchClass) {
        return null;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public R delete(String id) {
        return null;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public R deleteBatch(String[] ids) {
        return null;
    }

    /**
     * 查看明细
     *
     * @param id
     * @return
     */
    @Override
    public R view(String id) {
        return null;
    }


    /**
     * 查询班级列表
     *
     * @param params
     * @param loginUserId
     * @return
     */
    @Override
    public R queryClassListData(Map<String, Object> params, String loginUserId) {
        if (StrUtils.isEmpty(loginUserId)) {
            return R.error("必传参数为空");
        }
        if (StrUtils.isNull(params.get("majorId"))) {
            return R.ok().put(Constant.R_DATA, new ArrayList<>());
        }
        params.put("classState", "3"); // 只取3授课状态的班级
        List<Map<String,Object>> list = tevglTchClassMapper.selectSimpleListMap(params);
        list.stream().forEach(item -> {
            String title = "";
            if (!StrUtils.isNull(item.get("orgParentName"))) {
                title += item.get("orgParentName");
            }
            if (!StrUtils.isNull(item.get("orgName"))) {
                title += " " + item.get("orgName");
            }
            title += " " + item.get("className");
            item.put("title", title);
        });
        return R.ok().put(Constant.R_DATA, list);
    }

}
