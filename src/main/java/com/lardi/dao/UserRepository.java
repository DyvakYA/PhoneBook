package com.lardi.dao;

import com.lardi.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.username = :username")
    boolean existsByUserName(@Param("username") String username);

    @Query("SELECT u FROM User u where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);
}
