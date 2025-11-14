package com.betalyze.usermanagement.infrastructure.authorization.sfs.services;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.betalyze.usermanagement.infrastructure.persistance.jpa.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service(value = "defaultUserDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * This method is responsible for loading the user details from the database.
   * 
   * @param username The username.
   * @return The UserDetails object.
   * @throws UsernameNotFoundException If the user is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("[UserDetailsServiceImpl.loadUserByUsername] Loading user details for username: {}", username);
    return userRepository.findByEmail(username)
        .map(user -> new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            true,
            true,
            true,
            true,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))))
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
  }
}
