package com.skymusic.server.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.skymusic.common.exception.EsException;
import com.skymusic.common.service.TypeDataRemoteService;
import com.skymusic.server.util.SearchClient;

/**
 * ES类型服务接口实现类
 * @author xqt
 */
public class TypeDataRemoteServiceImpl extends UnicastRemoteObject implements TypeDataRemoteService {

    private static final long serialVersionUID=1L;

    public TypeDataRemoteServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void createType(String index, String type) throws RemoteException {
        try {
            Client client=SearchClient.getEsClient();

            XContentBuilder builder=XContentFactory.jsonBuilder().startObject().startObject(type).startObject("properties")
                .startObject("id").field("type", "long").endObject().startObject("roomId").field("type", "string").endObject()
                .startObject("unAnalyzedRoomId").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("TraditionalRoomId").field("type", "string").endObject().startObject("name").field("type", "string")
                .endObject().startObject("unAnalyzedName").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("unAnalyzedBase64Name").field("type", "string").field("index", "not_analyzed").endObject().endObject()
                .endObject().endObject();

            PutMappingRequest mapping=Requests.putMappingRequest(index).type(type).source(builder);
            client.admin().indices().putMapping(mapping).actionGet();
        } catch(EsException e) {
            e.printStackTrace();
            throw new RemoteException(e.getErrorCode().getDesc());
        } catch(Exception e) {
            e.printStackTrace();
            throw new RemoteException();
        }
    }

}