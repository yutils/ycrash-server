package com.yujing.crash.bean;

import javax.persistence.*;

/**
 * 手机和APP信息
 *
 * @author yujing  2019年6月19日16:25:21
 */
@Entity
@Table(name = "appInfo")
public class AppInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean 已处理;
    private String isDebug;
    private String 提交时间;
    private String 设备时间;
    private String 软件名称;
    private String 包名;
    private String 版本名;
    private String 版本号;
    private String 第一次安装时间;
    private String 最后一次更新时间;
    private String 安装路径;
    private String 分辨率;
    private String DPI;
    private String imei;
    private String androidId;
    private String 手机号;
    private String 网络运营商;
    private String 网络类型;
    private String 位置;
    private String 精度;
    private String 高度;
    private String 方向;
    private String 速度;
    private String 定位时间;
    private String 定位类型;
    @Column(columnDefinition = "text")
    private String 其他信息;
    @Column(columnDefinition = "text")
    private String 崩溃信息;
    private String 日志存放位置;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean is已处理() {
        return 已处理;
    }

    public void set已处理(boolean 已处理) {
        this.已处理 = 已处理;
    }

    public String getIsDebug() {
        return isDebug;
    }

    public void setIsDebug(String isDebug) {
        this.isDebug = isDebug;
    }

    public String get提交时间() {
        return 提交时间;
    }

    public void set提交时间(String 提交时间) {
        this.提交时间 = 提交时间;
    }

    public String get设备时间() {
        return 设备时间;
    }

    public void set设备时间(String 设备时间) {
        this.设备时间 = 设备时间;
    }

    public String get软件名称() {
        return 软件名称;
    }

    public void set软件名称(String 软件名称) {
        this.软件名称 = 软件名称;
    }

    public String get包名() {
        return 包名;
    }

    public void set包名(String 包名) {
        this.包名 = 包名;
    }

    public String get版本名() {
        return 版本名;
    }

    public void set版本名(String 版本名) {
        this.版本名 = 版本名;
    }

    public String get版本号() {
        return 版本号;
    }

    public void set版本号(String 版本号) {
        this.版本号 = 版本号;
    }

    public String get第一次安装时间() {
        return 第一次安装时间;
    }

    public void set第一次安装时间(String 第一次安装时间) {
        this.第一次安装时间 = 第一次安装时间;
    }

    public String get最后一次更新时间() {
        return 最后一次更新时间;
    }

    public void set最后一次更新时间(String 最后一次更新时间) {
        this.最后一次更新时间 = 最后一次更新时间;
    }

    public String get安装路径() {
        return 安装路径;
    }

    public void set安装路径(String 安装路径) {
        this.安装路径 = 安装路径;
    }

    public String get分辨率() {
        return 分辨率;
    }

    public void set分辨率(String 分辨率) {
        this.分辨率 = 分辨率;
    }

    public String getDPI() {
        return DPI;
    }

    public void setDPI(String DPI) {
        this.DPI = DPI;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String get手机号() {
        return 手机号;
    }

    public void set手机号(String 手机号) {
        this.手机号 = 手机号;
    }

    public String get网络运营商() {
        return 网络运营商;
    }

    public void set网络运营商(String 网络运营商) {
        this.网络运营商 = 网络运营商;
    }

    public String get网络类型() {
        return 网络类型;
    }

    public void set网络类型(String 网络类型) {
        this.网络类型 = 网络类型;
    }

    public String get位置() {
        return 位置;
    }

    public void set位置(String 位置) {
        this.位置 = 位置;
    }

    public String get精度() {
        return 精度;
    }

    public void set精度(String 精度) {
        this.精度 = 精度;
    }

    public String get高度() {
        return 高度;
    }

    public void set高度(String 高度) {
        this.高度 = 高度;
    }

    public String get方向() {
        return 方向;
    }

    public void set方向(String 方向) {
        this.方向 = 方向;
    }

    public String get速度() {
        return 速度;
    }

    public void set速度(String 速度) {
        this.速度 = 速度;
    }

    public String get定位时间() {
        return 定位时间;
    }

    public void set定位时间(String 定位时间) {
        this.定位时间 = 定位时间;
    }

    public String get定位类型() {
        return 定位类型;
    }

    public void set定位类型(String 定位类型) {
        this.定位类型 = 定位类型;
    }

    public String get其他信息() {
        return 其他信息;
    }

    public void set其他信息(String 其他信息) {
        this.其他信息 = 其他信息;
    }

    public String get崩溃信息() {
        return 崩溃信息;
    }

    public void set崩溃信息(String 崩溃信息) {
        this.崩溃信息 = 崩溃信息;
    }

    public String get日志存放位置() {
        return 日志存放位置;
    }

    public void set日志存放位置(String 日志存放位置) {
        this.日志存放位置 = 日志存放位置;
    }
}
