package com.example.administrator.test_sock.Util;



import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DesUtil {
	private static String keyStr = "12348765";
	private static String vi = "12348765";
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * DES算法，加密 String, 使用时调用这个方法
	 * 
	 * @param data
	 *            待加密字符串
	 key
	 *            加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用

	 *             异常
	 */
	public static String encode(String data) throws Exception {
		return encode(data.getBytes());
	}

/*	*//**
	 * DES算法，加密 byte
	 * 
	 * @param data
	 *            待加密字符串
	 * @param
	 *
	 * @return 加密后的字节数组，一般结合Base64编码使用

	 *             异常
	 */
	private static String encode(byte[] data) throws Exception {
		try {
			DESKeySpec dks = new DESKeySpec(keyStr.getBytes());

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(vi.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

			byte[] bytes = cipher.doFinal(data);
			return Base64.encode(bytes);

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * DES算法，解密byte
	 * 
	 * @param data
	 *            待解密字符串
	 * @param key
	 *            解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception
	 *             异常
	 */
	private static byte[] decode(String key, byte[] data) throws Exception {
		try {
			// SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(vi.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 解密 String
	 * 

	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decodeValue(String data) {
		byte[] datas;
		String value = null;
		try {
			if (System.getProperty("os.name") != null
					&& (System.getProperty("os.name").equalsIgnoreCase("sunos") || System
							.getProperty("os.name").equalsIgnoreCase("linux"))) {
				datas = decode(keyStr, Base64.decode(data));
			} else {
				datas = decode(keyStr, Base64.decode(data));
			}

			value = new String(datas);
		} catch (Exception e) {
			value = "";
		}
		return value;
	}
}
