package com.ossbar.modules.evgl.site.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteSeoService;
import com.ossbar.modules.evgl.site.domain.TevglSiteSeo;
import com.ossbar.modules.evgl.site.persistence.TevglSiteSeoMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
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
@RequestMapping("/site/tevglsiteseo")
public class TevglSiteSeoServiceImpl implements TevglSiteSeoService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteSeoServiceImpl.class);
	@Autowired
	private TevglSiteSeoMapper tevglSiteSeoMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsiteseo/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteSeo> tevglSiteSeoList = tevglSiteSeoMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglSiteSeoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsiteseo/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteSeoList = tevglSiteSeoMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglSiteSeoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteSeo
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsiteseo/save")
	public R save(@RequestBody(required = false) TevglSiteSeo tevglSiteSeo) throws OssbarException {
		tevglSiteSeo.setSeoId(Identities.uuid());
		ValidatorUtils.check(tevglSiteSeo);
		tevglSiteSeoMapper.insert(tevglSiteSeo);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteSeo
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsiteseo/update")
	public R update(@RequestBody(required = false) TevglSiteSeo tevglSiteSeo) throws OssbarException {
	    ValidatorUtils.check(tevglSiteSeo);
		tevglSiteSeoMapper.update(tevglSiteSeo);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsiteseo/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglSiteSeoMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsiteseo/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglSiteSeoMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsiteseo/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglSiteSeoMapper.selectObjectById(id));
	}

	
	/**
	 * <p>执行数据保存和修改</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param tevglSiteSeo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/saveorupdate")
	public R saveorupdate(TevglSiteSeo tevglSiteSeo ,HttpServletRequest request, HttpServletResponse response) {
		try {
			// 数据校验
			ValidatorUtils.check(tevglSiteSeo);
			// 新增
		    if ((tevglSiteSeo.getSeoId() == null) || ("").equals(tevglSiteSeo.getSeoId())) {
		    	tevglSiteSeoMapper.insert(tevglSiteSeo);
			} else {
				tevglSiteSeoMapper.update(tevglSiteSeo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		//如果已发布，则重新发布
		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());	
		try {
			String id = tepsiteSeo.getSeoRelation();
		} catch (Exception e) {
			logger.error("文档发布", e);
		}
		*/
		return R.ok().put("seoId", tevglSiteSeo.getSeoId());
	}
	
	/**
	 * <p>进入编辑界面时，需要的数据</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public TevglSiteSeo editSiteSeo(HttpServletRequest request, HttpServletResponse response) {
		TevglSiteSeo tevglSiteSeo = tevglSiteSeoMapper.selectObjectById(request.getParameter("seoId"));
		if(tevglSiteSeo == null){
			tevglSiteSeo = new TevglSiteSeo();
			tevglSiteSeo.setSeoRelation(request.getParameter("siteId"));
			tevglSiteSeo.setSeoType(request.getParameter("seoType"));
		}
		return tevglSiteSeo;
	}
	
	/**
	 * <p>查看明细</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param id
	 * @return
	 */
	@Override
	public TevglSiteSeo selectObjectByRelationId(String id) {
		return tevglSiteSeoMapper.selectObjectByRelationId(id);
	}

	/**
	 * <p>删除</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param id
	 * @param request
	 * @param response
	 */
	@Override
	public void deleteSeo(String[] ids, HttpServletRequest request, HttpServletResponse response) {
		
	}

}
