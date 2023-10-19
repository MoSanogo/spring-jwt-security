package com.mosan.mosan.rest.jwtoken.dao.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.mosan.mosan.rest.jwtoken.dtos.UserResponseDto;
import static  com.mosan.mosan.rest.jwtoken.jsonviews.View.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NamedNativeQuery(name="User.findAllUsers",query="SELECT id,first_name,last_name,email,username FROM users  ;",
        resultSetMapping = "Mapping.UserResponseDto")
@SqlResultSetMapping(name="Mapping.UserResponseDto",
        classes=@ConstructorResult(targetClass= UserResponseDto.class,
                columns = {
                       @ColumnResult(name="id",type = UUID.class),
                        @ColumnResult(name="first_name"),
                        @ColumnResult(name="last_name"),
                        @ColumnResult(name="email"),
                        @ColumnResult(name="username"),}))
@Entity
@Table(name="users",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username","email"})
})
@NoArgsConstructor
public class User  {

    @JsonView(value = {UserView.External.class})
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @JsonView(value = {UserView.External.class})
    @NotBlank
    @Size(max=255)
    @Column(nullable=false)
    public  String username;

    @NotBlank
    @Size(max=255)
    @Column(nullable=false)
    public  String password;

    @JsonView(value = {UserView.External.class})
    @NotBlank
    @Size(max=255)
    @Column(nullable=false)
    @Email
    public  String email;

    @JsonView(value = {UserView.External.class})
    @NotBlank
    @Size(max=255)
    @Column(nullable=false)
    public String firstName;

    @JsonView(value = {UserView.External.class})
    @NotBlank
    @Size(max=255)
    @Column(nullable=false)
    public String lastName;

    @JsonIgnore
    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns = {@JoinColumn(name="USER_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="ROLE_ID",referencedColumnName = "ID")}
    )
    public Set<Role> roles;

    @Builder.Default
    public  Boolean accountNonExpired=true;

    @Builder.Default
    public  Boolean accountNonLocked=true;
    @Builder.Default
    public  Boolean credentialsNonExpired=true;
    @Builder.Default
    public  Boolean enabled=true;
    @JsonIgnore
    @Transient
    public  Set<Authority> authorities;
    public Collection<?extends GrantedAuthority> getAuthorities(){
        return this.roles
                .stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(Authority::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }




}
