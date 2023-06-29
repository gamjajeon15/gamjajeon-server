package com.bside.gamjajeon.domain.record.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.dto.response.RecordResponse;
import com.bside.gamjajeon.domain.record.entity.Hashtag;
import com.bside.gamjajeon.domain.record.entity.Image;
import com.bside.gamjajeon.domain.record.entity.Record;
import com.bside.gamjajeon.domain.record.entity.RecordHashtag;
import com.bside.gamjajeon.domain.record.mapper.RecordMapper;
import com.bside.gamjajeon.domain.record.repository.HashtagRepository;
import com.bside.gamjajeon.domain.record.repository.ImageRepository;
import com.bside.gamjajeon.domain.record.repository.RecordRepository;
import com.bside.gamjajeon.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {

	private final RecordRepository recordRepository;
	private final HashtagRepository hashtagRepository;
	private final ImageRepository imageRepository;
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
		Record record = new Record(user, recordRequest, recordHashtags);
		Record savedRecord = recordRepository.save(record);
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
		BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
		String url = endpoint + "/" + imagePath;
		Image newImage = new Image(bufferedImage.getWidth(), bufferedImage.getHeight(), url, savedRecord);
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

}
