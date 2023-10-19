package com.mosan.mosan.rest.jwtoken.dtos;

import com.mosan.mosan.rest.jwtoken.dao.domain.Breed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


public record AnimalCreateDto(
        @NotBlank String name  ,
        @PositiveOrZero int age,
        @NotNull Breed breed,
        @NotNull
        boolean isVaccinate
) { }
