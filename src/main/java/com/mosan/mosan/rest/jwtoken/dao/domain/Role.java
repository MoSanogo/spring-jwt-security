package com.mosan.mosan.rest.jwtoken.dao.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    public  UUID id;
    @NotBlank(message="Must not be null nor empty .")
    public  String name;

    @ManyToMany(mappedBy = "roles")
    public  Set<User> users;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name="role_authority",
            joinColumns = {@JoinColumn(name="ROLE_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="AUTHORITY_ID",referencedColumnName = "ID")}
    )
    public Set<Authority> authorities;
}
