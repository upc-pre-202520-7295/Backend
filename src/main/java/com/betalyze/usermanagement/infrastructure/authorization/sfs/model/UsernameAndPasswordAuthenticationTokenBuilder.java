package com.betalyze.usermanagement.infrastructure.authorization.sfs.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.http.HttpServletRequest;

public class UsernameAndPasswordAuthenticationTokenBuilder {
  public static UsernamePasswordAuthenticationToken build(UserDetails principal,
      HttpServletRequest request) {

    var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal,
        null, principal.getAuthorities());
    usernamePasswordAuthenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));
    return usernamePasswordAuthenticationToken;
  }
}
