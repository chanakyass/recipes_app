package spring.io.rest.recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.io.rest.recipes.exceptions.ApiOperationException;
import spring.io.rest.recipes.models.entities.User;
import spring.io.rest.recipes.repositories.UserRepository;
import spring.io.rest.recipes.services.dtos.entities.UserDto;
import spring.io.rest.recipes.services.dtos.entities.UserProxyDto;
import spring.io.rest.recipes.services.dtos.entities.UserUpdateDto;
import spring.io.rest.recipes.services.dtos.mappers.UserMapper;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserProxyDto registerUser(UserDto userDto) {

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findUserByEmail(userDto.getEmail()));
        if(optionalUser.isPresent()) {
            throw  new ApiOperationException("User already exists");
        }
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toUserProxyDto(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ApiOperationException("No such user"));
    }

    public UserProxyDto getUserProxyById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiOperationException("No such user"));
        return userMapper.toUserProxyDto(user);
    }

    public User getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findUserByEmail(email)).orElseThrow(() -> new ApiOperationException("No such user"));
    }

    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId()).orElseThrow(() -> new ApiOperationException("No such user"));
        userMapper.toUser(userUpdateDto, user);
    }
}
