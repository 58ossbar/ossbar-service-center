package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysSettingsService;
import com.ossbar.modules.sys.domain.TsysSettings;
import com.ossbar.modules.sys.persistence.TsysSettingsMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private ConvertUtil convertUtil;

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    @GetMapping("/query")
    @SysLog(value = "查询列表(返回List<Bean>)")
    @SentinelResource("/sys/settings/query")
    public R query(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TsysSettings> tsysSettingsList = tsysSettingsMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tsysSettingsList, "createUserId", "updateUserId");
        PageUtils pageUtil = new PageUtils(tsysSettingsList, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 根据条件查询记录
     *
     * @param map
     * @return
     */
    @Override
    @GetMapping("/queryForMap")
    @SysLog(value = "查询列表(返回List<Map<String, Object>)")
    @SentinelResource("/sys/settings/queryForMap")
    public R queryForMap(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<Map<String, Object>> tsysSettingsList = tsysSettingsMapper.selectListMapByMap(query);
        convertUtil.convertUserId2RealName(tsysSettingsList, "create_user_id", "update_user_id");
        PageUtils pageUtil = new PageUtils(tsysSettingsList, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 新增
     *
     * @param tsysSettings
     * @return
     */
    @Override
    public R save(TsysSettings tsysSettings) throws CreatorblueException {
        tsysSettings.setSettingId(Identities.uuid());
        tsysSettings.setCreateUserId(serviceLoginUtil.getLoginUserId());
        tsysSettings.setCreateTime(DateUtils.getNowTimeStamp());
        //ValidatorUtils.check(tsysSettings);
        tsysSettingsMapper.insert(tsysSettings);
        return R.ok();
    }

    /**
     * 修改
     *
     * @param tsysSettings
     * @return
     */
    @Override
    public R update(TsysSettings tsysSettings) throws CreatorblueException {
        tsysSettings.setUpdateUserId(serviceLoginUtil.getLoginUserId());
        tsysSettings.setUpdateTime(DateUtils.getNowTimeStamp());
        tsysSettingsMapper.update(tsysSettings);
        return R.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @SysLog(value = "单条删除")
    @GetMapping("delete/{id}")
    @SentinelResource("/sys/settings/delete")
    public R delete(@PathVariable("id") String id) throws CreatorblueException {
        tsysSettingsMapper.delete(id);
        return R.ok();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    @SysLog(value = "批量删除")
    @PostMapping("deleteBatch")
    @SentinelResource("/sys/settings/deleteBatch")
    public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
        tsysSettingsMapper.deleteBatch(ids);
        return R.ok();
    }


    /**
     * 查看明细
     *
     * @param id
     * @return
     */
    @Override
    @SysLog(value = "查看明细")
    @GetMapping("view/{id}")
    @SentinelResource("/sys/settings/view")
    public R view(String id) {
        return R.ok().put(Constant.R_DATA, tsysSettingsMapper.selectObjectById(id));
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
        return R.ok().put(Constant.R_DATA, settings);
    }

    /**
     * 批量修改
     *
     * @param settings
     * @return
     */
    @Override
    public R updateBatchSettings(List<TsysSettings> settings) {
        settings.stream().forEach(setting -> {
            update(setting);
        });
        return R.ok();
    }

    @Override
    public TsysSettings selectObjectById(String id) {
        return tsysSettingsMapper.selectObjectById(id);
    }

    @Override
    public List<TsysSettings> selectListByMap(Map<String, Object> map) {
        return tsysSettingsMapper.selectListByMap(map);
    }

}
