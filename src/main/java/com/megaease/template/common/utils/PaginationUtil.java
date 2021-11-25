package com.megaease.template.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;


public class PaginationUtil {

    public static int getOffset(int page, int rows) {
        return (page - 1) * rows;
    }

    public static <T> PageInfo<T> buildPageInfo(int page, int pageSize, long total, int pages, List<T> list) {
        Page<T> result = new Page<>(page, pageSize);
        result.setTotal(total);
        result.setPages(pages);
        result.addAll(list);
        return new PageInfo<>(result);
    }
}
