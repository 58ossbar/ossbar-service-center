package com.ossbar.modules.sys.service;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysRoleprivilegeService;
import com.ossbar.modules.sys.domain.TsysRoleprivilege;
import com.ossbar.modules.sys.persistence.TsysRoleprivilegeMapper;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huj
 * @create 2022-08-24 9:17
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
public class TsysRoleprivilegeServiceImpl implements TsysRoleprivilegeService {

    @Autowired
    private TsysRoleprivilegeMapper tsysRoleprivilegeMapper;

    /**
     * <p>保存角色与菜单关系</p>
     *
     * @param roleId
     * @param menuIdList
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    public R saveOrUpdate(String roleId, List<String> menuIdList) {
        // 先删除角色与菜单关系
        tsysRoleprivilegeMapper.delete(roleId);
        if (menuIdList == null || menuIdList.size() == 0) {
            return R.ok();
        }
        List<TsysRoleprivilege> insertList = new ArrayList<>();
        menuIdList.stream().forEach(menuId -> {
            TsysRoleprivilege t = new TsysRoleprivilege();
            t.setId(Identities.uuid());
            t.setRoleId(roleId);
            t.setMenuId(menuId);
            insertList.add(t);
        });
        if (insertList != null && insertList.size() > 0) {
            tsysRoleprivilegeMapper.insertBatch(insertList);
        }
        return R.ok();
    }

    /**
     * <p>根据角色ID,查询菜单ID列表</p>
     *
     * @param roleId
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    public List<String> selectMenuListByRoleId(String roleId) {
        return null;
    }
}
