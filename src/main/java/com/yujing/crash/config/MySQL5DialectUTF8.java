package com.yujing.crash.config;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * 重写MySQL5Dialect 的getTableTypeString() 方法
 */
public class MySQL5DialectUTF8 extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}