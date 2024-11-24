package com.portfolio.scott.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "회원가입")
@Setter
@Getter
@ToString
public class SignupDTO {

    @Schema(description = "사용자ID", example = "ASXX")
    @NotBlank(message = "User ID is required")
    private String userId;

    @Schema(description = "패스워드", example = "1234qwer")
    @NotBlank(message = "Password is required")
    private String password;

    @Schema(description = "이름", example = "관우")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "주민등록번호", example = "681108-1582816")
    @NotBlank(message = "Registration number is required")
    @Pattern(
        regexp = "^(\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01]))-([1-4]\\d{6})$",
        message = "Registration number must match the pattern 'YYMMDD-gabcdef'"
    )
    private String regNo;

}
