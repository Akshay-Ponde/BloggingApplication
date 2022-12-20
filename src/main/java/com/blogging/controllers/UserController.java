package com.blogging.controllers;

import com.blogging.payloads.ResponseApi;
import com.blogging.payloads.UserDto;
import com.blogging.services.UserService;
import com.blogging.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;



    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUser =  this.userService.createUser(userDto);
        return  new ResponseEntity<>(createdUser , HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable Integer id)
    {
        UserDto updatedUser = this.userService.updateUser(userDto ,id);
        return new ResponseEntity<>(updatedUser , HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteUser(@PathVariable Integer id)
    {
        this.userService.deleteUser(id);
        return new ResponseEntity<ResponseApi>(new ResponseApi("User Deleted Successfully." , true) , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id)
    {
        UserDto user = this.userService.getUserById(id);
        return new ResponseEntity<>(user , HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<UserDto> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
