package com.ossbar.modules.evgl.activity.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.activity.api.TevglActivityVoteQuestionnaireTraineeAnswerFileService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityVoteQuestionnaireTraineeAnswerFile;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityVoteQuestionnaireTraineeAnswerFileMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
@RequestMapping("/activity/tevglactivityvotequestionnairetraineeanswerquestionfile")
public class TevglActivityVoteQuestionnaireTraineeAnswerFileServiceImpl implements TevglActivityVoteQuestionnaireTraineeAnswerFileService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglActivityVoteQuestionnaireTraineeAnswerFileServiceImpl.class);
	@Autowired
	private TevglActivityVoteQuestionnaireTraineeAnswerFileMapper tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswerquestionfile/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglActivityVoteQuestionnaireTraineeAnswerFile> tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileList = tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswerquestionfile/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileList = tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswerquestionfile/save")
	public R save(@RequestBody(required = false) TevglActivityVoteQuestionnaireTraineeAnswerFile tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile) throws CreatorblueException {
		tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile.setFileId(Identities.uuid());
		//ValidatorUtils.check(tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile);
		tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileMapper.insert(tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswerquestionfile/update")
	public R update(@RequestBody(required = false) TevglActivityVoteQuestionnaireTraineeAnswerFile tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile) throws CreatorblueException {
	    //ValidatorUtils.check(tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile);
		tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileMapper.update(tevglActivityVoteQuestionnaireTraineeAnswerQuestionFile);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswerquestionfile/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswerquestionfile/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/activity/tevglactivityvotequestionnairetraineeanswerquestionfile/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglActivityVoteQuestionnaireTraineeAnswerQuestionFileMapper.selectObjectById(id));
	}
}
