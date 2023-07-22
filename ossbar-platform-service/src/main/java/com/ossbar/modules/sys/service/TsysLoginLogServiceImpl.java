package com.ossbar.modules.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysLoginLogService;
import com.ossbar.modules.sys.domain.TsysLoginLog;
import com.ossbar.modules.sys.persistence.TsysLoginLogMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.IPUtils;
import com.ossbar.utils.tool.Identities;
import com.github.pagehelper.PageHelper;

/**
 * 登录日志接口实现类
 * 
 * @author huangwb
 * @date 2019-05-08 17:30
 */
@Service(version = "1.0.0")
@RequestMapping("/sys/loginlog")
@RestController
public class TsysLoginLogServiceImpl implements TsysLoginLogService {
	@Autowired
	private TsysLoginLogMapper tsysLoginLogMapper;

	/**
	 * 删除操作
	 * 
	 * @date 2019-05-08 17:30
	 * @param ids
	 * @return
	 */
	@DeleteMapping("delete")
	@SentinelResource("/sys/loginlog/delete")
	@Transactional
	@Override
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
	 * @date 2019-05-08 17:30
	 * @param map
	 * @return
	 */
	@GetMapping("query")
	@SentinelResource("/sys/loginlog/query")
	@Override
	public R selectAllByMap(Map<String, Object> map) {
		// 构建查询条件对象Query
		Query query = new Query(map);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<TsysLoginLog> loginLogs = tsysLoginLogMapper.selectListByMap(map);
		PageUtils pageUtil = new PageUtils(loginLogs, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年5月31日
	 * @param tsysLoginLog
	 * @return
	 */
	@Override
	public R save(TsysLoginLog tsysLoginLog) {
		try {
			tsysLoginLog.setLoginlogid(Identities.uuid());
			tsysLoginLogMapper.insert(tsysLoginLog);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("保存失败");
		}
		return R.ok("保存成功");
	}

	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年5月31日
	 * @param tsysLoginLog
	 * @return
	 */
	@Override
	public R update(TsysLoginLog tsysLoginLog) {
		tsysLoginLogMapper.update(tsysLoginLog);
		return R.ok();
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

}
