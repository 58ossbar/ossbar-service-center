package com.ossbar.modules.evgl.activity.service;

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
import com.ossbar.modules.evgl.activity.api.TevglActivityBrainstormingAnswerFileService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityBrainstormingAnswerFile;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityBrainstormingAnswerFileMapper;

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
@RequestMapping("/activity/tevglactivitybrainstorminganswerfile")
public class TevglActivityBrainstormingAnswerFileServiceImpl implements TevglActivityBrainstormingAnswerFileService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglActivityBrainstormingAnswerFileServiceImpl.class);
	@Autowired
	private TevglActivityBrainstormingAnswerFileMapper tevglActivityBrainstormingAnswerFileMapper;
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
	@SentinelResource("/activity/tevglactivitybrainstorminganswerfile/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglActivityBrainstormingAnswerFile> tevglActivityBrainstormingAnswerFileList = tevglActivityBrainstormingAnswerFileMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglActivityBrainstormingAnswerFileList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglActivityBrainstormingAnswerFileList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/activity/tevglactivitybrainstorminganswerfile/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglActivityBrainstormingAnswerFileList = tevglActivityBrainstormingAnswerFileMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglActivityBrainstormingAnswerFileList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglActivityBrainstormingAnswerFileList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglActivityBrainstormingAnswerFile
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/activity/tevglactivitybrainstorminganswerfile/save")
	public R save(@RequestBody(required = false) TevglActivityBrainstormingAnswerFile tevglActivityBrainstormingAnswerFile) throws OssbarException {
		tevglActivityBrainstormingAnswerFile.setFiId(Identities.uuid());
		if (StrUtils.isEmpty(tevglActivityBrainstormingAnswerFile.getCreateUserId())) {
			tevglActivityBrainstormingAnswerFile.setCreateUserId(serviceLoginUtil.getLoginUserId());
		}
		tevglActivityBrainstormingAnswerFile.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglActivityBrainstormingAnswerFile);
		tevglActivityBrainstormingAnswerFileMapper.insert(tevglActivityBrainstormingAnswerFile);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglActivityBrainstormingAnswerFile
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/activity/tevglactivitybrainstorminganswerfile/update")
	public R update(@RequestBody(required = false) TevglActivityBrainstormingAnswerFile tevglActivityBrainstormingAnswerFile) throws OssbarException {
	    ValidatorUtils.check(tevglActivityBrainstormingAnswerFile);
		tevglActivityBrainstormingAnswerFileMapper.update(tevglActivityBrainstormingAnswerFile);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/activity/tevglactivitybrainstorminganswerfile/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglActivityBrainstormingAnswerFileMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/activity/tevglactivitybrainstorminganswerfile/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglActivityBrainstormingAnswerFileMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/activity/tevglactivitybrainstorminganswerfile/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglActivityBrainstormingAnswerFileMapper.selectObjectById(id));
	}
}
