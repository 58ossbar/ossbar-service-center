package com.ossbar.modules.sys.persistence;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TsysDict;
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
	public List<TsysDict> selectListByMapNotZero(Map<String, Object> map);

	public List<TsysDict> selectListParentId(String parentId);
	public List<TsysDict> selectAllTsysDict();

}