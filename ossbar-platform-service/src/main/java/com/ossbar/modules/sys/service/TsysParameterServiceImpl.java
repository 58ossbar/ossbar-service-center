package com.ossbar.modules.sys.service;

import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysParameterService;
import com.ossbar.modules.sys.domain.TsysParameter;
import com.ossbar.modules.sys.persistence.TsysParameterMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-08-25 11:43
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/parameter")
public class TsysParameterServiceImpl implements TsysParameterService {

    @Autowired
    private TsysParameterMapper tsysParameterMapper;
    @Autowired
    private ConvertUtil convertUtil;

    /**
     * 根据paraType获取类型的所有参数 方便前端下拉框选择
     *
     * @param paraType
     * @return R
     * @author huangwb
     * @date 2019-05-29 15:18
     */
    @Override
    public R getPara(String paraType) {
        // 构建查询条件对象Query
        Map params = new HashMap();
        params.put("paraType", paraType);
        List<TsysParameter> parameters = tsysParameterMapper.selectListByMap(params);
        params.clear();
        params.put("displaysort", "paradisplaysort");
        params.put("isdefault", "isdefault");
        convertUtil.convertParam(parameters, params);
        return R.ok().put("data", parameters);
    }
}
