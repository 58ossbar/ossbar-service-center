package com.ossbar.modules.sys.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.sys.domain.TsysDocs;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;

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
public interface TsysDocsMapper extends BaseSqlMapper<TsysDocs> {
	public int selectMaxSortByMap(Map<String,Object> map);
	public void plusNum(TsysDocs tsysDocs);
	public List<TsysDocs> selectListSimpleByMap(Map<String,Object> map);
}