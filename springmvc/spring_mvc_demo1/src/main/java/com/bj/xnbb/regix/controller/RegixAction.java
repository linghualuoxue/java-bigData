package com.bj.xnbb.regix.controller;

import com.bj.xnbb.regix.domain.PageModel;
import com.bj.xnbb.regix.domain.RegixDomain;
import com.bj.xnbb.regix.service.RegixService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by hasee on 2017/8/4.
 */
@Controller
public class RegixAction {

    @Resource
    private RegixService service;

    @RequestMapping("index.do")
    public String index(){
        return "regix/index";
    }

    @RequestMapping("getPage.do")
    @ResponseBody
    public PageModel<RegixDomain> getPageView(){
        PageModel<RegixDomain> pm = service.getPageView();
        return pm;
    }

    /**
     * 根据id获取规则
     * @param id
     * @return
     */

    @RequestMapping("getRegixById")
    @ResponseBody
    public RegixDomain getRegixById(String id){
       return service.getDomainById(id);
    }

    /**
     * 增加或者更新规则
     * @param domain
     */
    @RequestMapping("saveOrUpdate")
    public void saveOrUpdate(RegixDomain domain){
        service.saveOrUpdate(domain);
    }



}
