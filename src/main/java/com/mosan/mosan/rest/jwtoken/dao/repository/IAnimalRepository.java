package com.mosan.mosan.rest.jwtoken.dao.repository;

import com.mosan.mosan.rest.jwtoken.dao.domain.Animal;
import com.mosan.mosan.rest.jwtoken.dao.repository.customs.CustomizedAnimalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IAnimalRepository extends JpaRepository<Animal,UUID>, JpaSpecificationExecutor<Animal>, CustomizedAnimalRepository {}
