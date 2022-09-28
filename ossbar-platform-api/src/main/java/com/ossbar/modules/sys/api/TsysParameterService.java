package com.ossbar.modules.sys.api;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.domain.TsysParameter;

import java.util.Map;

/**
 * 参数管理
 * @author huj
 * @create 2022-08-25 11:42
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TsysParameterService {

    /**
     * 根据paraType获取类型的所有参数 方便前端下拉框选择
     *
     * @param paraType
     * @return R
     * @author huangwb
     * @date 2019-05-29 15:18
     */
    R getPara(String paraType);

    /**
     * 根据参数管理左侧的树结构
     *
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    R paraTree();

    /**
     * 根据条件分页查询记录
     * @param params
     * @return
     */
    R query(Map<String, Object> params);

    /**
     * 查看明细
     * @param parameterId
     * @return
     */
    R view(String parameterId);

    /**
     * 新增
     * @param tsysParameter
     * @return
     */
    R save(TsysParameter tsysParameter);

    /**
     * 修改
     * @param tsysParameter
     * @return
     */
    R update(TsysParameter tsysParameter);

    /**
     * 删除
     *
     * @param ids
     * @return R
     * @author huangwb
     * @date 2019-05-05 15:18
     */
    R deleteBatch(String[] ids);

}
