package com.ossbar.modules.evgl.tch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
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
import com.ossbar.modules.common.enums.BizCodeEnume;
import com.ossbar.modules.evgl.book.vo.RoomBookVo;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomSbService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomSb;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomSbMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
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
@RequestMapping("/tch/tevgltchclassroomsb")
public class TevglTchClassroomSbServiceImpl implements TevglTchClassroomSbService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchClassroomSbServiceImpl.class);
	@Autowired
	private TevglTchClassroomSbMapper tevglTchClassroomSbMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@Override
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchclassroomsb/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTchClassroomSb> tevglTchClassroomSbList = tevglTchClassroomSbMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomSbList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@Override
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchclassroomsb/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchClassroomSbList = tevglTchClassroomSbMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomSbList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTchClassroomSb
	 * @throws OssbarException
	 */
	@Override
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchclassroomsb/save")
	public R save(@RequestBody(required = false) TevglTchClassroomSb tevglTchClassroomSb) throws OssbarException {
		tevglTchClassroomSb.setSbId(Identities.uuid());
		ValidatorUtils.check(tevglTchClassroomSb);
		tevglTchClassroomSbMapper.insert(tevglTchClassroomSb);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTchClassroomSb
	 * @throws OssbarException
	 */
	@Override
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchclassroomsb/update")
	public R update(@RequestBody(required = false) TevglTchClassroomSb tevglTchClassroomSb) throws OssbarException {
	    ValidatorUtils.check(tevglTchClassroomSb);
		tevglTchClassroomSbMapper.update(tevglTchClassroomSb);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@Override
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchclassroomsb/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglTchClassroomSbMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@Override
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchclassroomsb/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		tevglTchClassroomSbMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@Override
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchclassroomsb/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglTchClassroomSbMapper.selectObjectById(id));
	}

	@Override
	public R saveExtraBooksRelation(TevglTchClassroomSb sb, String traineeId) {
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(sb.getCtId());
		if (tevglTchClassroom == null) {
			return R.error("课堂已不存在");
		}
		/*if (!tevglTchClassroom.getPkgId().equals(sb.getCtPkgId())) {
			return R.error(BizCodeEnume.PARAM_INVALID.getCode(), BizCodeEnume.PARAM_INVALID.getMsg());
		}*/
		boolean isCreator = StrUtils.isEmpty(tevglTchClassroom.getReceiverUserId()) && tevglTchClassroom.getCreateUserId().equals(traineeId);
		boolean isReceiver = StrUtils.isNotEmpty(tevglTchClassroom.getReceiverUserId()) && tevglTchClassroom.getReceiverUserId().equals(traineeId);
		if (!isCreator && !isReceiver) {
			return R.error(BizCodeEnume.WITHOUT_PACKAGE.getCode(), BizCodeEnume.WITHOUT_PERMISSION.getMsg());
		}
		// 先删除
		tevglTchClassroomSbMapper.deleteByCtId(sb.getCtId());
		// 再重新生成
		List<TevglTchClassroomSb> insertList = new ArrayList<>();
		sb.getSubjectList().stream().forEach(item -> {
			TevglTchClassroomSb t = new TevglTchClassroomSb();
			t.setSbId(Identities.uuid());
			t.setCtId(sb.getCtId());
			t.setCtPkgId(tevglTchClassroom.getPkgId());
			t.setSubjectId(item.get("subjectId").toString());
			t.setPkgId(item.get("pkgId").toString());
			t.setCreateTime(DateUtils.getNowTimeStamp());
			t.setCreateUserId(traineeId);
			insertList.add(t);
		});
		if (insertList.size() > 0) {
			tevglTchClassroomSbMapper.insertBatch(insertList);
		}
		return R.ok("设置成功");
	}

	@Override
	public R queryExtraBooks(Map<String, Object> params, String traineeId) {
		if (StrUtils.isNull(params.get("ctId")) || StrUtils.isEmpty(traineeId)) {
			return R.error(BizCodeEnume.PARAM_MISSING.getCode(), BizCodeEnume.PARAM_MISSING.getMsg());
		}
		params.put("loginUserId", traineeId);
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<RoomBookVo> bookList = tevglTchClassroomSbMapper.queryExtraBooks(query);
		//List<RoomBookVo> bookList = tevglTchClassroomSbMapper.queryExtraBooksByUnionAll(query);
		PageUtils pageUtil = new PageUtils(bookList,query.getPage(),query.getLimit());
		// 已选择好的教材
		List<String> subjectIdList = tevglTchClassroomSbMapper.findSubjectIdList(params.get("ctId").toString());
		return R.ok().put(Constant.R_DATA, pageUtil).put("subjectIdList", subjectIdList);
	}

	@Override
	public R findExtraBooks(String ctId, String traineeId) {
		// 找到当前课堂正在使用的教材
		RoomBookVo subject = tevglTchClassroomSbMapper.findSubjectByCtId(ctId);
		if (subject != null) {
			// 课堂详情页面中，左上区域的版本号展示的是所引用的教学包版本号
			TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(subject.getRefPkgId());
			if (tevglPkgInfo != null) {
				subject.setPkgVersion(tevglPkgInfo.getPkgVersion());
			}
		}
		// 找到课堂关联的教材
		List<RoomBookVo> subjectList = tevglTchClassroomSbMapper.findSubjectList(ctId);
		if (subjectList != null && subject != null) {
			subjectList.add(0, subject);
		}
		return R.ok().put(Constant.R_DATA, subjectList);
	}
}
