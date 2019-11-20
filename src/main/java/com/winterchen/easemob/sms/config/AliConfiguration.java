package com.winterchen.easemob.sms.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliConfiguration {
	
	@Value("${ali.oss.secretId}")
	private String secretId;
	
	@Value("${ali.oss.secretKey}")
	private String secretKey;

	@Value("${ali.oss.bucket}")
	private String bucket;
	
	@Value("${ali.oss.domain}")
	private String domain;
	
	@Value("${ali.sms.signName}")
	private String signName;
	
	@Value("${ali.sms.codeTemplateCode}")
	private String codeTempateCode;

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getCodeTempateCode() {
		return codeTempateCode;
	}

	public void setCodeTempateCode(String codeTempateCode) {
		this.codeTempateCode = codeTempateCode;
	}
	
	@Bean
	public OSSClient ossClient() {
		return (OSSClient)(new OSSClientBuilder().build(domain, secretId, secretKey));
	}

}
