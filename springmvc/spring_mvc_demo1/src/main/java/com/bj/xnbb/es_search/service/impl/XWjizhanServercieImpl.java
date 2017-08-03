package com.bj.xnbb.es_search.service.impl;

import com.bj.xnbb.es_search.domain.RequestParam;
import com.bj.xnbb.es_search.constant.Constant;
import com.bj.xnbb.es_search.domain.WeijizhanDetail;
import com.bj.xnbb.es_search.entity.XWeijizhan;
import com.bj.xnbb.es_search.service.XWjizhanServercie;
import com.bj.xnbb.es_search.util.ClientHelper;
import com.bj.xnbb.es_search.util.ESQueryUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hasee on 2017/7/31.
 */
@Service
public class XWjizhanServercieImpl  implements XWjizhanServercie<XWeijizhan> {

    private static Map<String,String[]> yysMap = new HashMap<>();
    static {
        yysMap.put("all", null);
        yysMap.put("yidong",new String[]{"00","02","07"});
        yysMap.put("liantong",new String[]{"01","06"});
        yysMap.put("dianxin",new String[]{"03","05"});
    }

    @Override
    public List<XWeijizhan> queryData(RequestParam param) {

        List<XWeijizhan> result = new ArrayList<>();

        if(param!=null){
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();


            if(StringUtils.isNotEmpty(param.getProvince())){
                queryBuilder.must(QueryBuilders.matchPhraseQuery("province",param.getProvince()));
            }
            if(StringUtils.isNotEmpty(param.getCity())){
                queryBuilder.must(QueryBuilders.matchPhraseQuery("city",param.getCity()));
            }
            if(param.getStime()!=0 && param.getEtime()!=0){
                queryBuilder.must(QueryBuilders.rangeQuery("date")
                        .from(param.getStime()).to(param.getEtime()));
            }
            if(StringUtils.isNotEmpty(param.getYys())){
                  if(yysMap.get(param.getYys())!=null){
                     queryBuilder.must(QueryBuilders.termsQuery("bts",yysMap.get(param.getYys())));
                  }
            }
            if(StringUtils.isNotEmpty(param.getKeyword())){
                queryBuilder.must(QueryBuilders.matchPhraseQuery("body",param.getKeyword()));
            }
            ESQueryUtil esUtil = ESQueryUtil.getInstance();
            final Set<String> resultSet = new HashSet<String>();
            Client client = null;
            try {
                 client = ClientHelper.getInstance().getClient();
                Set<String> vSet = esUtil.searchByScroll(client, queryBuilder, Constant.INDEX, Constant.TYPE_X_SMSDATA
                        , new ESQueryUtil.HandlerData(){
                            @Override
                            public void beforeHandler(SearchHit[] hits) {
                                for (SearchHit hit : hits) {
                                    resultSet.add(String.valueOf(hit.getSource().get("wid")));
                                }
                            }

                            @Override
                            public void afterHandler(SearchHit[] hits) {
                                beforeHandler(hits);
                            }

                            @Override
                            public Set<String> getResult() {
                                return resultSet;
                            }


                        });

                if(vSet.size()>0){
                  final  List<XWeijizhan> v = new ArrayList<>();
                   result = esUtil.searchByScroll(client, QueryBuilders.idsQuery().ids(vSet)
                            , Constant.INDEX, Constant.TYPE_X_WEIJIZHEN,  new ESQueryUtil.HandlerData() {
                                @Override
                                public void beforeHandler(SearchHit[] hits) {

                                    for (SearchHit hit : hits) {
                                        Map<String,Object> source = hit.getSource();
                                        XWeijizhan xw =  new XWeijizhan();
                                        xw.setBody(String.valueOf(source.get("body")));
                                        xw.setBts(String.valueOf(source.get("bts")));
                                        xw.setCity(String.valueOf(source.get("city")));
                                        xw.setDate(String.valueOf(source.get("date")));
                                        xw.setProvince(String.valueOf(source.get("province")));
                                        xw.setAddress(String.valueOf(source.get("address")));
                                        xw.setNum(String.valueOf(source.get("num")));
                                        xw.setWid(String.valueOf(source.get("wid")));
                                        xw.setWjz_latitude(String.valueOf(source.get("wjz_latitude")));
                                        xw.setWjz_longitude(String.valueOf(source.get("wjz_longitude")));
                                        v.add(xw);
                                    }
                                }

                               @Override
                               public void afterHandler(SearchHit[] hits) {
                                   beforeHandler(hits);
                               }

                               @Override
                               public List<XWeijizhan> getResult() {
                                   return v;
                               }
                           });
                     }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ClientHelper.getInstance().closeClient(client);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public WeijizhanDetail queryWjizhanData(String wid) {
        final WeijizhanDetail wjz = new WeijizhanDetail();
        WeijizhanDetail result  =null;
        Client client = null;
        try {
            client = ClientHelper.getInstance().getClient();
            result = ESQueryUtil.getInstance().searchByScroll(client,
                    QueryBuilders.matchQuery("wid", wid),
                    Constant.INDEX,
                    Constant.TYPE_X_SMSDATA,
                    new ESQueryUtil.HandlerData() {
                        @Override
                        public void beforeHandler(SearchHit[] hits) {
                            for (SearchHit hit : hits) {
                                Map<String,Object> source = hit.getSource();
                                if(StringUtils.isEmpty(wjz.getWid())){
                                    wjz.setWid(wid);
                                    wjz.setBody(String.valueOf(source.get("body")));
                                    wjz.setProvince(String.valueOf(source.get("province")));
                                    wjz.setCity(String.valueOf(source.get("city")));
                                    wjz.setList(new ArrayList<>());
                                }

                                WeijizhanDetail.Latitude latitude =    new WeijizhanDetail.Latitude();
                                        latitude.setDate(String.valueOf(source.get("date")));
                                        latitude.setWjz_latitude(String.valueOf(source.get("wjz_latitude")));
                                        latitude.setWjz_longitude(String.valueOf(source.get("wjz_longitude")));
                                wjz.getList().add(latitude);
                            }
                        }

                        @Override
                        public void afterHandler(SearchHit[] hits) {
                            for (SearchHit hit : hits) {
                                Map<String,Object> source = hit.getSource();
                                WeijizhanDetail.Latitude latitude =    new WeijizhanDetail.Latitude();
                                latitude.setDate(String.valueOf(source.get("date")));
                                latitude.setWjz_latitude(String.valueOf(source.get("wjz_latitude")));
                                latitude.setWjz_longitude(String.valueOf(source.get("wjz_longitude")));
                                wjz.getList().add(latitude);
                            }
                        }

                        @Override
                        public WeijizhanDetail getResult() {
                            return wjz;
                        }
                    }
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                ClientHelper.getInstance().closeClient(client);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
