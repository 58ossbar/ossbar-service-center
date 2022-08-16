package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysPost;

import java.util.List;
import java.util.Map;

/**
 * 岗位管理 接口
 * @author huj
 * @create 2022-08-16 11:15
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysPostService {

    /**
     * 根据条件查询列表(返回List<Bean>)
     * @param params
     * @return
     */
    R query(Map<String, Object> params);

    /**
     * 根据条件查询列表(返回List<Bean>)
     * @param params
     * @return
     */
    List<TsysPost> selectListByMap(Map<String, Object> params);

    /**
     * 新增岗位
     * @param tsysPost
     * @return
     */
    R save(TsysPost tsysPost);

    /**
     * 修改岗位
     * @param tsysPost
     * @return
     */
    R update(TsysPost tsysPost);

}
