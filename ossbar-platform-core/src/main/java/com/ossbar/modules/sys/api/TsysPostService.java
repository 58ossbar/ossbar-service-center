package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysPost;
import com.ossbar.modules.sys.dto.post.SavePostDTO;

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
     * 查询明细
     * @author huj
     * @data 2019年5月29日
     * @param id
     * @return
     */
    R selectObjectById(String id);

    /**
     * 根据条件查询列表(返回List<Bean>)
     * @param params
     * @return
     */
    List<TsysPost> selectListByMap(Map<String, Object> params);

    /**
     * 新增岗位
     * @param post
     * @return
     */
    R save(SavePostDTO post);

    /**
     * 修改岗位
     * @param post
     * @return
     */
    R update(SavePostDTO post);

    /**
     * 批量删除
     * @author huj
     * @data 2019年5月29日
     * @param ids
     * @return
     */
    R deleteBatch(String[] ids);

    /**
     * 更新排序号
     * @author huj
     * @data 2019年6月18日
     * @param map
     * @return
     */
    R updateSort(Map<String, Object> map);

    /**
     * 获取最大排序号
     * @author huj
     * @data 2019年6月24日
     * @return
     */
    Integer getMaxSortNum();
}
