package com.blogging.services;

import com.blogging.payloads.UserDto;
import org.apache.catalina.LifecycleState;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto , Integer id);
    UserDto getUserById(Integer id);
    List<UserDto> getAllUsers();
    void deleteUser(Integer id);



}
