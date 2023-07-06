package com.ossbar.modules.evgl.pkg.service;

import com.ossbar.modules.evgl.pkg.api.TevglPkgActivityRelationService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgActivityRelation;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: 教学包与活动关系表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/pkg/tevglpkgactivityrelation")
public class TevglPkgActivityRelationServiceImpl implements TevglPkgActivityRelationService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglPkgActivityRelationServiceImpl.class);
	@Autowired
	private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;

	/**
	 * 根据条件查询活动
	 * @param params
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectSimpleListMap(Map<String, Object> params) {
		return tevglPkgActivityRelationMapper.selectSimpleListMap(params);
	}

	@Override
	public List<TevglPkgActivityRelation> selectListByMap(Map<String, Object> params) {
		return tevglPkgActivityRelationMapper.selectListByMap(params);
	}
}
