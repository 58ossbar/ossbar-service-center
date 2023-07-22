package com.ossbar.modules.evgl.trainee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossbar.modules.evgl.tch.persistence.TevglTchRoomPereTraineeAnswerMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeEmpiricalValueLogMapper;
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
import com.ossbar.modules.common.GlobalActivity;
import com.ossbar.modules.evgl.tch.domain.TevglTchRoomPereTraineeAnswer;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeEmpiricalValueLogService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeEmpiricalValueLog;
import com.ossbar.utils.constants.Constant;
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
@RequestMapping("/trainee/tevgltraineeempiricalvaluelog")
public class TevglTraineeEmpiricalValueLogServiceImpl implements TevglTraineeEmpiricalValueLogService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTraineeEmpiricalValueLogServiceImpl.class);
	@Autowired
	private TevglTraineeEmpiricalValueLogMapper tevglTraineeEmpiricalValueLogMapper;
	@Autowired
	private TevglTchRoomPereTraineeAnswerMapper tevglTchRoomPereTraineeAnswerMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/trainee/tevgltraineeempiricalvaluelog/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTraineeEmpiricalValueLog> tevglTraineeEmpiricalValueLogList = tevglTraineeEmpiricalValueLogMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTraineeEmpiricalValueLogList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/trainee/tevgltraineeempiricalvaluelog/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTraineeEmpiricalValueLogList = tevglTraineeEmpiricalValueLogMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTraineeEmpiricalValueLogList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTraineeEmpiricalValueLog
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/trainee/tevgltraineeempiricalvaluelog/save")
	public R save(@RequestBody(required = false) TevglTraineeEmpiricalValueLog tevglTraineeEmpiricalValueLog) throws OssbarException {
		ValidatorUtils.check(tevglTraineeEmpiricalValueLog);
		tevglTraineeEmpiricalValueLog.setEvId(Identities.uuid());
		tevglTraineeEmpiricalValueLogMapper.insert(tevglTraineeEmpiricalValueLog);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTraineeEmpiricalValueLog
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/trainee/tevgltraineeempiricalvaluelog/update")
	public R update(@RequestBody(required = false) TevglTraineeEmpiricalValueLog tevglTraineeEmpiricalValueLog) throws OssbarException {
	    ValidatorUtils.check(tevglTraineeEmpiricalValueLog);
		tevglTraineeEmpiricalValueLogMapper.update(tevglTraineeEmpiricalValueLog);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/trainee/tevgltraineeempiricalvaluelog/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglTraineeEmpiricalValueLogMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/trainee/tevgltraineeempiricalvaluelog/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglTraineeEmpiricalValueLogMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/trainee/tevgltraineeempiricalvaluelog/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglTraineeEmpiricalValueLogMapper.selectObjectById(id));
	}
	
	/**
	 * 查看经验值
	 * @param ctId
	 * @param traineeId
	 * @param loginUserId
	 * @return
	 */
	@Override
	@GetMapping("/viewEmpiricalValueLogs")
	public R viewEmpiricalValueLogs(String ctId, String traineeId, String loginUserId, Integer pageNum, Integer pageSize) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(traineeId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("pageNum", pageNum = pageNum == null ? 1 : pageNum);
		params.put("pageSize", pageSize = pageSize == null ? 10 : pageSize);
		params.put("ctId", ctId);
		params.put("traineeId", traineeId);
		params.put("state", "Y");
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String,Object>> list = tevglTraineeEmpiricalValueLogMapper.selectSimpleListMapByMap(query);
		list.stream().forEach(empiricalValueLog -> {
			if (StrUtils.isNull(empiricalValueLog.get("msg"))) {
				String msg = "参与课堂【"+empiricalValueLog.get("name")+"】";
				if (!StrUtils.isNull(empiricalValueLog.get("activtityTitle"))) {
					String name = handlesignName(empiricalValueLog);
					msg += "的活动["+empiricalValueLog.get("activtityTitle")+"]，" + name + "获得 " + empiricalValueLog.get("empiricalValue") +" 经验值";
				}
				empiricalValueLog.put("msg", msg);	
			}
		});
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	private String handlesignName(Map<String,Object> empiricalValueLog) {
		if (StrUtils.isNull(empiricalValueLog.get("type"))) {
			return "";
		}
		String type = empiricalValueLog.get("type").toString();
		if (!GlobalActivity.ACTIVITY_8_SIGININ_INFO.equals(type)) {
			return "";
		}
		if (StrUtils.isNull(empiricalValueLog.get("params2"))) {
			return "";
		}
		String signState = empiricalValueLog.get("params2").toString();
		String str = "";
		// 签到状态1正常2迟到3早退4旷课5请假
		switch (signState) {
		case "1":
			str = "【正常】";
			break;
		case "2":
			str = "【迟到】";		
			break;
		case "3":
			str = "【早退】";
			break;
		case "4":
			str = "【旷课】";
			break;
		case "5":
			str = "【请假】";
			break;
		default:
			break;
		}
		return str;
	}

	/**
	 * 查看某学员的当前课堂的经验值、热心解答次数、获取点赞数、课堂表现次数、视频学习个数
	 * @param ctId
	 * @param traineeId
	 * @return
	 */
	@Override
	public R viewNums(String ctId, String traineeId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(traineeId)) {
			return R.error("必传参数为空");
		}
		// 最总返回数据
		Map<String, Object> data = new HashMap<>();
		// 查询条件
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("traineeId", traineeId);
		// 当前学员课堂获取到的总经验值
		Integer currRoomEmpiricalValue = tevglTraineeEmpiricalValueLogMapper.sumEmpiricalValueByMap(params);
		// 热心解答次数（暂定取评论条数）
		Integer commentNum = 0;
		// 获取点赞数
		Integer likeNums = 0;
		// 课堂表现
		Map<String, Object> performanceData = getPerformanceData(ctId, traineeId);
		// 视频学习
		Map<String, Object> videoStudyData = new HashMap<String, Object>();
		videoStudyData.put("num", 0);
		videoStudyData.put("minute", "1.024");
		// 返回
		data.put("currRoomEmpiricalValue", currRoomEmpiricalValue);
		data.put("commentNum", commentNum);
		data.put("likeNums", likeNums);
		data.put("performanceData", performanceData);
		data.put("videoStudyData", videoStudyData);
		return R.ok();
	}
	
	/**
	 * 获取某学员某课堂中的课堂表现次数，和得到的对应的总分（经验值）
	 * @param ctId
	 * @param traineeId
	 * @return {"num":"", "score":""}
	 */
	private Map<String, Object> getPerformanceData(String ctId, String traineeId) {
		Map<String, Object> performanceData = new HashMap<String, Object>();
		performanceData.put("ctId", ctId);
		performanceData.put("traineeId", traineeId);
		List<TevglTchRoomPereTraineeAnswer> list = tevglTchRoomPereTraineeAnswerMapper.selectListByMap(performanceData);
		int score = 0;
		if (list != null && list.size() > 0) {
			for (TevglTchRoomPereTraineeAnswer tevglTchRoomPereTraineeAnswer : list) {
				score += tevglTchRoomPereTraineeAnswer.getEmpiricalValue();
			}
		}
		performanceData.clear();
		performanceData.put("num", list == null ? 0 : list.size());
		performanceData.put("score", score);
		return performanceData;
	}
}
