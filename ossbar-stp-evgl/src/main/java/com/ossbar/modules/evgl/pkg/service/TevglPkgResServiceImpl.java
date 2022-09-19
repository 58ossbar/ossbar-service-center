package com.ossbar.modules.evgl.pkg.service;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.pkg.api.TevglPkgResService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgRes;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgResMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Title: 教学包资源</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
public class TevglPkgResServiceImpl implements TevglPkgResService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TevglPkgResServiceImpl.class);
    @Autowired
    private TevglPkgResMapper tevglPkgResMapper;

    /**
     * 新增资源
     *
     * @param tevglPkgRes
     * @param loginUserId
     * @return
     * @author huj
     * @data 2019年8月13日
     */
    @Override
    public R saveResInfo(TevglPkgRes tevglPkgRes, String loginUserId) {
        return null;
    }

    /**
     * 修改资源
     *
     * @param tevglPkgRes
     * @param loginUserId
     * @param pkgId
     * @return
     * @author huj
     * @data 2019年8月13日
     */
    @Override
    public R editResInfo(TevglPkgRes tevglPkgRes, String loginUserId, String pkgId) {
        return null;
    }

    /**
     * 查看资源
     *
     * @param params
     * @return
     * @author huj
     * @data 2019年8月13日
     */
    @Override
    public Map<String, Object> viewResInfo(Map<String, Object> params) {
        String resgroupId = (String) params.get("resgroupId");
        if (StrUtils.isEmpty(resgroupId)) {
            return R.error("参数resgroupId为空");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resId", "");
        result.put("resgroupId", "");
        result.put("resContent", "");
        // 根据章节和分组获取资源
        List<TevglPkgRes> list = tevglPkgResMapper.selectListByMap(params);
        if (list != null && list.size() > 0) {
            TevglPkgRes tevglPkgRes = list.get(0);
            if (tevglPkgRes != null) {
                result.put("resId", tevglPkgRes.getResId());
                result.put("resgroupId", tevglPkgRes.getResgroupId());
                result.put("resContent", tevglPkgRes.getResContent());
            }
        }
        return R.ok().put(Constant.R_DATA, result);
    }

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    public R query(Map<String, Object> map) {
        return null;
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
     * @param tevglPkgRes
     * @return
     */
    @Override
    public R save(TevglPkgRes tevglPkgRes) {
        return null;
    }

    /**
     * 修改
     *
     * @param tevglPkgRes
     * @return
     */
    @Override
    public R update(TevglPkgRes tevglPkgRes) {
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
}
