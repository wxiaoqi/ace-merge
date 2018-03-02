package com.github.wxiaoqi.merge.facade;

import java.util.List;

/**
 * @author ace
 * @create 2018/2/3.
 */
public interface IMergeResultParser {
    /**
     * 提取防范返回值中需要合并的有效列表
     * @param methodResult
     * @return
     */
    public List parser(Object methodResult);
}
