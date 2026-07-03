package com.cvs.service;

import com.cvs.dto.LoginRequest;
import com.cvs.dto.RegisterRequest;
import com.cvs.dto.UserVO;
import com.cvs.model.User;
import com.cvs.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 用户登录
     */
    public UserVO login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        return UserVO.fromUser(user);
    }

    /**
     * 用户注册
     */
    public UserVO register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        User.Role role;
        try {
            role = User.Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("角色无效，请选择 TEACHER 或 STUDENT");
        }

        User user = new User(request.getUsername(), request.getPassword(),
                request.getRealName(), role);
        user = userRepository.save(user);

        return UserVO.fromUser(user);
    }

    /**
     * 根据ID查找用户
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    /**
     * 根据用户名查找
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
