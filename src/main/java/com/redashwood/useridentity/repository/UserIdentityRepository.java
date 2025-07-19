package com.redashwood.useridentity.repository;

import com.redashwood.useridentity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.contact c " +
            "LEFT JOIN FETCH u.address a " +
            "LEFT JOIN FETCH u.credential cr " +
            "WHERE c.email = :email")
    Optional<UserEntity> findByEmailWithAllRelations(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.credential cr " +
            "LEFT JOIN FETCH u.contact c " +
            "LEFT JOIN FETCH u.address a " +
            "WHERE cr.username = :username")
    Optional<UserEntity> findByUsernameWithAllRelations(@Param("username") String username);
}
