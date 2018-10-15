package com.skymusic.server.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.skymusic.common.service.DocumentDataRemoteService;

public class DocumentDataRemoteServiceImpl extends UnicastRemoteObject implements DocumentDataRemoteService {

    public static final long serialVersionUID=1L;

    public DocumentDataRemoteServiceImpl() throws RemoteException {
        super();
    }

}