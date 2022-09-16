package com.ossbar.modules.evgl.pkg.service;

import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.pkg.api.TevglPkgInfoService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
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
    private ConvertUtil convertUtil;
    @Autowired
    private UploadFileUtils uploadFileUtils;

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
            a.put("pkgLogo", uploadFileUtils.stitchingPath(a.get("pkgLogo"), "12"));
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
                pkgInfo.put("pkgLogo", uploadFileUtils.stitchingPath(pkgInfo.get("pic"), "14"));
            } else {
                pkgInfo.put("pkgLogo", uploadFileUtils.stitchingPath(pkgInfo.get("pic"), "12"));
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
}
