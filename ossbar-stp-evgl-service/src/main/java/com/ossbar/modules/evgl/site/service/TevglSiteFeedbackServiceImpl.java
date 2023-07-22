package com.ossbar.modules.evgl.site.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.modules.common.DateUtil;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.utils.tool.DateUtils;
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
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.evgl.site.api.TevglSiteFeedbackService;
import com.ossbar.modules.evgl.site.domain.TevglSiteFeedback;
import com.ossbar.modules.evgl.site.domain.TevglSiteFeedbackReply;
import com.ossbar.modules.evgl.site.persistence.TevglSiteFeedbackMapper;
import com.ossbar.modules.evgl.site.persistence.TevglSiteFeedbackReplyMapper;

/**
 * <p> Title: 意见反馈表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/site/tevglsitefeedback")
public class TevglSiteFeedbackServiceImpl implements TevglSiteFeedbackService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteFeedbackServiceImpl.class);
	@Autowired
	private TevglSiteFeedbackMapper tevglSiteFeedbackMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private UploadPathUtils uploadPathUtils;
	@Autowired
	private TevglSiteFeedbackReplyMapper tevglSiteFeedbackReplyMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsitefeedback/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteFeedback> tevglSiteFeedbackList = tevglSiteFeedbackMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteFeedbackList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglSiteFeedbackList, "createUserId", "updateUserId");
		convertUtil.convertDict(tevglSiteFeedbackList, "state", "state"); 
		convertUtil.convertDict(tevglSiteFeedbackList, "type", "feedbackBigType"); // 反馈的大类型，1网站2小程序3APP
		convertUtil.convertDict(tevglSiteFeedbackList, "feedbackType", "feedbackType"); // 1功能异常2体验问题3新功能建议4其它
		convertUtil.convertDict(tevglSiteFeedbackList, "traineeType", "trainee_type"); // 用户类型1游客2系统用户3学员4教师
		PageUtils pageUtil = new PageUtils(tevglSiteFeedbackList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsitefeedback/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteFeedbackList = tevglSiteFeedbackMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteFeedbackList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglSiteFeedbackList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteFeedback
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsitefeedback/save")
	public R save(@RequestBody(required = false) TevglSiteFeedback tevglSiteFeedback) throws OssbarException {
		tevglSiteFeedback.setFeedbackId(Identities.uuid());
		tevglSiteFeedback.setState("Y");
		tevglSiteFeedback.setHasReplied("N"); // 是否已回复（Y已回复N未回复）
		tevglSiteFeedback.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglSiteFeedback);
		tevglSiteFeedbackMapper.insert(tevglSiteFeedback);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteFeedback
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsitefeedback/update")
	public R update(@RequestBody(required = false) TevglSiteFeedback tevglSiteFeedback) throws OssbarException {
	    tevglSiteFeedback.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSiteFeedback.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglSiteFeedback);
		tevglSiteFeedbackMapper.update(tevglSiteFeedback);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsitefeedback/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglSiteFeedbackMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsitefeedback/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		// 如果有回复则先删除回复表
		Map<String, Object> params = new HashMap<>();
		params.put("feedbackIds", Arrays.asList(ids));
		List<TevglSiteFeedbackReply> replyList = tevglSiteFeedbackReplyMapper.selectListByMap(params);
		if (replyList != null && replyList.size() > 0) {
			replyList.stream().forEach(a -> {
				tevglSiteFeedbackReplyMapper.delete(a.getReplyId());
			});	
		}
		tevglSiteFeedbackMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsitefeedback/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglSiteFeedbackMapper.selectObjectById(id));
	}

	/**
	 * 查询意见反馈
	 * @param params
	 * @return
	 */
	@Override
	@GetMapping("/queryFeedbackInfo")
	public R queryFeedbackInfo(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> tevglSiteFeedbackList = tevglSiteFeedbackMapper.selectListMapByMap(query);
		convertUtil.convertDict(tevglSiteFeedbackList, "state", "state"); 
		convertUtil.convertDict(tevglSiteFeedbackList, "type", "feedbackBigType"); // 反馈的大类型，1网站2小程序3APP
		convertUtil.convertDict(tevglSiteFeedbackList, "feedbackType", "feedbackType"); // 1功能异常2体验问题3新功能建议4其它
		convertUtil.convertDict(tevglSiteFeedbackList, "traineeType", "trainee_type"); // 用户类型1游客2系统用户3学员4教师
		if (tevglSiteFeedbackList != null && tevglSiteFeedbackList.size() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
			// 查询回复内容
			List<Object> feedbackIds = tevglSiteFeedbackList.stream().map(a -> a.get("feedbackId")).collect(Collectors.toList());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("feedbackIds", feedbackIds);
			List<TevglSiteFeedbackReply> feedbackReplyList = tevglSiteFeedbackReplyMapper.selectListByMap(map);
			convertUtil.convertUserId2RealName(feedbackReplyList, "createUserId", "updateUserId");
			convertUtil.convertUserId2RealName(feedbackReplyList, "createUserId", "updateUserId");
			feedbackReplyList.stream().forEach(a -> {
				Date date = DateUtil.convertStringToDate(a.getCreateTime());
				a.setCreateTime(format.format(date));
			});
			tevglSiteFeedbackList.stream().forEach(feedback -> {
				// 日期处理
				Date date = DateUtil.convertStringToDate(feedback.get("createTime").toString());
				feedback.put("createTime", format.format(date));
				// 头像处理
				String traineePic = feedback.get("traineePic").toString();
				feedback.put("traineePic", uploadPathUtils.stitchingPath(traineePic, "16"));
				List<TevglSiteFeedbackReply> children = feedbackReplyList.stream().filter(a -> a.getFeedbackId().equals(feedback.get("feedbackId"))).collect(Collectors.toList());
				feedback.put("children", children);
			});
		}
		PageUtils pageUtil = new PageUtils(tevglSiteFeedbackList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
}
