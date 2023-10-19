package com.mosan.mosan.rest.jwtoken.dao.repository.customs;

import com.mosan.mosan.rest.jwtoken.dao.domain.Animal;

import java.util.Collection;

public interface CustomizedAnimalRepository {
    Boolean persistAll(Collection<Animal> candidates);
}
