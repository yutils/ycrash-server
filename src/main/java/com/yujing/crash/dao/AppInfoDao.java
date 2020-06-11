package com.yujing.crash.dao;

import com.yujing.crash.bean.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface AppInfoDao extends JpaRepository<AppInfo, Long>, JpaSpecificationExecutor<AppInfo> {
    //根据包名查询
    List<AppInfo> findAppInfosBy包名(String packageName);

    //根据ID查询
    AppInfo findAppInfoById(long id);

    //根据id删除
    @Transactional//事物，更新表都要用
    long deleteAppInfoById(long id);

    //根据id修改已处理信息
    @Transactional//事物，更新表都要用
    @Modifying(clearAutomatically = true)
    @Query(value = "update appInfo app set app.已处理 = ?1 where app.id = ?2",nativeQuery = true)
    int update已处理byId(boolean 已处理, long id);

    //查询所有包名
    @Modifying
    @Query(value = "select 包名 from appInfo group by 包名", nativeQuery = true)
    List<String> findGroup包名();

    //查询所有包名
    @Modifying
    @Query(value = "select 版本号 from appInfo where 包名 = ?1  group by 版本号", nativeQuery = true)
    List<String> findGroup版本号(String 包名);

    //查询所有软件名
    @Modifying
    @Query(value = "select 软件名称 from appInfo group by 软件名称", nativeQuery = true)
    List<String> findGroup软件名称();

}
