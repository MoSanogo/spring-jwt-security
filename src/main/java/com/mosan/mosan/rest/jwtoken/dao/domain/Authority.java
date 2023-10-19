package com.mosan.mosan.rest.jwtoken.dao.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    public  UUID id;
    @NotBlank(message="Must not be null nor empty.")
    public  String permission;
    @ManyToMany(mappedBy = "authorities")
    public  Set<Role> roles;
}
