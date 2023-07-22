package com.ossbar.modules.sys.persistence;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TuserPost;
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
public interface TuserPostMapper extends BaseSqlMapper<TuserPost> {
	
	/**
	 * <p></p>
	 * @author huj
	 * @data 2019年6月21日
	 * @return
	 */
	List<TuserPost> selectListMapByMap(String[] ids);

    /**
     * 批量新增
     * @param list
     */
    void insertBatch(List<TuserPost> list);
    
    /**
     * 根据用户ID，获取岗位ID列表
     */
    List<String> selectPostIdListByUserId(String userId);
}