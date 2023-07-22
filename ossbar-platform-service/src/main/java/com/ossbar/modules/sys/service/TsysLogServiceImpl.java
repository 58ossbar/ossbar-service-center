package com.ossbar.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysLogService;
import com.ossbar.modules.sys.domain.TsysLog;
import com.ossbar.modules.sys.persistence.TsysLogMapper;
import com.ossbar.utils.tool.Identities;
import com.github.pagehelper.PageHelper;

/**
 * 系统日志api的实现类
 * <p>Title: TsysLogServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年5月6日
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/log")
public class TsysLogServiceImpl implements TsysLogService {

	@Autowired
	private TsysLogMapper tsysLogMapper;
	
	/**
	 * <p>根据条件查询站点列表</p>
	 * @author huj
	 * @data 2019年5月6日
	 * @param params
	 * @return
	 */
	@Override
	@RequestMapping("/query")
	@SentinelResource("/sys/log/query")
	public R query(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<TsysLog> list = tsysLogMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put("data", pageUtil);
	}

	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年5月6日
	 * @param sysLog
	 * @return
	 */
	@Override
	public R save(TsysLog sysLog) {
		try {
			sysLog.setId(Identities.uuid());
			tsysLogMapper.insert(sysLog);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("新增失败");
		}
		return R.ok("新增成功");
	}

	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年5月6日
	 * @param sysLog
	 * @return
	 */
	@Override
	public R update(TsysLog sysLog) {
		try {
			tsysLogMapper.update(sysLog);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("修改失败");
		}
		return R.ok("修改成功");
	}

	/**
	 * <p>单挑删除</p>
	 * @author huj
	 * @data 2019年5月23日
	 * @param id
	 * @return
	 */
	@Override
	@RequestMapping("/delete/{id}")
	@SentinelResource("/sys/log/remove")
	public R delete(@PathVariable("id") String id) {
		tsysLogMapper.delete(id);
		return R.ok("删除成功");
	}

	/**
	 * <p>批量删除</p>
	 * @author huj
	 * @data 2019年5月6日
	 * @param ids
	 * @return
	 */
	@Override
	@RequestMapping("/remove")
	@SentinelResource("/sys/log/remove")
	public R deleteBatch(@RequestBody(required = true) String[] ids) {
		tsysLogMapper.deleteBatch(ids);
		return R.ok("删除成功");
	}

	/**
	 * <p>查看明细</p>
	 * @author huj
	 * @data 2019年5月6日
	 * @param id
	 * @return
	 */
	@Override
	@RequestMapping("/view/{id}")
	@SentinelResource("/sys/log/view")
	public R view(@PathVariable("id") String id) {
		TsysLog tsysLog = tsysLogMapper.selectObjectById(id);
		return R.ok().put("data", tsysLog);
	}

}
