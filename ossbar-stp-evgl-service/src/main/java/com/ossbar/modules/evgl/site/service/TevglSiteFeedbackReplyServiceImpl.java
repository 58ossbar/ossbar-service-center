package com.ossbar.modules.evgl.site.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteFeedbackReplyService;
import com.ossbar.modules.evgl.site.domain.TevglSiteFeedback;
import com.ossbar.modules.evgl.site.domain.TevglSiteFeedbackReply;
import com.ossbar.modules.evgl.site.persistence.TevglSiteFeedbackMapper;
import com.ossbar.modules.evgl.site.persistence.TevglSiteFeedbackReplyMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: 意见反馈回复</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/site/tevglsitefeedbackreply")
public class TevglSiteFeedbackReplyServiceImpl implements TevglSiteFeedbackReplyService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteFeedbackReplyServiceImpl.class);
	@Autowired
	private TevglSiteFeedbackReplyMapper tevglSiteFeedbackReplyMapper;
	@Autowired
	private TevglSiteFeedbackMapper tevglSiteFeedbackMapper;
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
	@SentinelResource("/site/tevglsitefeedbackreply/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteFeedbackReply> tevglSiteFeedbackReplyList = tevglSiteFeedbackReplyMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteFeedbackReplyList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglSiteFeedbackReplyList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglSiteFeedbackReplyList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsitefeedbackreply/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteFeedbackReplyList = tevglSiteFeedbackReplyMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteFeedbackReplyList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglSiteFeedbackReplyList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteFeedbackReply
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsitefeedbackreply/save")
	public R save(@RequestBody(required = false) TevglSiteFeedbackReply tevglSiteFeedbackReply) throws OssbarException {
		tevglSiteFeedbackReply.setReplyId(Identities.uuid());
		tevglSiteFeedbackReply.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSiteFeedbackReply.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglSiteFeedbackReply);
		tevglSiteFeedbackReplyMapper.insert(tevglSiteFeedbackReply);
		// 更新为已回复
		TevglSiteFeedback t = new TevglSiteFeedback();
		t.setFeedbackId(tevglSiteFeedbackReply.getFeedbackId());
		t.setHasReplied("Y");
		tevglSiteFeedbackMapper.update(t);
		return R.ok("保存成功");
	}
	/**
	 * 修改
	 * @param tevglSiteFeedbackReply
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsitefeedbackreply/update")
	public R update(@RequestBody(required = false) TevglSiteFeedbackReply tevglSiteFeedbackReply) throws OssbarException {
	    tevglSiteFeedbackReply.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSiteFeedbackReply.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglSiteFeedbackReply);
		tevglSiteFeedbackReplyMapper.update(tevglSiteFeedbackReply);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsitefeedbackreply/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglSiteFeedbackReplyMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsitefeedbackreply/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglSiteFeedbackReplyMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsitefeedbackreply/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglSiteFeedbackReplyMapper.selectObjectById(id));
	}

	@Override
	public R viewFeedbackReplyInfo(String feedbackId) {
		Map<String, Object> map = new HashMap<>();
		map.put("feedbackId", feedbackId);
		map.put("createUserId", serviceLoginUtil.getLoginUserId());
		List<TevglSiteFeedbackReply> list = tevglSiteFeedbackReplyMapper.selectListByMap(map);
		return R.ok().put(Constant.R_DATA, list);
	}
}
