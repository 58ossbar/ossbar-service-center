package com.ossbar.modules.evgl.book.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.tch.persistence.TevglTchClassMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.evgl.book.api.TevglBookMajorService;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.domain.TevglBookSubperiod;
import com.ossbar.modules.evgl.book.persistence.TevglBookMajorMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubperiodMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClass;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: 职业课程</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/book/tevglbookmajor")
public class TevglBookMajorServiceImpl implements TevglBookMajorService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglBookMajorServiceImpl.class);
	@Autowired
	private TevglBookMajorMapper tevglBookMajorMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TevglBookSubperiodMapper tevglBookSubperiodMapper;
	@Autowired
	private TevglBookSubjectMapper tevglBookSubjectMapper;
	@Autowired
	private UploadPathUtils uploadPathUtils;
	@Value("${com.ossbar.file-access-path}")
	public String ossbarFieAccessPath;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglTchClassMapper tevglTchClassMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/book/tevglbookmajor/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglBookMajor> tevglBookMajorList = tevglBookMajorMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglBookMajorList, "createUserId", "updateUserId");
		convertUtil.convertOrgId(tevglBookMajorList, "orgId");
		PageUtils pageUtil = new PageUtils(tevglBookMajorList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/book/tevglbookmajor/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglBookMajorList = tevglBookMajorMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglBookMajorList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglBookMajorList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglBookMajor
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/book/tevglbookmajor/save")
	public R save(@RequestBody(required = false) TevglBookMajor tevglBookMajor) throws OssbarException {
		tevglBookMajor.setMajorId(Identities.uuid());
		tevglBookMajor.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglBookMajor.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglBookMajor);
		tevglBookMajorMapper.insert(tevglBookMajor);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglBookMajor
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/book/tevglbookmajor/update")
	public R update(@RequestBody(required = false) TevglBookMajor tevglBookMajor) throws OssbarException {
	    tevglBookMajor.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglBookMajor.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglBookMajor);
		tevglBookMajorMapper.update(tevglBookMajor);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/book/tevglbookmajor/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglBookMajorMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/book/tevglbookmajor/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		if (ids == null || ids.length == 0) {
			return R.ok();
		}
		List<String> majorIds = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			majorIds.add(ids[i]);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("majorIds", majorIds);
		List<TevglBookSubperiod> list = tevglBookSubperiodMapper.selectListByMap(map);
		if (list != null && list.size() > 0) {
			return R.error("该职业课程路径下还存在课程，请先删除课程");
			/*
			// 删除职业课程路径于课程的关系
			list.forEach(a -> {
				tevglBookSubperiodMapper.delete(a.getSubperiodId());
			});
			*/
		}
		tevglBookMajorMapper.deleteBatch(ids);
		return R.ok();
	}
	
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("viewForMgr/{id}")
	@SentinelResource("/book/tevglbookmajor/viewForMgr")
	public R viewForMgr(@PathVariable("id") String id) {
		TevglBookMajor major = tevglBookMajorMapper.selectObjectById(id);
		if (major == null) {
			return R.ok();
		}
		major.setMajorLogo(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("11") + "/" + major.getMajorLogo());
		return R.ok().put(Constant.R_DATA, major);
	}
	
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/book/tevglbookmajor/view")
	public R view(@PathVariable("id") String id) {
		TevglBookMajor major = tevglBookMajorMapper.selectObjectById(id);
		if (major == null) {
			return R.ok();
		}
		major.setMajorLogo(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("11") + "/" + major.getMajorLogo());
		convertUtil.convertOrgId(major, "orgId");
		// resultList 存储分组后的数据
		List<List<TevglBookSubperiod>> resultList = new ArrayList<>();
		// 查询该职业课程路径下的课程
		Map<String, Object> map = new HashMap<>();
		map.put("majorId", major.getMajorId());
		List<TevglBookSubperiod> tevglBookSubperiodList = tevglBookSubperiodMapper.selectListByMap(map);
		// 根据所属学期分组
		tevglBookSubperiodList.stream().collect(Collectors.groupingBy(TevglBookSubperiod::getTerm, Collectors.toList()))
		.forEach((name, dataList) -> {
			// 查询课程信息
			dataList.forEach(a -> {
				TevglBookSubject subject = tevglBookSubjectMapper.selectObjectById(a.getSubjectId());
				if (subject != null) {
					// 图片处理
					if (subject.getSubjectLogo() != null && !"".equals(subject.getSubjectLogo())) {
						int i = subject.getSubjectLogo().indexOf(ossbarFieAccessPath);
						if (i == -1) {
							subject.setSubjectLogo(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("10") + "/" + subject.getSubjectLogo());
						}
					}
				}
				a.setTevglBookSubject(subject);
			});
			resultList.add(dataList);
		});
		
		// 进一步处理数据，便于前端识别
		List<Map<String, Object>> ok = new ArrayList<>();
		resultList.forEach(list -> {
			Map<String, Object> mm = new HashMap<>();
			List<TevglBookSubject> subjects = new ArrayList<>();
			list.forEach(a -> { // a此时是实体TevglBookSubperiod，利用map的key不能重复，取值
				mm.put("subperiodId", a.getSubperiodId());
				mm.put("majorId", a.getMajorId());
				mm.put("subjectId", a.getSubjectId());
				mm.put("sortNum", a.getSortNum());
				mm.put("term", a.getTerm());
				mm.put("subjectProperty", a.getSubjectProperty());
				mm.put("classHour", a.getClassHour());
				mm.put("classScore", a.getClassScore());
				// 将课程加入集合
				subjects.add(a.getTevglBookSubject());
			});
			mm.put("subjectList", subjects); // 课程集合
			ok.add(mm);
		});
		
		// 计算总课时、总学分
		BigDecimal totalClassHour = new BigDecimal(0);
		BigDecimal totalClassScore = new BigDecimal(0);
		for (Map<String, Object> k : ok) {
			if (k.get("subjectList") != null) {
				@SuppressWarnings("unchecked")
				List<TevglBookSubject> list = (List<TevglBookSubject>) k.get("subjectList");
				for (TevglBookSubject subject : list) {
					if (subject.getClassHour() != null) {
						totalClassHour = totalClassHour.add(subject.getClassHour());
					}
					if (subject.getClassScore() != null) {
						totalClassScore = totalClassScore.add(subject.getClassScore());
					}
				}
			}
		}
		convertUtil.convertDict(ok, "term", "term"); // 所属学期
		return R.ok().put(Constant.R_DATA, major)
				.put("more", ok) // 学期下的课程
				.put("totalClassHour", totalClassHour)
				.put("totalClassScore", totalClassScore);
	}
	
	/**
	 * <p>复制</p>
	 * @author huj
	 * @data 2019年7月9日
	 * @param id
	 * @return
	 */
	@Override
	@GetMapping("/copy/{id}")
	@SentinelResource("/book/tevglbookmajor/copy")
	public R copy(@PathVariable("id") String id) {
		TevglBookMajor major = tevglBookMajorMapper.selectObjectById(id);
		if (major == null) {
			return R.error("复制失败");
		}
		// 填充并保存职业课程路径信息
		TevglBookMajor tb = fillMyBookMajorInfo(major.getParentId(), major);
		tevglBookMajorMapper.insert(tb);
		// 查询并填充和保存专业课程关系
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("majorId", major.getMajorId());
		List<TevglBookSubperiod> list = tevglBookSubperiodMapper.selectListByMap(map);
		if (list.size() > 0) {
			list.forEach(a -> {
				TevglBookSubperiod obj = fillMySubperiodInfo(tb.getMajorId(), null, a);
				tevglBookSubperiodMapper.insert(obj);
			});
		}
		return R.ok("复制成功");
	}
	
	/**
	 * <p>填充职业课程路径基本信息</p>
	 * @author huj
	 * @data 2019年7月9日
	 * @param parentId 父ID
	 * @param major 职业课程路径对象
	 * @return 返回已经填充好的对象
	 */
	private TevglBookMajor fillMyBookMajorInfo(String parentId, TevglBookMajor major) {
		TevglBookMajor tb = new TevglBookMajor(); 
		tb.setMajorId(major.getMajorId()); // 专业方向主键ID
		tb.setOrgId(major.getOrgId()); // 所属院校
		tb.setMajorName(major.getMajorName()); // 专业名称
		tb.setMajorLogo(major.getMajorLogo()); // 专业logo图
		tb.setMajorDesc(major.getMajorDesc()); // 专业简介(文本)
		tb.setMajorRemark(major.getMajorRemark()); // 专业详细描述(富文本)
		tb.setMajorType(major.getMajorType()); // 专业类型(拓展字段)
		tb.setParentId(parentId); // 父级ID
		tb.setLevel(major.getLevel()); // 层级
		tb.setShowIndex(major.getShowIndex()); // 是否推荐到首页(Y是N否)
		tb.setSortNum(major.getSortNum()); // 排序号
		tb.setCreateUserId(serviceLoginUtil.getLoginUserId()); // 创建人
		tb.setCreateTime(DateUtils.getNowTimeStamp()); // 创建时间
		tb.setState(tb.getState()); // 状态(Y有效N无效)
		return tb;
	}
	

	/**
	 * <p>填充专业课程关系信息</p>
	 * @author huj
	 * @data 2019年7月8日
	 * @param subperiod
	 * @param sb
	 * @return
	 */
	private TevglBookSubperiod fillMySubperiodInfo(String majorId, String subjectId, TevglBookSubperiod subperiod) {
		TevglBookSubperiod obj = new TevglBookSubperiod();
		obj.setSubperiodId(Identities.uuid()); // 主键ID
		obj.setMajorId(majorId); // 专业ID
		obj.setSubjectId(subjectId); // 课程ID
		obj.setSortNum(subperiod.getSortNum()); // 排序号
		obj.setTerm(subperiod.getTerm()); // 所属学期(来源字典)
		obj.setSubjectProperty(subperiod.getSubjectProperty()); // 课程属性(选修or必修)
		obj.setClassHour(subperiod.getClassHour()); // 课时
		obj.setClassScore(subperiod.getClassScore()); // 学分
		obj.setCreateUserId(serviceLoginUtil.getLoginUserId()); // 创建人 
		obj.setCreateTime(DateUtils.getNowTimeStamp()); // 创建时间
		return obj;
	}
	
	/**
	 * <p>前端查询该专业下的课程</p>
	 * @author huj
	 * @data 2019年7月11日
	 * @param map
	 * @return
	 */
	@Override
	@GetMapping("/selectListMapByMapForWeb")
	public List<Map<String, Object>> selectListMapByMapForWeb(@RequestParam Map<String, Object> map){
		String type = (String)map.get("type");
		map.put("sidx", "hot");
		map.put("order", "desc,sort_num asc");
		map.put("state", "Y");
		List<Map<String, Object>> majorList = tevglBookMajorMapper.selectListMapByMapForWeb(map);
		// 获取所有有效的课堂
		if (StrUtils.isNotEmpty(type) && "countClassroomNum".equals(type)) {
			map.clear();
			map.put("state", "Y");
			List<Map<String, Object>> classroomList = tevglTchClassroomMapper.selectListMapForCommon(map);
			majorList.stream().forEach(major -> {
				// 计算此专业下的课堂数量
				long classroomNum = classroomList.stream().filter(a -> a.get("majorId").equals(major.get("majorId"))).count();
				major.put("classroomNum", classroomNum);
			});
		}
		return majorList;
	}
	
	

	/**
	 * <p>查询所有(无分页)</p>
	 * @author huj
	 * @data 2019年7月12日
	 * @param params
	 * @return
	 */
	@Override
	@GetMapping("/queryAll")
	public List<TevglBookMajor> queryAll(Map<String, Object> params) {
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		params.put("state", "Y");
		List<TevglBookMajor> tevglBookMajorList = tevglBookMajorMapper.selectListByMap(params);
		tevglBookMajorList.forEach(a -> {
			//图片路径处理
			a.setMajorLogo(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("11") + "/" + a.getMajorLogo());
			Map<String, Object> map = new HashMap<>();
			map.put("state", "Y");
			map.put("majorId", a.getMajorId());
			List<TevglBookSubject> list = tevglBookSubjectMapper.selectListByMapForWeb(map);
			if (list != null && list.size() > 0) {
				a.setSubjectTotalSize(list.size());
			} else {
				a.setSubjectTotalSize(0);
			}
		});
		return tevglBookMajorList;
	}

	/**
	 * <p>根据条件查询记录</p>
	 * @author huj
	 * @data 2019年7月27日
	 * @param map
	 * @return
	 */
	@Override
	public R selectListByMapForWeb(Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglBookMajor> tevglBookMajorList = tevglBookMajorMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglBookMajorList, "createUserId", "updateUserId");
		convertUtil.convertOrgId(tevglBookMajorList, "orgId");
		tevglBookMajorList.forEach(a -> {
			//图片路径处理
			a.setMajorLogo(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("11") + "/" + a.getMajorLogo());
		});
		PageUtils pageUtil = new PageUtils(tevglBookMajorList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * <p>根据条件查询记录</p>
	 * @author huj
	 * @data 2019年7月27日
	 * @param map
	 * @return
	 */
	@Override
	@GetMapping("/queryForTree")
	@SentinelResource("/book/tevglbookmajor/queryForTree")
	public R queryForTree(Map<String, Object> map) {
		map.put("sidx", "sort_num");
		map.put("order", "asc");
		Query query = new Query(map);
		List<TevglBookMajor> tevglBookMajorList = tevglBookMajorMapper.selectListByMap(query);
		return R.ok().put(Constant.R_DATA, tevglBookMajorList);
	}

	/**
	 * <p>根据条件查询记录，无分页</p>  
	 * @author huj
	 * @data 2019年8月20日	
	 * @param map
	 * @return
	 */
	@Override
	@GetMapping("/selectListByMap")
	public List<TevglBookMajor> selectListByMap(@RequestParam Map<String, Object> params) {
		params.put("state", "Y");
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		List<TevglBookMajor> list = tevglBookMajorMapper.selectListByMap(params);
		return list;
	}
	
	/**
	 * <b>职业路径与班级组成层次结构的数据</b>  
	 * @author huj
	 * @param name
	 * @return
	 */
	@Override
	@GetMapping("/queryMajorClassTreeData")
	public List<Map<String, Object>> queryMajorClassTreeData(String name) {
		// 查出职业路径
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", "Y");
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		List<TevglBookMajor> list = tevglBookMajorMapper.selectListByMap(params);
		List<Map<String, Object>> majorList = list.stream().map(a -> {
			Map<String, Object> info = new HashMap<>();
			info.put("id", a.getMajorId());
			info.put("name", a.getMajorName());
			return info;
		}).collect(Collectors.toList());
		// 查出班级
		params.clear();
		List<TevglTchClass> classList = tevglTchClassMapper.selectListByMap(params);
		majorList.stream().forEach(major -> {
			List<Map<String, Object>> children = classList.stream()
				.filter(a -> a.getMajorId().equals(major.get("id")))
				.map(a -> {
					Map<String, Object> info = new HashMap<>();
					info.put("id", a.getClassId());
					info.put("name", a.getClassName());
					return info;
				})
				.collect(Collectors.toList());
			if (children != null && children.size() > 0) {
				major.put("children", children);
			}
		});
		return majorList;
	}

	@Override
	public List<Map<String, Object>> queryMajorSubjectTreeData(String name) {
		// 查出职业路径
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", "Y");
		params.put("sidx", "sort_num");
		params.put("order", "asc");
		List<TevglBookMajor> list = tevglBookMajorMapper.selectListByMap(params);
		List<Map<String, Object>> majorList = list.stream().map(a -> {
			Map<String, Object> info = new HashMap<>();
			info.put("id", a.getMajorId());
			info.put("name", a.getMajorName());
			return info;
		}).collect(Collectors.toList());
		majorList.stream().forEach(major -> {
			// 查出课程
			params.clear();
			params.put("state", "Y");
			params.put("isSubjectRefNull", "Y");
			params.put("majorId", major.get("id"));
			List<TevglBookSubject> tevglBookSubjectList = tevglBookSubjectMapper.selectListByMapForCommon(params);
			List<Map<String, Object>> children = tevglBookSubjectList.stream()
					.map(a -> {
						Map<String, Object> info = new HashMap<>();
						info.put("id", a.getSubjectId());
						info.put("name", a.getSubjectName());
						return info;
					})
					.collect(Collectors.toList());
			if (children != null && children.size() > 0) {
				major.put("children", children);
			} else {
				major.put("disabled", true);
			}
		});
		return majorList;
	}
	
}
