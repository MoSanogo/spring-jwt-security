package com.mosan.mosan.rest.jwtoken.dao.domain.metamodels;

import com.mosan.mosan.rest.jwtoken.dao.domain.Animal;
import com.mosan.mosan.rest.jwtoken.dao.domain.User;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value="org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Animal.class)
public abstract class Animal_ {

    public static volatile String IsVaccinated="isVaccinated";
    public static volatile String Breed="breed";
    public static volatile SingularAttribute<Animal, User> owner;
    public  static volatile  String Name="name";
    public static volatile String Age="age";
}
