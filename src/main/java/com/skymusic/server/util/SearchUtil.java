package com.skymusic.server.util;

import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;

public class SearchUtil {

    /**
     * 批量向es中插入数据
     * @param index
     * @param type
     * @param list
     */
    public static void createBulkIndexResponse(String index, String type, List<String> list) {
        Client client=SearchClient.getEsClient();
        BulkRequestBuilder bulkRequest=client.prepareBulk();
        for(int i=0; i < list.size(); i++) {
            bulkRequest.add(client.prepareIndex(index, type).setSource(list.get(i)));
        }
        bulkRequest.execute().actionGet();
    }

    public static SearchHits searcherAndCheck(QueryBuilder queryBuilder, String indexname, String type) {
        Client client=SearchClient.getEsClient();
        SearchResponse searchResponse=
            client.prepareSearch(indexname).setTypes(type).setQuery(queryBuilder).setSize(100).setFrom(0).execute().actionGet();
        return searchResponse.getHits();
    }
}