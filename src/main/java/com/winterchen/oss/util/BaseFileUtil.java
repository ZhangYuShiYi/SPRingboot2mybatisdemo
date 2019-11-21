package com.winterchen.oss.util;

import cn.hutool.core.lang.UUID;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.jmimemagic.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseFileUtil {
	
	@Value("${fileSize}")
	private String fileSize;
	
	@Value("${scale}")
	private String scale;
	
	private int sequence = 1;
	
	private int getNextNumber()
	{
		if (sequence == Integer.MAX_VALUE)
		{
			sequence = 1;
		}
		else
		{
			sequence = sequence  + 1;
		}
		return sequence;
	}
	
	/**
	 * 保存商户相关文件
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public abstract String uploadShopImg(MultipartFile file) throws Exception;
	
	/**
	 * 保存用户相关文件
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public abstract String uploadPersonalImg(MultipartFile file) throws Exception;
	
	/**
	 * 生成唯一文件名
	 * @param file
	 * @return
	 */
	public String genTempFileName(MultipartFile file)
    {
		String name = file.getOriginalFilename();
    	StringBuilder fileNameBuilder = new StringBuilder();
    	fileNameBuilder.append(System.currentTimeMillis());
    	fileNameBuilder.append(getNextNumber());
    	fileNameBuilder.append("_");
    	fileNameBuilder.append(name);
    	return fileNameBuilder.toString();
    }
	
	/**
	 * 在本地生成临时文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public File genTempFile(MultipartFile file) throws IOException {
		String originalFileName = file.getOriginalFilename();
		int suffixIdx = originalFileName.lastIndexOf(".");
		String suffix = originalFileName.substring(suffixIdx);
		File tmpFile = File.createTempFile(UUID.randomUUID().toString(),suffix);
		return tmpFile;
	}
	
	/**
	 * 不是图片类型文件部压缩
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public InputStream handleFile(MultipartFile file) throws Exception {
		File tmpFile = genTempFile(file);
		boolean isValid  = isValidPic(file);
		if(!isValid) return file.getInputStream();
		if(StringUtils.isNoneEmpty(fileSize) && StringUtils.isNoneEmpty(scale)) {
			if(file.getSize() >= Long.parseLong(fileSize)) {
				Thumbnails.of(file.getInputStream()).scale(Float.parseFloat(scale)).toFile(tmpFile);
				return new FileInputStream(tmpFile);
			}
		}
		return file.getInputStream();
	}
	
	/**
	 * 检查是否是图片
	 * @param file
	 * @return
	 * @throws MagicParseException
	 * @throws MagicMatchNotFoundException
	 * @throws MagicException
	 * @throws IOException
	 */
	public boolean isValidPic(MultipartFile file) throws MagicParseException, MagicMatchNotFoundException, MagicException, IOException {
		MagicMatch matcher = Magic.getMagicMatch(file.getBytes());
		String mimeType = matcher.getMimeType();
		if(mimeType.equals("image/jpeg") || mimeType.equals("image/gif") || mimeType.equals("image/png") || mimeType.equals("image/bmp")) {
			return true;
		}
		return false;
	}
}
