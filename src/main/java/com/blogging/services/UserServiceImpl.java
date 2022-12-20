package com.blogging.services;

import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.exceptions.userNameAlreadyExistsException;
import com.blogging.models.Role;
import com.blogging.models.User;
import com.blogging.payloads.UserDto;
import com.blogging.repositories.RoleRepo;
import com.blogging.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = this.DtoToUser(userDto);

        if(userRepo.existsByEmail(user.getEmail()))
        {
            throw new userNameAlreadyExistsException(user.getEmail());
        }

        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        Role role = this.roleRepo.findByName("ROLE_USER");
        user.getRoles().add(role);
        User savedUser = this.userRepo.save(user);
        return this.UserToDto(savedUser);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.DtoToUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        Role role = this.roleRepo.findByName("ROLE_USER");
        user.getRoles().add(role);
        User savedUser = this.userRepo.save(user);
        return this.UserToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto , Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        User savedUser = this.userRepo.save(user);

        return this.UserToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , id));
        return this.UserToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();
        return users.stream().map(user -> this.UserToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , id));
        user.setPosts(null);
        user.setComments(null);
        user.setRoles(null);
        this.userRepo.delete(user);
    }

    User DtoToUser(UserDto userDto)
    {
        User user = this.modelMapper.map(userDto , User.class);
        return user;
    }

    UserDto UserToDto(User user)
    {
        UserDto userDto = this.modelMapper.map(user , UserDto.class);
        return userDto;
    }
}
