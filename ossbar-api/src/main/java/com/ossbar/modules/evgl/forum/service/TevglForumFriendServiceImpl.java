package com.ossbar.modules.evgl.forum.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.forum.api.TevglForumFriendService;
import com.ossbar.modules.evgl.forum.domain.TevglForumFriend;
import com.ossbar.modules.evgl.forum.persistence.TevglForumFriendMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
@RequestMapping("/forum/tevglforumfriend")
public class TevglForumFriendServiceImpl implements TevglForumFriendService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglForumFriendServiceImpl.class);
	@Autowired
	private TevglForumFriendMapper tevglForumFriendMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private UploadFileUtils uploadPathUtils;
	@Autowired
	private TsysAttachService tsysAttachService;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/forum/tevglforumfriend/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglForumFriend> tevglForumFriendList = tevglForumFriendMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglForumFriendList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglForumFriendList, "createUserId", "updateUserId");
		convertUtil.convertDict(tevglForumFriendList, "state", "state4");
		convertUtil.convertDict(tevglForumFriendList, "showIndex", "state3");
		if (tevglForumFriendList != null && tevglForumFriendList.size() > 0) {
			tevglForumFriendList.stream().forEach(tevglForumFriend -> {
				tevglForumFriend.setFriendLogo(uploadPathUtils.stitchingPath(tevglForumFriend.getFriendLogo(), "24"));
			});
		}
		PageUtils pageUtil = new PageUtils(tevglForumFriendList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/forum/tevglforumfriend/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglForumFriendList = tevglForumFriendMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglForumFriendList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglForumFriendList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglForumFriend
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/forum/tevglforumfriend/save")
	public R save(@RequestBody(required = false) TevglForumFriend tevglForumFriend) throws CreatorblueException {
		String uuid = Identities.uuid();
		tevglForumFriend.setFriendId(uuid);
		tevglForumFriend.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglForumFriend.setCreateTime(DateUtils.getNowTimeStamp());
		tevglForumFriend.setState("Y");
		tevglForumFriend.setShowIndex(StrUtils.isEmpty(tevglForumFriend.getShowIndex()) ? "N" : tevglForumFriend.getShowIndex());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("friendType", tevglForumFriend.getFriendType());
		Integer maxSortNum = tevglForumFriendMapper.getMaxSortNum(map);
		tevglForumFriend.setSortNum(maxSortNum);
		//ValidatorUtils.check(tevglForumFriend);
		tevglForumFriendMapper.insert(tevglForumFriend);
		// 如果上传了附件
		String attachId = tevglForumFriend.getFriendLogoAttachId();
		if (StrUtils.isNotEmpty(attachId)) {
			tsysAttachService.updateAttachForAdd(attachId, uuid, "24");
		}
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglForumFriend
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/forum/tevglforumfriend/update")
	public R update(@RequestBody(required = false) TevglForumFriend tevglForumFriend) throws CreatorblueException {
	    tevglForumFriend.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglForumFriend.setUpdateTime(DateUtils.getNowTimeStamp());
	    //ValidatorUtils.check(tevglForumFriend);
		tevglForumFriendMapper.update(tevglForumFriend);
		// 如果上传了附件
		String attachId = tevglForumFriend.getFriendLogoAttachId();
		if (StrUtils.isNotEmpty(attachId)) {
			tsysAttachService.updateAttachForEdit(attachId, tevglForumFriend.getFriendId(), "24");
		}
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/forum/tevglforumfriend/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglForumFriendMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/forum/tevglforumfriend/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		tevglForumFriendMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/forum/tevglforumfriend/view")
	public R view(@PathVariable("id") String id) {
		TevglForumFriend tevglForumFriend = tevglForumFriendMapper.selectObjectById(id);
		if (tevglForumFriend != null) {
			tevglForumFriend.setFriendLogo(uploadPathUtils.stitchingPath(tevglForumFriend.getFriendLogo(), "24"));
		}
		return R.ok().put(Constant.R_DATA, tevglForumFriend);
	}

	/**
	 * 更新状态
	 * @param tevglForumFriend
	 * @return
	 */
	@Override
	public R updateState(TevglForumFriend tevglForumFriend) {
		if (StrUtils.isEmpty(tevglForumFriend.getFriendId()) && StrUtils.isEmpty(tevglForumFriend.getState())) {
			return R.error("必传参数为空");
		}
		tevglForumFriendMapper.update(tevglForumFriend);
		return R.ok("状态更新成功");
	}
}
