package com.bside.gamjajeon.domain.record.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class HashtagResponse {
    List<String> hashtags;
}
