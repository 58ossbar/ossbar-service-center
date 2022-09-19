package com.ossbar.modules.evgl.pkg.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;

import java.util.List;
import java.util.Map;

/**
 * 教学包
 * @author huj
 * @create 2022-09-16 9:22
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TevglPkgInfoService extends IBaseService<TevglPkgInfo> {


    /**
     * 保存教学包信息
     * @param tevglPkgInfo
     * @param loginUserId
     * @return
     */
    R saveInfo(TevglPkgInfo tevglPkgInfo, String loginUserId);

    /**
     * 修改教学包信息
     * @param tevglPkgInfo
     * @param loginUserId
     * @return
     */
    R updateInfo(TevglPkgInfo tevglPkgInfo, String loginUserId);

    /**
     * 根据条件查询课程
     * @param params
     * @return
     */
    List<Map<String, Object>> selectSubjectRefList(Map<String, Object> params);

    /**
     * 我的教学包列表
     * @param params
     * @param loginUserId 当前登录用户
     * @param together 为Y时查询自己创建的教学包和共建中的教学包
     * @return
     */
    R listMyPkgInfo(Map<String, Object> params, String loginUserId, String together);

    /**
     * 修改教学包时，查询教学包信息（专用）
     * @param pkgId
     * @param loginUserId
     * @return
     */
    R viewPkgInfoForUpdate(String pkgId, String loginUserId);

    /**
     * 查看教学包基本信息
     * @param pkgId
     * @param loginUserId
     * @return
     */
    R viewPkgBaseInfo(String pkgId, String loginUserId);

    /**
     * 获取当前教学包的直系父教学包下的直系子教学包们（即历史版本）
     * @param pkgId
     * @return
     */
    R getHistoryPkgList(String pkgId);

    /**
     * 【教学包下拉列表】注意：会一次性查询自己的，衍生版本，以及别人创建的免费的，以及被授权的
     * @param params
     * @param loginUserId
     * @return
     */
    R queryPkgListByUnionAllForSelect(Map<String, Object> params, String loginUserId);
}
