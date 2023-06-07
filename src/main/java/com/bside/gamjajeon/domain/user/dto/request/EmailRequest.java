package com.bside.gamjajeon.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    @NotBlank
    @Email
    private String email;
}
