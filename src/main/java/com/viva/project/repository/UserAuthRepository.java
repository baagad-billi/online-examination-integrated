package com.viva.project.repository;

import com.viva.project.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Long> {
    Optional<UserAuthEntity> findByUserName(@Param("user_name") String userName);
}
