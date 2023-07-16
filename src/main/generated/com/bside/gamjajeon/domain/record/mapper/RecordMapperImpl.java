package com.bside.gamjajeon.domain.record.mapper;

import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.entity.Record;
import com.bside.gamjajeon.domain.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-16T13:46:38+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
@Component
public class RecordMapperImpl implements RecordMapper {

    @Override
    public Record toRecord(User user, RecordRequest recordRequest) {
        if ( user == null && recordRequest == null ) {
            return null;
        }

        Record.RecordBuilder record = Record.builder();

        record.user( user );
        record.recordRequest( recordRequest );

        return record.build();
    }
}
