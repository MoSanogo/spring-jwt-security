package com.mosan.mosan.rest.jwtoken.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.mosan.mosan.rest.jwtoken.Services.UserCredentials;
import com.mosan.mosan.rest.jwtoken.dao.domain.Authority;
import com.mosan.mosan.rest.jwtoken.dao.domain.Role;
import com.mosan.mosan.rest.jwtoken.dao.domain.User;
import com.mosan.mosan.rest.jwtoken.dao.repository.IUserRepository;
import com.mosan.mosan.rest.jwtoken.dtos.UserResponseDto;
import com.mosan.mosan.rest.jwtoken.exceptions.NotFound;
import static com.mosan.mosan.rest.jwtoken.jsonviews.View.*;
import com.mosan.mosan.rest.jwtoken.utils.AuthSignUpResponse;
import com.mosan.mosan.rest.jwtoken.utils.IJwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    public IJwtTokenProvider jwtTokenProvider;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    public IUserRepository userRepository;
   @PreAuthorize("hasAuthority('subscription.read')")
    @GetMapping("")
    public Collection<UserResponseDto> getAllUserRecords(){
        System.out.println("okay");
        System.out.println(userRepository.findAll());
        return userRepository.findAllUsers();
    }
    @PostMapping("/signup")
    public AuthSignUpResponse SignUp(@RequestBody UserCredentials credentials){
        Authority readSubscription= Authority.builder().permission("subscription.read").build();
        Authority writeSubscription= Authority.builder().permission("subscription.write").build();
        Authority deleteSubscription= Authority.builder().permission("subscription.delete").build();
        Role admin= Role.builder().name("ADMIN").build();
        admin.setAuthorities(Set.of(readSubscription,writeSubscription,deleteSubscription));
        User user= userRepository.save(User.builder()
                .email("modisalhydro@gmail")
                .firstName("Modibo")
                .lastName("Sanogo")
                .username(credentials.getUsername())
                .password(passwordEncoder.encode(credentials.getPassword()))
                .role(admin)
                .build());
        var token =jwtTokenProvider.generateTokenFromString(user.getUsername());
        return new AuthSignUpResponse(user.getId(), user.getUsername(), user.getEmail(),token,"not implemented");

    }
    @JsonView(value = {UserView.External.class})
    @PreAuthorize("@permission.decide(#root,'subscription.read',#userID)")
    @GetMapping("/{userID}")
     public ResponseEntity<User> getUser(@PathVariable UUID userID) throws NotFound {
      var candidate=  userRepository.findById(userID)
                        .orElseThrow(()->new NotFound("Could not found any user record " +
                                "for userID" +userID));

        return ResponseEntity.status(200).body(candidate);
     }
    @GetMapping("/refresh_token")
    public String refreshToken(){
        return "";
    }

}
