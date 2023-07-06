package com.ossbar.modules.evgl.medu.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.medu.api.TmeduMediaAvdService;
import com.ossbar.modules.evgl.medu.domain.TmeduMediaAvd;
import com.ossbar.modules.evgl.medu.persistence.TmeduMediaAvdMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/medu/tmedumediaavd")
public class TmeduMediaAvdServiceImpl implements TmeduMediaAvdService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TmeduMediaAvdServiceImpl.class);
	@Autowired
	private TmeduMediaAvdMapper tmeduMediaAvdMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TsysAttachService tsysAttachService;
	@Autowired
	private UploadFileUtils uploadPathUtils;
	
	@Value("${com.creatorblue.file-access-path}")
	public String creatorblueFieAccessPath;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/medu/tmedumediaavd/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TmeduMediaAvd> tmeduMediaAvdList = tmeduMediaAvdMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tmeduMediaAvdList, "createUserId", "updateUserId");
		convertUtil.convertDict(tmeduMediaAvdList, "avdState", "avdState");
		// 图片处理
		tmeduMediaAvdList.forEach(a -> {
			a.setAvdPicurl(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("15") + "/" + a.getAvdPicurl());
		});
		PageUtils pageUtil = new PageUtils(tmeduMediaAvdList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/medu/tmedumediaavd/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tmeduMediaAvdList = tmeduMediaAvdMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tmeduMediaAvdList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tmeduMediaAvdList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tmeduMediaAvd
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/medu/tmedumediaavd/save")
	public R save(@RequestBody(required = false) TmeduMediaAvd tmeduMediaAvd) throws CreatorblueException {
		tmeduMediaAvd.setAvdId(Identities.uuid());
		tmeduMediaAvd.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tmeduMediaAvd.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tmeduMediaAvd);
		tmeduMediaAvdMapper.insert(tmeduMediaAvd);
		return R.ok();
	}
	/***
	 * 新增
	 * @param tmeduMediaAvd
	 * @param attachId
	 */
	@SysLog(value="新增")
	@PostMapping("saveHasAttach")
	@SentinelResource("/medu/tmedumediaavd/saveHasAttach")
	public R saveHasAttach(@RequestBody(required = false) TmeduMediaAvd tmeduMediaAvd, String attachId) throws CreatorblueException {
		String id = Identities.uuid();
		tmeduMediaAvd.setAvdId(id);
		tmeduMediaAvd.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tmeduMediaAvd.setCreateTime(DateUtils.getNowTimeStamp());
		tmeduMediaAvd.setScene(StrUtils.isEmpty(tmeduMediaAvd.getScene()) ? "1" : tmeduMediaAvd.getScene());
		//ValidatorUtils.check(tmeduMediaAvd);
		tmeduMediaAvdMapper.insert(tmeduMediaAvd);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttachForAdd(attachId, id, "15");
		}
		return R.ok();
	}
	/**
	 * 修改
	 * @param tmeduMediaAvd
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/medu/tmedumediaavd/update")
	public R update(@RequestBody(required = false) TmeduMediaAvd tmeduMediaAvd) throws CreatorblueException {
	    tmeduMediaAvd.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tmeduMediaAvd.setUpdateTime(DateUtils.getNowTimeStamp());
	    //ValidatorUtils.check(tmeduMediaAvd);
		tmeduMediaAvdMapper.update(tmeduMediaAvd);
		return R.ok();
	}
	
	/***
	 * 修改
	 * @param tmeduMediaAvd
	 * @param attachId
	 */
	@SysLog(value="修改")
	@PostMapping("updateHasAttach")
	@SentinelResource("/medu/tmedumediaavd/updateHasAttach")
	public R updateHasAttach(@RequestBody(required = false) TmeduMediaAvd tmeduMediaAvd, String attachId) throws CreatorblueException {
	    tmeduMediaAvd.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tmeduMediaAvd.setUpdateTime(DateUtils.getNowTimeStamp());
	    //ValidatorUtils.check(tmeduMediaAvd);
		tmeduMediaAvdMapper.update(tmeduMediaAvd);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttachForEdit(attachId, tmeduMediaAvd.getAvdId(), "15");
		}
		return R.ok();
	}
	
	@SysLog(value="更新状态")
	@PostMapping("updateState")
	@SentinelResource("/medu/tmedumediaavd/updateState")
	public R updateState(TmeduMediaAvd tmeduMediaAvd){
	    if((tmeduMediaAvd == null) || ((tmeduMediaAvd != null) && ((tmeduMediaAvd.getAvdId() == null) || ("".equals(tmeduMediaAvd.getAvdId())) ) ) ){
	    	return R.error("操作失败");
	    }
		tmeduMediaAvdMapper.update(tmeduMediaAvd);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/medu/tmedumediaavd/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tmeduMediaAvdMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/medu/tmedumediaavd/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws CreatorblueException {
		tmeduMediaAvdMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/medu/tmedumediaavd/view")
	public R view(@PathVariable("id") String id) {
		TmeduMediaAvd tmeduMediaAvd = tmeduMediaAvdMapper.selectObjectById(id);
		String avdPicurl = "";
		if(tmeduMediaAvd != null){
			avdPicurl = creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("15") + "/" + tmeduMediaAvd.getAvdPicurl();
		}
		return R.ok().put(Constant.R_DATA,tmeduMediaAvd).put("avdPicurl",avdPicurl);
	}
	/**
	 * 查询首页轮播图列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/queryShowIndex")
	@SentinelResource("/medu/tmedumediaavd/queryShowIndex")
	public R queryShowIndex(@RequestParam Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>(); // 查询条件
		map.clear();
		map.put("sidx", "avd_num");
		map.put("order", "asc");
		map.put("avdState", "Y"); // 状态(Y已上线N已下线)
		map.put("avdBegintime", DateUtils.getNowTimeStamp());
		map.put("avdEndtime", DateUtils.getNowTimeStamp());
		map.put("scene", params.get("scene"));
		List<TmeduMediaAvd> tmeduMediaAvdList = tmeduMediaAvdMapper.selectListByMap(map);
		String nowDateTime = DateUtils.getNowTimeStamp();
		// 过滤，只取有效时间内的广告图
		tmeduMediaAvdList = tmeduMediaAvdList.parallelStream().filter(a -> (nowDateTime.compareTo(((TmeduMediaAvd) a).getAvdBegintime()) >= 0
				&& nowDateTime.compareTo(((TmeduMediaAvd) a).getAvdEndtime()) <= 0)).collect(Collectors.toList());
		
		convertUtil.convertUserId2RealName(tmeduMediaAvdList, "createUserId", "updateUserId");
//		convertUtil.convertDict(tmeduMediaAvdList, "avdState", "avdState");
		// 图片处理
		tmeduMediaAvdList.forEach(a -> {
			a.setAvdPicurl(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("15") + "/" + a.getAvdPicurl());
		});
		return R.ok().put(Constant.R_DATA, tmeduMediaAvdList);
	}
}
