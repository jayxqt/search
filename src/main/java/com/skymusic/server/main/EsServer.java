package com.skymusic.server.main;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import com.skymusic.common.enums.ErrorCode;
import com.skymusic.common.exception.EsException;
import com.skymusic.common.util.PropertiesUtil;
import com.skymusic.server.service.impl.DocumentDataRemoteServiceImpl;
import com.skymusic.server.service.impl.IndexDataRemoteServiceImpl;
import com.skymusic.server.service.impl.TypeDataRemoteServiceImpl;

public class EsServer {

    private static final String URL_REMOTE_SERVICE="rmi://%s:%s/%s";

    private static final String PROPERTIES_NAME="Server.conf.properties";

    private static Properties serverSetting=new Properties();

    public static void init(String host, Integer port, String classSources) throws EsException {
        Class<?> clazz;
        IndexDataRemoteServiceImpl indexDataRemoteService=null;
        TypeDataRemoteServiceImpl typeDataRemoteService=null;
        DocumentDataRemoteServiceImpl documentDataRemoteService=null;
        try {
            LocateRegistry.createRegistry(port);// 生成远程对象注册表Registry的实例，并指定端口为8888（默认端口是1099）

            String[] classSourceArry=classSources.split(",");
            for(String classSource: classSourceArry) {
                String[] classInfoArry=classSource.split("_");
                String hostName=classInfoArry[0];
                String classPath=classInfoArry[1];

                clazz=Class.forName(classPath);
                if(clazz.isAssignableFrom(IndexDataRemoteServiceImpl.class)) {
                    indexDataRemoteService=(IndexDataRemoteServiceImpl)clazz.newInstance();
                    Naming.bind(String.format(URL_REMOTE_SERVICE, host, port, hostName), indexDataRemoteService);// 把远程对象注册到RMI注册服务器上，并命名为esDataRemoteService,绑定的URL标准格式为：rmi://host:port/name

                } else if(clazz.isAssignableFrom(TypeDataRemoteServiceImpl.class)) {
                    typeDataRemoteService=(TypeDataRemoteServiceImpl)clazz.newInstance();
                    Naming.bind(String.format(URL_REMOTE_SERVICE, host, port, hostName), typeDataRemoteService);// 把远程对象注册到RMI注册服务器上，并命名为esDataRemoteService,绑定的URL标准格式为：rmi://host:port/name

                } else if(clazz.isAssignableFrom(DocumentDataRemoteServiceImpl.class)) {
                    documentDataRemoteService=(DocumentDataRemoteServiceImpl)clazz.newInstance();
                    Naming.bind(String.format(URL_REMOTE_SERVICE, host, port, hostName), documentDataRemoteService);// 把远程对象注册到RMI注册服务器上，并命名为esDataRemoteService,绑定的URL标准格式为：rmi://host:port/name
                }
                System.out.println(String.format(URL_REMOTE_SERVICE, host, port, hostName));
            }
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            throw new EsException(ErrorCode.ERROR_SERVER_CLASS_NOT_FOUND);
        } catch(RemoteException e) {
            e.printStackTrace();
            throw new EsException(ErrorCode.ERROR_CREATE_REMOTE_SERVICE);
        } catch(AlreadyBoundException e) {
            e.printStackTrace();
            throw new EsException(ErrorCode.ERROR_REMOTE_SERVICE_ALREADY_BOUND);
        } catch(MalformedURLException e) {
            e.printStackTrace();
            throw new EsException(ErrorCode.ERROR_REMOTE_SERVICE_URL);
        } catch(Exception e) {
            e.printStackTrace();
            throw new EsException(ErrorCode.ERROR_NEW_SERVER_INSTANTCE);
        }
    }

    public static void main(String args[]) {
        try {
            PropertiesUtil.loadConfig(serverSetting, EsServer.class, PROPERTIES_NAME);
            String host=PropertiesUtil.getProperty("DataServer.srv.host", serverSetting);
            Integer port=Integer.parseInt(PropertiesUtil.getProperty("DataServer.srv.port", serverSetting));
            String classSources=PropertiesUtil.getProperty("DataServer.srv.classSources", serverSetting);

            init(host, port, classSources);
            System.out.println("ES remote service start success!");
        } catch(EsException e) {
            e.printStackTrace();
            System.out.println("ES remote service start failed!");
            System.out.println(e.getErrorCode().getDesc());

        } catch(Exception e2) {
            e2.printStackTrace();
            System.out.println("ES remote service start failed!");
            System.out.println(e2.getMessage());
        }
    }
}
