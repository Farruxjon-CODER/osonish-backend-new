package uz.osonish.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.osonish.backend.dto.AuthRequest;
import uz.osonish.backend.dto.AuthResponse;
import uz.osonish.backend.entity.User;
import uz.osonish.backend.enums.Role;
import uz.osonish.backend.repository.UserRepository;
import uz.osonish.backend.security.CustomUserDetailsService;
import uz.osonish.backend.security.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public AuthResponse register(AuthRequest request) {
        var user = User.builder()
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
        var userDetails = userDetailsService.loadUserByUsername(user.getPhone());
        var jwtToken = jwtUtils.generateToken(userDetails);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()
                )
        );
        var userDetails = userDetailsService.loadUserByUsername(request.getPhone());
        var jwtToken = jwtUtils.generateToken(userDetails);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
