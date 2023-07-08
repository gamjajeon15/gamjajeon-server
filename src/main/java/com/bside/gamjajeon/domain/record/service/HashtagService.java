package com.bside.gamjajeon.domain.record.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bside.gamjajeon.domain.record.dto.response.HashtagResponse;
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
		List<List<String>> results = hashtagRepository.findAllKeywordByUser(user, PageRequest.of(0, 10));
		List<String> keywords = results.stream().map(list -> list.get(0)).collect(Collectors.toList());
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
		List<List<Integer>> moodTypeCount = recordRepository.findMoodTypeCountByUserAndDate(user, startDate.toString(), endDate.toString());

		if (!moodTypeCount.isEmpty()) {
			weeklyHashtagResponse.setTotalPotato(moodTypeCount.get(0).get(0));
			if (moodTypeCount.get(0).size() > 1) {
				weeklyHashtagResponse.setTotalSweetPotato(moodTypeCount.get(0).get(1));
			}
		}
	}
}
