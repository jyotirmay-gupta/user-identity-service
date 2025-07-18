package com.redashwood.useridentity.repository;

import com.redashwood.useridentity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserIdentityRepository extends JpaRepository<UserEntity, UUID> {
}
