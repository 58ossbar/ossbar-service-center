package com.ossbar.modules.evgl.activity.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.activity.domain.TevglActivitySigninInfo;

/**
 * <p> Title: 活动-签到活动信息表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglActivitySigninInfoMapper extends BaseSqlMapper<TevglActivitySigninInfo> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 更新数量
	 * @param tevglActivitySigninInfo
	 */
	void plusNum(TevglActivitySigninInfo tevglActivitySigninInfo);
	
	TevglActivitySigninInfo selectObjectByIdAndPkgId(Map<String, Object> params);
}