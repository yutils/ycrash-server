package com.yujing.crash.dao;

import com.yujing.crash.bean.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao extends JpaRepository<Login, Long>, JpaSpecificationExecutor<Login> {
}