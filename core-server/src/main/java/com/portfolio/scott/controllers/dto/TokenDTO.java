package com.portfolio.scott.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "토큰")
@Setter
@Getter
@ToString
public class TokenDTO {

    @Schema(description = "토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWI.......")
    String accessToken;
}
