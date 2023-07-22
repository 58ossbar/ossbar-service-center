package com.ossbar.modules.evgl.tch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ossbar.modules.evgl.pkg.persistence.TevglBookpkgTeamMapper;
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
import com.ossbar.modules.common.RoleUtils;
import com.ossbar.modules.common.UploadPathUtils;
import com.ossbar.modules.evgl.book.domain.TevglBookMajor;
import com.ossbar.modules.evgl.book.persistence.TevglBookMajorMapper;
import com.ossbar.modules.evgl.medu.sys.domain.TmeduApiToken;
import com.ossbar.modules.evgl.medu.sys.persistence.TmeduApiTokenMapper;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeam;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.modules.sys.domain.TsysParameter;
import com.ossbar.modules.sys.domain.TsysUserinfo;
import com.ossbar.modules.sys.persistence.TsysParameterMapper;
import com.ossbar.modules.sys.persistence.TsysUserinfoMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.ossbar.utils.tool.TicketDesUtil;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: 【布道师】接口实现类</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchteacher")
public class TevglTchTeacherServiceImpl implements TevglTchTeacherService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchTeacherServiceImpl.class);
	@Autowired
	private TevglTchTeacherMapper tevglTchTeacherMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private TsysAttachService tsysAttachService;
	
	@Value("${com.ossbar.file-access-path}")
	public String ossbarFieAccessPath;
	@Autowired
	private UploadPathUtils uploadPathUtils;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TsysUserinfoMapper tsysUserinfoMapper;
	@Autowired
	private TevglTraineeInfoMapper tevglTraineeInfoMapper;
	@Autowired
	private TmeduApiTokenMapper tmeduApiTokenMapper;
	@Autowired
	private TsysParameterMapper tsysParameterMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglBookMajorMapper tevglBookMajorMapper;
	@Autowired
	private TevglBookpkgTeamMapper tevglBookpkgTeamMapper;
	@Autowired
	private RoleUtils roleUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchteacher/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTchTeacher> tevglTchTeacherList = tevglTchTeacherMapper.selectListByMap(query);
		convertUtil.convertOrgId(tevglTchTeacherList, "orgId"); // 转换机构
		convertUtil.convertDict(tevglTchTeacherList, "state", "teacher_state"); // 教师状态(Y有效N无效)
		convertUtil.convertDict(tevglTchTeacherList, "showIndex", "state1"); // 是否推荐到首页(Y是N否)
		tevglTchTeacherList.forEach(a -> {
			a.setTeacherPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("7") + "/" + a.getTeacherPic());
		});
		PageUtils pageUtil = new PageUtils(tevglTchTeacherList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * <p>前端根据条件查询列表</p>
	 * @author huj
	 * @data 2019年7月23日
	 * @param map
	 * @return
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@Override
	@GetMapping("/querySimpleListMapByMap")
	@SentinelResource("/tch/tevgltchteacher/querySimpleListMapByMap")
	public R querySimpleListMapByMap(@RequestParam Map<String, Object> params, String type) {
		if (StrUtils.isNull(params.get("loginUserId"))) {
			Query query = new Query(params);
			PageUtils pageUtil = new PageUtils(new ArrayList<>(), query.getPage(), query.getLimit());
			return R.ok().put(Constant.R_DATA, pageUtil);
		}
		// 如果当前登录用户不是教师
		if (!roleUtils.checkIsTeacher(params.get("loginUserId").toString())) {
			Query query = new Query(params);
			PageUtils pageUtil = new PageUtils(new ArrayList<>(), query.getPage(), query.getLimit());
			return R.ok().put(Constant.R_DATA, pageUtil);
		}
		// 手机号码查询
		if (!StrUtils.isNull(params.get("mobile"))) {
			params.put("username", params.get("mobile"));
		}
		// 不查自己
		if (!StrUtils.isNull(params.get("loginUserId"))) {
			params.put("notInList", Arrays.asList(params.get("loginUserId")));
		}
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchTeacherList = tevglTchTeacherMapper.selectSimpleListByMap(query);
		// 转换一级机构
		convertUtil.convertOrgId(tevglTchTeacherList, "orgId");
		// 转换二级机构
		convertUtil.convertOrgId(tevglTchTeacherList, "orgIdDepartment");
		convertUtil.convertDict(tevglTchTeacherList, "sex", "sex");
		if (tevglTchTeacherList != null && tevglTchTeacherList.size() > 0) {
			List<TevglBookpkgTeam> teamList = new ArrayList<TevglBookpkgTeam>();
			if (!StrUtils.isNull(params.get("pkgId"))) {
				Map<String, Object> ps = new HashMap<>();
				ps.put("pkgId", params.get("pkgId"));
				teamList = tevglBookpkgTeamMapper.selectListByMap(ps);
			}
			for (Map<String, Object> teacherInfo : tevglTchTeacherList) {
				// 头像处理
				teacherInfo.put("teacherPic", uploadPathUtils.stitchingPath(teacherInfo.get("teacherPic"), "7"));
				// 主攻科目(所教专业)
				String mainSubjects = "";
				if (!StrUtils.isNull(teacherInfo.get("majorId"))) {
					String[] split = teacherInfo.get("majorId").toString().split(",");
					List<String> majorIds = Stream.of(split).collect(Collectors.toList());
					if (majorIds != null && majorIds.size() > 0) {
						Map<String, Object> map = new HashMap<>();
						map.put("majorIds", majorIds);
						List<TevglBookMajor> majorList = tevglBookMajorMapper.selectListByMap(map);
						mainSubjects = majorList.stream().map(a -> a.getMajorName()).collect(Collectors.joining(","));
					}
				}
				teacherInfo.put("mainSubjects", mainSubjects);
				// 此教学包是否已授权
				if (teamList != null && teamList.size() > 0) {
					boolean isAuthorized = teamList.stream().anyMatch(a -> a.getUserId().equals(teacherInfo.get("traineeId")) || a.getUserId().equals(teacherInfo.get("teacherId")));
					teacherInfo.put("isAuthorized", isAuthorized);
				} else {
					teacherInfo.put("isAuthorized", false);
				}
			}
		}
		PageUtils pageUtil = new PageUtils(tevglTchTeacherList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchteacher/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchTeacherList = tevglTchTeacherMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchTeacherList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTchTeacher
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchteacher/save")
	public R save(@RequestBody(required = false) TevglTchTeacher tevglTchTeacher) throws OssbarException {
		if (StrUtils.isEmpty(tevglTchTeacher.getTeacherId())) {
			tevglTchTeacher.setTeacherId(Identities.uuid());
		}
		//ValidatorUtils.check(tevglTchTeacher);
		tevglTchTeacherMapper.insert(tevglTchTeacher);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTchTeacher
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchteacher/update")
	public R update(@RequestBody(required = false) TevglTchTeacher tevglTchTeacher) throws OssbarException {
	    //ValidatorUtils.check(tevglTchTeacher);
		tevglTchTeacherMapper.update(tevglTchTeacher);
		String attachId = tevglTchTeacher.getAttachId();
		if (StrUtils.isNotEmpty(attachId)) {
			tsysAttachService.updateAttach(attachId, tevglTchTeacher.getTeacherId(), "0", "7");
		}
		return R.ok();
	}
	

	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年7月26日
	 * @param tevglTchTeacher
	 * @param attachId
	 * @return
	 * @throws OssbarException
	 */
	@Override
	@SysLog(value="新增")
	@PostMapping("saveTeacherInfo")
	@SentinelResource("/tch/tevgltchteacher/saveTeacherInfo")
	public R saveTeacherInfo(@RequestBody(required = false) TevglTchTeacher tevglTchTeacher, String attachId) throws OssbarException {
		TsysUserinfo user = null;
		String mobile = tevglTchTeacher.getUsername();
		if (!isMobile(mobile)) {
			return R.error("请填写手机号码");
		}
		if (StrUtils.isEmpty(tevglTchTeacher.getTeacherId())) {
			// 账号不能为空
			if (tevglTchTeacher.getUsername() == null || tevglTchTeacher.getUsername().isEmpty()) {
				throw new OssbarException(-1, "教师账号不能为空");
			}
			// 查询数据库中是否已经存在正常的同名账号
			user = tsysUserinfoMapper.selectObjectByUserName(tevglTchTeacher.getUsername().trim());
			if (user != null && !"".equals(user.getUserType())) {
				throw new OssbarException(-1, "该教师账号已经存在");
			}
		}
		TmeduApiToken token = tmeduApiTokenMapper.selectTokenByUserId(tevglTchTeacher.getTraineeId());
		// 如果该粉丝状态不是游客 注:用户类型，1、客户，2、系统用户，3、学员，4、教师。 注: 状态:报名、在册、退学、毕业、就职
		if (token != null && !"1".equals(token.getUserType())) {
			throw new OssbarException(-1, "所选关联粉丝ID已被标识为学员");
		} else { // 否则标记为教师
			TmeduApiToken tk = new TmeduApiToken();
			tk.setUserId(tevglTchTeacher.getTraineeId());
			tk.setUserType("4");
			tmeduApiTokenMapper.update(tk);
			TevglTraineeInfo t = new TevglTraineeInfo();
			t.setTraineeId(tevglTchTeacher.getTraineeId());
			t.setTraineeState("2");
			t.setTraineeType("4");
			tevglTraineeInfoMapper.update(t);
		}
		// 如果该账号存在，且被锁定，则启用
		if (user != null) {
			user.setUserType("1");
    		tsysUserinfoMapper.update(user);
		} else { // 否则新建一个账号
			TsysUserinfo u = new TsysUserinfo();
			u.setUserId(tevglTchTeacher.getTraineeId());
		    u.setUsername(tevglTchTeacher.getUsername().trim());
		    // 从参数表中获取默认密码并加密
		    String pwd = TicketDesUtil.encryptWithMd5(getDefaultPasswordFormParameters(), null);
			u.setPassword(pwd);
			u.setUserRealname(tevglTchTeacher.getTeacherName());
			u.setUserTheme("black");
			u.setUserType("0");
			u.setSex("1");
			//默认教师角色 b0d61a55132540e6a1c10b88852a29a8,如有改动，可替换
			//u.setRoleIdList(new ArrayList<String>(){{add("b0d61a55132540e6a1c10b88852a29a8");}});
			u.setRoleIdList(Arrays.asList("b0d61a55132540e6a1c10b88852a29a8"));
			//默认主机构为教师所在的教育中心
			//u.setOrgIdList(new ArrayList<String>(){{add(tevglTchTeacher.getOrgId());}});
			u.setOrgIdList(Arrays.asList(tevglTchTeacher.getOrgId()));
			u.setPostIdList(new ArrayList<String>());
			tsysUserinfoMapper.insert(u);
		}
		// 默认的机构
		if (StrUtils.isEmpty(tevglTchTeacher.getOrgId())) {
			tevglTchTeacher.setOrgId(getDefaultOrgIdDepartment());
		}
		tevglTchTeacher.setOrgIdDepartment(getDefaultOrgIdDepartment());
		tevglTchTeacher.setTeacherId(tevglTchTeacher.getTraineeId());
		tevglTchTeacher.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglTchTeacher.setCreateTime(DateUtils.getNowTimeStamp());
		tevglTchTeacher.setUpdateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglTchTeacher);
		tevglTchTeacherMapper.insert(tevglTchTeacher);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttach(attachId, tevglTchTeacher.getTraineeId(), "1", "7");
		}
		return R.ok();
	}

	/**
	 * 手机格式验证
	 * 
	 * @param mobile
	 * @return
	 */
	private boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^1[3456789]\\d{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年7月26日
	 * @param tevglTchTeacher
	 * @param attachId
	 * @return
	 * @throws OssbarException
	 */
	@Override
	@SysLog(value="修改")
	@SentinelResource("/tch/tevgltchteacher/update")
	public R updateTeacherInfo(TevglTchTeacher tevglTchTeacher, String attachId) throws OssbarException {
		// 查询修改之前的教师信息
		TevglTchTeacher old = tevglTchTeacherMapper.selectObjectById(tevglTchTeacher.getTeacherId());
		// 手机号唯一校验,注意目前是username是手机号码
		List<Map<String, Object>> mobileList = tevglTchTeacherMapper.getMobileList();
		boolean isRepetition = mobileList.stream().anyMatch(a -> 
			!a.get("teacherId").equals(tevglTchTeacher.getTeacherId())
			&& a.get("username").equals(tevglTchTeacher.getUsername()));
		if (isRepetition) {
			return R.error("该账号名称[手机号码]"+tevglTchTeacher.getUsername()+"，已被绑定，请重新输入");
		}
		// 新老 关联粉丝发生变化
		if (tevglTchTeacher.getTraineeId() != null && !tevglTchTeacher.getTraineeId().equals(old.getTraineeId())) {
			TmeduApiToken token = tmeduApiTokenMapper.selectTokenByUserId(tevglTchTeacher.getTraineeId());
			//如果新关联的粉丝状态不是游客
			if(token != null && !"1".equals(token.getUserType())){
	    		throw new OssbarException(-1, "所选关联粉丝ID已被标识为学员");
			}
			//标记回老粉丝为游客
			TmeduApiToken otk = new TmeduApiToken();
			otk.setUserId(old.getTraineeId());
			otk.setUserType("1");
			tmeduApiTokenMapper.update(otk);
			TevglTraineeInfo oldinfo = new TevglTraineeInfo();
			oldinfo.setTraineeId(old.getTraineeId());
			oldinfo.setTraineeState("1");
			oldinfo.setTraineeType("1");
			tevglTraineeInfoMapper.update(oldinfo);
			//标记新粉丝为教师
			TmeduApiToken ntk = new TmeduApiToken();
			ntk.setUserId(tevglTchTeacher.getTraineeId());
			ntk.setUserType("4");
			tmeduApiTokenMapper.update(ntk);
			TevglTraineeInfo info = new TevglTraineeInfo();
			info.setTraineeId(tevglTchTeacher.getTraineeId());
			info.setTraineeState("2");
			info.setTraineeType("4");
			tevglTraineeInfoMapper.update(info);
		}
		tevglTchTeacher.setUpdateTime(DateUtils.getNowTimeStamp()); // 修改时间
		tevglTchTeacher.setUpdateUserId(serviceLoginUtil.getLoginUserId());
		ValidatorUtils.check(tevglTchTeacher);
		tevglTchTeacherMapper.update(tevglTchTeacher);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttach(attachId, tevglTchTeacher.getTeacherId(), "0", "7");
		}
		return R.ok();
	}

	
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchteacher/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglTchTeacherMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchteacher/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		if (ids == null || ids.length == 0) {
			return R.ok();
		}
		List<String> teacherIds = Arrays.asList(ids);
		// 处理外键约束
		List<String> teacherNames = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for (String teacherId : teacherIds) {
			map.put("teacherId", teacherId);
			map.put("createUserId", teacherId);
			map.put("state", "Y");
			List<Map<String, Object>> list = tevglTchClassroomMapper.selectListMapByMap(map);
			if (list != null && list.size() > 0) {
				String teacherName = list.get(0).get("teacher_name").toString();
				if (!teacherNames.contains(teacherName)) {
					teacherNames.add(teacherName);
				}
				continue;
			} else {
				tevglTchTeacherMapper.delete(teacherId);
			}
		}
		String msg = "删除成功";
		String str = teacherNames.stream().collect(Collectors.joining(","));
		if (!StrUtils.isEmpty(str)) {
			msg = "删除成功，有如下老师：" + str + "开设了课堂（已忽略删除）";
		}
		return R.ok(msg);
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchteacher/view")
	public R view(@PathVariable("id") String id) {
		TevglTchTeacher a = tevglTchTeacherMapper.selectObjectById(id);
		if (a == null) {
			return R.ok().put(Constant.R_DATA, new TevglTchTeacher());
		}
		a.setTeacherPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("7") + "/" + a.getTeacherPic());
		return R.ok().put(Constant.R_DATA, a);
	}

	/**
	 * <p>更新状态或是否首页显示</p>
	 * @author huj
	 * @data 2019年7月28日
	 * @param tevglTchTeacher
	 * @return
	 */
	@Override
	public R updateStateOrShowIndex(TevglTchTeacher tevglTchTeacher) {
		if (tevglTchTeacher == null) {
			return R.error("操作失败");
		}
		if (tevglTchTeacher.getTeacherId() == null || "".equals(tevglTchTeacher.getTeacherId())) {
			return R.error("操作失败");
		}
		tevglTchTeacherMapper.update(tevglTchTeacher);
		return R.ok();
	}
	
	/**
	 * <p>从参数表中获取默认密码</p>
	 * @author huj
	 * @data 2019年8月9日
	 * @return
	 */
	public String getDefaultPasswordFormParameters() {
		String password = "123456";
		Map<String, Object> map = new HashMap<>();
		map.put("paraname", "系统用户默认密码");
		//map.put("paraType", "password");
		List<TsysParameter> list = tsysParameterMapper.selectListByMap(map);
		if (list.size() > 0 && list != null) {
			if (list.get(0).getParano() != null && !"".equals(list.get(0).getParano()) && list.get(0).getParano().length() >= 6) {
				password = list.get(0).getParano();
			}
		}
		return password;
	}

	/**
	 * <p>根据条件查询教师，无分页</p>  
	 * @author huj
	 * @data 2019年8月20日	
	 * @param map
	 * @return
	 */
	@Override
	public List<TevglTchTeacher> queryNoPage(Map<String, Object> map) {
		map.put("state", "Y");
		List<TevglTchTeacher> list = tevglTchTeacherMapper.selectListByMap(map);
		list.stream().forEach(a -> {
			a.setTeacherPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("7") + "/" + a.getTeacherPic());
		});
		return list;
	}

	@Override
	public TevglTchTeacher selectObjectById(Object id) {
		return tevglTchTeacherMapper.selectObjectById(id);
	}
	

	@Override
	public TevglTchTeacher selectObjectByTraineeId(Object id) {
		return tevglTchTeacherMapper.selectObjectByTraineeId(id);
	}

	/**
	 * 更新信息
	 * @param data
	 * @return
	 * @throws OssbarException
	 */
	@Override
	@GetMapping("/updateTeacherInfo")
	public R updateTeacherInfo(@RequestParam Map<String, Object> data) throws OssbarException {
		String teacherId = (String) data.get("teacherId");
        String teacherName = (String) data.get("teacherName");
        String teacherPic = (String) data.get("teacherPic");
        String jobNumber = (String) data.get("jobNumber"); // 工号/学号
        String teacherErtificateNumber = (String) data.get("teacherErtificateNumber"); // 教师资格证
        String traineeSex = (String) data.get("traineeSex"); // 工号/学号
        if (StrUtils.isEmpty(teacherId)) {
            return R.error("必传参数为空");
        }
        TevglTchTeacher t = new TevglTchTeacher();
        t.setTeacherId(teacherId);
        t.setJobNumber(jobNumber);
        t.setTeacherErtificateNumber(teacherErtificateNumber);
        t.setSex(traineeSex);
        if (StrUtils.isNotEmpty(teacherName)) {
            t.setTeacherName(teacherName);
        }
        if (StrUtils.isNotEmpty(teacherPic)) {
            t.setTeacherPic(teacherPic);
        }
        tevglTchTeacherMapper.update(t);
        return R.ok("编辑成功");
	}
	
	/**
	 * 默认的学校机构id
	 * @return
	 */
	private String getDefaultOrgIdDepartment() {
		return "10865";
	}

	@Override
	public List<TevglTchTeacher> selectListByMapInnerJoinTraineeTable(Map<String, Object> map) {
		List<TevglTchTeacher> list = tevglTchTeacherMapper.selectListByMapInnerJoinTraineeTable(map);
		list.stream().forEach(a -> {
			a.setTeacherPic(ossbarFieAccessPath + uploadPathUtils.getPathByParaNo("7") + "/" + a.getTeacherPic());
		});
		return list;
	}


}
