package com.skymusic.common.enums;

/**
 * 系统错误码表实体类
 * @author xqt
 */
public enum ErrorCode {

        /**
         * 加载properties配置文件异常
         */
        ERROR_LOAD_PROPERTIES_CONFIG(2001, "加载properties配置文件异常"),
        /**
         * 读取properties配置文件内容异常
         */
        ERROR_READING_PROPERTIES_CONFIG(2002, "读取properties配置文件内容异常"),

        /**
         * 远程service类不存在
         */
        ERROR_SERVER_CLASS_NOT_FOUND(3001, "远程service类不存在"),
        /**
         * 远程service类实例化异常
         */
        ERROR_NEW_SERVER_INSTANTCE(3002, "远程service类实例化异常"),

        /**
         * 创建远程对象发生异常
         */
        ERROR_CREATE_REMOTE_SERVICE(4001, "创建远程对象发生异常"),
        /**
         * 重复绑定对象异常
         */
        ERROR_REMOTE_SERVICE_ALREADY_BOUND(4002, "重复绑定对象异常"),
        /**
         * URL畸形异常
         */
        ERROR_REMOTE_SERVICE_URL(4003, "URL畸形异常");

    ErrorCode(int code, String desc) {
        this.code=code;
        this.desc=desc;
    }

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code=code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc=desc;
    }

}