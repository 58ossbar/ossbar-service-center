package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysLoginLogService;
import com.ossbar.modules.sys.domain.TsysLoginLog;
import com.ossbar.modules.sys.persistence.TsysLoginLogMapper;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.IPUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 登录日志接口实现类
 * @author huangwb
 * @create 2019-05-08 17:30
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/loginlog")
public class TsysLoginLogServiceImpl implements TsysLoginLogService {

    @Autowired
    private TsysLoginLogMapper tsysLoginLogMapper;

    /**
     * 删除操作
     *
     * @param ids
     * @return
     * @date 2019-05-08 17:30
     */
    @Override
    @DeleteMapping("delete")
    @SentinelResource("/sys/loginlog/delete")
    @Transactional
    public R delete(@RequestBody String[] ids) {
        try {
            if (ids != null && ids.length > 0) {
                if (ids.length > 1) {
                    tsysLoginLogMapper.deleteBatch(ids);
                } else {
                    tsysLoginLogMapper.delete(ids[0]);
                }
            }
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 查询操作
     *
     * @param map
     * @return
     * @date 2019-05-08 17:30
     */
    @Override
    public R selectAllByMap(Map<String, Object> map) {
        // 构建查询条件对象Query
        Query query = new Query(map);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TsysLoginLog> loginLogs = tsysLoginLogMapper.selectListByMap(map);
        PageUtils pageUtil = new PageUtils(loginLogs, query.getPage(), query.getLimit());
        return R.ok().put("data", pageUtil);
    }

    /**
     * 登录失败时，记录下日志
     *
     * @param request
     * @param message
     * @return
     */
    @Override
    public R saveFailMessage(HttpServletRequest request, String message) {
        TsysLoginLog tsysLoginLog = new TsysLoginLog();
        tsysLoginLog.setLoginlogid(Identities.uuid());
        tsysLoginLog.setLogname("系统登陆");
        tsysLoginLog.setCreateTime(DateUtils.getNowTimeStamp());
        tsysLoginLog.setSucceed("失败");
        tsysLoginLog.setMessage(message);
        tsysLoginLog.setIp(IPUtils.getIpAddr(request));
        tsysLoginLogMapper.insert(tsysLoginLog);
        return R.ok("保存成功");
    }

    /**
     * 登录成功时，记录下日志
     *
     * @param request
     * @param message
     * @return
     */
    @Override
    public R saveSuccessMessage(HttpServletRequest request, String message, String userRealname) {
        TsysLoginLog tsysLoginLog = new TsysLoginLog();
        tsysLoginLog.setLoginlogid(Identities.uuid());
        tsysLoginLog.setLogname("登陆日志");
        tsysLoginLog.setUserid(userRealname);
        tsysLoginLog.setCreateTime(DateUtils.getNowTimeStamp());
        tsysLoginLog.setSucceed("成功");
        tsysLoginLog.setMessage(message);
        tsysLoginLog.setIp(request.getRemoteHost());
        tsysLoginLogMapper.insert(tsysLoginLog);
        return R.ok("保存成功");
    }

    /**
     * 修改
     *
     * @param tsysLoginLog
     * @return
     * @author huj
     * @data 2019年5月31日
     */
    @Override
    public R update(TsysLoginLog tsysLoginLog) {
        return null;
    }
}
