package com.ossbar.modules.sys.service;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TuserRoleService;
import com.ossbar.modules.sys.domain.TuserRole;
import com.ossbar.modules.sys.persistence.TuserRoleMapper;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huj
 * @create 2022-08-25 9:57
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
public class TuserRoleServiceImpl implements TuserRoleService {

    @Autowired
    private TuserRoleMapper tuserRoleMapper;

    /**
     * 保存用户与角色关系
     *
     * @param roleIdList
     * @param userIdList
     * @return
     * @author huj
     * @data 2019年5月6日
     */
    @Override
    public R saveOrUpdate(List<String> roleIdList, List<String> userIdList) {
        roleIdList.stream().forEach(roleId -> {
            // 先删除用户与角色关系
            tuserRoleMapper.deleteByRole(roleId);
            // 批量保存
            List<TuserRole> insertList = new ArrayList<>();
            userIdList.stream().forEach(userId -> {
                TuserRole t = new TuserRole();
                t.setId(Identities.uuid());
                t.setRoleId(roleId);
                t.setUserId(userId);
                insertList.add(t);
            });
            if (insertList.size() > 0) {
                tuserRoleMapper.insertBatch(insertList);
            }
        });
        return R.ok("保存成功");
    }
}
