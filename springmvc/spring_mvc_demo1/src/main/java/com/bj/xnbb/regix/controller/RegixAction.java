package com.bj.xnbb.regix.controller;

import com.bj.xnbb.regix.domain.PageModel;
import com.bj.xnbb.regix.domain.RegixDomain;
import com.bj.xnbb.regix.service.RegixService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by hasee on 2017/8/4.
 */
@Controller
@RequestMapping("regix")
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
     * 根据type获取规则
     * @param type
     * @return
     */

    @RequestMapping("getRegixByType")
    @ResponseBody
    public RegixDomain getRegixByType(String type){
       return service.getDomainByType(type);
    }

    /**
     * 增加或者更新规则
     * @param domain
     */
    @RequestMapping("saveOrUpdate")
    public void saveOrUpdate(RegixDomain domain){
        service.saveOrUpdate(domain);
    }

    @RequestMapping("deleteType")
    public void deleteType(String type){
        service.deleteRegix(type);
    }


}
