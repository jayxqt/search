package com.skymusic.server.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.elasticsearch.client.Client;

import com.skymusic.common.exception.EsException;
import com.skymusic.common.service.IndexDataRemoteService;
import com.skymusic.server.util.SearchClient;

/**
 * ES索引服务接口实现类
 * @author xqt
 */
public class IndexDataRemoteServiceImpl extends UnicastRemoteObject implements IndexDataRemoteService {

    private static final long serialVersionUID=1L;

    public IndexDataRemoteServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void createIndex(String index) throws RemoteException {
        try {
            Client client=SearchClient.getEsClient();
            client.admin().indices().prepareCreate(index).execute().actionGet();
        } catch(EsException e) {
            e.printStackTrace();
            throw new RemoteException(e.getErrorCode().getDesc());
        } catch(Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void dropIndex(String index) throws RemoteException {
        try {
            Client client=SearchClient.getEsClient();
            client.admin().indices().prepareDelete(index).execute().actionGet();
        } catch(EsException e) {
            e.printStackTrace();
            throw new RemoteException(e.getErrorCode().getDesc());
        } catch(Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

}