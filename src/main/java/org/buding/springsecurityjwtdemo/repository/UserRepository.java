package org.buding.springsecurityjwtdemo.repository;

import org.buding.springsecurityjwtdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * @program: spring-security-jwt-demo
 * @author: miaochen
 * @create: 2019-07-05 10:03
 * @description:
 **/
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}
