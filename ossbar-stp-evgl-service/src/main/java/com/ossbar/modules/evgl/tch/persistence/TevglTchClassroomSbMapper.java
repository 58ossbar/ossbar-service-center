package com.ossbar.modules.evgl.tch.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.book.vo.RoomBookVo;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomSb;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglTchClassroomSbMapper extends BaseSqlMapper<TevglTchClassroomSb> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);

	/**
	 * 分页查询课外教材
	 * @param map
	 * @return
	 */
	List<RoomBookVo> queryExtraBooks(Map<String, Object> map);
	
	List<RoomBookVo> queryExtraBooksByUnionAll(Map<String, Object> map);
	
	/**
	 * 查询当前课堂已关联的课外教材
	 * @param ctId
	 * @return
	 */
	List<String> findSubjectIdList(String ctId);

	/**
	 * 查询当前课堂已关联的课外教材
	 * @param ctId
	 * @return
	 */
	List<RoomBookVo> findSubjectList(String ctId);

	RoomBookVo findSubjectByCtId(String ctId);
	
	/**
	 * 根据课堂id删除记录
	 * @param ctId
	 * @return
	 */
	int deleteByCtId(@Param("ctId") String ctId);

	/**
	 * 批量新增
	 * @param list
	 */
	@Override
	void insertBatch(List<TevglTchClassroomSb> list);
	
}