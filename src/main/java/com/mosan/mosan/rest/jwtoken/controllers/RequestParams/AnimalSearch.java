package com.mosan.mosan.rest.jwtoken.controllers.RequestParams;

import com.mosan.mosan.rest.jwtoken.dao.domain.Breed;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AnimalSearch(@NotNull Breed breed, @PositiveOrZero  int Age, boolean isVaccinated) {}
