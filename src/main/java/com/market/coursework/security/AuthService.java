package com.market.coursework.security;


import com.market.coursework.security.dto.LoginRequest;
import com.market.coursework.security.dto.LoginResponse;
import com.market.coursework.security.dto.SignUpRequest;
import com.market.coursework.security.model.User;
import com.market.coursework.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public Authentication authenticateRequest(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
//        String jwt = jwtUtils.generateJwtToken(auth);
//        UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
//
//        return LoginResponse.builder()
//                .jwt(jwt)
//                .email(details.getEmail())
//                .userId(details.getId().toHexString())
//                .build();
        return auth;
    }

    public String signUpUser(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("user with " + request.getEmail() + " already exist");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return "User was successfully created";
    }
}
