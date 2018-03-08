package com.sun.demo.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @auther zhsun5@iflytek.com
 * @date 2017/12/22
 */
public class DefaultElasticSearchRepo {
    private RestHighLevelClient client ;

    public void init(String host, int port){
        client =  new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, "http")));
    }

    public void close(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String index, String type, String id, String json){
        IndexRequest request = new IndexRequest(index, type, id);
        request.source(json, XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(indexResponse);
    }

    public String get(String index, String type, String id){
        GetRequest request = new GetRequest(index, type, id);
        GetResponse response = null;
        try {
            response = client.get(request);
            return response.getSourceAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(String index, String type, String id, String json){
        UpdateRequest request = new UpdateRequest(index, type, id).doc(json);
        UpdateResponse response = null;
        try {
            response = client.update(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }

    public void delete(String index, String type, String id){
        DeleteRequest request = new DeleteRequest(index, type, id);
        DeleteResponse response = null;
        try {
            response = client.delete(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }

    public String search(String index, String type, QueryBuilder query){
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(query).from(0).size(5);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest);
            SearchHits hits = searchResponse.getHits();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                System.out.println(sourceAsString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteIndex(String index){
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        DeleteIndexResponse deleteIndexResponse = null;
        try {
            deleteIndexResponse = client.indices().deleteIndex(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(deleteIndexResponse);
    }
}
