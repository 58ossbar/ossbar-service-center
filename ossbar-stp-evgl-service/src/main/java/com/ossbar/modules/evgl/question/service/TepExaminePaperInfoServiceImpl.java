package com.ossbar.modules.evgl.question.service;

import java.util.List;
import java.util.Map;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.common.utils.ServiceLoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ossbar.utils.tool.StrUtils;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.question.api.TepExaminePaperInfoService;
import com.ossbar.modules.evgl.question.domain.TepExaminePaperInfo;
import com.ossbar.modules.evgl.question.persistence.TepExaminePaperInfoMapper;

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
@RequestMapping("/question/tepexaminepaperinfo")
public class TepExaminePaperInfoServiceImpl implements TepExaminePaperInfoService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TepExaminePaperInfoServiceImpl.class);
	@Autowired
	private TepExaminePaperInfoMapper tepExaminePaperInfoMapper;
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
	@SentinelResource("/question/tepexaminepaperinfo/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TepExaminePaperInfo> tepExaminePaperInfoList = tepExaminePaperInfoMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tepExaminePaperInfoList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tepExaminePaperInfoList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tepExaminePaperInfoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/question/tepexaminepaperinfo/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tepExaminePaperInfoList = tepExaminePaperInfoMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tepExaminePaperInfoList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tepExaminePaperInfoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tepExaminePaperInfo
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/question/tepexaminepaperinfo/save")
	public R save(@RequestBody(required = false) TepExaminePaperInfo tepExaminePaperInfo) throws OssbarException {
		tepExaminePaperInfo.setPaperId(Identities.uuid());
		if (StrUtils.isEmpty(tepExaminePaperInfo.getCreateUserId())) {
			tepExaminePaperInfo.setCreateUserId(serviceLoginUtil.getLoginUserId());
		}
		tepExaminePaperInfo.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tepExaminePaperInfo);
		tepExaminePaperInfoMapper.insert(tepExaminePaperInfo);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tepExaminePaperInfo
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/question/tepexaminepaperinfo/update")
	public R update(@RequestBody(required = false) TepExaminePaperInfo tepExaminePaperInfo) throws OssbarException {
	    tepExaminePaperInfo.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tepExaminePaperInfo.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tepExaminePaperInfo);
		tepExaminePaperInfoMapper.update(tepExaminePaperInfo);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/question/tepexaminepaperinfo/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tepExaminePaperInfoMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/question/tepexaminepaperinfo/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tepExaminePaperInfoMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/question/tepexaminepaperinfo/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tepExaminePaperInfoMapper.selectObjectById(id));
	}

	/**
	 * <p>试卷列表</p>  
	 * @author huj
	 * @data 2019年12月12日	
	 * @param parmas
	 * @return
	 */
	@Override
	@SysLog(value="根据条件查询试卷列表")
	@GetMapping("queryPapers/{id}")
	@SentinelResource("/question/tepexaminepaperinfo/queryPapers")
	public R queryPapers(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tepExaminePaperInfoList = tepExaminePaperInfoMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tepExaminePaperInfoList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tepExaminePaperInfoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * <p>明细</p>  
	 * @author huj
	 * @data 2019年12月30日	
	 * @param paperId 试卷ID
	 * @return
	 */
	@Override
	public TepExaminePaperInfo selectObjectById(String paperId) {
		return tepExaminePaperInfoMapper.selectObjectById(paperId);
	}

	@Override
	public void plusNum(TepExaminePaperInfo tepExaminePaperInfo) {
		tepExaminePaperInfoMapper.plusNum(tepExaminePaperInfo);
	}

	@Override
	public List<TepExaminePaperInfo> selectListByMap(Map<String, Object> map) {
		return tepExaminePaperInfoMapper.selectListByMap(map);
	}

	/**
	 * <p>根据条件获取日期</p>  
	 * @author huj
	 * @data 2020年1月11日	
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDates(Map<String, Object> map) {
		return tepExaminePaperInfoMapper.getDates(map);
	}
}
