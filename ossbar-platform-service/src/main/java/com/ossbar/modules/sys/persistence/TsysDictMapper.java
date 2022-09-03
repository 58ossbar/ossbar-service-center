package com.ossbar.modules.sys.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TsysDict;
import com.ossbar.modules.sys.vo.dict.TsysDictVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhujw
 * @version 1.0
 */

@Mapper
public interface TsysDictMapper extends BaseSqlMapper<TsysDict> {

	//@Cacheable(value="dict_cache")
	List<TsysDict> selectListByMapNotZero(Map<String, Object> map);

	@Cacheable(value="dict_cache", key="'selectListParentId_'+#parentId")
	List<TsysDict> selectListParentId(String parentId);

	@Cacheable(value="dict_cache")
	List<TsysDict> selectAllTsysDict();

	@Cacheable(value="dict_cache", key = "'selectVoListByMap'")
	List<TsysDictVO> selectVoListByMap(Map<String, Object> map);

	/**
	 * 获取最大排序号
	 * @return
	 */
	Integer getMaxSortNum(@Param("parentType") String parentType);
}