package com.bside.gamjajeon.domain.record.service;

import com.bside.gamjajeon.domain.record.dto.response.HashtagResponse;
import com.bside.gamjajeon.domain.record.repository.HashtagRepository;
import com.bside.gamjajeon.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public HashtagResponse getHashtagList(User user) {
        List<String> keywords = hashtagRepository.findAllKeywordByUser(user);
        return new HashtagResponse(keywords);
    }
}
