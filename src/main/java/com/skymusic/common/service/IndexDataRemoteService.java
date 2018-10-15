package com.skymusic.common.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ES索引服务接口
 * @author xqt
 */
public interface IndexDataRemoteService extends Remote {

    /**
     * 创建索引(创建es数据库)
     * @param index 索引名(数据库名)
     * @throws RemoteException
     */
    void createIndex(String index) throws RemoteException;

    /**
     * 删除索引(删除es数据库)
     * @param index 索引名(数据库名)
     * @throws RemoteException
     */
    void dropIndex(String index) throws RemoteException;
}