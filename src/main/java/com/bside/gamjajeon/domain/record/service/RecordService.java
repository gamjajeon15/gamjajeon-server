package com.bside.gamjajeon.domain.record.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.dto.response.RecordResponse;
import com.bside.gamjajeon.domain.record.entity.Hashtag;
import com.bside.gamjajeon.domain.record.entity.Record;
import com.bside.gamjajeon.domain.record.entity.RecordHashtag;
import com.bside.gamjajeon.domain.record.exception.RecordDateInvalidException;
import com.bside.gamjajeon.domain.record.repository.HashtagRepository;
import com.bside.gamjajeon.domain.record.repository.RecordRepository;
import com.bside.gamjajeon.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {

	private final RecordRepository recordRepository;
	private final HashtagRepository hashtagRepository;

	@Transactional
	public RecordResponse save(User user, RecordRequest recordRequest) {
		validateRecordDate(recordRequest);

		if (!ObjectUtils.isEmpty(recordRequest.getHashtagList())) {
			return createRecordWithHashtags(user, recordRequest);
		} else {
			return createRecordWithoutHashtags(user, recordRequest);
		}

	}

	private void validateRecordDate(RecordRequest recordRequest) {
		Date recordDate = recordRequest.getRecordDate();
		Date today = new Date();

		if (recordDate.after(today)) {
			throw new RecordDateInvalidException();
		}
	}

	@Transactional
	public RecordResponse createRecordWithoutHashtags(User user, RecordRequest recordRequest) {
		Record record = Record.createRecord(user, recordRequest.getContent(), recordRequest.getMoodType(),
			recordRequest.getRecordDate());
		recordRepository.save(record);
		return new RecordResponse(record.getId());
	}

	@Transactional
	public RecordResponse createRecordWithHashtags(User user, RecordRequest recordRequest) {
		List<Hashtag> convertedHashtags = validateHashtags(recordRequest);
		List<RecordHashtag> recordHashtags = new ArrayList<RecordHashtag>();

		for (Hashtag hashtag : convertedHashtags) {
			RecordHashtag recordHashtag = RecordHashtag.createRecordHashtag(hashtag);
			recordHashtags.add(recordHashtag);
		}

		Record record = Record.createRecordWithHashtags(user, recordRequest.getContent(), recordRequest.getMoodType(),
			recordRequest.getRecordDate(), recordHashtags);
		recordRepository.save(record);
		return new RecordResponse(record.getId());
	}

	@Transactional
	public List<Hashtag> validateHashtags(RecordRequest recordRequest) {

		List<Hashtag> hashtaglist = new ArrayList<Hashtag>();

		for (String keyword : recordRequest.getHashtagList()) {
			if (hashtagRepository.findByKeyword(keyword).isPresent()) {
				hashtaglist.add(hashtagRepository.findByKeyword(keyword).get());
			} else {
				hashtaglist.add(hashtagRepository.save(new Hashtag(keyword)));
			}
		}

		return hashtaglist;
	}

}
