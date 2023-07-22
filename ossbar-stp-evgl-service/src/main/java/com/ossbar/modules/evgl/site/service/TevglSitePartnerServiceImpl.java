package com.ossbar.modules.evgl.site.service;

import java.util.List;
import java.util.Map;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.common.utils.ServiceLoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.utils.tool.Identities;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.evgl.site.api.TevglSitePartnerService;
import com.ossbar.modules.evgl.site.domain.TevglSitePartner;
import com.ossbar.modules.evgl.site.persistence.TevglSitePartnerMapper;
import com.ossbar.modules.sys.api.TsysAttachService;

/**
 * <p> Title: 【合作伙伴】接口实现类</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/site/tevglsitepartner")
public class TevglSitePartnerServiceImpl implements TevglSitePartnerService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSitePartnerServiceImpl.class);
	@Autowired
	private TevglSitePartnerMapper tevglSitePartnerMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TsysAttachService tsysAttachService;	
	
	@Value("${com.ossbar.file-access-path}")
	public String ossbarFieAccessPath;
	@Autowired
	private UploadPathUtils uploadPathUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsitepartner/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSitePartner> tevglSitePartnerList = tevglSitePartnerMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglSitePartnerList, "createUserId", "updateUserId");
		convertUtil.convertDict(tevglSitePartnerList, "isKeyPoint", "state1"); // 是否首页显示(重点企业)
		convertUtil.convertDict(tevglSitePartnerList, "state", "state4"); // 状态(Y有效N无效)
		tevglSitePartnerList.forEach(a -> {
			a.setCompanyLogo(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("9") + "/" + a.getCompanyLogo());
		});
		PageUtils pageUtil = new PageUtils(tevglSitePartnerList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsitepartner/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSitePartnerList = tevglSitePartnerMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglSitePartnerList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglSitePartnerList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSitePartner
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsitepartner/save")
	public R save(@RequestBody(required = false) TevglSitePartner tevglSitePartner) throws OssbarException {
		tevglSitePartner.setCompanyId(Identities.uuid());
		tevglSitePartner.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSitePartner.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglSitePartner);
		tevglSitePartnerMapper.insert(tevglSitePartner);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSitePartner
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsitepartner/update")
	public R update(@RequestBody(required = false) TevglSitePartner tevglSitePartner) throws OssbarException {
	    tevglSitePartner.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSitePartner.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglSitePartner);
		tevglSitePartnerMapper.update(tevglSitePartner);
		return R.ok();
	}
	
	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年7月26日
	 * @param tevglSitePartner
	 * @param attachId
	 * @return
	 */
	@SysLog(value="新增")
	@SentinelResource("/site/tevglsitepartner/save")
	@Override
	public R save2(TevglSitePartner tevglSitePartner, String attachId) {
		String id = Identities.uuid();
		tevglSitePartner.setCompanyId(id);
		tevglSitePartner.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSitePartner.setCreateTime(DateUtils.getNowTimeStamp());
		tevglSitePartner.setUpdateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglSitePartner);
		tevglSitePartnerMapper.insert(tevglSitePartner);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttach(attachId, id, "1", "9");
		}
		return R.ok();
	}

	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年7月26日
	 * @param tevglSitePartner
	 * @param attachId
	 * @return
	 */
	@Override
	public R update2(TevglSitePartner tevglSitePartner, String attachId) {
		tevglSitePartner.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSitePartner.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglSitePartner);
		tevglSitePartnerMapper.update(tevglSitePartner);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttach(attachId, tevglSitePartner.getCompanyId(), "0", "9");
		}
		return R.ok();
	}
	
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsitepartner/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglSitePartnerMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsitepartner/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		tevglSitePartnerMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsitepartner/view")
	public R view(@PathVariable("id") String id) {
		TevglSitePartner tevglSitePartner = tevglSitePartnerMapper.selectObjectById(id);
		if (tevglSitePartner == null) {
			return R.ok().put(Constant.R_DATA, new TevglSitePartner());
		}
		tevglSitePartner.setCompanyLogo(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("9") + "/" + tevglSitePartner.getCompanyLogo());
		return R.ok().put(Constant.R_DATA, tevglSitePartner);
	}

	/**
	 * <p>更新状态</p>
	 * @author huj
	 * @data 2019年7月28日
	 * @param tevglSitePartner
	 * @return
	 */
	@Override
	public R updateState(TevglSitePartner tevglSitePartner) {
		if (tevglSitePartner == null) {
			return R.error("操作失败");
		}
		if (tevglSitePartner.getCompanyId() == null || "".equals(tevglSitePartner.getCompanyId())) {
			return R.error("操作失败");
		}
		tevglSitePartnerMapper.update(tevglSitePartner);
		return R.ok();
	}

}
