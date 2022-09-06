package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysParameterService;
import com.ossbar.modules.sys.domain.TsysParameter;
import com.ossbar.modules.sys.persistence.TsysParameterMapper;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/getPara")
    @SentinelResource("/sys/parameter/getPara")
    @Cacheable(value="parameter_cache", key="'getPara_'+#paraType")
    public R getPara(String paraType) {
        // 构建查询条件对象Query
        Map params = new HashMap();
        params.put("paraType", paraType);
        List<TsysParameter> parameters = tsysParameterMapper.selectListByMap(params);
        params.clear();
        params.put("displaysort", "paradisplaysort");
        params.put("isdefault", "isdefault");
        convertUtil.convertParam(parameters, params);
        return R.ok().put(Constant.R_DATA, parameters);
    }

    /**
     * 根据参数管理左侧的树结构
     *
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @Override
    @GetMapping("/paraTree")
    @SentinelResource("/sys/parameter/paraTree")
    public R paraTree() {
        return R.ok().put(Constant.R_DATA, tsysParameterMapper.selectDistinctList());
    }

    /**
     * 根据条件分页查询记录
     *
     * @param params
     * @return
     */
    @Override
    public R query(Map<String, Object> params) {
        params.put("sidx", "update_time");
        params.put("order", "desc");
        // 构建查询条件对象Query
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TsysParameter> configList = tsysParameterMapper.selectListByMap(params);
        Map<String, String> map = new HashMap<String, String>();
        map.put("displaysort", "paradisplaysort");
        map.put("isdefault", "isdefault");
        convertUtil.convertParam(configList, map);
        PageUtils pageUtil = new PageUtils(configList, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 查看明细
     *
     * @param parameterId
     * @return
     */
    @Override
    @GetMapping("/view/{parameterId}")
    @SentinelResource("/sys/parameter/view")
    public R view(@PathVariable("parameterId") String parameterId) {
        return R.ok().put(Constant.R_DATA, tsysParameterMapper.selectObjectById(parameterId));
    }
}
