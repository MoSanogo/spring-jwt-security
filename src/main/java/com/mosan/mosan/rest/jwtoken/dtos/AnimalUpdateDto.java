package com.mosan.mosan.rest.jwtoken.dtos;

import com.mosan.mosan.rest.jwtoken.dao.domain.Breed;
import com.mosan.mosan.rest.jwtoken.dao.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.UUID;

public record  AnimalUpdateDto(
        @NotBlank String name  ,
        @PositiveOrZero int age,
        @NotNull Breed breed,
        @NotNull
        boolean isVaccinate,
        UUID ownerId
) {}



//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    public UUID id;
//    @NotBlank
//    public String Name;
//    @PositiveOrZero
//    public int age;
//    @NotBlank
//    public Breed breed;
//    @Builder.Default
//    public boolean isVaccinated=true;
//    @ManyToOne
//    @JoinColumn(name = "owner_id")
//    public User owner;