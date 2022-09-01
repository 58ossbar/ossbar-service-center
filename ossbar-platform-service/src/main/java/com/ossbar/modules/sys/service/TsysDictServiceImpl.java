package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysDictService;
import com.ossbar.modules.sys.domain.TsysDict;
import com.ossbar.modules.sys.persistence.TsysDictMapper;
import com.ossbar.modules.sys.vo.dict.TsysDictVO;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-08-16 15:48
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/dict")
public class TsysDictServiceImpl implements TsysDictService {

    @Autowired
    private TsysDictMapper tsysDictMapper;

    @Autowired
    private ConvertUtil convertUtil;

    /**
     * 获取字典表中parentType!=0的字典信息
     *
     * @param map
     * @return
     */
    @Override
    public List<TsysDictVO> selectVoListByMap(Map<String, Object> map) {
        Query query = new Query(map);
        query.put("sidx", "SORT_NUM");
        query.put("order", "asc");
        return tsysDictMapper.selectVoListByMap(query);
    }

    /**
     * 获取字典表中parentType!=0的字典信息
     *
     * @param map
     * @return
     */
    @Override
    public R selectListByMapNotZero(Map<String, Object> map) {
        Query query = new Query(map);
        query.put("sidx", "SORT_NUM");
        query.put("order", "asc");
        return R.ok().put(Constant.R_DATA, tsysDictMapper.selectListByMapNotZero(query));
    }

    /**
     * 查询操作
     *
     * @param params (page页码,limit显示条数)
     * @return R
     * @author huangwb
     */
    @Override
    @GetMapping("query")
    @SentinelResource("/sys/dict/query")
    //@Cacheable(value="dict_cache", key = "'cb_' + #params.get('parentType')")
    public R query(Map<String, Object> params) {
        params.put("sidx", "update_time");
        params.put("order", "desc");
        // 构建查询条件对象Query
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TsysDict> tsysDictList = tsysDictMapper.selectListByMapNotZero(query);
        Map<String, String> map = new HashMap<String, String>();
        map.put("displaying", "displaying");
        map.put("displaySort", "displaySort");
        map.put("isdefault", "isdefault");
        convertUtil.convertParam(tsysDictList, map);
        PageUtils pageUtil = new PageUtils(tsysDictList, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }
}
