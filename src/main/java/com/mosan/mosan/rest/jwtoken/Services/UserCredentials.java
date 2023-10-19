package com.mosan.mosan.rest.jwtoken.Services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {
    public String username;
    public String password;
}
