package com.bside.gamjajeon.domain.record.mapper;


import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.entity.Record;
import com.bside.gamjajeon.domain.user.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RecordMapper {
	Record toRecord(User user, RecordRequest recordRequest);

}
