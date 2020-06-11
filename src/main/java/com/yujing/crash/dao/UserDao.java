package com.yujing.crash.dao;

import com.yujing.crash.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
//    @Query("select t from User t where t.name = :name")
//    User findByUserName(@Param("name") String name);
    User findUserByName(String name);
}