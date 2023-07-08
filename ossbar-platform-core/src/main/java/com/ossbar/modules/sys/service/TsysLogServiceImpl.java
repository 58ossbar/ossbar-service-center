package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysLogService;
import com.ossbar.modules.sys.domain.TsysLog;
import com.ossbar.modules.sys.persistence.TsysLogMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统日志api的实现类
 * <p>Title: TsysLogServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p>
 * @author huj
 * @date 2019年5月6日
 */
@Api(tags="系统日志接口")
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/log")
public class TsysLogServiceImpl implements TsysLogService {

    @Autowired
    private TsysLogMapper tsysLogMapper;

    /**
     * 根据条件查询站点列表
     *
     * @param params
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    @RequestMapping("/query")
    @SentinelResource("/sys/log/query")
    public R query(Map<String, Object> params) {
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TsysLog> list = tsysLogMapper.selectListByMap(query);
        PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 新增
     *
     * @param sysLog
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    public R save(TsysLog sysLog) {
        sysLog.setId(Identities.uuid());
        tsysLogMapper.insert(sysLog);
        return R.ok();
    }

    /**
     * 修改
     *
     * @param sysLog
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    public R update(TsysLog sysLog) {
        tsysLogMapper.update(sysLog);
        return R.ok();
    }

    /**
     * 单条删除
     *
     * @param id
     * @return
     * @author huj
     * @data 2019年5月23日
     */
    @Override
    @RequestMapping("/delete/{id}")
    @SentinelResource("/sys/log/remove")
    public R delete(@PathVariable("id") String id) {
        tsysLogMapper.delete(id);
        return R.ok("删除成功");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    @RequestMapping("/remove")
    @SentinelResource("/sys/log/remove")
    public R deleteBatch(@RequestBody String[] ids) {
        tsysLogMapper.deleteBatch(ids);
        return R.ok("删除成功");
    }

    /**
     * 查看明细
     *
     * @param id
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    @RequestMapping("/view/{id}")
    @SentinelResource("/sys/log/view")
    public R view(@PathVariable("id") String id) {
        return R.ok().put(Constant.R_DATA, tsysLogMapper.selectObjectById(id));
    }
}
