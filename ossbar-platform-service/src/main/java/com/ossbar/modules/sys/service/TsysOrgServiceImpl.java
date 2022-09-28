package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.ossbar.common.cbsecurity.dataprivilege.annotation.DataFilter;
import com.ossbar.common.utils.ParamUtil;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysOrgService;
import com.ossbar.modules.sys.domain.TsysOrg;
import com.ossbar.modules.sys.domain.TsysParameter;
import com.ossbar.modules.sys.dto.org.SaveOrgDTO;
import com.ossbar.modules.sys.persistence.TsysOrgMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    private ServiceLoginUtil serviceLoginUtil;

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
        if (!Constant.SUPER_ADMIN.equals(serviceLoginUtil.getLoginUserId())) {
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


    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @Override
    @PostMapping("save")
    @SentinelResource("/sys/org/save")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "authorization_cache", allEntries = true)
    public R save(@RequestBody SaveOrgDTO dto) {
        TsysOrg tsysOrg = new TsysOrg();
        BeanUtils.copyProperties(tsysOrg, dto);
        if (!validateOrgName(tsysOrg)) {
            return R.error("在当前节点下,您输入的机构名称有重复,请重新输入");
        }
        tsysOrg.setOrgId(Identities.uuid());
        tsysOrg.setOrgCode(buildCode(tsysOrg));
        tsysOrg.setParentId(StrUtils.isEmpty(tsysOrg.getParentId()) ? "-1" : tsysOrg.getParentId());
        tsysOrg.setCreateTime(DateUtils.getNowTimeStamp());
        tsysOrg.setCreateUserId(serviceLoginUtil.getLoginUserId());
        // 状态:1有效 2、停用
        tsysOrg.setState(StrUtils.isEmpty(tsysOrg.getState()) ? "1" : tsysOrg.getState());
        tsysOrgMapper.insert(tsysOrg);
        return R.ok("机构新增成功").put(Constant.R_DATA, tsysOrg);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @Override
    @PostMapping("update")
    @SentinelResource("/sys/org/update")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "authorization_cache", allEntries = true)
    public R update(@RequestBody SaveOrgDTO dto) {
        TsysOrg tsysOrg = new TsysOrg();
        BeanUtils.copyProperties(tsysOrg, dto);
        return update(tsysOrg);
    }

    /**
     * 修改
     *
     * @param tsysOrg
     * @return
     */
    @Override
    @CacheEvict(value = "authorization_cache", allEntries = true)
    public R update(TsysOrg tsysOrg) {
        if (!validateOrgName(tsysOrg)) {
            return R.error("在当前机构下,您输入的机构名称有重复值,请重新输入");
        }
        // 取得修改前的的机构，比较是否有修改父机构，如果有修改则重新生成机构
        TsysOrg oldOrg = tsysOrgMapper.selectObjectById(tsysOrg.getOrgId());
        if (!Objects.equal(oldOrg.getParentId(), tsysOrg.getParentId())) {
            String orgCode = buildCode(tsysOrg);
            tsysOrg.setOrgCode(orgCode);
            // 修改子机构编码
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("parentCode", oldOrg.getOrgCode());
            List<TsysOrg> orgList = tsysOrgMapper.selectListByMap(m);
            for (TsysOrg org : orgList) {
                if (!Objects.equal(org.getOrgId(), tsysOrg.getOrgId())) {
                    org.setOrgCode(org.getOrgCode().replace(oldOrg.getOrgCode(), orgCode));
                    tsysOrgMapper.update(org);
                }
            }
        }
        tsysOrg.setState(StrUtils.isNull(tsysOrg.getState()) ? "1" : tsysOrg.getState());
        tsysOrg.setUpdateTime(DateUtils.getNowTimeStamp());
        tsysOrgMapper.update(tsysOrg);
        return R.ok("机构修改成功").put(Constant.R_DATA, tsysOrg);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @author huangwb
     * @date 2019-05-05 15:18
     */
    @Override
    @PostMapping("/deletes")
    @SentinelResource("/sys/org/deletes")
    @Transactional(rollbackFor = Exception.class)
    public R deleteBatch(@RequestBody String[] ids) {
        if (ids == null || ids.length == 0) {
            return R.error("您的参数信息有误，请检查参数信息是否正确");
        }
        List<TsysOrg> datas = null;
        for (int i = 0; i < ids.length; i++) {
            datas = tsysOrgMapper.selectListByParentId(ids[i]);
            if (datas != null && !datas.isEmpty()) {
                return R.error("此结点下存在子结点，请先删除子结点，再执行删除！");
            }
        }
        tsysOrgMapper.deleteBatch(ids);
        return R.ok("机构删除成功");
    }

    /**
     * 验证输入的机构名称在同辈节点下是否有重复
     *
     * @param tsysOrg
     * @author huangwb
     * @date 2019-06-15 09:18
     */
    private Boolean validateOrgName(TsysOrg tsysOrg) {
        if (!Strings.isNullOrEmpty(tsysOrg.getParentId())) {
            List<TsysOrg> parents = tsysOrgMapper.selectListByParentId(tsysOrg.getParentId()).stream()
                    .filter(a -> !a.getOrgId().equals(tsysOrg.getOrgId())).collect(Collectors.toList());
            for (TsysOrg org : parents) {
                if (Objects.equal(org.getOrgName(), tsysOrg.getOrgName())) {
                    return false;
                }
            }
        }
        return true;
    }

    // 生成新的机构编码
    private synchronized String buildCode(TsysOrg tsysOrg) {
        // 获取机构编码每级的长度
        List<TsysParameter> paraList = SpringContextUtils.getBean(ParamUtil.class).getByType("codeLength"); // 从数据库中获取数据
        int length = 0;
        for (TsysParameter para : paraList) {
            if ("orgCodeLength".equals(para.getParaKey())) {
                length = Integer.parseInt(para.getParano());
                break;
            }
        }
        // 取得兄弟机构编码的最大值
        String orgCode = "";
        String maxCode = tsysOrgMapper.selectMaxCodeByParenId(tsysOrg.getParentId());
        if (!tsysOrg.getParentId().equals("-1")) {
            // 计算编码
            if (maxCode == null) {// 如果没有兄弟机构，则表示是当前是父机构中的第一个子机构，编码通过父机构的编码计算
                // 取得父机构对象
                TsysOrg parent = tsysOrgMapper.selectObjectById(tsysOrg.getParentId());
                // orgCode = String.format("%0" + length + "d", 1);
                if (parent != null) {// 如果有父机构，则编码为父机构的编码+初始编码
                    orgCode = parent.getOrgCode() + "01";
                }
            } else {// 否则在兄弟机构的最大值上加1
                // 得到父编码
                String parentCode = maxCode.substring(0, maxCode.length() - length);
                // 得到子编码
                orgCode = maxCode.substring(maxCode.length() - length);
                // 在原子编码的基础上加1
                orgCode = parentCode + String.format("%0" + length + "d", Integer.parseInt(orgCode) + 1);
            }
        } else {
            // 取得父机构对象
            int num = Integer.valueOf(maxCode.substring(maxCode.length() - 1)) + 1;
            orgCode = "0" + num;
        }
        return orgCode;
    }

    /**
     * 移动资源菜单操作
     *
     * @param moveId   移动对象的id
     * @param targetId 目标对象的id
     * @param moveType 移动的类型
     * @return
     * @author huangwb
     * @date 2019-06-03 17:18
     */
    @Override
    @PostMapping("/drag")
    @SentinelResource("/sys/org/drag")
    public R drag(String moveId, String targetId, String moveType) {
        TsysOrg move = tsysOrgMapper.selectObjectById(moveId);
        TsysOrg target = tsysOrgMapper.selectObjectById(targetId);
        // 如果是移动到内部
        if (Objects.equal(moveType, "inner")) {
            // 获取最大的排序号
            move.setOrgSn(tsysOrgMapper.selectMaxOrderSnByParentId(target.getOrgId()) + 1);
            move.setParentId(target.getOrgId());
            String orgCode = buildCode(move);
            move.setOrgCode(orgCode);
        } else if (Objects.equal(moveType, "before")) {
            // 获取目标元素的所有同辈元素 如果目标元素和移动元素同级则移除移动元素
            List<TsysOrg> targetParentOrgs = tsysOrgMapper.selectListByParentId(target.getParentId()).stream()
                    .filter(org -> !org.getOrgId().equals(move.getOrgId())).collect(Collectors.toList());
            // 计算排序号
            int k = 0;
            String orgCode = "";
            if (!Objects.equal(target.getParentId(), "-1")) {
                TsysOrg parentOrg = tsysOrgMapper.selectObjectById(target.getParentId());
                orgCode = parentOrg.getOrgCode();
            } else {
                orgCode = "0";
            }
            // 遍历目标元素所有同辈元素
            for (int i = 0; i < targetParentOrgs.size(); i++) {
                TsysOrg tsysOrg = targetParentOrgs.get(i);
                // 排序号从1开始
                k++;
                // 由于是before 所以找到元素之后要在找到的元素将他的排序号再次+1 并将未+1的排序号给move对象
                // 留出一个空闲的排序号位置给move对象
                if (Objects.equal(tsysOrg.getOrgId(), target.getOrgId())) {
                    move.setOrgSn(k);
                    move.setOrgCode(orgCode + "0" + move.getOrgSn());
                    tsysOrg.setOrgSn(k + 1);
                } else {
                    // 否则k就是自然排序号
                    tsysOrg.setOrgSn(k);
                }
                tsysOrg.setOrgCode(orgCode + "0" + tsysOrg.getOrgSn());
                update(tsysOrg);
            }
            // 讲move的父id设置成target目标对象的父id
            move.setParentId(target.getParentId());
        } else {
            // 获取目标元素的所有同辈元素 如果目标元素和移动元素同级则移除移动元素
            List<TsysOrg> targetParentOrgs = tsysOrgMapper.selectListByParentId(target.getParentId()).stream()
                    .filter(org -> !org.getOrgId().equals(move.getOrgId())).collect(Collectors.toList());
            int k = 0;
            String orgCode = "";
            if (!Objects.equal(target.getParentId(), "-1")) {
                TsysOrg parentOrg = tsysOrgMapper.selectObjectById(target.getParentId());
                orgCode = parentOrg.getOrgCode();
            } else {
                orgCode = "0";
            }
            for (int i = 0; i < targetParentOrgs.size(); i++) {
                TsysOrg tsysOrg = targetParentOrgs.get(i);
                k++;
                // 由于我们是after操作 所以先自然排序
                tsysOrg.setOrgSn(k);
                // 当找到目标元素的时候 此时k再+1 留出一个空闲的排序号位置给move对象
                if (Objects.equal(tsysOrg.getOrgId(), target.getOrgId())) {
                    k++;
                    move.setOrgSn(k);
                    move.setOrgCode(orgCode + "0" + move.getOrgSn());
                }
                tsysOrg.setOrgCode(orgCode + "0" + tsysOrg.getOrgSn());
                update(tsysOrg);
            }
            move.setParentId(target.getParentId());
        }
        update(move);
        return R.ok().put("data", move);
    }

    /**
     * 点击上下按钮移动机构管理节点操作
     *
     * @param moveId   移动对象的id
     * @param targetId 目标对象的id
     * @return
     * @author huangwb
     * @date 2019-06-17 14:18
     */
    @Override
    public R clickDrag(String moveId, String targetId) {
        // 思路：交换两节点的OrgSN和OrgCode便可行
        TsysOrg move = tsysOrgMapper.selectObjectById(moveId);
        TsysOrg target = tsysOrgMapper.selectObjectById(targetId);
        Integer targetSn = target.getOrgSn();
        String targetCode = target.getOrgCode();
        target.setOrgSn(move.getOrgSn());
        target.setOrgCode(move.getOrgCode());
        move.setOrgSn(targetSn);
        move.setOrgCode(targetCode);
        tsysOrgMapper.update(target);
        tsysOrgMapper.update(move);
        move.setUpdateUserId(serviceLoginUtil.getLoginUserId());
        move.setUpdateTime(DateUtils.getNow());
        target.setUpdateUserId(serviceLoginUtil.getLoginUserId());
        target.setUpdateTime(DateUtils.getNow());
        recursionOrgDrag(target);
        recursionOrgDrag(move);
        return R.ok();
    }

    /**
     * 递归设置上移下移节点的子节点的orgCode
     *
     * @param tsysOrg
     * @author huangwb
     * @date 2019-06-18 10:18
     */
    private void recursionOrgDrag(TsysOrg tsysOrg) {
        List<TsysOrg> parents = tsysOrgMapper.selectListByParentId(tsysOrg.getOrgId());
        if (parents != null && !parents.isEmpty()) {
            for (int i = 0; i < parents.size(); i++) {
                parents.get(i).setOrgCode(tsysOrg.getOrgCode() + "0" + (i + 1));
                tsysOrgMapper.update(parents.get(i));
                recursionOrgDrag(parents.get(i));
            }
        }
    }

}
