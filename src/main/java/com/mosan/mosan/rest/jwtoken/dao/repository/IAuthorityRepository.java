package com.mosan.mosan.rest.jwtoken.dao.repository;


import com.mosan.mosan.rest.jwtoken.dao.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAuthorityRepository  extends JpaRepository<Authority, UUID>, JpaSpecificationExecutor<Authority> {
}
