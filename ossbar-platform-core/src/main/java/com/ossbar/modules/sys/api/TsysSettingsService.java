package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysSettings;

import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2019-05-13 10:19
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysSettingsService extends IBaseService<TsysSettings> {

    /**
     * 查询设置 settingType 系统设置或用户设置（必填） settingUserId 用户设置则必填
     * @param map
     * @return
     */
    R querySetting(Map<String, Object> map);

    /**
     * 批量修改
     *
     * @param settings
     * @return
     */
    R updateBatchSettings(List<TsysSettings> settings);

    TsysSettings selectObjectById(String id);

    List<TsysSettings> selectListByMap(Map<String, Object> map);
}
