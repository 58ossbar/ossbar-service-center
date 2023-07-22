package com.ossbar.modules.evgl.site.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.annotation.NoRepeatSubmit;
import com.ossbar.modules.evgl.site.api.TevglSiteJoinUsService;
import com.ossbar.modules.evgl.site.domain.TevglSiteJoinUs;
import com.ossbar.modules.evgl.site.persistence.TevglSiteJoinUsMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/site/tevglsitejoinus")
public class TevglSiteJoinUsServiceImpl implements TevglSiteJoinUsService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteJoinUsServiceImpl.class);
	@Autowired
	private TevglSiteJoinUsMapper tevglSiteJoinUsMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsitejoinus/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteJoinUs> tevglSiteJoinUsList = tevglSiteJoinUsMapper.selectListByMap(query);
		convertUtil.convertDict(tevglSiteJoinUsList, "type", "cooperation_model");
		PageUtils pageUtil = new PageUtils(tevglSiteJoinUsList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsitejoinus/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteJoinUsList = tevglSiteJoinUsMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteJoinUsList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglSiteJoinUsList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteJoinUs
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsitejoinus/save")
	public R save(@RequestBody(required = false) TevglSiteJoinUs tevglSiteJoinUs) throws OssbarException {
		tevglSiteJoinUs.setId(Identities.uuid());
		tevglSiteJoinUs.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSiteJoinUs.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglSiteJoinUs);
		tevglSiteJoinUsMapper.insert(tevglSiteJoinUs);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteJoinUs
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsitejoinus/update")
	public R update(@RequestBody(required = false) TevglSiteJoinUs tevglSiteJoinUs) throws OssbarException {
	    ValidatorUtils.check(tevglSiteJoinUs);
		tevglSiteJoinUsMapper.update(tevglSiteJoinUs);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsitejoinus/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglSiteJoinUsMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsitejoinus/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglSiteJoinUsMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsitejoinus/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglSiteJoinUsMapper.selectObjectById(id));
	}

	/**
	 * 加入我们
	 * @param tevglSiteJoinUs
	 * @param loginUserId
	 * @return
	 */
	@Override
	@NoRepeatSubmit(value = 1000)
	public R joinUs(TevglSiteJoinUs tevglSiteJoinUs, String loginUserId) {
		if (StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("createUserId", loginUserId);
		List<TevglSiteJoinUs> list = tevglSiteJoinUsMapper.selectListByMap(map);
		if (list != null && list.size() > 0) {
			boolean flag = list.stream().anyMatch(a -> a.getMobile().equals(tevglSiteJoinUs.getMobile()));
			if (flag) {
				return R.ok("你已经提交过该联系方式等信息了，无需多次提交");
			}
		}
		tevglSiteJoinUs.setId(Identities.uuid());
		tevglSiteJoinUs.setCreateUserId(loginUserId);
		tevglSiteJoinUs.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglSiteJoinUs);
		tevglSiteJoinUsMapper.insert(tevglSiteJoinUs);
		return R.ok("您好，已经收到了您填写的信息，我们尽快与您联系，请保持手机号码联系畅通");
	}
	
	/**
	 * 手机格式验证
	 * 
	 * @param mobile
	 * @return
	 */
	private boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^1[3456789]\\d{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 邮箱格式验证
	 * @param email
	 * @return
	 */
	private boolean isEmail(String email) {
		return email.matches("^\\w+@(\\w+\\.)+\\w+$");
	}
}
