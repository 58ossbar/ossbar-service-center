package com.ossbar.modules.evgl.site.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.site.domain.TevglSiteSysMsg;
import com.ossbar.modules.evgl.site.query.TevglSiteSysMsgQuery;
import com.ossbar.modules.evgl.site.vo.TevglSiteSysMsgVo;

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
public interface TevglSiteSysMsgMapper extends BaseSqlMapper<TevglSiteSysMsg> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 统计未读消息数
	 * @param loginUserId
	 * @return
	 */
	int countUnReadMsgNum(String loginUserId);

	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<TevglSiteSysMsg> list);
	
	/**
	 * 根据条件查询记录
	 * @param queryParameters
	 * @return
	 */
	List<TevglSiteSysMsgVo> findMsgList(TevglSiteSysMsgQuery queryParameters);
	
	/**
	 * 批量更新消息为已读
	 * @param list
	 * @return
	 */
	int batchUpdateRead(List<String> list);
}