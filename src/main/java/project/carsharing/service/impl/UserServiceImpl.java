package project.carsharing.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.carsharing.dto.user.UserDto;
import project.carsharing.dto.user.UserRegistrationDto;
import project.carsharing.dto.user.UserUpdateRequestDto;
import project.carsharing.dto.user.UserUpdateRoleDto;
import project.carsharing.exception.RegistrationException;
import project.carsharing.mapper.UserMapper;
import project.carsharing.model.Role;
import project.carsharing.model.User;
import project.carsharing.repository.user.UserRepository;
import project.carsharing.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findById(Long id) {
        User user = findUserById(id);
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDto updateRole(Long id, UserUpdateRoleDto requestDto) {
        User user = findUserById(id);
        userMapper.updateRole(requestDto, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDto update(Long id, UserUpdateRequestDto requestDto) {
        User user = findUserById(id);
        userMapper.updateUser(requestDto, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDto register(UserRegistrationDto requestDto) {
        existsByEmail(requestDto);
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id " + id + " not found")
                );
    }

    private void existsByEmail(UserRegistrationDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(requestDto.getEmail() + "allready exists!");
        }
    }
}
