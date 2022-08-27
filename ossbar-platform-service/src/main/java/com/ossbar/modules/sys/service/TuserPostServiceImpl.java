package com.ossbar.modules.sys.service;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TuserPostService;
import com.ossbar.modules.sys.domain.TuserPost;
import com.ossbar.modules.sys.persistence.TuserPostMapper;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author huj
 * @create 2022-08-25 16:06
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
public class TuserPostServiceImpl implements TuserPostService {

    @Autowired
    private TuserPostMapper tuserPostMapper;

    /**
     * <p>保存用户与岗位之间的关系</p>
     *
     * @param userId
     * @param postIds
     * @return
     * @author huj
     * @data 2019年5月29日
     */
    @Override
    public R saveOrUpdate(String userId, List<String> postIds) {
        if (postIds == null || postIds.size() == 0){
            return R.ok();
        }
        // 先删除
        tuserPostMapper.delete(userId);
        // 批量新增
        List<TuserPost> list = new ArrayList<>();
        Stream.iterate(0, i -> i + 1).limit(postIds.size()).forEach(i -> {
            TuserPost t = new TuserPost();
            // 列表中第一条为主岗位
            if (i <= 0) {
                t.setIsMain("1");
            } else {
                t.setIsMain("0");
            }
            t.setUserJobid(Identities.uuid());
            t.setPostId(postIds.get(i));
            t.setUserId(userId);
            list.add(t);
        });
        if (list.size() > 0) {
            tuserPostMapper.insertBatch(list);
        }
        return R.ok();
    }

    /**
     * 根据用户id，查询岗位id
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> selectPostIdListByUserId(String userId) {
        return null;
    }
}
