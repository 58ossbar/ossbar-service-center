package com.ossbar.modules.sys.persistence;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.sys.domain.TsysPost;
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
public interface TsysPostMapper extends BaseSqlMapper<TsysPost> {
	
	/**
	 * <p>查询岗位parentPostid 为 "" 的数据</p>
	 * @author huj
	 * @data 2019年6月14日
	 * @param map
	 * @return
	 */
	List<TsysPost> selectListByParentPostidIsNull(Map<String, Object> map);
	
	int updateSort(TsysPost post);
	
	/**
	 * <p>获取最大的排序号</p>
	 * @author huj
	 * @data 2019年6月24日
	 * @return
	 */
	int getMaxSort();
	
}