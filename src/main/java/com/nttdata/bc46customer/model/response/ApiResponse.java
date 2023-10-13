package com.nttdata.bc46customer.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
  private T data;
  private ErrorResponse error;

  public ApiResponse(T data) {
    this.data = data;
  }

  public ApiResponse(ErrorResponse error) {
    this.error = error;
  }
}