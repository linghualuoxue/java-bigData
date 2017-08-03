package com.bj.xnbb.es_search.util;

import com.alibaba.fastjson.JSON;
import com.bj.xnbb.ssmTest.domain.User;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.*;

/**
 * Created by hasee on 2017/7/21.
 */
public class ESQueryUtil {


    private static class ESQueryUtilHelper{
        static ESQueryUtil util = new ESQueryUtil();
    }

    private ESQueryUtil(){}

    public static ESQueryUtil getInstance(){
        return ESQueryUtilHelper.util;
    }

    /**
     * 创建索引
     * @param client
     * @param user
     * @return
     */
    public static void create(String index,String type,Client client,User user){
        IndexRequestBuilder requestBuilder = client.prepareIndex(index,type,String.valueOf(user.getId()))
                .setRefresh(true);
        IndexResponse response = requestBuilder.setSource(JSON.toJSONString(user)).execute().actionGet();
    }

    /**
     * 根据字段查询
     * @param client
     * @param field
     * @param value
     */
    public static void getByField(String index,String type,Client client,String field,String value){
        List<String> list = new ArrayList<String>();
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.matchQuery(field,value))
                .setFrom(0).setSize(10).setExplain(true).execute().actionGet();
        SearchHits hits = response.getHits();
        for (int i = 0; i <hits.getHits().length ; i++) {
            list.add(hits.getHits()[i].getSourceAsString());
        }
        System.out.printf(JSON.toJSONString(list));
    }

    /**
     * 根据id获取某个值
     * @param client
     * @param id
     * @return
     */
    public static String get(String index,String type,Client client,String id){
       GetResponse response = client.prepareGet(index,type,id).get();
       Map<String,Object> value =  response.getSourceAsMap();
       return JSON.toJSONString(value);
    }

    /**
     *
     * @param queryBuilder 查询builder
     * @param index     索引
     * @param type      文档
     * @param size      获取条数
     * @return
     */
    public <T>T queryData(Client client, QueryBuilder queryBuilder, String index, String type,int size,HandlerData handler){
            SearchResponse response = client.prepareSearch(index).setTypes(type)
                    .setQuery(queryBuilder)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setFrom(0).setSize(size)
                    .setExplain(true).execute().actionGet();
            SearchHit[] hits = response.getHits().getHits();
            return handler.getResult();    //更改代码
    }

    /**
     * scroll的方式查询，更高效
     * @param client
     * @param queryBuilder
     * @param index
     * @param type
     * @param handler
     */
    public <T>T searchByScroll(Client client, QueryBuilder queryBuilder, String index, String type,HandlerData handler){
     //   List<SearchHit[]> result = new ArrayList<>();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch();
        searchRequestBuilder.setIndices(index);
        searchRequestBuilder.setQuery(queryBuilder);
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
        searchRequestBuilder.setTypes(type);
        searchRequestBuilder.setScroll(new TimeValue(3000));

        SearchResponse searchResponse = searchRequestBuilder.get();
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
       // result.add(searchHits);
        handler.beforeHandler(searchHits);
        /*for (SearchHit searchHit : searchHits) {
            resultList.add(searchHit.getSourceAsString());
            //System.out.println(searchHit.getSourceAsString());
        }*/

        while (true){
            SearchScrollRequestBuilder searchScrollRequestBuilder = client.prepareSearchScroll(scrollId);
            searchScrollRequestBuilder.setScroll(new TimeValue(3000));
            searchResponse = searchScrollRequestBuilder.get();
            if(searchResponse.getHits().getHits().length==0){
                break;
            }
            searchHits = searchResponse.getHits().getHits();
          //  result.add(searchHits);
            handler.afterHandler(searchHits);
            scrollId = searchResponse.getScrollId();
        }
       return handler.getResult();
    }


    /**
     * 删除数据
     * @param client
     * @param index
     * @param type
     * @param id
     */
    public static void delete(Client client,String index,String type,String id){
          DeleteResponse response = client.prepareDelete(index,type,id).get();
        System.out.println("删除一条数据："+response.isFound());
    }

    /**
     * 数据处理
      */
   public interface HandlerData{
       public void beforeHandler(SearchHit[] hits);
       public void afterHandler(SearchHit[] hits);
       public <T>T getResult();
   }
}
