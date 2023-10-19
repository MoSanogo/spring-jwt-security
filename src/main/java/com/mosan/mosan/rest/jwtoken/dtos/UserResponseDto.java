package com.mosan.mosan.rest.jwtoken.dtos;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  UserResponseDto{
    public  UUID id;
    public  String username;
    public  String  email;
    public  String firstName;
    public  String lastName;
}
//public record UserResponseDto(UUID id,String username,String email,
//                              String firstName,String lastName) {
//}


//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    public UUID id;
//    @NotBlank
//    @Size(max=255)
//    @Column(nullable=false)
//    public  String username;
//    @NotBlank
//    @Size(max=255)
//    @Column(nullable=false)
//    public  String password;
//
//    @NotBlank
//    @Size(max=255)
//    @Column(nullable=false)
//    @Email
//    public  String email;
//
//    @NotBlank
//    @Size(max=255)
//    @Column(nullable=false)
//    public String firstName;
//    @NotBlank
//    @Size(max=255)
//    @Column(nullable=false)
//    public String lastName;