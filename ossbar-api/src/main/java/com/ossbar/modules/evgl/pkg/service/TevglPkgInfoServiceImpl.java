package com.ossbar.modules.evgl.pkg.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.PkgPermissionUtils;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubperiodMapper;
import com.ossbar.modules.evgl.pkg.api.TevglPkgInfoService;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeam;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.persistence.TevglBookpkgTeamMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huj
 * @create 2022-09-16 9:22
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
public class TevglPkgInfoServiceImpl implements TevglPkgInfoService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TevglPkgInfoServiceImpl.class);

    @Autowired
    private TevglPkgInfoMapper tevglPkgInfoMapper;
    @Autowired
    private TevglTraineeInfoMapper tevglTraineeInfoMapper;
    @Autowired
    private TevglBookpkgTeamMapper tevglBookpkgTeamMapper;
    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;
    @Autowired
    private TevglBookSubperiodMapper tevglBookSubperiodMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private UploadFileUtils uploadPathUtils;
    @Autowired
    private PkgPermissionUtils pkgPermissionUtils;

    /**
     * 根据条件分页查询记录
     *
     * @param map
     * @return
     */
    @Override
    @SysLog(value = "分页查询列表(返回List<Bean>)")
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TevglPkgInfo> tevglPkgInfoList = tevglPkgInfoMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tevglPkgInfoList, "createUserId", "updateUserId");
        convertUtil.convertOrgId(tevglPkgInfoList, "orgId");
        // 使用限制(来源字典：授权，购买，免费)
        convertUtil.convertDict(tevglPkgInfoList, "pkgLimit", "pkgLimit");
        PageUtils pageUtil = new PageUtils(tevglPkgInfoList, query.getPage(), query.getLimit());
        // 取部分属性
        List<Map<String, Object>> list = tevglPkgInfoList.stream().map(this::converToSimpleMapInfo).collect(Collectors.toList());
        pageUtil.setList(list);
        return R.ok().put(Constant.R_DATA, pageUtil);
    }


    /**
     * 取部分属性
     *
     * @param tevglPkgInfo
     * @return
     */
    private Map<String, Object> converToSimpleMapInfo(TevglPkgInfo tevglPkgInfo) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("pkgId", tevglPkgInfo.getPkgId());
        info.put("orgId", tevglPkgInfo.getOrgId());
        info.put("pkgName", tevglPkgInfo.getPkgName());
        info.put("pkgNo", tevglPkgInfo.getPkgNo());
        info.put("subjectId", tevglPkgInfo.getSubjectId());
        info.put("pkgKey", tevglPkgInfo.getPkgKey());
        info.put("pkgDesc", tevglPkgInfo.getPkgDesc());
        info.put("pkgLevel", tevglPkgInfo.getPkgLevel());
        info.put("pkgLimit", tevglPkgInfo.getPkgLimit());
        info.put("deployMainType", tevglPkgInfo.getDeployMainType());
        info.put("pkgRefCount", tevglPkgInfo.getPkgRefCount());
        info.put("pkgResCount", tevglPkgInfo.getPkgResCount());
        info.put("pkgActCount", tevglPkgInfo.getPkgActCount());
        // 图片处理
        String logo = tevglPkgInfo.getPkgLogo();
        /*if (StrUtils.isNotEmpty(logo)) {
            // 如果未转换，则需要拼接路径
            if (logo.indexOf("/uploads") == -1) {
                logo = creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("12") + "/" + logo;
            }
        } else {
            // 可以设置一个默认的
            logo = creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("12") + "/" + "default_pkg_logo.png";
        }*/
        info.put("pkgLogo", logo);
        return info;
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
     * @param tevglPkgInfo
     * @return
     */
    @Override
    public R save(TevglPkgInfo tevglPkgInfo) {
        return null;
    }

    /**
     * 修改
     *
     * @param tevglPkgInfo
     * @return
     */
    @Override
    public R update(TevglPkgInfo tevglPkgInfo) {
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
     * 保存教学包信息
     *
     * @param tevglPkgInfo
     * @param loginUserId
     * @return
     */
    @Override
    public R saveInfo(TevglPkgInfo tevglPkgInfo, String loginUserId) {
        return null;
    }

    /**
     * 修改教学包信息
     *
     * @param tevglPkgInfo
     * @param loginUserId
     * @return
     */
    @Override
    public R updateInfo(TevglPkgInfo tevglPkgInfo, String loginUserId) {
        return null;
    }

    /**
     * 根据条件查询课程
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> selectSubjectRefList(Map<String, Object> params) {
        return tevglPkgInfoMapper.selectSubjectRefList(params);
    }

    /**
     * 我的教学包列表
     *
     * @param params
     * @param loginUserId 当前登录用户
     * @param together    为Y时查询自己创建的教学包和共建中的教学包
     * @return
     */
    @Override
    public R listMyPkgInfo(Map<String, Object> params, String loginUserId, String together) {
        if (StrUtils.isEmpty(loginUserId)) {
            return R.error("参数loginUserId为空");
        }
        if (!StrUtils.isNull(params.get("type")) && "other".equals(params.get("type"))) {
            return listMyPkgInfoForOther(params, loginUserId);
        }
        // [重点♥]不展示该值为3的，可见性(来源字典:1私有or2公有3都不可见)
        //params.put("displayNo", "3");
        // 我的教学包
        params.put("state", "Y");
        params.put("createUserId", loginUserId);
        params.put("loginUserId", loginUserId);
        together = StrUtils.isEmpty(together) ? null : together;
        params.put("together", together);
        log.debug("查询条件：" + params);
        // 构建查询条件对象Query
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<Map<String, Object>> tevglPkgInfoList = tevglPkgInfoMapper.selectListMapByMapUnionAll(query);
        //List<Map<String, Object>> tevglPkgInfoList = tevglPkgInfoMapper.selectListMapByMap(query);
        //convertUtil.convertDict(tevglPkgInfoList, "pkgLimit", "pkgLimit"); // 使用限制(来源字典：授权，购买，免费)
        //convertUtil.convertDict(tevglPkgInfoList, "pkgLevel", "pkgLevel"); // 适用层次(来源字典：本科，高职，中职)
        //convertUtil.convertDict(tevglPkgInfoList, "deployMainType", "deployMainType"); // 发布方大类(来源字典：学校，个人，创蓝)
        // 查询衍生出的版本
        List<Object> pkgIds = tevglPkgInfoList.stream()
                .filter(tevglPkgInfo -> tevglPkgInfo.get("createUserId").equals(loginUserId) || loginUserId.equals(tevglPkgInfo.get("receiverUserId")))
                .map(tevglPkgInfo -> tevglPkgInfo.get("pkgId"))
                .collect(Collectors.toList());
        List<Map<String, Object>> deriveList = new ArrayList<Map<String,Object>>();
        if (pkgIds != null && pkgIds.size() > 0) {
            params.clear();
            params.put("refPkgIds", pkgIds);
            params.put("releaseStatus", "Y");
            params.put("sidx", "t1.create_time");
            params.put("order", "desc");
            List<Map<String,Object>> selectSimpleList = tevglPkgInfoMapper.selectListByMapForSimple(params);
            selectSimpleList.stream().forEach(info -> {
                info.put("isCreator", true);
                // 如果有人使用过就不再允许删除
                info.put("hasDeleteIcon", false);
                List<Map<String, Object>> list = tevglPkgInfoMapper.selectHowManyPeopleUseIt(info.get("pkgId"));
                if (list == null || list.size() == 0) {
                    info.put("hasDeleteIcon", true);
                }
                info.put("howManyPeopleUseIt", list.size());
            });
            deriveList.addAll(selectSimpleList);
        }
        tevglPkgInfoList.stream().forEach(a -> {
            // 当前源教学包的发布出去的衍生版本
            List<Map<String, Object>> children = deriveList.stream().filter(pp -> !StrUtils.isNull("refPkgId") && pp.get("refPkgId").equals(a.get("pkgId")) && !pp.get("pkgId").equals(a.get("pkgId"))).collect(Collectors.toList());
            a.put("children", children);
            // 当前有多少人正在使用这个教学包
            List<Map<String, Object>> list = tevglPkgInfoMapper.selectHowManyPeopleUseIt(a.get("pkgId"));
            log.debug("当前有多少人正在使用这个教学包：" + list.size());
            a.put("howManyPeopleUseIt", list.size());
            // 图片处理
            a.put("pkgLogo", uploadPathUtils.stitchingPath(a.get("pkgLogo"), "12"));
            // 是否为创建者
            handleTags(loginUserId, a, params, list);
        });
        PageUtils pageUtil = new PageUtils(tevglPkgInfoList, query.getPage(), query.getLimit());
        /*TevglSiteSysMsgQuery queryParameters = new TevglSiteSysMsgQuery();
        queryParameters.setMsgType(SiteSysMsgTypeEnum.MST_TYPE_110_PKG_TRANSFER.getCode());
        queryParameters.setReadState(CommonEnum.STATE_NO.getCode());
        queryParameters.setToTraineeId(loginUserId);
        List<TevglSiteSysMsgVo> msgList = tevglSiteSysMsgMapper.findMsgList(queryParameters);
        if (msgList != null && !msgList.isEmpty()) {
            tevglSiteSysMsgMapper.batchUpdateRead(msgList.stream().map(a -> a.getMsgId()).collect(Collectors.toList()));
        }*/
        return R.ok().put(Constant.R_DATA, pageUtil).put("msgList", new ArrayList<>());
    }

    private R listMyPkgInfoForOther(@RequestParam Map<String, Object> params, String loginUserId) {
        params.put("display", "3");
        params.put("createUserId", loginUserId);
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        if (StrUtils.isNull(params.get("type")) || !"other".equals(params.get("type"))) {
            PageUtils pageUtil = new PageUtils(new ArrayList<>(), query.getPage(), query.getLimit());
            return R.ok().put(Constant.R_DATA, pageUtil);
        }
        List<Map<String,Object>> tevglPkgInfoList = tevglPkgInfoMapper.selectListMapByMapFromCtRoom(query);
        if (tevglPkgInfoList == null || tevglPkgInfoList.size() == 0) {
            PageUtils pageUtil = new PageUtils(tevglPkgInfoList, query.getPage(), query.getLimit());
            return R.ok().put(Constant.R_DATA, pageUtil);
        }
        // 查出这些教学包的衍生版本
        List<Object> pkgIdList = tevglPkgInfoList.stream().map(a -> a.get("pkgId")).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("refPkgIds", pkgIdList);
        List<Map<String, Object>> selectSimpleList = tevglPkgInfoMapper.selectSimpleList(map);
        tevglPkgInfoList.stream().forEach(pkgInfo -> {
            if (StrUtils.isNull(pkgInfo.get("pkgLogo"))) {
                pkgInfo.put("pkgLogo", uploadPathUtils.stitchingPath(pkgInfo.get("pic"), "14"));
            } else {
                pkgInfo.put("pkgLogo", uploadPathUtils.stitchingPath(pkgInfo.get("pic"), "12"));
            }
            // 取出源教学包的发布出去的衍生版本
            List<Map<String, Object>> children = selectSimpleList.stream().filter(a -> a.get("refPkgId").equals(pkgInfo.get("pkgId"))).collect(Collectors.toList());
            children.stream().forEach(childrenPkgInfo -> {
                if (loginUserId.equals(childrenPkgInfo.get("createUserId"))) {
                    childrenPkgInfo.put("isCreator", true);
                }
                params.clear();
                params.put("refPkgId", childrenPkgInfo.get("pkgId"));
                List<Map<String,Object>> simpleList = tevglPkgInfoMapper.selectSimpleList(params);
                if (simpleList != null && simpleList.size() > 0) {
                    childrenPkgInfo.put("hasDeleteIcon", false);
                } else {
                    childrenPkgInfo.put("hasDeleteIcon", true);
                }
            });
            pkgInfo.put("children", children);
        });
        PageUtils pageUtil = new PageUtils(tevglPkgInfoList, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 处理
     * @param loginUserId 当前登录用户
     * @param pkgInfo 教学包信息
     * @param params 查询条件
     * @param list 当前有多少人使用了这个教学包
     */
    private void handleTags(String loginUserId, Map<String, Object> pkgInfo, Map<String, Object> params, List<Map<String, Object>> list) {
        if (StrUtils.isNull(pkgInfo) || StrUtils.isNull(pkgInfo.get("createUserId"))) {
            pkgInfo.put("isTogetherBuild", false);
            pkgInfo.put("isCreator", false);
            pkgInfo.put("hasEditPermission", false);
            return;
        }
        // 是否有删除的小图标
        String type = pkgInfo.get("type").toString();
        switch (type) {
            // 自建的情况
            case "ower":
                pkgInfo.put("isCreator", true); // 是否为创建者
                pkgInfo.put("isTogetherBuild", false); // 是否为共建者
                pkgInfo.put("hasEditPermission", true); // 是否能修改基本信息
                params.clear();
                params.put("refPkgId", pkgInfo.get("pkgId"));
                List<Map<String,Object>> simpleList = tevglPkgInfoMapper.selectSimpleList(params);
                if (simpleList != null && simpleList.size() > 0) {
                    pkgInfo.put("hasDeleteIcon", false);
                } else {
                    pkgInfo.put("hasDeleteIcon", true);
                }
                // 但是
                if (!StrUtils.isNull(pkgInfo.get("display")) && "3".equals(pkgInfo.get("display"))) {
                    pkgInfo.put("hasDeleteIcon", false);
                }
                break;
            // 移交接管的情况
            case "receiver" :
                pkgInfo.put("isCreator", false); // 是否为创建者
                pkgInfo.put("isTogetherBuild", false); // 是否为共建者
                pkgInfo.put("hasEditPermission", true); // 是否能修改基本信息
                params.clear();
                params.put("refPkgId", pkgInfo.get("pkgId"));
                List<Map<String,Object>> selectSimpleList = tevglPkgInfoMapper.selectSimpleList(params);
                if (selectSimpleList != null && selectSimpleList.size() > 0) {
                    pkgInfo.put("hasDeleteIcon", false);
                } else {
                    pkgInfo.put("hasDeleteIcon", true);
                }
                // 但是
                if (!StrUtils.isNull(pkgInfo.get("display")) && "3".equals(pkgInfo.get("display"))) {
                    pkgInfo.put("hasDeleteIcon", false);
                }
                break;
            // 共建的情况下
            case "together":
                pkgInfo.put("hasDeleteIcon", false);
                pkgInfo.put("isCreator", false);
                pkgInfo.put("isTogetherBuild", true);
                pkgInfo.put("hasEditPermission", false);
                break;
            // 授权的情况下
            case "auth":
                pkgInfo.put("hasDeleteIcon", false);
                pkgInfo.put("isCreator", false);
                pkgInfo.put("isTogetherBuild", false);
                pkgInfo.put("hasEditPermission", false);
                break;
            default:
                break;
        }
    }

    /**
     * 修改教学包时，查询教学包信息（专用）
     *
     * @param pkgId
     * @param loginUserId
     * @return
     */
    @Override
    public R viewPkgInfoForUpdate(String pkgId, String loginUserId) {
        Map<String, Object> pkgInfo = tevglPkgInfoMapper.selectObjectMapById(pkgId);
        if (pkgInfo != null) {
            pkgInfo.put("subjectIdActual", pkgInfo.get("subjectId"));
            pkgInfo.put("subjectId", pkgInfo.get("subjectRef")); // 用于回显
            pkgInfo.put("pkgLogo", uploadPathUtils.stitchingPath(pkgInfo.get("pkgLogo"), "12"));
        }
        return R.ok().put(Constant.R_DATA, pkgInfo);
    }

    /**
     * 查看教学包基本信息
     *
     * @param pkgId
     * @param loginUserId
     * @return
     */
    @Override
    public R viewPkgBaseInfo(String pkgId, String loginUserId) {
        if (StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(loginUserId)) {
            return R.error("必传参数为空");
        }
        Map<String, Object> pkgInfo = tevglPkgInfoMapper.selectObjectMapById(pkgId);
        if (pkgInfo == null) {
            return R.error("无效的记录");
        }
        if (!"Y".equals((String)pkgInfo.get("state"))) {
            return R.error("此教学包状态已无效，无法查看");
        }
        // 如果是已经发布了的教学包,不管是谁都没有权限
        if ("Y".equals(pkgInfo.get("releaseStatus"))) {
            pkgInfo.put("hasPermissionActual", false);
        } else {
            pkgInfo.put("hasPermissionActual", true);
        }
        // 字典转换
        pkgInfo.put("pkgLimitName", pkgInfo.get("pkgLimit"));
        convertUtil.convertDict(pkgInfo, "pkgLimitName", "pkgLimit"); // 使用限制(来源字典：授权，免费)
        convertUtil.convertDict(pkgInfo, "pkgLevel", "pkgLevel"); // 适用层次(来源字典：本科，高职，中职)
        convertUtil.convertDict(pkgInfo, "deployMainType", "deployMainType"); // 发布方大类(来源字典：学校，个人，创蓝)
        convertUtil.convertDict(pkgInfo, "deploySubType", "deploySubType"); // 发布方小类
        // 图片处理
        Object logo = pkgInfo.get("pkgLogo");
        pkgInfo.put("pkgLogo", uploadPathUtils.stitchingPath(logo, "12"));
        // 是否为创建者或共建者、处理文字显示
        String createUserId = (String)pkgInfo.get("createUserId");
        handleTextShow(pkgInfo, createUserId, loginUserId);
        // 创建者信息(理解为主编)
        pkgInfo.put("createUserInfo", getUserInfo(createUserId));
        // 副主编信息
        String traineeId = getSubeditorTraineeId(pkgId);
        pkgInfo.put("subeditorInfo", getUserInfo(traineeId));
        // 更新查阅数
        TevglPkgInfo pkg = new TevglPkgInfo();
        pkg.setPkgId(pkgId);
        pkg.setViewNum(1);
        tevglPkgInfoMapper.plusNum(pkg);
        // 当前教学包最新的衍生版本
        if ("N".equals(pkgInfo.get("releaseStatus"))) {
            Map<String, Object> params = new HashMap<>();
            params.put("refPkgId", pkgId);
            params.put("sidx", "create_time");
            params.put("order", "desc");
            List<TevglPkgInfo> list = tevglPkgInfoMapper.selectListByMap(params);
            if (list != null && list.size() > 0) {
                String pkgLogo = list.get(0).getPkgLogo();
                pkgInfo.put("prePkgLogo", uploadPathUtils.stitchingPath(pkgLogo, "12"));
            }
        }
        // 返回数据
        return R.ok().put(Constant.R_DATA, pkgInfo);
    }


    /**
     * 获取这个教学包的创建者（主编）或者副主编信息
     * @param traineeId
     * @return
     */
    private Map<String, Object> getUserInfo(String traineeId) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("traineeId", "");
        info.put("traineePic", "/uploads/defaulthead.png");
        info.put("subeditorPic", "/uploads/defaulthead.png");
        info.put("traineeName", "");
        if (StrUtils.isNotEmpty(traineeId)) {
            TevglTraineeInfo traineeInfo = tevglTraineeInfoMapper.selectObjectById(traineeId);
            if (traineeInfo != null) {
                info.put("traineeId", traineeInfo.getTraineeId());
                String traineePic = StrUtils.isEmpty(traineeInfo.getTraineePic()) ? traineeInfo.getTraineeHead() : traineeInfo.getTraineePic();
                info.put("traineePic", uploadPathUtils.stitchingPath(traineePic, "16"));
                info.put("subeditorPic", uploadPathUtils.stitchingPath(traineePic, "16"));
                String traineeName = StrUtils.isEmpty(traineeInfo.getTraineeName()) ? traineeInfo.getNickName() : traineeInfo.getTraineeName();
                info.put("traineeName", traineeName);
            }
        }
        return info;
    }

    /**
     * 获取这个教学包的副主编（学员主键）
     * @param pkgId
     * @return
     */
    private String getSubeditorTraineeId(String pkgId) {
        Map<String, Object> map = new HashMap<>();
        map.put("pkgId", pkgId);
        List<TevglBookpkgTeam> list = tevglBookpkgTeamMapper.selectListByMap(map);
        if (list != null && list.size() > 0) {
            List<TevglBookpkgTeam> collect = list.stream().filter(a -> StrUtils.isNotEmpty(a.getIsSubeditor()) && a.getIsSubeditor().equals("Y")).collect(Collectors.toList());
            if (collect.size() > 0) {
                return collect.get(0).getUserId();
            }
        }
        return null;
    }

    /**
     * 处理文字
     * @param info 教学包
     * @param createUserId 教学包的创建者
     * @param loginUserId 当前登录用户
     * @apiNote
     * <br>如果未登录，则右上角的文字，只需要字典转换
     * <br>如果登录了，且登录用户为此教学包创建者，则显示【拥有者】
     * <br>如果登录了，但不是此教学包的创建者，则去判断教学包创建者是否授权了当前登录用户，如果有权限，则显示【已授权】，否则显示字典转换后的就行了
     */
    private void handleTextShow(Map<String, Object> info, String createUserId, String loginUserId) {
        if (StrUtils.isEmpty(loginUserId)) {
            // 前面已经字典转换了无需处理
            info.put("isCreator", false);
            info.put("isTogetherBuild", false);
        } else {
            if (loginUserId.equals(createUserId)) {
                info.put("pkgLimitName", "拥有者");
                info.put("isCreator", true);
                info.put("isTogetherBuild", false);
            } else {
                String pkgId = (String) info.get("pkgId");
                boolean flag = pkgPermissionUtils.hasPermission(pkgId, loginUserId, createUserId);
                if (flag) {
                    info.put("pkgLimitName", "已授权");
                    info.put("isCreator", false);
                    info.put("isTogetherBuild", true);
                }
                boolean isReceiver = !StrUtils.isNull(info.get("receiverUserId")) && loginUserId.equals(info.get("receiverUserId"));
                if (isReceiver) {
                    info.put("pkgLimitName", "接管");
                }
            }
        }
    }


    /**
     * 获取当前教学包的直系父教学包下的直系子教学包们（即历史版本）
     *
     * @param pkgId
     * @return
     */
    @Override
    public R getHistoryPkgList(String pkgId) {
        if (StrUtils.isEmpty(pkgId)) {
            return R.error("必传参数为空");
        }
        TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
        if (tevglPkgInfo == null) {
            return R.error("数据获取失败");
        }
        Map<String, Object> params = new HashMap<>();
        String refPkgId = "";
        boolean ifOrigin = StrUtils.isEmpty(tevglPkgInfo.getRefPkgId());
        if (ifOrigin) {
            refPkgId = tevglPkgInfo.getPkgId();
        } else {
            refPkgId = tevglPkgInfo.getRefPkgId();
        }
        // 可能会是最初的源教学包
        params.put("refPkgId", refPkgId);
        params.put("sidx", "t1.create_time");
        params.put("order", "desc");
        List<Map<String,Object>> list = tevglPkgInfoMapper.selectListByMapForSimple(params);
        if (ifOrigin) {
            Map<String, Object> data = new HashMap<>();
            data.put("pkgId", tevglPkgInfo.getPkgId());
            data.put("pkgName", tevglPkgInfo.getPkgName());
            data.put("pkgVersion", tevglPkgInfo.getPkgVersion());
            data.put("createTime", tevglPkgInfo.getCreateTime());
            list.add(list.size(), data);
        }
        return R.ok().put(Constant.R_DATA, list);
    }

    /**
     * 【教学包下拉列表】注意：会一次性查询自己的，衍生版本，以及别人创建的免费的，以及被授权的
     *
     * @param params
     * @param loginUserId
     * @return
     */
    @Override
    public R queryPkgListByUnionAllForSelect(Map<String, Object> params, String loginUserId) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        params.put("loginUserId", loginUserId);
        if (params.get("subjectRef") != null) {
            List<String> majorIdList = tevglBookSubperiodMapper.findMajorIdListBySubjectId(params.get("subjectRef").toString());
            if (majorIdList != null && !majorIdList.isEmpty()) {
                params.put("majorIdList", majorIdList);
            }
        }
        result = tevglPkgInfoMapper.queryPkgListByUnionAllForSelect(params);
        // 用于修改课堂时的教学包回显
        if (!StrUtils.isNull(params.get("ctId"))) {
            log.debug("查询课堂对应的教学包");
            TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(params.get("ctId"));
            if (classroom != null) {
                TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(classroom.getPkgId());
                if (tevglPkgInfo != null) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("pkgId", tevglPkgInfo.getPkgId());
                    data.put("pkgName", tevglPkgInfo.getPkgName());
                    data.put("pkgLogo", tevglPkgInfo.getPkgLogo());
                    data.put("pkgVersion", tevglPkgInfo.getPkgVersion());
                    data.put("createUserId", tevglPkgInfo.getCreateUserId());
                    result.add(data);
                }
            }
        }
        // 部分数据处理
        result.stream().forEach(pkgInfo -> {
            // 教学包封面处理
            pkgInfo.put("pkgLogo", uploadPathUtils.stitchingPath(pkgInfo.get("pkgLogo"), "12"));
            // 返回标签名表示[授权][自建]
            handlePkgName(pkgInfo, loginUserId);
        });
        return R.ok().put(Constant.R_DATA, result);
    }

    /**
     * 处理名称
     * @param pkgInfo
     * @param loginUserId
     */
    private void handlePkgName(Map<String, Object> pkgInfo, String loginUserId) {
        if (pkgInfo == null) {
            return;
        }
        String tempName = pkgInfo.get("pkgName").toString();
        // 版本号
        String pkgVersion = StrUtils.isNull(pkgInfo.get("pkgVersion")) ? "" : pkgInfo.get("pkgVersion").toString();
        // 使用限制1授权2免费
        String pkgLimit = StrUtils.isNull(pkgInfo.get("pkgLimit")) ? "0" : pkgInfo.get("pkgLimit").toString();
        String pkgLimitTagName = "";
        // 返回标签名表示[授权][自建]
        if (pkgInfo.get("createUserId").equals(loginUserId)) {
            pkgInfo.put("tagName", "自建");
        } else {
            switch (pkgLimit) {
                case "1":
                    pkgLimitTagName = "授权";
                    break;
                case "2":
                    pkgLimitTagName = "免费";
                    break;
                default:
                    break;
            }
            pkgInfo.put("tagName", pkgLimitTagName);
        }
        // 拼接
        if (!StrUtils.isNull(pkgInfo.get("pkgVersion"))) {
            tempName = tempName + " ["+pkgVersion+"]";
        }
        if (!loginUserId.equals(pkgInfo.get("createUserId")) && !StrUtils.isNull(pkgInfo.get("createUserName"))) {
            tempName = tempName + "【"+pkgInfo.get("createUserName") +" "+ pkgLimitTagName + "】";
        } else {
			/*String pattern = DateUtils.convertDatePattern(pkgInfo.get("createTime").toString(), "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH:mm");
			tempName += " " + pattern;*/
        }
        pkgInfo.put("pkgName", tempName);
    }

}
