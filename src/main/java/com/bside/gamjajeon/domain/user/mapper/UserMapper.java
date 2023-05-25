package com.bside.gamjajeon.domain.user.mapper;

import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "password", target = "password", qualifiedByName = "encryptedPassword")
    User toUser(SignupRequest signupRequest);

    @Named("encryptedPassword")
    default String encryptedPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
