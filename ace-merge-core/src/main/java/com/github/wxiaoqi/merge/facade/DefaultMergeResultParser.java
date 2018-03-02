package com.github.wxiaoqi.merge.facade;

import java.util.List;

/**
 * @author ace
 * @create 2018/2/3.
 */
public class DefaultMergeResultParser implements IMergeResultParser {
    @Override
    public List parser(Object methodResult) {
        return (List<?>) methodResult;
    }
}
