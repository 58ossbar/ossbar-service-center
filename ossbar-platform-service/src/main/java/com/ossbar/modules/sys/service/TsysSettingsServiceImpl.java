package com.ossbar.modules.sys.service;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysSettingsService;
import com.ossbar.modules.sys.domain.TsysSettings;
import com.ossbar.modules.sys.persistence.TsysSettingsMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-08-15 10:24
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/settings")
public class TsysSettingsServiceImpl implements TsysSettingsService {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(TsysSettingsServiceImpl.class);

    @Autowired
    private TsysSettingsMapper tsysSettingsMapper;

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
     * @param tsysSettings
     * @return
     */
    @Override
    public R save(TsysSettings tsysSettings) {
        return null;
    }

    /**
     * 修改
     *
     * @param tsysSettings
     * @return
     */
    @Override
    public R update(TsysSettings tsysSettings) {
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
     * 查询设置 settingType 系统设置或用户设置（必填） settingUserId 用户设置则必填
     *
     * @param map
     * @return
     */
    @Override
    public R querySetting(Map<String, Object> map) {
        if (map.get("settingType") == null || map.get("settingType").equals("")) {
            map.put("settingType", "sys");
        }
        List<TsysSettings> settings = tsysSettingsMapper.selectListByMap(map);
        return R.ok().put("data", settings);
    }

}
