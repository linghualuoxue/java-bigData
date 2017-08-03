package com.bj.xnbb.es_search.controller;

import com.bj.xnbb.es_search.domain.RequestParam;
import com.bj.xnbb.es_search.domain.WeijizhanDetail;
import com.bj.xnbb.es_search.entity.XWeijizhan;
import com.bj.xnbb.es_search.service.XWjizhanServercie;
import com.bj.xnbb.es_search.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by hasee on 2017/7/31.
 */
@Controller
public class XWAction {

    private Logger log =  Logger.getLogger("XWAction.class");

    @Resource
    private XWjizhanServercie xWjizhanServercie;

    /**
     * 根据条件进行查询smsdata 然后返回wjz表中的数据，
     * @param param
     * @return
     */
    @RequestMapping("getData")
    public @ResponseBody List<XWeijizhan> getWjizhanData(RequestParam param){
        log.info("客户端发送条件请求："+ DateUtils.getDate()+";请求参数："+param.toString());
        return xWjizhanServercie.queryData(param);
    }

    @RequestMapping("getWjzDetail")
    public @ResponseBody WeijizhanDetail getWjizhanData(String wid){
        log.info("客户端发送详情请求："+DateUtils.getDate()+";wid="+wid);
        return xWjizhanServercie.queryWjizhanData(wid);
    }

}
