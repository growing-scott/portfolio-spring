package com.portfolio.scott.domains.user.repository;

import com.portfolio.scott.domains.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(Long id);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByName(String name);

    Integer countByRegNoHash(String regNoHash);

}
