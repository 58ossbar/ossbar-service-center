package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.common.cbsecurity.dataprivilege.annotation.DataFilter;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysOrgService;
import com.ossbar.modules.sys.domain.TsysOrg;
import com.ossbar.modules.sys.persistence.TsysOrgMapper;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 机构管理
 * @author huj
 * @create 2022-08-17 11:44
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/org")
public class TsysOrgServiceImpl implements TsysOrgService {

    @Autowired
    private TsysOrgMapper tsysOrgMapper;
    @Autowired
    private ServiceLoginUtil loginUtils;

    /**
     * 获取子部门ID，用于数据过滤
     *
     * @param orgId
     * @author huangwb
     * @date 2019-05-05 15:18
     */
    @Override
    @RequestMapping("/getSubOrgIds")
    @SentinelResource("/sys/org/getSubOrgIds")
    public List<String> getSubOrgIdList(String orgId) {
        // 部门及子部门ID列表
        List<String> orgIdList = new ArrayList<>();
        // 获取子部门ID
        List<String> subIdList = tsysOrgMapper.selectOrgIdList(orgId);
        getOrgTreeList(subIdList, orgIdList);
        return orgIdList;
    }

    /**
     * 递归
     * TODO 优化 不要循环访问数据库
     */
    private void getOrgTreeList(List<String> subIdList, List<String> orgIdList) {
        for (String orgId : subIdList) {
            List<String> list = tsysOrgMapper.selectOrgIdList(orgId);
            if (list.size() > 0) {
                getOrgTreeList(list, orgIdList);
            }
            orgIdList.add(orgId);
        }
    }

    /**
     * 获取机构树形数据
     *
     * @param map
     * @return
     * @author huj
     * @data 2019年5月16日
     */
    @Override
    @GetMapping({ "getOrgTree" })
    @SentinelResource("/sys/org/getOrgTree")
    @DataFilter(tableAlias = "o", customDataAuth = "", selfUser = false)
    public R getOrgTree(Map<String, Object> map) {
        map.put("state", "1");
        Query query = new Query(map);
        List<TsysOrg> tsysOrgList = tsysOrgMapper.selectListByMap(query);
        // 如果不是超级管理员
        if (!Constant.SUPER_ADMIN.equals(loginUtils.getLoginUserId())) {
            List<TsysOrg> allList = tsysOrgMapper.selectListByMap(query);
            tsysOrgList.forEach(a -> {
                List<String> parentIds = new LinkedList<String>();
                initParantNode(allList, parentIds, a);
                a.setParentId(getParantId(tsysOrgList, parentIds));
            });
        }
        return R.ok().put(Constant.R_DATA, tsysOrgList);
    }

    /**
     * 根据条件查询机构列表
     *
     * @param map
     * @return
     */
    @Override
    @RequestMapping("/queryByMap")
    @SentinelResource("/sys/org/queryByMap")
    @DataFilter(tableAlias = "o", customDataAuth = "", selfUser = false)
    public R queryByMap(Map<String, Object> map) {
        Query query = new Query(map);
        List<TsysOrg> tsysOrgList = tsysOrgMapper.selectListByMap(query);
        Map<String, TsysOrg> dataMap = new HashMap<String, TsysOrg>();
        for (TsysOrg tsysOrg : tsysOrgList) {
            dataMap.put(tsysOrg.getOrgId(), tsysOrg);
        }
        // 排序操作
        Map<String, TsysOrg> sortMap = new TreeMap<String, TsysOrg>(new MapValueComparaor(dataMap));
        sortMap.putAll(dataMap);
        // 充分利用对象引用之间的关系 即便已经是Map集合 但数据来源引用还是collect的数据
        sortMap.forEach((k, v) -> {
            // 如果不是根节点
            if (!v.getParentId().equals("-1")) {
                sortMap.get(v.getParentId()).getChildren().add(v);
            }
        });
        List<TsysOrg> list = tsysOrgList.stream().filter(a -> a.getParentId().equals("-1")).collect(Collectors.toList());
        return R.ok().put(Constant.R_DATA, list);
    }

    class MapValueComparaor implements Comparator<String> {
        Map<String, TsysOrg> map;

        public MapValueComparaor(Map<String, TsysOrg> map) {
            this.map = map;
        }

        @Override
        public int compare(String o1, String o2) {
            if (map.get(map.get(o1).getParentId()) == null) {
                map.get(o1).setParentId("-1");
            } else if (map.get(map.get(o2).getParentId()) == null) {
                map.get(o2).setParentId("-1");
            }
            return map.get(o1).getOrgCode().compareTo(map.get(o2).getOrgCode());
        }

    }

    /**
     * 递归节点的所有父级节点
     *
     * @param allList
     * @param parentIds
     * @param org
     */
    private void initParantNode(List<TsysOrg> allList, List<String> parentIds, TsysOrg org) {
        // 保留父节点
        List<TsysOrg> parentNode = allList.stream().filter(a -> a.getOrgId().equals(org.getParentId())).collect(Collectors.toList());
        if (parentNode != null && parentNode.size() > 0) {
            parentIds.add(parentNode.get(0).getOrgId());
            initParantNode(allList, parentIds, parentNode.get(0));
        }
    }

    /**
     * 重新获取节点的父级节点
     * @param list
     * @param ids
     * @return
     */
    private String getParantId(List<TsysOrg> list, List<String> ids) {
        for (int i = 0; i < ids.size(); i++) {
            for (TsysOrg org : list) {
                if (org.getOrgId().equals(ids.get(i))) {
                    return ids.get(i);
                }
            }
        }
        return "-1";
    }
}
