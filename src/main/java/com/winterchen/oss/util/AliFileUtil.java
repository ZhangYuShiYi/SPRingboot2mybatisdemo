package com.winterchen.oss.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.winterchen.easemob.sms.config.AliConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Component
public class AliFileUtil extends BaseFileUtil {
	
	@Autowired
	private OSS ossClient;
	
	@Autowired
	private AliConfiguration ossConf;

	@Override
	public String uploadShopImg(MultipartFile file) throws Exception {
		String fileName = genTempFileName(file);
		InputStream inStream = handleFile(file);
		PutObjectRequest putObjectRequest = new PutObjectRequest(ossConf.getBucket(), "joyzone/"+fileName, inStream, null);
		PutObjectResult rst = ossClient.putObject(putObjectRequest);
		return "http://" + ossConf.getBucket() + "." + ossConf.getDomain() + "/joyzone/" + fileName;
	}

	@Override
	public String uploadPersonalImg(MultipartFile file) throws Exception {
		String fileName = genTempFileName(file);
		InputStream inStream = handleFile(file);
		PutObjectRequest putObjectRequest = new PutObjectRequest(ossConf.getBucket(), "joyzone/"+fileName, inStream ,null);
		PutObjectResult rst = ossClient.putObject(putObjectRequest);
		return "http://" + ossConf.getBucket() + "." + ossConf.getDomain() + "/joyzone/" + fileName;
	}

}
