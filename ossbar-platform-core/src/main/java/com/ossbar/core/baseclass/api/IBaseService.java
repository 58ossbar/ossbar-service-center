package com.ossbar.core.baseclass.api;

import java.util.Map;
import com.ossbar.core.baseclass.domain.R;

/**
 * 所有微服务的接口的父接口
 * 
 * @author zhuq
 *
 * @param <T>
 */
public interface IBaseService<T> {

	/**
	 * 根据条件查询记录
	 * @param map
	 * @return
	 */
	R query(Map<String, Object> map);

	/**
	 * 根据条件查询记录
	 * @param map
	 * @return
	 */
	R queryForMap(Map<String, Object> map);

	/**
	 * 新增
	 * @param t
	 * @return
	 */
	R save(T t);

	/**
	 * 修改
	 * @param t
	 * @return
	 */
	R update(T t);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	R delete(String id);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	R deleteBatch(String[] ids);

	/**
	 * 查看明细
	 * @param id
	 * @return
	 */
	R view(String id);
}
