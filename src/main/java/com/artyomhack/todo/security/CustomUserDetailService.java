package com.artyomhack.todo.security;

import com.artyomhack.todo.entity.enums.Role;
import com.artyomhack.todo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .filter(it -> it.getEmail().contains(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new User(
                user.getEmail(),
                user.getPasswordHash(),
                mapRolesToGrantedAuth(user.getRoles())
        );
    }

    private Collection<GrantedAuthority> mapRolesToGrantedAuth(List<Role> roles) {
        return roles.stream()
                .map(it -> new SimpleGrantedAuthority(it.getRole()))
                .collect(Collectors.toList());
    }
}
