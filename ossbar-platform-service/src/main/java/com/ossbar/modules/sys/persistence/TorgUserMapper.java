package com.ossbar.modules.sys.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TorgUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
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
public interface TorgUserMapper extends BaseSqlMapper<TorgUser> {

	List<String> selectListByUserId(String userId);

	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<TorgUser> list);
	
}