package com.ossbar.modules.evgl.web.controller.threefeetplatform.pkg;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.pkg.api.TevglPkgInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的教学包
 * @author huj
 * @create 2022-09-16 9:20
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/pkginfo-api")
public class PkgController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Reference(version = "1.0.0")
    private TevglPkgInfoService tevglPkgInfoService;

    /**
     * 获取筛选条件
     * @return
     */
    @RequestMapping("/getFilterTypeList")
    public R getFilterTypeList () {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> v0 = new HashMap<String, Object>();
        v0.put("label", "全部状态");
        v0.put("value", "");
        Map<String, Object> v1 = new HashMap<String, Object>();
        v1.put("label", "自建");
        v1.put("value", "ower");
        Map<String, Object> v2 = new HashMap<String, Object>();
        v2.put("label", "共建");
        v2.put("value", "together");
        Map<String, Object> v3 = new HashMap<String, Object>();
        v3.put("label", "授权");
        v3.put("value", "auth");
        Map<String, Object> v4 = new HashMap<String, Object>();
        v4.put("label", "其它");
        v4.put("value", "other");
        list.add(v0);
        list.add(v1);
        list.add(v2);
        list.add(v3);
        //list.add(v4);
        return R.ok().put(Constant.R_DATA, list);
    }

    /**
     * 我的教学包列表
     * @param request
     * @param params
     * @return
     */
    @GetMapping("/listMyPkgInfo")
    @CheckSession
    public R listMyPkgInfo(HttpServletRequest request, @RequestParam Map<String, Object> params) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        // 不展示该值为3的，可见性(来源字典:1私有or2公有3都不可见)
        params.put("displayNo", "3");
        String together = null;
        if (StrUtils.isNull(params.get("releaseStatus")) || "F".equals(params.get("releaseStatus"))) {
            together = "Y";
        }
        if (StrUtils.isNull(params.get("releaseStatus"))) {
            params.put("releaseStatus", "N");
        }
        params.put("createUserId", traineeInfo.getTraineeId());
        return tevglPkgInfoService.listMyPkgInfo(params, traineeInfo.getTraineeId(), together);
    }

    /**
     * 根据条件搜索课程
     * @param request
     * @param majorId
     * @return
     */
    @PostMapping("/selectSubjectRefList")
    @CheckSession
    public R selectSubjectRefList(HttpServletRequest request, String majorId) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("majorId", majorId);
        map.put("together", "Y");
        map.put("createUserId", traineeInfo.getTraineeId());
        map.put("loginUserId", traineeInfo.getTraineeId());
        map.put("state", "Y");
        return R.ok().put(Constant.R_DATA, tevglPkgInfoService.selectSubjectRefList(map));
    }
}
