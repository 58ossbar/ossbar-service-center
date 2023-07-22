package com.ossbar.modules.evgl.eao.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.eao.domain.TeaoFdRefusebill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
public interface TeaoFdRefusebillMapper extends BaseSqlMapper<TeaoFdRefusebill> {
	
	List<Map<String, Object>> selectListByMapForRefund(Map<String, Object> map);
}