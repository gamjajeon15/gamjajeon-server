package com.bside.gamjajeon.domain.user.mapper;

import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-03T15:04:57+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(SignupRequest signupRequest) {
        if ( signupRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.password( encryptedPassword( signupRequest.getPassword() ) );
        user.email( signupRequest.getEmail() );
        user.username( signupRequest.getUsername() );
        user.adStatus( signupRequest.getAdStatus() );

        return user.build();
    }
}
