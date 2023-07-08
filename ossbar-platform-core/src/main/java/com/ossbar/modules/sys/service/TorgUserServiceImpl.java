package com.ossbar.modules.sys.service;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TorgUserService;
import com.ossbar.modules.sys.domain.TorgUser;
import com.ossbar.modules.sys.persistence.TorgUserMapper;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author huj
 * @create 2022-08-25 15:54
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/orguser")
public class TorgUserServiceImpl implements TorgUserService {

    @Autowired
    private TorgUserMapper torgUserMapper;

    /**
     * 保存用户与机构的关系
     *
     * @param userId
     * @param orgIdList
     * @return
     */
    @Override
    public R saveOrUpdate(String userId, List<String> orgIdList) {
        if (orgIdList == null || orgIdList.isEmpty()) {
            return R.ok();
        }
        // 先删除用户与机构的关系
        torgUserMapper.delete(userId);
        // 批量新增
        List<TorgUser> list = new ArrayList<>();
        Stream.iterate(0, i -> i + 1).limit(orgIdList.size()).forEach(i -> {
            TorgUser t = new TorgUser();
            // 列表中第一条为主机构
            if (i <= 0) {
                t.setIsMain(1);
            } else {
                t.setIsMain(0);
            }
            t.setOrguserId(Identities.uuid());
            t.setUserId(userId);
            t.setOrgId(orgIdList.get(i));
            list.add(t);
        });
        if (list.size() > 0) {
            torgUserMapper.insertBatch(list);
        }
        return R.ok();
    }

    /**
     * <p>根据用户ID查询记录</p>
     *
     * @param userId
     * @return
     * @author huj
     * @data 2019年5月13日
     */
    @Override
    public List<String> selectOrgIdListByUserId(String userId) {
        return torgUserMapper.selectOrgIdListByUserId(userId);
    }

    /**
     * <p>批量删除</p>
     *
     * @param ids
     * @return
     * @author huj
     * @data 2019年5月13日
     */
    @Override
    public R deleteBatch(String[] ids) {
        if (ids != null && ids.length > 0) {
            torgUserMapper.deleteBatch(ids);
        }
        return R.ok();
    }
}
