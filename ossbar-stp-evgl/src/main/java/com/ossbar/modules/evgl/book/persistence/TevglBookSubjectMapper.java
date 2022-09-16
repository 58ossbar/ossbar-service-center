package com.ossbar.modules.evgl.book.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> Title: 课程教材</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglBookSubjectMapper extends BaseSqlMapper<TevglBookSubject> {
}
