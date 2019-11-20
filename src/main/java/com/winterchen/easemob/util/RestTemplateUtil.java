package com.winterchen.easemob.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RestTemplateUtil {
	
    public static String sendhttp(String url,Map<String, String> params,Map<String, String> headerParams, HttpMethod method) throws Exception{
        String responseData="";
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> newParams = null;
        if (params != null) {
        	newParams = new LinkedMultiValueMap<>();
            for (Entry<String, String> entry : params.entrySet()) {
                newParams.add(entry.getKey(), entry.getValue());
            }
        }
        HttpHeaders headers = new HttpHeaders();
        if(PublicUtil.isNotEmpty(headerParams)) {
        	for(Entry<String, String> entry : headerParams.entrySet()) {
        		headers.add(entry.getKey(), entry.getValue());
        	}
        }
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(newParams, headers);
        ResponseEntity<String> stringResponseEntity = null;
        if(null == method || method.equals(HttpMethod.POST)) {
        	stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
        }else if(method.equals(HttpMethod.GET)) {
        	stringResponseEntity = restTemplate.getForEntity(url, String.class, entity);
        }else if(method.equals(HttpMethod.DELETE)) {
        	restTemplate.exchange(url, method, entity, Void.class);
        	responseData = "Deleted";
        }
        if(null!=stringResponseEntity){
            HttpStatus httpCode = stringResponseEntity.getStatusCode();
             if(httpCode.value()==200){
                 responseData= stringResponseEntity.getBody();
            }
        }
        return responseData;
    }

    public static String sendJson(String url, String jsonStr,Map<String, String> headerParams, HttpMethod method) throws Exception{
        String responseData="";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        if(PublicUtil.isEmpty(headerParams)) {
        	headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        	headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        }
        else
        {
        	for(Entry<String, String> entry : headerParams.entrySet()) {
        		headers.add(entry.getKey(), entry.getValue());
        	}
        }
        HttpEntity<String> entity = new HttpEntity<String>(jsonStr, headers);
        ResponseEntity<String> stringResponseEntity = null;
        if(null == method || method.equals(HttpMethod.POST)) {
        	stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
        }else if(method.equals(HttpMethod.GET)) {
        	stringResponseEntity = restTemplate.getForEntity(url, String.class, entity);
        }else if(method.equals(HttpMethod.PUT)) {
        	stringResponseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        }else if(method.equals(HttpMethod.DELETE)) {
        	restTemplate.exchange(url, method, entity, Void.class);
        	responseData = "Deleted";
        }
        if(null!=stringResponseEntity){
            HttpStatus httpCode = stringResponseEntity.getStatusCode();
             if(httpCode.value()==200){
                 responseData= stringResponseEntity.getBody();
            }
        }
        return responseData;
    }
    /**
     * 创建一个SSL信任所有证书的httpClient对象
     * @return
     * @throws GeneralSecurityException
     */
	private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain,String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }

	public static HttpHeaders postForHeaders(String url, Map<String, String> params) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> newParams = new LinkedMultiValueMap<>();
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                newParams.add(entry.getKey(), entry.getValue());
            }
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(newParams, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
        if(null!=stringResponseEntity){
            HttpStatus httpCode = stringResponseEntity.getStatusCode();
             if(httpCode.value()==200){
            	return stringResponseEntity.getHeaders();
            }
        }
        return null;
    }
	
	public static ResponseEntity<String> exchange(String url, Map<String,Object> paramsMap, Map<String, Object> headersMap, HttpMethod method) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		if(PublicUtil.isNotEmpty(paramsMap)) {
			Iterator<Entry<String, Object>> ite = paramsMap.entrySet().iterator();
			while(ite.hasNext()) {
				Entry<String, Object> entry = ite.next();
				params.add(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		HttpHeaders headers = new HttpHeaders();
		if(PublicUtil.isNotEmpty(headersMap)) {
			Iterator<Entry<String,Object>> ite = headersMap.entrySet().iterator();
			while(ite.hasNext()) {
				Entry<String,Object> entry = ite.next();
				headers.add(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
		return responseEntity;
	}
}
