package com.skymusic.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.skymusic.common.enums.ErrorCode;
import com.skymusic.common.exception.EsException;

public class PropertiesUtil {

    /**
     * 加载服务器端配置文件
     * @param <T>
     * @throws EsException
     */
    public static <T> void loadConfig(Properties properties, Class<T> clazz, String fileName) throws EsException {
        try {
            properties.load(clazz.getClassLoader().getResourceAsStream(fileName));
        } catch(IOException e) {
            e.printStackTrace();
            throw new EsException(ErrorCode.ERROR_LOAD_PROPERTIES_CONFIG);
        }
    }

    public static String getProperty(String key, Properties properties) throws EsException {
        String value=properties.getProperty(key);
        if(null != value) {
            try {
                value=new String(value.getBytes("iso-8859-1"), "gb2312");
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new EsException(ErrorCode.ERROR_READING_PROPERTIES_CONFIG);
            }
        }
        return value;
    }
}