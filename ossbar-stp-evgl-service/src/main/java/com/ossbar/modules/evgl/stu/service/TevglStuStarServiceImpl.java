package com.ossbar.modules.evgl.stu.service;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.evgl.stu.api.TevglStuStarService;
import com.ossbar.modules.evgl.stu.domain.TevglStuStar;
import com.ossbar.modules.evgl.stu.persistence.TevglStuStarMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: 【实训故事】接口实现类</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/stu/tevglstustar")
public class TevglStuStarServiceImpl implements TevglStuStarService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglStuStarServiceImpl.class);
	@Autowired
	private TevglStuStarMapper tevglStuStarMapper;
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
	@SentinelResource("/stu/tevglstustar/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglStuStar> tevglStuStarList = tevglStuStarMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglStuStarList, "createUserId", "updateUserId");
		convertUtil.convertOrgId(tevglStuStarList, "orgId"); // 所属院校
		convertUtil.convertDict(tevglStuStarList, "state", "state1"); // 是否首页显示
		tevglStuStarList.forEach(a -> {
			a.setStarPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("8") + "/" + a.getStarPic());
		});
		PageUtils pageUtil = new PageUtils(tevglStuStarList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/stu/tevglstustar/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglStuStarList = tevglStuStarMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglStuStarList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglStuStarList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglStuStar
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/stu/tevglstustar/save")
	public R save(@RequestBody(required = false) TevglStuStar tevglStuStar) throws OssbarException {
		tevglStuStar.setStarId(Identities.uuid());
		tevglStuStar.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglStuStar.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglStuStar);
		tevglStuStarMapper.insert(tevglStuStar);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglStuStar
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/stu/tevglstustar/update")
	public R update(@RequestBody(required = false) TevglStuStar tevglStuStar) throws OssbarException {
	    tevglStuStar.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglStuStar.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglStuStar);
		tevglStuStarMapper.update(tevglStuStar);
		return R.ok();
	}
	
	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年7月26日
	 * @param tevglStuStar
	 * @param attachId
	 * @return
	 */
	@Override
	@SysLog(value="新增")
	@SentinelResource("/stu/tevglstustar/save")
	public R save2(TevglStuStar tevglStuStar, String attachId) {
		String id = Identities.uuid();
		tevglStuStar.setStarId(id);
		tevglStuStar.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglStuStar.setCreateTime(DateUtils.getNowTimeStamp());
		tevglStuStar.setUpdateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglStuStar);
		tevglStuStarMapper.insert(tevglStuStar);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttach(attachId, id, "1", "8");
		}
		return R.ok();
	}

	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年7月26日
	 * @param tevglStuStar
	 * @param attachId
	 * @return
	 */
	@Override
	@SysLog(value="修改")
	@SentinelResource("/stu/tevglstustar/update")
	public R update2(TevglStuStar tevglStuStar, String attachId) {
		tevglStuStar.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglStuStar.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglStuStar);
		tevglStuStarMapper.update(tevglStuStar);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttach(attachId, tevglStuStar.getStarId(), "0", "8");
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
	@SentinelResource("/stu/tevglstustar/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglStuStarMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/stu/tevglstustar/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		tevglStuStarMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/stu/tevglstustar/view")
	public R view(@PathVariable("id") String id) {
		TevglStuStar a = tevglStuStarMapper.selectObjectById(id);
		if (a == null) {
			return R.ok().put(Constant.R_DATA, new TevglStuStar());
		}
		a.setStarPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("8") + "/" + a.getStarPic());
		return R.ok().put(Constant.R_DATA, a);
	}

	/**
	 * <p>更新状态</p>
	 * @author huj
	 * @data 2019年7月28日
	 * @param tevglStuStar
	 * @return
	 */
	@Override
	public R updateState(TevglStuStar tevglStuStar) {
		if (tevglStuStar == null) {
			return R.error("操作失败");
		}
		if (tevglStuStar.getStarId() == null || "".equals(tevglStuStar.getStarId())) {
			return R.error("操作失败");
		}
		tevglStuStarMapper.update(tevglStuStar);
		return R.ok();
	}

}
