package com.betalyze.usermanagement.infrastructure.authorization.sfs.pipeline;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingFilter extends HttpFilter {

  @Override
  protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    long startTime = System.currentTimeMillis();
    log.info("==== FILTRO ====");
    log.info("Request: " + request.getMethod() + " " + request.getRequestURI());

    chain.doFilter(request, response);

    long duration = System.currentTimeMillis() - startTime;
    log.info("Response status: " + response.getStatus() + " (duración: " + duration + "ms)");
    log.info("==== FIN FILTRO ====");
  }
}
