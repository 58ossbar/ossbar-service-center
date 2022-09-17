package com.ossbar.modules.evgl.book.persistence;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.evgl.book.domain.TevglBookChapter;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> Title: 章节</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglBookChapterMapper extends BaseSqlMapper<TevglBookChapter> {
}
