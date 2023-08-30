package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.User;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

@RequiredArgsConstructor
@Service
public class
UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(STRICT);

        User user = mapper.map(userDto, User.class);
        user.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(user);
    }

    public ResponseUser getUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("User Not Found");
        }
        ResponseUser response = new ModelMapper().map(user, ResponseUser.class);
        List<ResponseOrder> orders = new ArrayList<>();
        response.setOrders(orders);
        return response;
    }

    public List<ResponseUser> getUserByAll() {
        List<ResponseUser> users = new ArrayList<>();
        userRepository.findAll().forEach(v -> {
            users.add(new ModelMapper().map(v, ResponseUser.class));
        });
        return users;
    }
}
