package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.vo.dict.TsysDictVO;

import java.util.List;
import java.util.Map;

/**
 * 字典管理
 * @author huj
 * @create 2022-08-16 15:46
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysDictService {


    /**
     * 获取字典表中parentType!=0的字典信息
     *
     * @return
     */
    List<TsysDictVO> selectVoListByMap(Map<String, Object> map);

    /**
     * 获取字典表中parentType!=0的字典信息
     *
     * @return
     */
    R selectListByMapNotZero(Map<String, Object> map);

    /**
     *
     * 查询操作
     *
     * @author huangwb
     * @param params (page页码,limit显示条数)
     *
     * @return R
     */
    R query(Map<String, Object> params);
}
