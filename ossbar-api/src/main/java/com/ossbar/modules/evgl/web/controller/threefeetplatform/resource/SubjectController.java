package com.ossbar.modules.evgl.web.controller.threefeetplatform.resource;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookChapterService;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.modules.evgl.book.domain.TevglBookChapter;
import com.ossbar.modules.evgl.book.vo.SaveChapterVo;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>课程</p>
 * <p>Title: SubjectController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p>
 * @author huj
 * @date 2019年7月5日
 */
@RestController
@RequestMapping("subject-api")
public class SubjectController {

    @Reference(version = "1.0.0")
    private TevglBookSubjectService tevglBookSubjectService;
    @Reference(version = "1.0.0")
    private TevglBookChapterService tevglBookChapterService;

    /**
     * 课程下拉列表
     * @param params
     * @return
     */
    @GetMapping("/listSelectSubject")
    public R listSelectSubject(@RequestParam Map<String, Object> params) {
        params.put("state", "Y"); // 状态(Y有效N无效)
        return tevglBookSubjectService.listSelectSubject(params);
    }

    /**
     * <p>保存章节</p>
     * @author huj
     * @data 2019年7月27日
     * @param tevglBookChapter
     * @return
     */
    @PostMapping("/saveChapter")
    @CheckSession
    public R saveChapter(@RequestBody(required = false) TevglBookChapter tevglBookChapter, HttpServletRequest request) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        // 如果是追加节点
        if (StrUtils.isNotEmpty(tevglBookChapter.getOperationType()) && "appendPeerNode".equals(tevglBookChapter.getOperationType())) {
            tevglBookChapter.setCreateUserId(traineeInfo.getTraineeId());
            return tevglBookChapterService.appendPeerNodes(tevglBookChapter);
        }
        // 如果是新增节点
        if (StrUtils.isEmpty(tevglBookChapter.getChapterId())) {
            tevglBookChapter.setCreateUserId(traineeInfo.getTraineeId());
            return tevglBookChapterService.saveChapterInfo(tevglBookChapter, traineeInfo.getTraineeId());
        } else {
            // 否则是修改节点
            tevglBookChapter.setUpdateUserId(tevglBookChapter.getCreateUserId());
            //return tevglBookChapterService.update(tevglBookChapter);
            return tevglBookChapterService.rename(tevglBookChapter.getPkgId(), tevglBookChapter.getChapterId(), tevglBookChapter.getChapterName(), traineeInfo.getTraineeId());
        }
    }

    /**
     * 批量保存章节
     * @param request
     * @param vo
     * @return
     */
    @PostMapping("/batchSaveChapter")
    @CheckSession
    public R batchSaveChapter(HttpServletRequest request, @RequestBody SaveChapterVo vo) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return tevglBookChapterService.batchSaveChapter(vo, traineeInfo.getTraineeId());
    }

    /**
     * <p>查看章节明细</p>
     * @author huj
     * @data 2019年7月18日
     * @param id 章节主键
     * @param type 标识：pkg表示是从教学包详情页面查看章节
     * @return
     */
    @GetMapping("/viewChapter")
    @CheckSession
    public R viewChapter(HttpServletRequest request, String id, String pkgId, String type) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return tevglBookChapterService.viewChapterInfo(id, traineeInfo.getTraineeId(), pkgId, type);
    }

    /**
     * <p>重命名节点</p>
     * @author huj
     * @data 2019年7月27日
     * @param id 教材或章节主键
     * @param name 名称
     * @param type 1表示重命名教材2表示重命名章节
     * @return
     */
    @GetMapping("/rename")
    public R rename(HttpServletRequest request, String pkgId, String id, String name, String type) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        if ("1".equals(type)) {
            //return tevglBookSubjectService.rename(id, name);
            return R.error("该节点不支持重命名！");
        }
        if ("2".equals(type)) {
            return tevglBookChapterService.rename(pkgId, id, name, traineeInfo.getTraineeId());
        }
        return R.ok();
    }

    /**
     * <p>删除节点</p>
     * @author huj
     * @data 2019年7月27日
     * @param pkgId 教学包主键
     * @param id
     * @param type 1课程2章节
     * @return
     */
    @GetMapping("/remove")
    @CheckSession
    public R remove(HttpServletRequest request, String pkgId, String id, String type) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        if ("1".equals(type)) {
            //return tevglBookSubjectService.remove(id, traineeInfo.getTraineeId());
            return R.error("该节点不允许删除！");
        }
        // 删除章节
        if ("2".equals(type)) {
            return tevglBookChapterService.removeV2(pkgId, id, traineeInfo.getTraineeId());
        }
        return R.ok();
    }


    /**
     * <p>移动，目前只是简单的修改排序号</p>
     * @author huj
     * @data 2019年7月27日
     * @param
     * @return
     */
    @GetMapping("/move")
    @CheckSession
    public R move(HttpServletRequest request, @RequestParam("currId") String currId, @RequestParam("targetId") String targetId
            ,@RequestParam("pkgId") String pkgId) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        return tevglBookChapterService.move(currId, targetId, traineeInfo.getTraineeId(), pkgId);
    }
}
