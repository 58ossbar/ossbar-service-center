package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.constants.ExecStatus;
import com.ossbar.common.utils.*;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysAttachService;
import com.ossbar.modules.sys.api.TsysDictService;
import com.ossbar.modules.sys.domain.TsysDict;
import com.ossbar.modules.sys.dto.dict.SaveDictDTO;
import com.ossbar.modules.sys.dto.dict.SaveDictTypeDTO;
import com.ossbar.modules.sys.persistence.TsysDictMapper;
import com.ossbar.modules.sys.vo.dict.TsysDictVO;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huj
 * @create 2022-08-16 15:48
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/dict")
public class TsysDictServiceImpl implements TsysDictService {

    /**
     * 下标位置，查阅属性文件中的com.creatorblue.cb-upload-paths得知
     */
    private final static String INDEX_DICT = "1";

    @Autowired
    private TsysDictMapper tsysDictMapper;

    @Autowired
    private TsysAttachService tsysAttachService;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil serviceLoginUtil;
    @Autowired
    private UploadUtils uploadUtils;

    /**
     * 获取字典表中parentType!=0的字典信息
     *
     * @param map
     * @return
     */
    @Override
    public List<TsysDictVO> selectVoListByMap(Map<String, Object> map) {
        Query query = new Query(map);
        query.put("sidx", "SORT_NUM");
        query.put("order", "asc");
        List<TsysDictVO> list = tsysDictMapper.selectVoListByMap(query);
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(item -> {
                item.setDictUrl(uploadUtils.stitchingPath(item.getDictUrl(), INDEX_DICT));
            });
        }
        return list;
    }

    /**
     * 获取字典表中parentType!=0的字典信息
     *
     * @param map
     * @return
     */
    @Override
    public R selectListByMapNotZero(Map<String, Object> map) {
        Query query = new Query(map);
        query.put("sidx", "SORT_NUM");
        query.put("order", "asc");
        List<TsysDict> list = tsysDictMapper.selectListByMapNotZero(query);
        list.stream().forEach(item -> {
            item.setDictUrl(uploadUtils.stitchingPath(item.getDictUrl(), INDEX_DICT));
        });
        return R.ok().put(Constant.R_DATA, list);
    }

    /**
     * 查询操作
     *
     * @param params (page页码,limit显示条数)
     * @return R
     * @author huangwb
     */
    @Override
    @GetMapping("query")
    @SentinelResource("/sys/dict/query")
    //@Cacheable(value="dict_cache", key = "'cb_' + #params.get('parentType')")
    public R query(Map<String, Object> params) {
        params.put("sidx", "update_time");
        params.put("order", "desc");
        // 构建查询条件对象Query
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TsysDict> tsysDictList = tsysDictMapper.selectListByMapNotZero(query);
        tsysDictList.stream().forEach(item -> {
            item.setDictUrl(uploadUtils.stitchingPath(item.getDictUrl(), INDEX_DICT));
        });
        Map<String, String> map = new HashMap<String, String>();
        map.put("displaying", "displaying");
        map.put("displaySort", "displaySort");
        map.put("isdefault", "isdefault");
        convertUtil.convertParam(tsysDictList, map);
        PageUtils pageUtil = new PageUtils(tsysDictList, query.getPage(), query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 获取字典详情信息
     *
     * @param dictId
     * @return R
     * @author huangwb
     */
    @Override
    @GetMapping("/get/{dictId}")
    @SentinelResource("/sys/dict/get")
    public R view(@PathVariable("dictId") String dictId) {
        TsysDict tsysDict = tsysDictMapper.selectObjectById(dictId);
        if (tsysDict != null) {
            tsysDict.setDictUrl(uploadUtils.stitchingPath(tsysDict.getDictUrl(), INDEX_DICT));
        }
        return R.ok().put(Constant.R_DATA, tsysDict);
    }

    /**
     * 查询指定父字典id的所有数据
     * @param parentId
     * @return
     */
    @Override
    @GetMapping("/selectListParentId")
    @SentinelResource("/sys/dict/selectListParentId")
    public R selectListParentId(String parentId) {
        List<TsysDict> list = tsysDictMapper.selectListParentId(parentId);
        list.stream().forEach(item -> {
            item.setDictUrl(uploadUtils.stitchingPath(item.getDictUrl(), INDEX_DICT));
        });
        return R.ok().put(Constant.R_DATA, list);
    }

    /**
     * 根据条件查询数据
     *
     * @param dictname
     * @return
     * @throws Exception
     */
    @Override
    public R dicttree(String dictname) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentType", "0");
        map.put("dictName", dictname);
        List<TsysDict> list = tsysDictMapper.selectListByMap(map);
        list.stream().forEach(item -> {
            item.setDictUrl(uploadUtils.stitchingPath(item.getDictUrl(), INDEX_DICT));
        });
        return R.ok().put(Constant.R_DATA, list);
    }

    /**
     * 新增字典
     *
     * @param dict
     * @return
     */
    @Override
    @PostMapping("saveType")
    @SentinelResource("/sys/dict/saveType")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "dict_cache", allEntries = true)
    public R saveType(@RequestBody SaveDictTypeDTO dict) {
        TsysDict tsysDict = new TsysDict();
        BeanUtils.copyProperties(tsysDict, dict);
        String uuid = Identities.uuid();
        tsysDict.setDictId(uuid);
        tsysDict.setDictSort("1");
        tsysDict.setParentType("0");
        tsysDict.setDictCode("");
        tsysDict.setDictValue("");
        // 是否默认显示，1显示，0不显示
        tsysDict.setDisplaying(StrUtils.isEmpty(dict.getDisplaying()) ? "1" : dict.getDisplaying());
        tsysDict.setCreateTime(DateUtils.getNowTimeStamp());
        tsysDict.setCreateUserId(serviceLoginUtil.getLoginUserId());
        tsysDict.setSortNum(tsysDictMapper.getMaxSortNum("0"));
        tsysDictMapper.insert(tsysDict);
        // 如果上传了资源文件
        tsysAttachService.updateAttachForAdd(dict.getDictUrlAttachId(), uuid,  INDEX_DICT);
        return R.ok("字典分类新增成功").put(Constant.R_DATA, tsysDict);
    }

    /**
     * 修改字典
     *
     * @param dict
     * @return
     */
    @Override
    @PostMapping("updateType")
    @SentinelResource("/sys/dict/updateType")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "dict_cache", allEntries = true)
    public R updateType(@RequestBody SaveDictTypeDTO dict) {
        TsysDict tsysDict = new TsysDict();
        BeanUtils.copyProperties(tsysDict, dict);
        String time = DateUtils.getNowTimeStamp();
        String userId = serviceLoginUtil.getLoginUserId();
        List<TsysDict> updateList = new ArrayList<>();
        List<TsysDict> list = tsysDictMapper.selectListParentId(dict.getDictId());
        list.stream().forEach(item -> {
            TsysDict t = new TsysDict();
            t.setDictId(item.getDictId());
            t.setDictType(dict.getDictType());
            t.setDictName(dict.getDictName());
            t.setUpdateTime(time);
            t.setUpdateUserId(userId);
            updateList.add(t);
        });
        // 更新
        tsysDict.setUpdateTime(time);
        tsysDict.setUpdateUserId(userId);
        tsysDictMapper.update(tsysDict);
        // 更新子数据
        if (updateList.size() > 0) {
            tsysDictMapper.updateBatchByCaseWhen(updateList);
        }
        // 如果上传了资源文件
        tsysAttachService.updateAttachForEdit(dict.getDictUrlAttachId(), dict.getDictId(),  INDEX_DICT);
        return R.ok("字典分类修改成功").put(Constant.R_DATA, tsysDict);
    }

    /**
     * 新增字典
     *
     * @param dto
     * @return
     */
    @Override
    @PostMapping("save")
    @SentinelResource("/sys/dict/save")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "dict_cache", allEntries = true)
    public R save(@RequestBody SaveDictDTO dto) {
        R r = checkData(dto);
        if (!r.get("code").equals(0)) {
            return r;
        }
        TsysDict tsysDict = new TsysDict();
        BeanUtils.copyProperties(tsysDict, dto);
        tsysDict.setDictId(Identities.uuid());
        // 是否默认显示，1显示，0不显示
        tsysDict.setDisplaying(StrUtils.isEmpty(dto.getDisplaying()) ? "1" : dto.getDisplaying());
        tsysDict.setCreateTime(DateUtils.getNowTimeStamp());
        tsysDict.setCreateUserId(serviceLoginUtil.getLoginUserId());
        tsysDict.setSortNum(tsysDictMapper.getMaxSortNum(dto.getParentType()));
        tsysDictMapper.insert(tsysDict);
        // 如果上传了资源文件
        tsysAttachService.updateAttachForEdit(dto.getDictUrlAttachId(), tsysDict.getDictId(),  INDEX_DICT);
        return R.ok("字典新增成功").put(Constant.R_DATA, tsysDict);
    }

    /**
     * 修改字典
     *
     * @param dto
     * @return
     */
    @Override
    public R update(SaveDictDTO dto) {
        R r = checkData(dto);
        if (!r.get("code").equals(0)) {
            return r;
        }
        TsysDict tsysDict = new TsysDict();
        BeanUtils.copyProperties(tsysDict, dto);
        tsysDict.setUpdateTime(DateUtils.getNowTimeStamp());
        tsysDict.setUpdateUserId(serviceLoginUtil.getLoginUserId());
        // 如果上传了资源文件
        tsysAttachService.updateAttachForEdit(dto.getDictUrlAttachId(), tsysDict.getDictId(),  INDEX_DICT);
        return R.ok("字典修改成功");
    }

    private R checkData(SaveDictDTO dto) {
        // 查询父分类
        TsysDict parent = tsysDictMapper.selectObjectById(dto.getParentType());
        if (parent == null) {
            return R.error(ExecStatus.INVALID_PARAM.getCode(), ExecStatus.INVALID_PARAM.getMsg());
        }
        if (!parent.getDictType().equals(dto.getDictType()) || !parent.getDictName().equals(dto.getDictName())) {
            return R.error(ExecStatus.ILLEGAL_OPERATION.getCode(), ExecStatus.ILLEGAL_OPERATION.getMsg());
        }
        return R.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return R
     * @author huangwb
     */
    @Override
    @PostMapping("/removeType")
    @SentinelResource("/sys/dict/removeType")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "dict_cache", allEntries = true)
    public R deleteType(@RequestBody String[] ids) {
        if (ids == null || ids.length == 0) {
            return R.error("您的参数信息有误，请检查参数信息是否正确");
        }
        List<TsysDict> dictList = tsysDictMapper.selectListParentId(ids[0]);
        if (dictList != null && dictList.size() > 0) {
            return R.error("请先删除目录下的所有字典");
        }
        tsysDictMapper.deleteBatch(ids);
        // 解绑附件
        tsysAttachService.unBind(ids, INDEX_DICT);
        return R.ok("删除成功");
    }

}
