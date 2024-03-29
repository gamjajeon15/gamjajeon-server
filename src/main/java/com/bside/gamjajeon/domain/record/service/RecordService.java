package com.bside.gamjajeon.domain.record.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.dto.response.MoodResponse;
import com.bside.gamjajeon.domain.record.dto.response.RecordJoinResponse;
import com.bside.gamjajeon.domain.record.dto.response.RecordResponse;
import com.bside.gamjajeon.domain.record.entity.Hashtag;
import com.bside.gamjajeon.domain.record.entity.Image;
import com.bside.gamjajeon.domain.record.entity.Record;
import com.bside.gamjajeon.domain.record.entity.RecordHashtag;
import com.bside.gamjajeon.domain.record.exception.RecordNotFoundException;
import com.bside.gamjajeon.domain.record.mapper.RecordMapper;
import com.bside.gamjajeon.domain.record.repository.HashtagRepository;
import com.bside.gamjajeon.domain.record.repository.ImageRepository;
import com.bside.gamjajeon.domain.record.repository.RecordHashtagRepository;
import com.bside.gamjajeon.domain.record.repository.RecordRepository;
import com.bside.gamjajeon.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {

	private final RecordRepository recordRepository;
	private final HashtagRepository hashtagRepository;
	private final ImageRepository imageRepository;
	private final RecordHashtagRepository recordHashtagRepository;
	private final RecordMapper recordMapper;
	private final AmazonS3Client s3Client;

	@Value("${cloud.aws.s3.endpoint}")
	private String endpoint;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Value("${cloud.aws.s3.folder}")
	private String folderName;

	@Transactional
	public RecordResponse save(User user, RecordRequest recordRequest, MultipartFile image) throws IOException {
		if (!ObjectUtils.isEmpty(recordRequest.getHashtagList())) {
			return createRecordWithHashtags(user, recordRequest, image);
		}
		return createRecordWithoutHashtags(user, recordRequest, image);
	}

	@Transactional
	public RecordResponse createRecordWithHashtags(User user, RecordRequest recordRequest, MultipartFile image) throws
		IOException {
		List<Hashtag> convertedHashtags = validateHashtags(recordRequest);
		List<RecordHashtag> recordHashtags = new ArrayList<>();

		for (Hashtag hashtag : convertedHashtags) {
			RecordHashtag recordHashtag = RecordHashtag.createRecordHashtag(hashtag);
			recordHashtags.add(recordHashtag);
		}

		Record savedRecord = new Record(user, recordRequest, recordHashtags);
		recordRepository.save(savedRecord);
		saveImage(user, image, savedRecord);
		return new RecordResponse(savedRecord.getId());
	}

	@Transactional
	public List<Hashtag> validateHashtags(RecordRequest recordRequest) {

		List<Hashtag> hashtaglist = new ArrayList<>();

		for (String keyword : recordRequest.getHashtagList()) {
			Hashtag hashtag = hashtagRepository.findByKeyword(keyword)
				.orElseGet(() -> hashtagRepository.save(new Hashtag(keyword)));
			hashtaglist.add(hashtag);
		}

		return hashtaglist;
	}

	@Transactional
	public RecordResponse createRecordWithoutHashtags(User user, RecordRequest recordRequest,
		MultipartFile image) throws IOException {
		Record savedRecord = recordRepository.save(recordMapper.toRecord(user, recordRequest));
		saveImage(user, image, savedRecord);
		return new RecordResponse(savedRecord.getId());
	}

	private void saveImage(User user, MultipartFile image, Record savedRecord) throws IOException {
		if (image == null)
			return;

		String imagePath = uploadStorage(user, image);
		String url = endpoint + "/" + bucketName + "/" + imagePath;
		Image newImage = new Image(url, savedRecord);
		imageRepository.save(newImage);
	}

	private String uploadStorage(User user, MultipartFile image) throws IOException {
		String[] originalName = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");
		String fileName = originalName[0];
		String fileType = originalName[1];
		String objectName =
			folderName + user.getId().toString() + "/" + fileName + System.currentTimeMillis() + "." + fileType;

		// 메타데이터 설정
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(image.getContentType());
		objectMetadata.setContentLength(image.getSize());

		// 파일 저장
		s3Client.putObject(bucketName, objectName, image.getInputStream(), objectMetadata);

		// 권한 설정
		AccessControlList accessControlList = s3Client.getObjectAcl(bucketName, objectName);
		accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		s3Client.setObjectAcl(bucketName, objectName, accessControlList);
		return objectName;
	}

	public MoodResponse getMoodStatistics(User user, Integer year) {
		return recordRepository.findMoodTypeByUserAndYear(user, year);
	}

	public List<RecordJoinResponse> findRecordsAll(User user, LocalDate localDate) {

		Sort sort = Sort.by(
			Sort.Order.desc("recordDate"),
			Sort.Order.desc("createdAt")
		);

		int year = localDate.getYear();
		int month = localDate.getMonthValue();

		// Record 조회
		List<Record> recordList = recordRepository.findAllbyRecordDate(user, year, month, sort);

		// Response 객체 변환
		List<RecordJoinResponse> recordJoinResponseList = new ArrayList<>();

		for (Record record : recordList) {
			RecordJoinResponse recordJoinResponse = new RecordJoinResponse(record);
			recordJoinResponseList.add(recordJoinResponse);
		}

		return recordJoinResponseList;
	}

	public RecordJoinResponse findRecord(User user, Integer recordId){
		return new RecordJoinResponse(recordRepository.findById(Long.valueOf(recordId)).orElseThrow(RecordNotFoundException::new));
	}

	@Transactional
	public void deleteRecord(User user, Integer recordId) {

		Optional<Record> record = recordRepository.findById(Long.valueOf(recordId));

		if (record.isEmpty()){
			throw new RecordNotFoundException();
		}

		deleteRecordRelation(record.get());

		recordRepository.deleteByIdAndUser(record.get().getId(), user);
	}

	private void deleteRecordHashtagList(List<RecordHashtag> recordHashtagList) {
		recordHashtagList.stream().forEach(recordHashtag -> {
			recordHashtag.setRecord(null);
			recordHashtag.setHashtag(null);
			recordHashtagRepository.delete(recordHashtag);
		});
	}

	private void deleteImage(Image image) {
		// Storage Image 삭제
		String objectName = image.getUrl();
		objectName = objectName.split(endpoint + "/" + bucketName + "/")[1];
		s3Client.deleteObject(bucketName, objectName);

		// Image 객체 삭제
		image.setRecord(null);
		imageRepository.delete(image);
	}


	private Record deleteRecordRelation(Record record) {
		if (record.getRecordHashtags() != null || !record.getRecordHashtags().isEmpty()) {
			deleteRecordHashtagList(record.getRecordHashtags());
			record.setRecordHashtags(null);
		}
		if (record.getImage() != null) {
			deleteImage(record.getImage());
			record.setImage(null);
		}
		return record;
	}

	@Transactional
	public void updateRecord(User user, Integer recordId, RecordRequest recordRequest,
		MultipartFile image) throws
		IOException {

		Optional<Record> record = recordRepository.findById(Long.valueOf(recordId));

		if (record.isEmpty()) {
			throw new RecordNotFoundException();
		}

		deleteRecordRelation(record.get());

		recordRequest.setId(record.get().getId());

		save(user, recordRequest, image);

	}
}
