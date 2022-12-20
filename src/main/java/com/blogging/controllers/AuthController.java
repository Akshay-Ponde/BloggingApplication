package com.blogging.controllers;

import com.blogging.exceptions.InvalidLoginCredentialsException;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.AuthenticationRequest;
import com.blogging.payloads.AuthenticationResponse;
import com.blogging.payloads.UserDto;
import com.blogging.security.JwtManager;
import com.blogging.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtManager jwtManager;

    @Autowired
    UserService userService;

    @PostMapping("/login")
     public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
         try {
             Authentication authentication = this.authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
             );

             // return ResponseEntity.status(HttpStatus.OK).build();
         }
         catch (BadCredentialsException ex)
         {
             // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

             throw new InvalidLoginCredentialsException(authenticationRequest.getUserName() , ex);

            // throw new ResourceNotFoundException("Akshay" , "Ponde" , "yeols");
         }

         final UserDetails userDetails = this.userDetailsService
                 .loadUserByUsername(authenticationRequest.getUserName());

         final  String jwt = this.jwtManager.generateToken(userDetails);

         return ResponseEntity.ok(new AuthenticationResponse(jwt));
     }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUser =  this.userService.registerUser(userDto);
        return  new ResponseEntity<>(createdUser , HttpStatus.CREATED);
    }
}
