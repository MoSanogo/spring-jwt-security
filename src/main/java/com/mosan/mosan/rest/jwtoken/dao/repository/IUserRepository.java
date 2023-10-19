package com.mosan.mosan.rest.jwtoken.dao.repository;

import com.mosan.mosan.rest.jwtoken.dao.domain.User;
import com.mosan.mosan.rest.jwtoken.dtos.UserResponseDto;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    boolean  existsByUsername(String username);
//    @Query(value = "SELECT id, firstName,lastName,email,username FROM User")
//    List<UserResponseDto> findUsers();
    @Query(nativeQuery = true)
    List<UserResponseDto> findAllUsers();
}
