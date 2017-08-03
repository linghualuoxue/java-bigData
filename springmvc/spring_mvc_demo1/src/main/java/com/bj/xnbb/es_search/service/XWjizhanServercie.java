package com.bj.xnbb.es_search.service;

import com.bj.xnbb.es_search.domain.RequestParam;
import com.bj.xnbb.es_search.domain.WeijizhanDetail;

import java.util.List;

/**
 * Created by hasee on 2017/7/31.
 */
public interface XWjizhanServercie<T> {

    /**
     * 根据条件查询数据
     * @param param
     * @return
     */
    public List<T> queryData(RequestParam param);

    WeijizhanDetail queryWjizhanData(String wid);
}
