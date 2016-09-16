/*******************************************************************************
 * Copyright (c) 2015 Unilever.
 *
 * All rights reserved. Do not distribute any of these files without prior consent from Unilever.
 *
 * Contributors:
 *     Publicis.Sapient - initial API and implementation
 *******************************************************************************/
package com.unilever.d2.dcu.crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class to encrypt and decrypt the password.
 * 
 * @author Awanish Kumar
 * @since Jun 1, 2016
 */
public class CryptoUtils {

	public static void main(String[] args) throws Exception {
		process(args);
	}

	private static void process(final String[] args) throws Exception {
		if (args.length != 3) {
			System.err
					.println("Parameter missing.\nFirst Parameter: salt File\nSecond Parameter: -e(encryption)/-d(decryption)\nThird Parameter: plainText/encryptedText");
			System.exit(1);
		}
		String saltFile = args[0];
		String action = args[1];
		String data = args[2];

		String salt = readFeed(saltFile);

		if (action.equals("-e")) {
			System.out.println(encrypt(data, salt));
		} else if (action.equals("-d")) {
			System.out.println(decrypt(data, salt));
		} else {
			System.err.println("Second Parameter Must be either -e for encryption or -d for decryption");
		}
	}

	/**
	 * This method encrypt the given text using salt passed as parameter.
	 * 
	 * // String plainText = "Hello World";//
	 * // String salt = "Awanish0Awanish1"; // 128 bit key
	 * 
	 * // System.out.println(encrypt(plainText, salt));
	 * // System.out.println(decrypt(encrypt(plainText, salt), salt));
	 * 
	 * @param plainText
	 * @param salt
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static String encrypt(String plainText, String salt) throws Exception {
		Key aesKey = new SecretKeySpec(salt.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] encryptedByte = cipher.doFinal(plainText.getBytes());
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}

	/**
	 * This method decrypt the given encrypted text using salt passed as parameter.
	 * 
	 * @param encryptedText
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptedText, String salt) throws Exception {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		Key aesKey = new SecretKeySpec(salt.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}

	public static String readFeed(String file) throws Exception {
		StringBuilder data = new StringBuilder("");
		try {
			try (BufferedReader reader = new BufferedReader(new FileReader(new File(file)))) {
				for (;;) {
					String line = reader.readLine();
					if (line == null)
						break;
					data.append(line);
				}
			}
		} catch (Exception ex) {
			throw new Exception("Error while reading salt file: " + file + "\nError Msg:" + ex.getMessage() + "\nError Details:" + ex.toString());
		}
		return data.toString();
	}
}
