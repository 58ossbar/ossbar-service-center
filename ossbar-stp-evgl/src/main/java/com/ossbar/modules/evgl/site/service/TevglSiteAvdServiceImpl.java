package com.ossbar.modules.evgl.site.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteAvdService;
import com.ossbar.modules.evgl.site.domain.TevglSiteAvd;
import com.ossbar.modules.evgl.site.persistence.TevglSiteAvdMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: 【广告轮播图】接口实现类</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/site/tevglsiteavd")
public class TevglSiteAvdServiceImpl implements TevglSiteAvdService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteAvdServiceImpl.class);
	@Autowired
	private TevglSiteAvdMapper tevglSiteAvdMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TsysAttachService tsysAttachService;
	
	@Value("${com.creatorblue.file-access-path}")
	public String creatorblueFieAccessPath;
	@Autowired
	private UploadFileUtils uploadPathUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsiteavd/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteAvd> tevglSiteAvdList = tevglSiteAvdMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteAvdList, "createUserId", "updateUserId");
		convertUtil.convertDict(tevglSiteAvdList, "avdState", "avdState");
		// 图片处理
		tevglSiteAvdList.forEach(a -> {
			a.setAvdPicurlPc(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("5") + "/" + a.getAvdPicurlPc());
			a.setAvdPicurlMobile(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("5") + "/" + a.getAvdPicurlMobile());
		});
		PageUtils pageUtil = new PageUtils(tevglSiteAvdList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsiteavd/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteAvdList = tevglSiteAvdMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteAvdList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglSiteAvdList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteAvd
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsiteavd/save")
	public R save(@RequestBody(required = false) TevglSiteAvd tevglSiteAvd) throws CreatorblueException {
		tevglSiteAvd.setAvdId(Identities.uuid());
		tevglSiteAvd.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSiteAvd.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglSiteAvd);
		tevglSiteAvdMapper.insert(tevglSiteAvd);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteAvd
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsiteavd/update")
	public R update(@RequestBody(required = false) TevglSiteAvd tevglSiteAvd) throws CreatorblueException {
	    tevglSiteAvd.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSiteAvd.setUpdateTime(DateUtils.getNowTimeStamp());
	    //ValidatorUtils.check(tevglSiteAvd);
		tevglSiteAvdMapper.update(tevglSiteAvd);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsiteavd/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglSiteAvdMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsiteavd/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws CreatorblueException {
		tevglSiteAvdMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsiteavd/view")
	public R view(@PathVariable("id") String id) {
		TevglSiteAvd tevglSiteAvd = tevglSiteAvdMapper.selectObjectById(id);
		String avdPicurlPc = "", avdPicurlMobile = "";
		if (tevglSiteAvd != null) {
			avdPicurlPc = creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("5") + "/" + tevglSiteAvd.getAvdPicurlPc();
			avdPicurlMobile = creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("5") + "/" + tevglSiteAvd.getAvdPicurlMobile();
		}
		return R.ok().put(Constant.R_DATA, tevglSiteAvd)
				.put("avdPicurlPc", avdPicurlPc)
				.put("avdPicurlMobile", avdPicurlMobile);
	}

	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param tevglSiteAvd
	 * @param attachId
	 * @return
	 */
	@Override
	@SysLog(value="新增")
	@SentinelResource("/site/tevglsiteavd/save2")
	public R save2(TevglSiteAvd tevglSiteAvd, String attachId, String attachIdForMobile) {
		String id = Identities.uuid();
		tevglSiteAvd.setAvdId(id);
		tevglSiteAvd.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSiteAvd.setCreateTime(DateUtils.getNowTimeStamp());
		tevglSiteAvd.setUpdateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglSiteAvd);
		tevglSiteAvdMapper.insert(tevglSiteAvd);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttachForAdd(attachId, id, "5");
		}
		if (attachIdForMobile != null && !"".equals(attachIdForMobile)) {
			tsysAttachService.updateAttachForAdd(attachIdForMobile, id, "5");
		}
		return R.ok();
	}

	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年7月18日
	 * @param tevglSiteAvd
	 * @param attachId
	 * @return
	 */
	@Override
	@SysLog(value="修改")
	@SentinelResource("/site/tevglsiteavd/update2")
	public R update2(TevglSiteAvd tevglSiteAvd, String attachId, String attachIdForMobile) {
		tevglSiteAvd.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSiteAvd.setUpdateTime(DateUtils.getNowTimeStamp());
	    //ValidatorUtils.check(tevglSiteAvd);
		tevglSiteAvdMapper.update(tevglSiteAvd);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttachForEdit(attachId, tevglSiteAvd.getAvdId(), "5");
		}
		if (attachIdForMobile != null && !"".equals(attachIdForMobile)) {
			tsysAttachService.updateAttachForEdit(attachIdForMobile, tevglSiteAvd.getAvdId(), "5");
		}
		return R.ok();
	}

	/**
	 * <p>更新状态</p>
	 * @author huj
	 * @data 2019年7月29日
	 * @param tevglSiteAvd
	 * @return
	 */
	@Override
	@SysLog(value="更新状态")
	@SentinelResource("/site/tevglsiteavd/updateState")
	public R updateState(TevglSiteAvd tevglSiteAvd) {
		if (tevglSiteAvd == null) {
			return R.error("操作失败");
		}
		if (tevglSiteAvd.getAvdId() == null || "".equals(tevglSiteAvd.getAvdId())) {
			return R.error("操作失败");
		}
		tevglSiteAvdMapper.update(tevglSiteAvd);
		return R.ok();
	}
}
