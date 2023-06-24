package com.bside.gamjajeon.domain.user.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 32)
    private String username;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

}
