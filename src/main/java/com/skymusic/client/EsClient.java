package com.skymusic.client;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.skymusic.common.service.DocumentDataRemoteService;
import com.skymusic.common.service.IndexDataRemoteService;
import com.skymusic.common.service.TypeDataRemoteService;
import com.skymusic.common.util.PropertiesUtil;

public class EsClient {

    private static final String PROPERTIES_NAME="Client.conf.properties";

    private static Properties clientSetting=new Properties();

    private static IndexDataRemoteService indexDataRemoteService;

    private static TypeDataRemoteService typeDataRemoteService;

    private static DocumentDataRemoteService documentDataRemoteService;

    private static EsClient esClient;

    static {
        try {
            PropertiesUtil.loadConfig(clientSetting, EsClient.class, PROPERTIES_NAME);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ES client init failed.");
        }
    }

    private EsClient() {
        init();
    }

    public static EsClient getInstance() {
        if(null == esClient) {
            try {
                esClient=new EsClient();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return esClient;
    }

    private static void init() {
        String host=PropertiesUtil.getProperty("DataClient.srv.host", clientSetting);
        String port=PropertiesUtil.getProperty("DataClient.srv.port", clientSetting);
        String classSources=PropertiesUtil.getProperty("DataClient.srv.classSources", clientSetting);
        Class<?> clazz;
        try {
            Context namingContext=new InitialContext();
            String[] classSourceArry=classSources.split(",");
            for(String classPath: classSourceArry) {
                String hostName=classPath.substring(classPath.lastIndexOf(".") + 1, classPath.length());
                String url=String.format("rmi://%s:%s/%s", host, port, hostName);
                System.out.println(String.format("rmi://%s:%s/%s", host, port, hostName));

                clazz=Class.forName(classPath);
                if(clazz.isAssignableFrom(IndexDataRemoteService.class)) {
                    indexDataRemoteService=(IndexDataRemoteService)namingContext.lookup(url);

                } else if(clazz.isAssignableFrom(TypeDataRemoteService.class)) {
                    typeDataRemoteService=(TypeDataRemoteService)namingContext.lookup(url);

                } else if(clazz.isAssignableFrom(DocumentDataRemoteService.class)) {
                    documentDataRemoteService=(DocumentDataRemoteService)namingContext.lookup(url);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("EsDataRemoteService初始化异常");
        }
    }

    public IndexDataRemoteService getIndexDataRemoteService() {
        return indexDataRemoteService;
    }

    public TypeDataRemoteService getTypeDataRemoteService() {
        return typeDataRemoteService;
    }

    public DocumentDataRemoteService getDocumentDataRemoteService() {
        return documentDataRemoteService;
    }

    public static void main(String[] args) throws RemoteException {
        String index="xqttest";
        EsClient client=EsClient.getInstance();
        client.getIndexDataRemoteService().createIndex(index);
        client.getIndexDataRemoteService().dropIndex(index);
    }

}