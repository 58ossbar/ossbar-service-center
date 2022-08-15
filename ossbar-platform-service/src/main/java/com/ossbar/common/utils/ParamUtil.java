package com.ossbar.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossbar.modules.sys.domain.TsysParameter;
import com.ossbar.modules.sys.persistence.TsysParameterMapper;
import com.ossbar.utils.tool.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Title:封装参数传递的辅助类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Company:creatorblue.co.,ltd
 * </p>
 *
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
@Component
public class ParamUtil {
	@Autowired
	private TsysParameterMapper tsysParameterMapper;

	public ParamUtil() {
	}

	/**
	 * 按内部值查找显示值
	 *
	 * @param codeType
	 * @param val
	 * @return
	 * @author
	 * @since 2014-4-28
	 */
	public String getKeyByVal(String codeType, String val) {
		Map<String, List<TsysParameter>> parsed = initIfEmpty();

		if (StrUtils.isNull(val)) {
			return val;
		}

		List<TsysParameter> list = parsed.get(codeType);

		if (list == null) {
			return null;
		}

		for (TsysParameter p : list) {
			if (p.getParano().equals(val)) {
				return p.getParaKey();
			}
		}

		return null;
	}

	/**
	 * 按显示值查找内部值
	 *
	 * @param codeType
	 * @param key
	 * @return
	 * @author
	 * @since 2014-4-28
	 */
	public String getValByKey(String codeType, String key) {
		Map<String, List<TsysParameter>> parsed = initIfEmpty();

		if (StrUtils.isNull(key)) {
			return key;
		}

		List<TsysParameter> list = parsed.get(codeType);

		if (list == null) {
			return null;
		}

		for (TsysParameter p : list) {
			if (p.getParaKey().equals(key)) {
				return p.getParano();
			}
		}

		return null;
	}

	/**
	 * 取某一类型的编码
	 *
	 * @param type
	 * @return
	 */
	public List<TsysParameter> getByType(String type) {
		Map<String, List<TsysParameter>> parsed = initIfEmpty();

		List<TsysParameter> list = parsed.get(type);

		return list;
	}

	/**
	 * 将所有编码按类型分类
	 *
	 * @param
	 * @return
	 */
	private Map<String, List<TsysParameter>> initIfEmpty() {
		List<TsysParameter> params = tsysParameterMapper.selectAllTsysParameter();
		Map<String, List<TsysParameter>> parsed = new HashMap<String, List<TsysParameter>>(128);

		for (TsysParameter p : params) {
			String type = p.getParaType();
			List<TsysParameter> list = parsed.get(type);

			if (list == null) {
				list = new ArrayList<TsysParameter>();
			}

			list.add(p);
			parsed.put(type, list);
		}

		return parsed;
	}
}
