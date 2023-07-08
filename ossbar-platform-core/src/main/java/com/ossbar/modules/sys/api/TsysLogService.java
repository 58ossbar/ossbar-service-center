package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysLog;

import java.util.Map;


/**
 * 系统日志api
 * <p>Title: TsysLogServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年5月6日
 */
public interface TsysLogService {

	/**
	 * 根据条件查询站点列表
	 * @author huj
	 * @data 2019年5月6日
	 * @param params
	 * @return
	 */
	R query(Map<String, Object> params);
	
	/**
	 * 新增
	 * @author huj
	 * @data 2019年5月6日
	 * @param sysLog
	 * @return
	 */
	R save(TsysLog sysLog);
	
	/**
	 * 修改
	 * @author huj
	 * @data 2019年5月6日
	 * @param sysLog
	 * @return
	 */
	R update(TsysLog sysLog);
	
	/**
	 * 单条删除
	 * @author huj
	 * @data 2019年5月23日
	 * @param id
	 * @return
	 */
	R delete(String id);
	
	/**
	 * 批量删除
	 * @author huj
	 * @data 2019年5月6日
	 * @param ids
	 * @return
	 */
	R deleteBatch(String[] ids);
	
	/**
	 * 查看明细
	 * @author huj
	 * @data 2019年5月6日
	 * @param id
	 * @return
	 */
	R view(String id);
}
