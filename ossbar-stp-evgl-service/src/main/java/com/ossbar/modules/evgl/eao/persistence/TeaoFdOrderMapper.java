package com.ossbar.modules.evgl.eao.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.eao.domain.TeaoFdOrder;

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
public interface TeaoFdOrderMapper extends BaseSqlMapper<TeaoFdOrder> {
	
	TeaoFdOrder selectObjectBySeriano(String id);
	
	List<Map<String, Object>> selectListByState(Map<String, Object> map);
	
	List<String> findObjectByTraineeIdAndClassId(@Param("traineeId") String traineeId, @Param("classId") String classId);
}