package com.elastic.job.xxljob;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: MD5Generator
 * @Description: MD5算法类
 * @author Joran
 * @date 24 Jul 2014 15:48:27
 * 
 */
public class MD5Generator {
	// 日志常量初始化
	private static final Logger logger = LoggerFactory.getLogger(MD5Generator.class);

	/**
	 * @Title: MD5
	 * @Description: MD5加密算法
	 * @param @param sourceStr 要被加密的字符串
	 * @return String 返回加密后的字符串
	 * @throws
	 */
	public static String MD5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// 将错误记录到日志中
			logger.error(String.valueOf(e.getCause()));
		}
		return result;
	}

	/**
	 * @Title: encryptPassword
	 * @Description: 加盐密码的加密和验证算法
	 * @param password
	 *            密码值
	 * @param salt
	 *            密码加密的盐值
	 * @return String 返回加密后的密码值
	 * @throws
	 */
	public static String encryptPassword(String password, String salt) {

		password = salt + password;
		String tempPsw = password;
		System.out.println(tempPsw);
		// 第二次MD5运算
		tempPsw = MD5Generator.MD5("1234560josnIP15379538173460");
		// 生成最终加密密码，前8位是盐值，后面是第二次MD5运算值
//		tempPsw = salt + tempPsw;
		// 返回加密后的密码
		return tempPsw;
	}
	
	public static void main(String[] args) {
//		String customerId = "0";
//		String format = "josn";
//		String plat = "IP";
//		String timestamp = "1537953817346";
//		String token = "0";
//
//		String salt = "123456";
//
//		String sign =  customerId + format + plat + timestamp + token;
//		System.out.println(sign);
//
//		String Md5string = MD5Generator.encryptPassword(sign, salt);
//
//		System.out.println(Md5string);
		MD5Generator.MD5("1234560josnIP15379538173460");
		MD5Generator.MD5("1234560josnIP15379538173460 ");

	}

}
