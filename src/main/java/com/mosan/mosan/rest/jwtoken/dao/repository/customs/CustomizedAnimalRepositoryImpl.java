package com.mosan.mosan.rest.jwtoken.dao.repository.customs;

import com.mosan.mosan.rest.jwtoken.dao.domain.Animal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
public class CustomizedAnimalRepositoryImpl implements CustomizedAnimalRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Boolean persistAll(Collection<Animal> candidates) {

        candidates.forEach(c-> entityManager.persist(c));
        return true;
    }
}
