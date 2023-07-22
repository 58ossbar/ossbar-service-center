package com.ossbar.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysUserprivilegeService;
import com.ossbar.modules.sys.domain.TsysUserprivilege;
import com.ossbar.modules.sys.persistence.TsysUserprivilegeMapper;
import com.ossbar.utils.tool.Identities;

/**
 * <p>用户特权api的实现类</p>
 * <p>Title: TsysUserprivilegeServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年5月8日
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/userprivilege")
public class TsysUserprivilegeServiceImpl implements TsysUserprivilegeService {

	@Autowired
	private TsysUserprivilegeMapper tsysUserprivilegeMapper;
	
	/**
	 * <p>根据条件查询记录</p>
	 * @author huj
	 * @data 2019年5月8日
	 * @param map
	 * @return
	 */
	@Override
	@RequestMapping("/query")
	@SentinelResource("/sys/userprivilege/query")
	public R query(@RequestParam Map<String, Object> map) {
		List<TsysUserprivilege> list = tsysUserprivilegeMapper.selectListByMap(map);
		return R.ok().put("list", list);
	}

	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年5月8日
	 * @param tsysUserprivilege
	 * @return
	 */
	@Override
	@RequestMapping("/save")
	@SentinelResource("/sys/userprivilege/save")
	@SysLog("新增用户特权")
	public R save(TsysUserprivilege tsysUserprivilege) {
		try {
			verifyForm(tsysUserprivilege); // 数据校验
			tsysUserprivilege.setUserprviid(Identities.uuid());
			tsysUserprivilegeMapper.insert(tsysUserprivilege);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("新增失败");
		}
		return R.ok();
	}

	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年5月8日
	 * @param tsysUserprivilege
	 * @return
	 */
	@Override
	@RequestMapping("/update")
	@SentinelResource("/sys/userprivilege/update")
	@SysLog("修改用户特权")
	public R update(TsysUserprivilege tsysUserprivilege) {
		try {
			verifyForm(tsysUserprivilege); // 数据校验
			tsysUserprivilegeMapper.update(tsysUserprivilege);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("修改失败");
		}
		return R.ok();
	}

	/**
	 * <p>删除</p>
	 * @author huj
	 * @data 2019年5月8日
	 * @param id
	 * @return
	 */
	@Override
	@RequestMapping("/delete")
	@SentinelResource("/sys/userprivilege/delete")
	@SysLog("单个删除用户特权")
	public R delete(String id) {
		try {
			tsysUserprivilegeMapper.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("删除失败");
		}
		return R.error();
	}

	
	/**
	 * <p>批量删除</p>
	 * @author huj
	 * @data 2019年5月8日
	 * @param ids
	 * @return
	 */
	@Override
	@RequestMapping("/remove")
	@SentinelResource("/sys/userprivilege/remove")
	@SysLog("批量删除用户特权")
	public R deleteBatch(String[] ids) {
		try {
			tsysUserprivilegeMapper.deleteBatch(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("删除失败");
		}
		return R.ok();
	}

	/**
	 * <p>查看明细</p>
	 * @author huj
	 * @data 2019年5月8日
	 * @param id
	 * @return
	 */
	@Override
	@RequestMapping("/view/{id}")
	@SentinelResource("/sys/userprivilege/view")
	public R view(@PathVariable("id") String id) {
		TsysUserprivilege tsysUserprivilege = tsysUserprivilegeMapper.selectObjectById(id);
		return R.ok().put("tsysUserprivilege", tsysUserprivilege);
	}
	
	@Override
	public void saveOrUpdate(String userId, String[] menuIds) {
		TsysUserprivilege tsysUserprivilege = null;
		for(String menuId : menuIds){
			tsysUserprivilege = new TsysUserprivilege();
			tsysUserprivilege.setMenuId(menuId);
			tsysUserprivilege.setUserid(userId);
			save(tsysUserprivilege);
		}
	}
	
	/**
	 * <p>验证参数是否正确</p>
	 * @author huj
	 * @data 2019年5月8日
	 * @param tsysUserprivilege
	 */
	private void verifyForm(TsysUserprivilege tsysUserprivilege) {
		/*if (StringUtils.isBlank("")) {
			throw new OssbarException("****不能为空");
		}*/
	}


}
