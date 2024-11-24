package com.portfolio.scott.domains.user.service;

import com.portfolio.scott.configs.security.TokenProvider;
import com.portfolio.scott.controllers.dto.*;
import com.portfolio.scott.controllers.errors.*;
import com.portfolio.scott.domains.user.model.User;
import com.portfolio.scott.infrastructure.utils.EncryptionUtil;
import com.portfolio.scott.domains.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, TokenProvider tokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public UserDTO signup(SignupDTO signupDTO) {
        String regNoHash = hashRegNo(signupDTO.getRegNo());
        if (getUserByLoginId(signupDTO.getUserId()).isPresent() || existRegNo(regNoHash)) {
            throw new AlreadyUserException();
        }

        String uuid = UUID.randomUUID().toString();
        User user = User.builder()
            .loginId(signupDTO.getUserId())
            .name(signupDTO.getName())
            .passwordHash(encryptPassword(signupDTO.getPassword()))
            .salt(uuid)
            .regNoEnc(encryptRegNo(signupDTO.getRegNo(), signupDTO.getName(), uuid))
            .regNoHash(regNoHash).build();
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public TokenDTO login(LoginDTO dto) {
        TokenDTO tokenDTO = new TokenDTO();

        String userId = dto.getUserId();
        String password = dto.getPassword();

        User user = getUserByLoginId(userId).orElseThrow(LoginFailedException::new);

        if (!matchesPassword(password, user.getPasswordHash())) {
            throw new UserPasswordMismatchException();
        }
        tokenDTO.setAccessToken(tokenProvider.createAccessToken(mapToDTO(user)));
        return tokenDTO;
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLoginId(user.getLoginId());
        userDTO.setName(user.getName());
        userDTO.setCreatedDate(user.getCreatedDate());
        return userDTO;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByLoginId(String userId) {
        return userRepository.findByLoginId(userId);
    }

    public UserIncludeRegNoDTO getUserIncludeRegNoById(Long id) {
        return userRepository.findById(id)
            .map(user -> {
                UserIncludeRegNoDTO userDto = new UserIncludeRegNoDTO();
                userDto.setId(user.getId());
                userDto.setName(user.getName());
                userDto.setRegNo(decryptRegNo(user.getRegNoEnc(), user.getName(), user.getSalt()));
                return userDto;
            })
            .orElseThrow(UserNotFoundException::new);
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public String encryptPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean matchesPassword(String plainPassword, String encodedPassword) {
        return passwordEncoder.matches(plainPassword, encodedPassword);
    }

    public boolean existRegNo(String regNo) {
        return userRepository.countByRegNoHash(regNo) > 0;
    }

    private String hashRegNo(String regNo) {
        try {
            return EncryptionUtil.hash(regNo);
        } catch (Exception e) {
            throw new EncryptionEncryptFailedException("Failed Encrypt Hash.");
        }
    }

    private String encryptRegNo(String regNo, String name, String salt) {
        try {
            return EncryptionUtil.encrypt(regNo, name.toCharArray(), salt);
        } catch (Exception e) {
            throw new EncryptionEncryptFailedException();
        }
    }

    private String decryptRegNo(String regNo, String name, String salt) {
        try {
            return EncryptionUtil.decrypt(regNo, name.toCharArray(), salt);
        } catch (Exception e) {
            throw new EncryptionDecryptFailedException();
        }
    }

}
