package com.ossbar.modules.evgl.site.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.*;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteNewsService;
import com.ossbar.modules.evgl.site.domain.TevglSiteCheckinfo;
import com.ossbar.modules.evgl.site.domain.TevglSiteNews;
import com.ossbar.modules.evgl.site.persistence.TevglSiteCheckinfoMapper;
import com.ossbar.modules.evgl.site.persistence.TevglSiteNewsMapper;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.modules.sys.domain.TsysDict;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
@RequestMapping("/site/tevglsitenews")
public class TevglSiteNewsServiceImpl implements TevglSiteNewsService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteNewsServiceImpl.class);
	@Autowired
	private TevglSiteNewsMapper tevglSiteNewsMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private DictUtil dictUtil;
	@Autowired
	private TsysAttachService tsysAttachService;
	
	@Value("${com.creatorblue.file-access-path}")
	public String creatorblueFieAccessPath;
	@Autowired
	private UploadFileUtils uploadPathUtils;

	@Autowired
	private TevglSiteCheckinfoMapper tevglSiteCheckinfoMapper;
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@Override
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsitenews/query")
	public R query(@RequestParam Map<String, Object> params) {
		params.put("scene", "0");
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteNews> tevglSiteNewsList = tevglSiteNewsMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteNewsList, "createUserId", "updateUserId");
		convertUtil.convertOrgId(tevglSiteNewsList, "orgId"); // 所属机构
		convertUtil.convertDict(tevglSiteNewsList, "categoryId", "newsCategory"); // 类别
		convertUtil.convertDict(tevglSiteNewsList, "status", "newsStatus"); // 状态
		convertUtil.convertDict(tevglSiteNewsList, "deployed", "deployed"); // 发布方式
		convertUtil.convertDict(tevglSiteNewsList, "noone", "isHeadlineNews"); // 是否头条资讯
		// 图片处理
		tevglSiteNewsList.forEach(a -> {
			a.setNewsLogo(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("6") + "/" + a.getNewsLogo());
		});
		PageUtils pageUtil = new PageUtils(tevglSiteNewsList,query.getPage(),query.getLimit());
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
	@SentinelResource("/site/tevglsitenews/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteNewsList = tevglSiteNewsMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteNewsList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglSiteNewsList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteNews
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsitenews/save")
	public R save(@RequestBody(required = false) TevglSiteNews tevglSiteNews) throws CreatorblueException {
		tevglSiteNews.setNewsid(Identities.uuid());
		tevglSiteNews.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSiteNews.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglSiteNews);
		tevglSiteNewsMapper.insert(tevglSiteNews);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteNews
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsitenews/update")
	public R update(@RequestBody(required = false) TevglSiteNews tevglSiteNews) throws CreatorblueException {
	    tevglSiteNews.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSiteNews.setUpdateTime(DateUtils.getNowTimeStamp());
	    //ValidatorUtils.check(tevglSiteNews);
		tevglSiteNewsMapper.update(tevglSiteNews);
		return R.ok();
	}
	
	/**
	 * <p>新增</p>
	 * @author huj
	 * @data 2019年7月23日
	 * @param tevglSiteNews
	 * @param attachId
	 * @return
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="新增")
	@SentinelResource("/site/tevglsitenews/save")
	public R save2(@RequestBody(required = false) TevglSiteNews tevglSiteNews, String attachId) throws CreatorblueException {
		String id = Identities.uuid();
		tevglSiteNews.setNewsid(id);
		tevglSiteNews.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSiteNews.setCreateTime(DateUtils.getNowTimeStamp());
		tevglSiteNews.setUpdateTime(DateUtils.getNowTimeStamp());
		// TODO 由于暂时未有审核功能，状态直接默认为已发布，状态1待审2已发布 3删除
		// 发布方式来源字段 deployed
		if ("2".equals(tevglSiteNews.getDeployed())) {
			tevglSiteNews.setStatus("1"); // 0810调整为待审
		} else {
			tevglSiteNews.setStatus("2");
		}
		//ValidatorUtils.check(tevglSiteNews);
		tevglSiteNews.setScene("0");
		tevglSiteNewsMapper.insert(tevglSiteNews);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttachForAdd(attachId, id, "6");
		}
		return R.ok();
	}

	/**
	 * <p>修改</p>
	 * @author huj
	 * @data 2019年7月23日
	 * @param tevglSiteNews
	 * @param attachId
	 * @return
	 * @throws CreatorblueException
	 */
	@Override
	@SysLog(value="修改")
	@SentinelResource("/site/tevglsitenews/update")
	public R update2(TevglSiteNews tevglSiteNews, String attachId) throws CreatorblueException {
		tevglSiteNews.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglSiteNews.setUpdateTime(DateUtils.getNowTimeStamp());
	    // TODO 由于暂时未有审核功能，状态直接默认为已发布，状态1待审2已发布 3删除
	    if ("2".equals(tevglSiteNews.getDeployed())) {
			tevglSiteNews.setStatus("1"); // 0810调整为待审
		} else {
			tevglSiteNews.setStatus("2");
		}
	    //ValidatorUtils.check(tevglSiteNews);
		tevglSiteNewsMapper.update(tevglSiteNews);
		// 如果上传了资源文件
		if (attachId != null && !"".equals(attachId)) {
			tsysAttachService.updateAttachForEdit(attachId, tevglSiteNews.getNewsid(), "6");
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
	@SentinelResource("/site/tevglsitenews/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglSiteNewsMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsitenews/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws CreatorblueException {
		tevglSiteNewsMapper.deleteBatch(ids);
		for (int i = 0; i < ids.length; i++) {			
			List<String> list = tevglSiteNewsMapper.selectIdListByParentId(ids[i]);
			if (list != null && list.size() > 0) {
				tevglSiteNewsMapper.deleteBatch(list.stream().toArray(String[]::new));
			}
		}
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("/view/{id}")
	@SentinelResource("/site/tevglsitenews/view")
	public R view(@PathVariable("id") String id) {
		TevglSiteNews news = tevglSiteNewsMapper.selectObjectById(id);
		if (news == null) {
			return R.ok().put(Constant.R_DATA, new TevglSiteNews());
		}
		// 浏览量+1
		TevglSiteNews t = new TevglSiteNews();
		t.setNewsid(news.getNewsid());
		t.setViewNum(1);
		tevglSiteNewsMapper.plusNum(t);
		news.setNewsLogo(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("6") + "/" + news.getNewsLogo());
		return R.ok().put(Constant.R_DATA, news)
				.put("previous", selectupNewsInfo(news) == null || selectupNewsInfo(news).size() == 0 ? new TevglSiteNews() : selectupNewsInfo(news).get(0))
				.put("next", selectdownNewsInfo(news) == null || selectdownNewsInfo(news).size() == 0 ? new TevglSiteNews() : selectdownNewsInfo(news).get(0));
	}
	
	@Override
	public R viewForWx(Object id) {
		TevglSiteNews news = tevglSiteNewsMapper.selectObjectById(id);
		if (news == null) {
			return R.ok().put(Constant.R_DATA, null);
		}
		// 浏览量+1
		TevglSiteNews t = new TevglSiteNews();
		t.setNewsid(news.getNewsid());
		t.setViewNum(1);
		tevglSiteNewsMapper.plusNum(t);
		news.setNewsLogo(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("6") + "/" + news.getNewsLogo());
		// 不需要这个条件
		news.setCategoryId(null);
		return R.ok().put(Constant.R_DATA, news)
				.put("previous", selectupNewsInfo(news) == null || selectupNewsInfo(news).size() == 0 ? new TevglSiteNews() : selectupNewsInfo(news).get(0))
				.put("next", selectdownNewsInfo(news) == null || selectdownNewsInfo(news).size() == 0 ? new TevglSiteNews() : selectdownNewsInfo(news).get(0));
	}
	
	public List<TevglSiteNews> selectupNewsInfo(TevglSiteNews tevglSiteNews){
		List<TevglSiteNews> list = tevglSiteNewsMapper.selectupNewsInfo(tevglSiteNews);
		if (list.size() > 0) {
			list.forEach(a -> {
				a.setNewsLogo(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("6") + "/" + a.getNewsLogo());
			});
		}
		return list;
	}
	
	public List<TevglSiteNews> selectdownNewsInfo(TevglSiteNews tevglSiteNews){
		List<TevglSiteNews> list = tevglSiteNewsMapper.selectdownNewsInfo(tevglSiteNews);
		if (list.size() > 0) {
			list.forEach(a -> {
				a.setNewsLogo(creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("6") + "/" + a.getNewsLogo());
			});
		}
		return list;
	}

	@Override
	@SysLog(value="查看明细")
	@SentinelResource("/site/tevglsitenews/view")
	public R toEdit(@PathVariable("id") String id) {
		TevglSiteNews tevglSiteNews = tevglSiteNewsMapper.selectObjectById(id);
		String newsLogo = "";
		if (tevglSiteNews != null) {
			newsLogo = creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("6") + "/"  + tevglSiteNews.getNewsLogo();	
		}
		return R.ok().put(Constant.R_DATA, tevglSiteNews)
				.put("newsLogo", newsLogo);
	}

	
	/**
	 * <p>从字典获取新增资讯分类,如企业新闻,行业新闻等</p>
	 * @author huj
	 * @data 2019年7月16日
	 * @return
	 */
	@Override
	@GetMapping("/getCategory")
	@SentinelResource("/site/tevglsitenews/getCategory")
	public R getCategory() {
		List<TsysDict> list = new ArrayList<TsysDict>();
		TsysDict t = new TsysDict();
		t.setDictId("1");
		t.setDictName("全部");
		t.setDictValue("全部");
		t.setDictCode("");
		t.setIsdefault("1");
		list.add(t);
		List<TsysDict> dictList = dictUtil.getByType("newsCategory");
		list.addAll(dictList);
		return R.ok().put(Constant.R_DATA, list);
	}
	
	/**
	 * <p>从字典获取发布方式,如直接发布、审核发布</p>
	 * @author huj
	 * @data 2019年7月20日
	 * @return
	 */
	@Override
	public List<TsysDict> getDeployed() {
		List<TsysDict> list = dictUtil.getByType("deployed");
		return list;
	}

	/**
	 * <p>从字典获取是否头条咨询</p>
	 * @author huj
	 * @data 2019年7月20日
	 * @return
	 */
	@Override
	public List<TsysDict> getIsHeadlineNews() {
		List<TsysDict> list = dictUtil.getByType("isHeadlineNews");
		return list;
	}

	/**
	 * <p>从字典获取状态，状态1待审2已发布 3删除</p>
	 * @author huj
	 * @data 2019年7月23日
	 * @return
	 */
	@Override
	public List<TsysDict> getNewsStatus() {
		List<TsysDict> list = dictUtil.getByType("newsStatus");
		return list;
	}

	/**
	 * <p>从字典获取是否展示Y显示N隐藏</p>
	 * @author huj
	 * @data 2019年7月23日
	 * @return
	 */
	@Override
	public List<TsysDict> getIsShow() {
		List<TsysDict> list = dictUtil.getByType("isShow");
		return list;
	}

	/**
	 * <p>更新状态</p>
	 * @author huj
	 * @data 2019年7月28日
	 * @param tevglSiteNews
	 * @return
	 */
	@Override
	public R updateState(TevglSiteNews tevglSiteNews) {
		if (tevglSiteNews == null) {
			return R.error("操作失败");
		}
		if (tevglSiteNews.getNewsid() == null || "".equals(tevglSiteNews.getNewsid())) {
			return R.error("操作失败");
		}
		tevglSiteNewsMapper.update(tevglSiteNews);
		return R.ok();
	}

	/**
	 * <p>审核</p>  
	 * @author huj
	 * @data 2019年8月10日	
	 * @param ids
	 * @return
	 */
	@Override
	@PostMapping("/check")
	public R check(@RequestBody String[] ids, String state, String reason) {
		if (ids == null || ids.length == 0) {
			return R.ok();
		}
		if (StrUtils.isEmpty(state)) {
			return R.ok("请选择是否通过");
		}
		List<String> newsid = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			newsid.add(ids[i]);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("newsid", newsid);
		map.put("status", state); // 状态1待审2已发布 3删除 4未通过
		tevglSiteNewsMapper.check(map);
		// 保存审核记录
		if (newsid.size() > 0) {
			newsid.forEach(a -> {
				TevglSiteCheckinfo t = new TevglSiteCheckinfo();
				t.setId(Identities.uuid());
				t.setCreateUserId(serviceLoginUtil.getLoginUserId());
				t.setCreateTime(DateUtils.getNowTimeStamp());
				t.setRefId(a);
				t.setType("1"); // 审核类型(1新闻2广告3等等等)
				t.setState("Y"); // 状态(Y有效N无效)
				t.setReason(reason); // 理由
				if ("4".equals(state)) {
					t.setPass("N");
				} else if ("2".equals(state)) {
					t.setPass("Y");
				}
				tevglSiteCheckinfoMapper.insert(t);
			});
		}
		return R.ok();
	}

	/**
	 * 查询部分信息，且对象为驼峰命名
	 * @param params
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectSimpleListByMap(Map<String, Object> params) {
		List<Map<String,Object>> list = tevglSiteNewsMapper.selectSimpleListByMap(params);
		if (list != null && list.size() > 0) {
			// 图片处理
			list.forEach(news -> {
				news.put("newsLogo", creatorblueFieAccessPath + uploadPathUtils.getPathByParaNo("6") + "/" + news.get("newsLogo"));
			});
		}
		return list;
	}

	/**
	 * 更新状态
	 * @param newsid
	 * @param status
	 * @return
	 */
	@Override
	public R release(String newsid, String status) {
		if (StrUtils.isEmpty(newsid) || StrUtils.isEmpty(status)) {
			return R.error("必传参数为空");
		}
		List<String> stringList = tevglSiteNewsMapper.selectIdListByParentId(newsid);
		if (stringList == null) {
			stringList = new ArrayList<>();
		}
		stringList.add(newsid);
		tevglSiteNewsMapper.updateStateBatch(stringList, status);
		return R.ok("操作成功");
	}

	/**
	 * 批量更新状态
	 * @param newidList
	 * @param status
	 * @return
	 */
	@Override
	public R releaseBatch(String[] newidList, String status) {
		if (newidList == null || newidList.length <= 0 || StrUtils.isEmpty(status)) {
			return R.error("必传参数为空");
		}
		tevglSiteNewsMapper.updateStateBatch(Stream.of(newidList).collect(Collectors.toList()), status);
		return R.ok("操作成功");
	}
	

}
