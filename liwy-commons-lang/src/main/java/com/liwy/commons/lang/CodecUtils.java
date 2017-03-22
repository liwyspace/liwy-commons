package com.liwy.commons.lang;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class CodecUtils {
	
	private static final char[] DIGITS =
	    {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	//加密字典
	private static final byte[] STANDARD_ENCODE_TABLE = { 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/' };
	//解密字典
	private static final byte[] DECODE_TABLE = {
	        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	        -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54,
	        55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
	        5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
	        24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34,
	        35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51
	};
	
	//-----Digest 摘要
	/**
	 * 生成字符串的MD5摘要，即单项加密
	 * @param str 待加密字符串
	 * @return 32位16进制字符串
	 */
	public static String md5(String str) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        // two characters form the hex value.
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字节数组的MD5摘要，即单项加密
	 * @param bytes 待加密字节数组
	 * @return 32位16进制字符串
	 */
	public static String md5(byte[] bytes) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(bytes);
			
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        // two characters form the hex value.
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成输入流的MD5摘要，即单项加密
	 * @param data 待加密输入流
	 * @return 32位16进制字符串
	 */
	public static String md5(InputStream data) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            digest.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = digest.digest();
	        
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字符串的SHA-1摘要，即单项加密
	 * @param str 待加密字符串
	 * @return 40位16进制字符串
	 */
	public static String sha1(String str) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        // two characters form the hex value.
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字节数组的SHA-1摘要，即单项加密
	 * @param bytes 待加密字节数组
	 * @return 40位16进制字符串
	 */
	public static String sha1(byte[] bytes) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] hash = digest.digest(bytes);
			
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        // two characters form the hex value.
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成输入流的SHA-1摘要，即单项加密
	 * @param data 待加密输入流
	 * @return 40位16进制字符串
	 */
	public static String sha1(InputStream data) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            digest.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = digest.digest();
	        
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字符串的SHA-256摘要，即单项加密
	 * @param str 待加密字符串
	 * @return 64位16进制字符串
	 */
	public static String sha256(String str) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        // two characters form the hex value.
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字节数组的SHA-256摘要，即单项加密
	 * @param bytes 待加密字节数组
	 * @return 64位16进制字符串
	 */
	public static String sha256(byte[] bytes) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(bytes);
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成输入流的SHA-256摘要，即单项加密
	 * @param data 待加密输入流
	 * @return 64位16进制字符串
	 */
	public static String sha256(InputStream data) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            digest.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = digest.digest();
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字符串的SHA-384摘要，即单项加密
	 * @param str 待加密字符串
	 * @return 96位16进制字符串
	 */
	public static String sha384(String str) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-384");
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字节数组的SHA-384摘要，即单项加密
	 * @param bytes 待加密字节数组
	 * @return 96位16进制字符串
	 */
	public static String sha384(byte[] bytes) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-384");
			byte[] hash = digest.digest(bytes);
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成输入流的SHA-384摘要，即单项加密
	 * @param data 待加密输入流
	 * @return 96位16进制字符串
	 */
	public static String sha384(InputStream data) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-384");
			final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            digest.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = digest.digest();
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字符串的SHA-512摘要，即单项加密
	 * @param str 待加密字符串
	 * @return 128位16进制字符串
	 */
	public static String sha512(String str) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成字节数组的SHA-512摘要，即单项加密
	 * @param bytes 待加密字节数组
	 * @return 128位16进制字符串
	 */
	public static String sha512(byte[] bytes) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest(bytes);
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 生成输入流的SHA-512摘要，即单项加密
	 * @param data 待加密输入流
	 * @return 128位16进制字符串
	 */
	public static String sha512(InputStream data) {
		String returnString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            digest.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = digest.digest();
	        
			final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        returnString = new String(out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 字符串的 HMAC-MD5加密
	 * 
	 * @param key
	 * @param str 待加密字符串
	 * @return
	 */
	public static String hmacMd5(String key, String str) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacMD5");
	        final Mac mac = Mac.getInstance("HmacMD5");
	        mac.init(keySpec);
	        byte[] hash = mac.doFinal(str.getBytes("UTF-8"));
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 输入流的 HMAC-MD5加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static String hmacMd5(String key, InputStream data) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacMD5");
	        final Mac mac = Mac.getInstance("HmacMD5");
	        mac.init(keySpec);
	        
	        final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            mac.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = mac.doFinal();
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 字符串的 HMAC-SHA1加密
	 * @param key
	 * @param str
	 * @return
	 */
	public static String hmacSha1(String key, String str) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
	        final Mac mac = Mac.getInstance("HmacSHA1");
	        mac.init(keySpec);
	        byte[] hash = mac.doFinal(str.getBytes("UTF-8"));
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 输入流的 HMAC-SHA1加密
	 * @param key
	 * @param data
	 * @return
	 */
	public static String hmacSha1(String key, InputStream data) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
	        final Mac mac = Mac.getInstance("HmacSHA1");
	        mac.init(keySpec);
	        
	        final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            mac.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = mac.doFinal();
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 字符串的 HMAC-SHA256加密
	 * @param key
	 * @param str
	 * @return
	 */
	public static String hmacSha256(String key, String str) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
	        final Mac mac = Mac.getInstance("HmacSHA256");
	        mac.init(keySpec);
	        byte[] hash = mac.doFinal(str.getBytes("UTF-8"));
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 输入流的 HMAC-SHA256加密
	 * @param key
	 * @param data
	 * @return
	 */
	public static String hmacSha256(String key, InputStream data) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
	        final Mac mac = Mac.getInstance("HmacSHA256");
	        mac.init(keySpec);
	        
	        final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            mac.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = mac.doFinal();
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 字符串的 HMAC-SHA384加密
	 * @param key
	 * @param str
	 * @return
	 */
	public static String hmacSha384(String key, String str) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA384");
	        final Mac mac = Mac.getInstance("HmacSHA384");
	        mac.init(keySpec);
	        byte[] hash = mac.doFinal(str.getBytes("UTF-8"));
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 输入流的 HMAC-SHA384加密
	 * @param key
	 * @param data
	 * @return
	 */
	public static String hmacSha384(String key, InputStream data) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA384");
	        final Mac mac = Mac.getInstance("HmacSHA384");
	        mac.init(keySpec);
	        
	        final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            mac.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = mac.doFinal();
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 字符串的 HMAC-SHA512加密
	 * @param key
	 * @param str
	 * @return
	 */
	public static String hmacSha512(String key, String str) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
	        final Mac mac = Mac.getInstance("HmacSHA512");
	        mac.init(keySpec);
	        byte[] hash = mac.doFinal(str.getBytes("UTF-8"));
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 输入流的 HMAC-SHA512加密
	 * @param key
	 * @param data
	 * @return
	 */
	public static String hmacSha512(String key, InputStream data) {
		try {
	        final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
	        final Mac mac = Mac.getInstance("HmacSHA512");
	        mac.init(keySpec);
	        
	        final byte[] buffer = new byte[1024];
	        int read = data.read(buffer, 0, 1024);
	
	        while (read > -1) {
	            mac.update(buffer, 0, read);
	            read = data.read(buffer, 0, 1024);
	        }
	        byte[] hash = mac.doFinal();
	        
	        final int l = hash.length;
	        final char[] out = new char[l << 1];
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
	            out[j++] = DIGITS[0x0F & hash[i]];
	        }
	        return new String(out);
	    } catch (final NoSuchAlgorithmException e) {
	        throw new IllegalArgumentException(e);
	    } catch (final InvalidKeyException e) {
	        throw new IllegalArgumentException(e);
	    } catch (IllegalStateException e) {
	    	throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	//Crypt加密（未实现）
	//md5Crypt()
	//sha256Crypt()
	//sha512Crypt()
	//UnixCrypt()
	
	
	//----- 对称加密
	/**
	 * Hex对称加密，使用默认的字符集(Character)
	 * @param str
	 * @return
	 */
	public static String encodeHex(String str) {
		String returnString = "";
		byte[] data = str.getBytes();
		
		final int l = data.length;
		final char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
		    out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
		    out[j++] = DIGITS[0x0F & data[i]];
		}
		returnString = new String(out);
		return returnString;
	}
	/**
	 * Hex对称加密
	 * @param data
	 * @return
	 */
	public static char[] encodeHex(byte[] data) {
		final int l = data.length;
		final char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
		    out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
		    out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}
	/**
	 * Hex对称解密
	 * @param hexStr
	 * @return
	 */
	public static byte[] decodeHex(String hexStr) {
		char[] data = hexStr.toCharArray();
		final int len = data.length;
	    final byte[] out = new byte[len >> 1];
	    // two characters form the hex value.
	    for (int i = 0, j = 0; j < len; i++) {
	        int f = Character.digit(data[j], 16) << 4;
	        j++;
	        f = f | Character.digit(data[j], 16);
	        j++;
	        out[i] = (byte) (f & 0xFF);
	    }
	    return out;
	}
	
	/**
	 * base64对称加密算法
	 * @param str
	 * @return
	 */
	public static String encodeBase64(String str) {
		byte[] in = str.getBytes();
		byte[] buffer = new byte[8192];  //用于保存加密后的字节缓冲区
		int modulus = 0;  
		int ibitWorkArea = 0;  //保存输入数据的字节工作区
		int pos = 0;
		
		for (int i = 0; i < in.length; i++) {  //遍历输入的字节
			
			if ((buffer.length < pos + 4)){ //自动增长buffer大小
		            byte[] b = new byte[buffer.length * 2];
		            System.arraycopy(buffer, 0, b, 0, buffer.length);
		            buffer = b;
	        }
			
	        modulus = (modulus+1) % 3;  //为字节分组，每3个字节为一组
	        int b = in[i];  //获取当前输入的字节
	        if (b < 0) {  //将负数转为整数
	            b += 256;
	        }
	        ibitWorkArea = (ibitWorkArea << 8) + b; //左移8位后加上输入的字节
	        if (0 == modulus) { //当3个字节及24位时，将3个8位字节转换为4个6位字节
	            buffer[pos++] = STANDARD_ENCODE_TABLE[(ibitWorkArea >> 18) & 63];
	            buffer[pos++] = STANDARD_ENCODE_TABLE[(ibitWorkArea >> 12) & 63];
	            buffer[pos++] = STANDARD_ENCODE_TABLE[(ibitWorkArea >> 6) & 63];
	            buffer[pos++] = STANDARD_ENCODE_TABLE[ibitWorkArea & 63];
	        }
	    }
		
		if ((buffer.length < pos + 4)){
			byte[] b = new byte[buffer.length * 2];
			System.arraycopy(buffer, 0, b, 0, buffer.length);
			buffer = b;
		}
		switch (modulus) { // 0-2
		    case 0 : // nothing to do here
		        break;
		    case 1 : // 8 bits = 6 + 2
		        buffer[pos++] = STANDARD_ENCODE_TABLE[(ibitWorkArea >> 2) & 63];
		        buffer[pos++] = STANDARD_ENCODE_TABLE[(ibitWorkArea << 4) & 63];
		        buffer[pos++] = '=';
		        buffer[pos++] = '=';
		        break;
		
		    case 2 : // 16 bits = 6 + 6 + 4
		        buffer[pos++] = STANDARD_ENCODE_TABLE[(ibitWorkArea >> 10) & 63];
		        buffer[pos++] = STANDARD_ENCODE_TABLE[(ibitWorkArea >> 4) & 63];
		        buffer[pos++] = STANDARD_ENCODE_TABLE[(ibitWorkArea << 2) & 63];
		        buffer[pos++] = '=';
		        break;
		    default:
		        throw new IllegalStateException("Impossible modulus "+modulus);
		}
		
		final byte[] buf = new byte[pos];
		System.arraycopy(buffer, 0, buf, 0, buf.length);
		
		return new String(buf);
	}
	/**
	 * base64对称解密算法
	 * @param str
	 * @return
	 */
	public static byte[] decodeBase64(String str) {
		byte[] in = str.getBytes();
		byte[] buffer = new byte[8192];  //用于保存加密后的字节缓冲区
		int modulus = 0;  
		int ibitWorkArea = 0;  //保存输入数据的字节工作区
		int pos = 0;
		
		for (int i = 0; i < in.length; i++) {  //遍历输入的字节
			if ((buffer.length < pos + 3)){ //自动增长buffer大小
		            byte[] b = new byte[buffer.length * 2];
		            System.arraycopy(buffer, 0, b, 0, buffer.length);
		            buffer = b;
	        }
			
	        int b = in[i];  //获取当前输入的字节
	        if(b=='=') {
	        	break;
	        } else {
	            if (b >= 0 && b < DECODE_TABLE.length) {
	                final int result = DECODE_TABLE[b];
	                if (result >= 0) {
	                    modulus = (modulus+1) % 4;
	                    ibitWorkArea = (ibitWorkArea << 6) + result;
	                    if (modulus == 0) {
	                        buffer[pos++] = (byte) ((ibitWorkArea >> 16) & 255);
	                        buffer[pos++] = (byte) ((ibitWorkArea >> 8) & 255);
	                        buffer[pos++] = (byte) (ibitWorkArea & 255);
	                    }
	                }
	            }
	        }
	    }
		
		if (modulus != 0) {
			if ((buffer.length < pos + 3)){ //自动增长buffer大小
		            byte[] b = new byte[buffer.length * 2];
		            System.arraycopy(buffer, 0, b, 0, buffer.length);
		            buffer = b;
		    }
	        switch (modulus) {
	            case 1 : // 6 bits - ignore entirely
	                break;
	            case 2 : // 12 bits = 8 + 4
	                ibitWorkArea = ibitWorkArea >> 4; // dump the extra 4 bits
	                buffer[pos++] = (byte) ((ibitWorkArea) & 255);
	                break;
	            case 3 : // 18 bits = 8 + 8 + 2
	                ibitWorkArea = ibitWorkArea >> 2; // dump 2 bits
	                buffer[pos++] = (byte) ((ibitWorkArea >> 8) & 255);
	                buffer[pos++] = (byte) ((ibitWorkArea) & 255);
	                break;
	            default:
	                throw new IllegalStateException("Impossible modulus "+modulus);
	        }
		}
		final byte[] buf = new byte[pos];
		System.arraycopy(buffer, 0, buf, 0, buf.length);
		return buf;
	}
	
	/**
	 * base64对称加密 -sun.misc提供
	 * @return
	 */
	public static String encodeBase64BySun(byte[] content) {
		BASE64Encoder encode = new BASE64Encoder();
		return encode.encode(content);
	}
	/**
	 * base64对称加解密-sun.misc提供
	 * @return
	 */
	public static byte[] decodeBase64BySun(String content) {
		BASE64Decoder decode = new BASE64Decoder();
		try {
			return decode.decodeBuffer(content);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取DES密钥
	 * @return
	 */
	public static String getDesKey() {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("DES");
			SecretKey deskey = keygen.generateKey();
			byte[] key = deskey.getEncoded();
			return CodecUtils.encodeBase64BySun(key);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 通过DES密钥，进行DES加密
	 * @param str
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encodeDES(String str, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = new SecretKeySpec(decodeBase64BySun(key), "DES");  
		
	    Cipher c = Cipher.getInstance("DES");
	    c.init(Cipher.ENCRYPT_MODE, secretKey);
	    return encodeBase64BySun(c.doFinal(str.getBytes()));
	}
	
	/**
	 * 通过DES密钥，进行DES解密
	 * @param str
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decodeDES(String str, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = new SecretKeySpec(decodeBase64BySun(key), "DES");
		
	    Cipher c = Cipher.getInstance("DES");
	    c.init(Cipher.DECRYPT_MODE, secretKey);
	    byte[] cipherByte = c.doFinal(decodeBase64BySun(str)); 
	    return new String(cipherByte);
	}
	
	//3des() 未实现
	//URLCodec 未实现
	
	/**
	 * 获取AES密钥
	 * @return
	 */
	public static String getAesKey() {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			SecretKey deskey = keygen.generateKey();
			byte[] key = deskey.getEncoded();
			return CodecUtils.encodeBase64BySun(key);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 通过AES密钥，进行AES加密
	 * @param str
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encodeAES(String str, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = new SecretKeySpec(decodeBase64BySun(key), "AES");  
		
	    Cipher c = Cipher.getInstance("AES");
	    c.init(Cipher.ENCRYPT_MODE, secretKey);
	    return encodeBase64BySun(c.doFinal(str.getBytes()));
	}
	
	/**
	 * 通过AES密钥，进行AES解密
	 * @param str
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decodeAES(String str, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = new SecretKeySpec(decodeBase64BySun(key), "AES");
		
	    Cipher c = Cipher.getInstance("AES");
	    c.init(Cipher.DECRYPT_MODE, secretKey);
	    byte[] cipherByte = c.doFinal(decodeBase64BySun(str)); 
	    return new String(cipherByte);
	}
	
	//---- 非对称加密算法
	/**
	 * 获取RSA的公有密钥和私有密钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<String,String> getRsaKey() throws NoSuchAlgorithmException {
		KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
	    kg.initialize(1024);
	    KeyPair keypair = kg.generateKeyPair();
	    RSAPublicKey publicKey = (RSAPublicKey) keypair.getPublic();//公有密钥
	    RSAPrivateKey privateKey = (RSAPrivateKey) keypair.getPrivate();//私有密钥
	    
	    Map<String,String> keyMap = new HashMap<String,String>(2);
	    keyMap.put("RSA_PUBLIC_KEY", encodeBase64BySun(publicKey.getEncoded()));
	    keyMap.put("RSA_PRIVATE_KEY", encodeBase64BySun(privateKey.getEncoded()));
	    return keyMap;
	}
	
	/** 
	 * 用私钥对加密信息生成数字签名 
	 *  
	 * @param data 私钥加密数据 
	 * @param privateKey 私钥 
	 *  
	 * @return 
	 * @throws Exception 
	 */  
	public static String getRsaSign(String data, String privateKey) throws Exception {  
	    // 解密由base64编码的私钥  
	    byte[] keyBytes = decodeBase64BySun(privateKey);  
	    // 构造PKCS8EncodedKeySpec对象  
	    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
	    // KEY_ALGORITHM 指定的加密算法  
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	    // 取私钥匙对象  
	    PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
	    // 用私钥对信息生成数字签名  
	    Signature signature = Signature.getInstance("MD5withRSA");  
	    signature.initSign(priKey);  
	    signature.update(decodeBase64(data));  
	    return encodeBase64BySun(signature.sign());  
	}
	
	/** 
	 * 校验数字签名 
	 *  
	 * @param data 加密数据 
	 * @param publicKey 公钥 
	 * @param sign 数字签名 
	 *  
	 * @return 校验成功返回true 失败返回false 
	 * @throws Exception 
	 *  
	 */  
	public static boolean verifyRsaSign(String data, String publicKey, String sign)  
	        throws Exception {  
	    // 解密由base64编码的公钥  
	    byte[] keyBytes = decodeBase64BySun(publicKey);  
	    // 构造X509EncodedKeySpec对象  
	    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
	    // KEY_ALGORITHM 指定的加密算法  
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	    // 取公钥匙对象  
	    PublicKey pubKey = keyFactory.generatePublic(keySpec);  
	    Signature signature = Signature.getInstance("MD5withRSA");  
	    signature.initVerify(pubKey);  
	    signature.update(decodeBase64BySun(data));
	    // 验证签名是否正常  
	    return signature.verify(decodeBase64BySun(sign));  
	}  
	
	/** 
	 * 用私钥加密 
	 *  
	 * @param data 
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static String encryptRsaByPrivateKey(byte[] data, String key)  
	        throws Exception {  
	    // 对密钥解密  
	    byte[] keyBytes = decodeBase64BySun(key);
	    // 取得私钥 
	    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
	    // 对数据加密 
	    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	    return encodeBase64BySun(cipher.doFinal(data));
	}
	/** 
	 * 用公钥解密 
	 *  
	 * @param data 
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static byte[] decryptRsaByPublicKey(String data, String key)  
	        throws Exception {
	    // 对密钥解密 
	    byte[] keyBytes = decodeBase64BySun(key);  
	    // 取得公钥 
	    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    Key publicKey = keyFactory.generatePublic(x509KeySpec);
	    // 对数据解密 
	    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
	    cipher.init(Cipher.DECRYPT_MODE, publicKey);
	    return cipher.doFinal(decodeBase64BySun(data));
	}
	
	/** 
	 * 用公钥加密 
	 *  
	 * @param data 
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static String encryptRsaByPublicKey(byte[] data, String key)  
	        throws Exception {  
	    // 对公钥解密  
	    byte[] keyBytes = decodeBase64BySun(key);  
	    // 取得公钥  
	    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	    Key publicKey = keyFactory.generatePublic(x509KeySpec);  
	    // 对数据加密  
	    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
	    return encodeBase64BySun(cipher.doFinal(data));  
	}
	
	/** 
	 * 用私钥解密 
	 *  
	 * @param data 
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static byte[] decryptRsaByPrivateKey(String data, String key)  
	        throws Exception {  
	    // 对密钥解密  
	        byte[] keyBytes = decodeBase64BySun(key);  
	  
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	    Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
	    // 对数据解密  
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
	  
	        return cipher.doFinal(decodeBase64BySun(data));  
	    } 
}
