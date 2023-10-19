package com.mosan.mosan.rest.jwtoken.dao.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Where(clause = "deleted = false")
//As in the case of @Formula annotation, since we’re dealing with raw SQL, the @Where condition won’t be reevaluated until we flush the entity to the database and evict it from the context.
//The problem with @Where annotation is that it allows us to only specify a static query without parameters, and it can’t be disabled or enabled by demand=> @Filter annotation.
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    @NotBlank
    public String Name;
    @PositiveOrZero
    public int age;
    @NotNull
    public Breed breed;
    @Builder.Default
    public boolean isVaccinated=true;
    @Builder.Default
    public boolean deleted=false;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    public User owner;

}
