package com.ossbar.modules.sys.service;

import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysDictService;
import com.ossbar.modules.sys.persistence.TsysDictMapper;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
