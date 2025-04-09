package com.example.newsfeed.users.dto;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserDeleteRequsetDto {

    @Pattern(regexp = "(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@?/~`,.])[A-Za-z0-9!@?/~`,.]+", message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    private String password;

}
