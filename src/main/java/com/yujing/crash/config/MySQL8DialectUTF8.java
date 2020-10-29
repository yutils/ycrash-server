package com.yujing.crash.config;

import org.hibernate.dialect.MySQL8Dialect;

/**
 * 重写MySQL5Dialect 的getTableTypeString() 方法
 */
public class MySQL8DialectUTF8 extends MySQL8Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}