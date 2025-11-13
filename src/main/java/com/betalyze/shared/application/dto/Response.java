package com.betalyze.shared.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
  private boolean success;
  private String message;
  private T data;

  public static <T> Response<T> success(T data) {
    return new Response<>(true, "Success", data);
  }

  public static <T> Response<T> success(String message, T data) {
    return new Response<>(true, message, data);
  }

  public static <T> Response<T> error(String message) {
    return new Response<>(false, message, null);
  }

  public static <T> Response<T> error(String message, T data) {
    return new Response<>(false, message, data);
  }
}
