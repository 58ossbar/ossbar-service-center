package com.ossbar.modules.sys.service;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysLogService;
import com.ossbar.modules.sys.domain.TsysLog;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 根据条件查询站点列表
     *
     * @param params
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    public R query(Map<String, Object> params) {
        return null;
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
        return null;
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
        return null;
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
    public R delete(String id) {
        return null;
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
    public R deleteBatch(String[] ids) {
        return null;
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
    public R view(String id) {
        return null;
    }
}
