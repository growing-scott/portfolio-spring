package com.portfolio.scott.controllers.doc;

import com.portfolio.scott.controllers.dto.*;
import com.portfolio.scott.controllers.errors.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "코어 서비스", description = "사용자 관련 API입니다.")
public interface SwaggerController {

    @Tag(name = "회원가입", description = "회원가입")
    @Operation(summary = "회원 가입", description = "회원 정보를 입력받아 새로운 사용자로 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "회원 가입 성공",
            content = @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json",
            examples = @ExampleObject(value="{\"id\":1,\"loginId\":\"ASXX\",\"name\":\"관우\",\"createdDate\":\"2024-11-17T23:57:51.576256700Z\"}"))
        ),
        @ApiResponse(responseCode = "400", description = "가입이 허용 되지 않은 사용자",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json",
            examples = @ExampleObject(value="{ \"errorCode\": \"USER_NOT_ALLOWED_SIGNUP\", \"status\": 400, \"errorMessage\": \"User is not allowed to sign up.\" }"))
        ),
        @ApiResponse(responseCode = "409", description = "이미 가입된 사용자",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json",
            examples = @ExampleObject(value="{ \"errorCode\": \"USER_ALREADY_SIGNUP\", \"status\": 409, \"errorMessage\": \"The user has already signed up.\" }"))
        ),
        @ApiResponse(responseCode = "500", description = "암호화 실패",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json",
                        examples = @ExampleObject(value="{ \"errorCode\": \"ENCRYPTION_ENCRYPT_FAILED\", \"status\": 500, \"errorMessage\": \"Encryption failed.\" }"))
        )
    })
    ResponseEntity<UserDTO> signup(@Valid @RequestBody SignupDTO signupDTO);

    @Tag(name = "로그인", description = "로그인")
    @Operation(summary = "로그인", description = "사용자ID/패스워드를 입력받아 토큰을 받아온다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "토큰 발급 성공",
            content = @Content(schema = @Schema(implementation = TokenDTO.class), mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "400", description = "로그인 실패",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json",
            examples = @ExampleObject(value="{ \"errorCode\": \"LOGIN_FAILURE\", \"status\": 400, \"errorMessage\": \"Login failed.\" }"))
        ),
    })
    ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO);
}
