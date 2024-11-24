package com.portfolio.scott.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "로그인")
@Setter
@Getter
public class LoginDTO {

    @Schema(description = "사용자ID", example = "ASXX")
    @NotBlank(message = "User ID is required")
    private String userId;

    @Schema(description = "패스워드", example = "1234qwer")
    @NotBlank(message = "Password is required")
    private String password;
}
