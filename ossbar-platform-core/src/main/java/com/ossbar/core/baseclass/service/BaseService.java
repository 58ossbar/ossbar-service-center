package com.ossbar.core.baseclass.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;

/**
 * Title: 业务逻辑层基类 Description: Copyright: Copyright (c) 2017
 * Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
public abstract class BaseService<T> {
	public abstract BaseSqlMapper<T> getMapper();

	/** The Constant log. */
	protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

	/**
	 * 插入數據
	 * 
	 * @param t
	 * @throws CreatorblueException
	 */
	public void insert(T t) throws CreatorblueException {
		getMapper().insert(t);
	}

	public void insertBatch(List<T> list) {
		getMapper().insertBatch(list);
	}

	/**
	 * 更新數據
	 * 
	 * @param t
	 * @throws CreatorblueException
	 */
	public int update(T t) throws CreatorblueException {
		return getMapper().update(t);
	}

	public int update(Map<String, Object> map) {
		return getMapper().update(map);
	}

	public void delete(String... ids) throws CreatorblueException {
		if (ids == null || ids.length < 1) {
			return;
		}
		for (String id : ids) {
			getMapper().delete(id);
		}
	}

	public int deleteBatch(Object[] id) {
		return getMapper().deleteBatch(id);
	}

	/**
	 * 刪除記錄
	 * 
	 * @param id
	 * @return
	 */
	public int delete(Object id) {
		return getMapper().delete(id);
	}

	/**
	 * 刪除記錄
	 * 
	 * @param map
	 * @return
	 */
	public int delete(Map<String, Object> map) {
		return getMapper().delete(map);
	}

	/**
	 * 查詢數據
	 * 
	 * @param id
	 * @return
	 */
	public T selectObjectById(Object id) {
		return getMapper().selectObjectById(id);
	}

	/**
	 * 查詢數據
	 * 
	 * @param map
	 * @return
	 */
	public List<T> selectListByMap(Map<String, Object> map) {
		return getMapper().selectListByMap(map);
	}

	public List<T> selectListById(Object id) {
		return getMapper().selectListById(id);
	}

	public int selectTotalByMap(Map<String, Object> map) {
		return getMapper().selectTotalByMap(map);
	}

	public int selectTotal() {
		return getMapper().selectTotal();
	}
}
