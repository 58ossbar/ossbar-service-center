package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysPostService;
import com.ossbar.modules.sys.domain.TsysPost;
import com.ossbar.modules.sys.domain.TuserPost;
import com.ossbar.modules.sys.dto.post.SavePostDTO;
import com.ossbar.modules.sys.persistence.TsysPostMapper;
import com.ossbar.modules.sys.persistence.TuserPostMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 岗位管理 实现类
 * @author huj
 * @create 2022-08-16 11:17
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/post")
public class TsysPostServiceImpl implements TsysPostService {

    @Autowired
    private TsysPostMapper tsysPostMapper;
    @Autowired
    private TuserPostMapper tuserPostMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil loginUtils;

    /**
     * 根据条件查询列表(返回List<Bean>)
     *
     * @param params
     * @return
     */
    @Override
    @GetMapping("/query")
    @SentinelResource("/sys/post/query")
    public R query(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<TsysPost> tsysPostList = tsysPostMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tsysPostList, "createUserId");
        convertUtil.convertUserId2RealName(tsysPostList, "updateUserId");
        convertUtil.convertParam(tsysPostList, "postType", "postType");
        PageUtils pageUtil = new PageUtils(tsysPostList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 查询明细
     *
     * @param id
     * @return
     * @author huj
     * @data 2019年5月29日
     */
    @Override
    public R selectObjectById(String id) {
        return R.ok().put(Constant.R_DATA, tsysPostMapper.selectObjectById(id));
    }

    /**
     * 根据条件查询列表(返回List<Bean>)
     *
     * @param params
     * @return
     */
    @Override
    public List<TsysPost> selectListByMap(Map<String, Object> params) {
        Query query = new Query(params);
        return tsysPostMapper.selectListByMap(query);
    }

    /**
     * 新增岗位
     *
     * @param post
     * @return
     */
    @Override
    @PostMapping("/save")
    @SentinelResource("/sys/post/save")
    public R save(@RequestBody SavePostDTO post) {
        TsysPost tsysPost = new TsysPost();
        BeanUtils.copyProperties(tsysPost, post);
        tsysPost.setPostId(Identities.uuid());
        tsysPost.setCreateUserId(loginUtils.getLoginUserId());
        tsysPost.setCreateTime(DateUtils.getNowTimeStamp());
        tsysPostMapper.insert(tsysPost);
        return R.ok("保存成功").put(Constant.R_DATA, tsysPost.getPostId());
    }

    /**
     * 修改岗位
     *
     * @param post
     * @return
     */
    @Override
    public R update(SavePostDTO post) {
        TsysPost tsysPost = new TsysPost();
        BeanUtils.copyProperties(tsysPost, post);
        // 排序号操作
        Map<String, Object> map = new HashMap<String, Object>();
        // 先获取已经存在的排序号
        map.put("sort", tsysPost.getSort());
        List<TsysPost> list = tsysPostMapper.selectListByMap(map);
        if (list != null && list.size() > 0) {
            TsysPost existedPost = tsysPostMapper.selectObjectById(tsysPost.getPostId());
            if (existedPost != null) {
                // 将修改的这个岗位的排序，赋值给被修改的岗位排序号
                list.get(0).setSort(post.getSort());
                tsysPostMapper.update(list.get(0));
            }
        }
        tsysPost.setUpdateUserId(loginUtils.getLoginUserId());
        tsysPost.setUpdateTime(DateUtils.getNowTimeStamp());
        tsysPostMapper.update(tsysPost);
        return R.ok("修改成功");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     * @author huj
     * @data 2019年5月29日
     */
    @Override
    public R deleteBatch(String[] ids) {
        List<TuserPost> list = tuserPostMapper.selectListByPostIds(ids);
        if (list != null && !list.isEmpty()) {
            convertUtil.convertUserId2RealName(list, "userId");
            String msg = "";
            for (TuserPost tuserPost : list) {
                msg += "【"+tuserPost.getUserId()+"】" + "任职了" + "【" + tuserPost.getPostName() + "】，";
            }
            return R.error("删除失败，有用户" + msg.substring(0, msg.length()-1)+"。请先移除更换用户的岗位");
        }
        tsysPostMapper.deleteBatch(ids);
        return R.ok("删除岗位成功").put("data", list);
    }

    /**
     * 更新排序号
     *
     * @param map
     * @return
     * @author huj
     * @data 2019年6月18日
     */
    @Override
    public R updateSort(Map<String, Object> map) {
        Query query = new Query(map);
        // 当前岗位ID
        String currPostId = query.get("currPostId").toString();
        // 目标岗位ID
        String targetPostId = query.get("targetPostId").toString();
        // 当前排序号
        String currSort = StrUtils.isNull(query.get("currSort")) ? "" : query.get("currSort").toString();
        // 目标排序号
        String targetSort = StrUtils.isNull(query.get("targetSort")) ? "" : query.get("targetSort").toString();
        if (StrUtils.isEmpty(currPostId) || StrUtils.isEmpty(targetPostId)) {
            return R.error("操作失败");
        }
        if (StrUtils.isNotEmpty(currSort) && StrUtils.isNotEmpty(targetSort)) {
            map.put("postId", currPostId);
            map.put("sort", targetSort);
            tsysPostMapper.update(map);
            map.clear();
            map.put("postId", targetPostId);
            map.put("sort", currSort);
            tsysPostMapper.update(map);
        } else {
            TsysPost currPost = tsysPostMapper.selectObjectById(currPostId);
            TsysPost targetPost = tsysPostMapper.selectObjectById(targetPostId);
            Integer cSort = currPost.getSort();
            Integer tSort = targetPost.getSort();
            currPost.setSort(tSort);
            targetPost.setSort(cSort);
            tsysPostMapper.updateSort(currPost);
            tsysPostMapper.updateSort(targetPost);
        }
        return R.ok("移动成功");
    }

    /**
     * 获取最大排序号
     *
     * @return
     * @author huj
     * @data 2019年6月24日
     */
    @Override
    public Integer getMaxSortNum() {
        return tsysPostMapper.getMaxSortNum();
    }
}
