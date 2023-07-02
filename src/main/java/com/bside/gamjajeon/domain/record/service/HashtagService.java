package com.bside.gamjajeon.domain.record.service;

import com.bside.gamjajeon.domain.record.dto.response.HashtagResponse;
import com.bside.gamjajeon.domain.record.repository.HashtagRepository;
import com.bside.gamjajeon.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public HashtagResponse getHashtagList(User user) {
        List<List<String>> results = hashtagRepository.findAllKeywordByUser(user, PageRequest.of(0, 10));
        List<String> keywords = results.stream().map(list -> list.get(0)).collect(Collectors.toList());
        return new HashtagResponse(keywords);
    }
}
