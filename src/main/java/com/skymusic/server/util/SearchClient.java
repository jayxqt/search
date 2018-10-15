package com.skymusic.server.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class SearchClient {

    private static Settings settings;

    private static Client esClient;

    static {
        settings=Settings.settingsBuilder().put("host1", "127.0.0.1").put("client.transport.sniff", true).build();
    }

    private static void initEsClient() {
        try {
            esClient=TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("search1.audiocn.org"), 9300))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("search2.audiocn.org"), 9300))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("search3.audiocn.org"), 9300));
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static Client getEsClient() {
        if(null == esClient) {
            initEsClient();
        }
        return esClient;
    }
}