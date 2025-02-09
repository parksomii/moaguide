package com.moaguide.service.ArticleContent;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;


@Service
public class ArticleImageService {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucketName}")
	private String bucketName;

	public ArticleImageService(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public String uploadImageToS3(byte[] imageBytes) throws IOException {

		String s3FileName = "img/article/" + UUID .randomUUID().toString().substring(0, 10)+".jpg";

		ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
		metadata.setContentType("image/jpeg");
		metadata.setContentLength(imageBytes.length);

		//S3에 요청할 때 사용할 byteInputStream 생성
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes)) {
			PutObjectRequest putObjectRequest =
				new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata);
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new RuntimeException("S3 업로드 실패: " + e.getMessage(), e);
		}

		return s3FileName;
	}

	public byte[] downloadImageFromUrl(String imageUrl) throws IOException {
		try (InputStream inputStream = new URL(imageUrl).openStream()) { // ✅ InputStream 자동 닫기
			return StreamUtils.copyToByteArray(inputStream);
		}
	}
}
