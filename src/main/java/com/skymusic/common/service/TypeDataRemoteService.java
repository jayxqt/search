package com.skymusic.common.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ES类型服务接口
 * @author xqt
 */
public interface TypeDataRemoteService extends Remote {

    /**
     * 创建索引类型(创建es表)
     * @param index 索引名称(数据库名)
     * @param type 类型名称(表名)
     * @throws RemoteException
     */
    void createType(String index, String type) throws RemoteException;
}