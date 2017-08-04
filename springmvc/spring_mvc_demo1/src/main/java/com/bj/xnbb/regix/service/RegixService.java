package com.bj.xnbb.regix.service;

import com.bj.xnbb.regix.domain.PageModel;
import com.bj.xnbb.regix.domain.RegixDomain;

/**
 * Created by hasee on 2017/8/4.
 */
public interface RegixService<T> {

    /**
     * 获取列表
     * @return
     */
    PageModel<T> getPageView();

    /**
     * 根据id获取实体类
     * @param id
     * @return
     */
    RegixDomain getDomainById(String id);

    /**
     * 存储或者更新数据
     * @param domain
     */
    void saveOrUpdate(T domain);

    /**
     * 根据id删除规则
     * @param id
     */
    void deleteRegix(String id);

}
