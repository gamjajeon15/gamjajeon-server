package com.bside.gamjajeon.domain.record.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bside.gamjajeon.domain.record.dto.response.HashtagResponse;
import com.bside.gamjajeon.domain.record.dto.response.MoodResponse;
import com.bside.gamjajeon.domain.record.dto.response.WeeklyHashtagResponse;
import com.bside.gamjajeon.domain.record.repository.HashtagRepository;
import com.bside.gamjajeon.domain.record.repository.RecordRepository;
import com.bside.gamjajeon.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HashtagService {
	private final HashtagRepository hashtagRepository;
	private final RecordRepository recordRepository;

	public HashtagResponse getHashtagList(User user) {
		List<String> keywords = hashtagRepository.findAllKeywordByUser(user.getId());
		return new HashtagResponse(keywords);
	}

	public WeeklyHashtagResponse getWeeklyHashtagList(User user, LocalDate startDate, LocalDate endDate) {
		WeeklyHashtagResponse weeklyHashtagResponse = new WeeklyHashtagResponse();
		findHashtags(user, startDate, endDate, weeklyHashtagResponse);
		findRecordCount(user, startDate, endDate, weeklyHashtagResponse);
		return weeklyHashtagResponse;
	}

	private void findHashtags(User user, LocalDate startDate, LocalDate endDate,
		WeeklyHashtagResponse weeklyHashtagResponse) {
		List<List<String>> result = hashtagRepository.findWeeklyKeywordByUserAndDate(user, startDate, endDate);

		for (List<String> list : result) {
			String hashtag = list.get(0);
			String moodType = list.get(1);

			if (moodType.equals("0"))
				weeklyHashtagResponse.getPotatoTags().add(hashtag);
			else
				weeklyHashtagResponse.getSweetPotatoTags().add(hashtag);
		}
	}

	private void findRecordCount(User user, LocalDate startDate, LocalDate endDate,
		WeeklyHashtagResponse weeklyHashtagResponse) {
		MoodResponse response = recordRepository.findMoodTypeCountByUserAndDate(user, startDate, endDate);
		weeklyHashtagResponse.setTotalPotato(response.getPotato());
		weeklyHashtagResponse.setTotalSweetPotato(response.getSweetPotato());
	}
}
