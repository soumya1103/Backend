package com.libraryManagement.backend.config;

import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LmsUsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUserCredential(username);
        if (users == null) {
            throw new UsernameNotFoundException("User details not found.");
        }

        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(users.getRole()));

        return new User(users.getUserCredential(), users.getPassword(), grantedAuthorities);
    }
}
