package com.ossbar.modules.evgl.medu.me.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.medu.me.persistence.TmeduMeFavorityMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
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
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.GlobalFavority;
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.evgl.forum.api.TevglForumBlogPostService;
import com.ossbar.modules.evgl.medu.me.api.TmeduMeFavorityService;
import com.ossbar.modules.evgl.medu.me.domain.TmeduMeFavority;
import com.ossbar.modules.evgl.question.persistence.TevglQuestionChoseMapper;
import com.ossbar.modules.evgl.question.service.TevglQuestionsInfoServiceImpl;
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
@RequestMapping("/me/tmedumefavority")
public class TmeduMeFavorityServiceImpl implements TmeduMeFavorityService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TmeduMeFavorityServiceImpl.class);
	@Autowired
	private TmeduMeFavorityMapper tmeduMeFavorityMapper;
	@Autowired
	private UploadPathUtils uploadPathUtils;
	@Autowired
	private ConvertUtil convertUtil;
	/** 标识:课堂创建者isOwner（boolean值，true是false否） */
	private static String IS_OWNER = "isOwner";
	@Autowired
	private TevglQuestionChoseMapper tevglQuestionChoseMapper;
	@Autowired
	private TevglQuestionsInfoServiceImpl tevglQuestionsInfoServiceImpl;
	@Autowired
	private TevglForumBlogPostService tevglForumBlogPostService;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/me/tmedumefavority/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TmeduMeFavority> tmeduMeFavorityList = tmeduMeFavorityMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tmeduMeFavorityList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/me/tmedumefavority/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tmeduMeFavorityList = tmeduMeFavorityMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tmeduMeFavorityList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tmeduMeFavority
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/me/tmedumefavority/save")
	public R save(@RequestBody(required = false) TmeduMeFavority tmeduMeFavority) throws OssbarException {
		tmeduMeFavority.setFavorityId(Identities.uuid());
		ValidatorUtils.check(tmeduMeFavority);
		tmeduMeFavorityMapper.insert(tmeduMeFavority);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tmeduMeFavority
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/me/tmedumefavority/update")
	public R update(@RequestBody(required = false) TmeduMeFavority tmeduMeFavority) throws OssbarException {
	    ValidatorUtils.check(tmeduMeFavority);
		tmeduMeFavorityMapper.update(tmeduMeFavority);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/me/tmedumefavority/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tmeduMeFavorityMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/me/tmedumefavority/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tmeduMeFavorityMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/me/tmedumefavority/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tmeduMeFavorityMapper.selectObjectById(id));
	}

	@Override
	public List<TmeduMeFavority> selectListByMap(Map<String, Object> map) {
		return tmeduMeFavorityMapper.selectListByMap(map);
	}

	/**
	 *  查询收藏的课堂数据
	 * @param map
	 * @return
	 */
	@Override
	public R selectClassroomList(Map<String, Object> params, String loginUserId) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> list = tmeduMeFavorityMapper.selectClassroomList(query);
		convertUtil.convertDict(list, "classroomStateName", "classroomState"); // 课堂状态
		convertUtil.convertOrgId(list, "orgIdTeacher");
		List<Map<String, Object>> classroomTraineeList = tevglTchClassroomMapper.getCtIdsByTraineeId(loginUserId);
		list.stream().forEach(info -> {
			// 课堂图片
			info.put("pic", uploadPathUtils.stitchingPath(info.get("pic"), "14"));
			// 处理课堂是否属于当前登录用户
			isThisClassroomBelongToLoginUser(info, loginUserId);
			// 处理是否申请了加入课堂,并通过了审核
			isApplyAndIsPass(info, loginUserId, classroomTraineeList);
			// 处理是否加入了此课堂
			isHasJoinedThisClassroom(info, loginUserId, classroomTraineeList);
		});
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 判断课堂是否属于当前登录用户
	 * @param classroomInfo 课堂信息
	 * @param loginUserId 当前登录用户id
	 */
	private void isThisClassroomBelongToLoginUser(Map<String, Object> classroomInfo, String loginUserId) {
		if (StrUtils.isEmpty(loginUserId)) {
			// 如果没登陆,则不是创建者
			classroomInfo.put(IS_OWNER, false);
		} else {
			if (loginUserId.equals(classroomInfo.get("createUserId"))) {
				classroomInfo.put(IS_OWNER, true);
			} else {
				classroomInfo.put(IS_OWNER, false);
			}
		}
	}
	
	/**
     * 判断非课堂创建者时，当前登录用户是否申请了加入此课堂，是否被审核通过
     * @param classroomInfo
     * @param loginUserId
     * param classroomTraineeList
     */
    private void isApplyAndIsPass(Map<String, Object> classroomInfo, String loginUserId, List<Map<String, Object>> classroomTraineeList) {
        if (!loginUserId.equals(classroomInfo.get("createUserId"))) {
            // 如果加入课堂需要审核
            // 返回状态标识是否已审核，课堂成员表中记录为有效则认为通过了审核
            // 前端[待审核]的盖章，根据ischeck=Y且isApply=true且isPass=false才显示
            classroomInfo.put("isApply", false); // 标识是否申请了加入课堂，t_evgl_tch_classroom_trainee表有记录则认为申请了
            classroomInfo.put("isPass", false);
            if (classroomTraineeList == null || classroomTraineeList.size() == 0) {
                classroomInfo.put("isPass", false);
                classroomInfo.put("isApply", false);
            } else {
                boolean isPass = classroomTraineeList.stream().anyMatch(a -> a.get("ctId").equals(classroomInfo.get("ctId")) &&  a.get("state").equals("Y"));
                boolean isApply = classroomTraineeList.stream().anyMatch(a -> a.get("ctId").equals(classroomInfo.get("ctId")));
                classroomInfo.put("isPass", isPass);
                classroomInfo.put("isApply", isApply);
            }
        }
    }

    /**
	 * 处理是否加入了此课堂（如果课堂需要审核，则认为审核通过了，才算加入了此课堂）
	 * @param classroomInfo 课堂信息
	 * @param loginUserId 当前登录用户
	 * @param classroomTraineeList
	 */
	private void isHasJoinedThisClassroom(Map<String, Object> classroomInfo, String loginUserId, List<Map<String, Object>> classroomTraineeList) {
		boolean isJoined = classroomTraineeList.stream().anyMatch(a -> a.get("ctId").equals(classroomInfo.get("ctId")) && "Y".equals(a.get("state")));
		classroomInfo.put("isJoined", isJoined);
	}
    
	/**
	 * 查询收藏的视频
	 * @param params
	 * @param loginUserId
	 * @return
	 */
	@Override
	@GetMapping("/selectVideoList")
	public R selectVideoList(Map<String, Object> params, String loginUserId) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> list = tmeduMeFavorityMapper.selectVideoList(query);
		list.stream().forEach(info -> {
			// 视频图片
			info.put("videoId", "1");
			info.put("videoName", "SpringBoot.mp4");
			info.put("playCount", 10240);
			info.put("videoPic", uploadPathUtils.stitchingPath(info.get("pic"), "14"));
		});
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 查询收藏的题目
	 * @param params
	 * @param loginUserId
	 * @data 2020年11月20日
	 * @author zyl改
	 * @return
	 */
	@Override
	@GetMapping("/selectQuestionList")
	public R selectQuestionList(Map<String, Object> params, String loginUserId) {
		params.put("traineeId", loginUserId);  //查询当前登录用户收藏的题目
		params.put("favorityType", GlobalFavority.FAVORITY_6_QUESTION); // 6题目
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		//根据查询条件查询登录用户收藏了题目的记录
		List<Map<String,Object>> list = tmeduMeFavorityMapper.selectQuestionList(query);
		if (list != null && list.size() > 0) {
			// 查询题目的收藏数据
			Map<String, Object> map = new HashMap<>();
			map.put("traineeId", loginUserId);
			map.put("favorityType", GlobalFavority.FAVORITY_6_QUESTION);
			List<TmeduMeFavority> favorityList = tmeduMeFavorityMapper.selectListByMap(map);
			// 字典转换
			convertUtil.convertDict(list, "questionsTypeName", "questions_type");
			convertUtil.convertDict(list, "questionsComplexity", "questions_complexity");
			// 题目选项
			List<Object> questionsIds = list.stream().map(a -> a.get("questionsId")).collect(Collectors.toList());
			map.clear();
			map.put("questionsIds", questionsIds);
			List<Map<String, Object>> allOptionList = tevglQuestionChoseMapper.selectSimpleListByMap(map);
			list.stream().forEach(questionInfo -> {
				List<Map<String, Object>> optionList = allOptionList.stream().filter(optionInfo -> optionInfo.get("questionsId").equals(questionInfo.get("questionsId"))).collect(Collectors.toList());
				questionInfo.put("optionList", optionList);
				boolean isCollected = favorityList.stream().anyMatch(a -> a.getTargetId().equals(questionInfo.get("questionsId")));
				questionInfo.put("isCollected", isCollected);
			});
			// 处理复合题
			tevglQuestionsInfoServiceImpl.handleCompositeQuestionList6(list, map, new ArrayList<>());
		}
		PageUtils pageUtil = new PageUtils(list, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 我的博客收藏列表
	 * @param params
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R selectBlogList(Map<String, Object> params, String loginUserId) {
		return tevglForumBlogPostService.queryBlogList(params, loginUserId, "2");
	}
}
