package com.ossbar.modules.evgl.activity.api;

import java.util.List;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityTaskTraineeAnswerFile;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglActivityTaskTraineeAnswerFileService extends IBaseService<TevglActivityTaskTraineeAnswerFile>{
	
    /**
     * 批量新增
     * @param list
     */
    void insertBatch(List<TevglActivityTaskTraineeAnswerFile> list);
}