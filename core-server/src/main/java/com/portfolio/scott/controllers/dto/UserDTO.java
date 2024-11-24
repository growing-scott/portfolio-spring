package com.portfolio.scott.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.scott.domains.user.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Schema(description = "회원")
@Setter
@Getter
@ToString
public class UserDTO {

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "사용자ID", example = "ASXX")
    private String loginId;

    @Schema(description = "이름", example = "관우")
    private String name;

    @JsonIgnore
    private String passwordHash;

    @Schema(description = "생성일", example = "2024-11-17T23:57:51.576256700Z")
    private Instant createdDate;

    @JsonIgnore
    private Role role = Role.ROLE_USER;
}
