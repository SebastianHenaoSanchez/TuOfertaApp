package io.swagger.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

@Service
public class AmazonS3Service {

	private AmazonS3 s3client;

	@Value("${amazon.s3.endpoint}")
	private String endpointUrl;
	@Value("${amazon.s3.bucketname}")
	private String bucketName;
	@Value("${amazon.aws.accesskey}")
	private String accessKey;
	@Value("${amazon.aws.secretkey}")
	private String secretKey;

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	@Bean
	public AmazonS3 getClient() {
		return s3client;
	}

	public String uploadFile(MultipartFile multipartFile) throws IOException {
		String fileUrl = "";
		
			File file = convertMultiPartToFile(multipartFile);
			String fileName = generateFileName(multipartFile);
			fileUrl = fileName;
			uploadFileTos3bucket(fileName, file);
			file.delete();
			return fileUrl;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
				.withCannedAcl(CannedAccessControlList.BucketOwnerFullControl));
	}

	public byte[] getObject(String fileName) {
		byte[] image = null;
		try {
			S3Object s3object =s3client.getObject(new GetObjectRequest(bucketName, fileName));
			image=  IOUtils.toByteArray(s3object.getObjectContent());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return image;
	}
	
	

}
