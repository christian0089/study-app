package com.study.app.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

	private static SecretKeySpec keySpec;
	
	// AES Key, only 16 byte size.
	private static final String AES_128_KEY = "3Fx2f304g343gwea";
	
	// SHA Encrypt add salt ( you can customizing )
	private static final String[] SALT = { ".", "[", "c", "W", "3", "&", "#", "?", "+", "<", "$", "3", "&", "#", "?", "/" };
	
	/**
	 * encrypt
	 * 
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String encrypt( String plainText ) throws Exception {
		setKeySpec();
		
		Cipher c = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
		c.init( Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec( AES_128_KEY.getBytes() ) );
		return new String( Base64.getEncoder().encode( c.doFinal( plainText.getBytes( "UTF-8" ) ) ) );
	}
	
	/**
	 * decrypt
	 * 
	 * @param encryptedText
	 * @return
	 * @throws Exception
	 */
	public static String decrypt( String encryptedText )throws Exception {
		setKeySpec();
		
		Cipher c = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
		c.init( Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec( AES_128_KEY.getBytes() ) );
		return new String( c.doFinal( Base64.getDecoder().decode( encryptedText.getBytes() ) ), "UTF-8" );
	}
	
	
	public static String sha512( String message ) throws Exception {
		MessageDigest md = MessageDigest.getInstance( "SHA-512" );
		md.update( addSalt( message ).getBytes( "UTF-8" ) );
		return String.format( "%0128x", new BigInteger( 1, md.digest() ) );
	}
	
	/**
	 *  make key-spec
	 */
	private static void setKeySpec(){
		if( keySpec == null ) {
			byte[] keyBytes = new byte[ AES_128_KEY.length() ];
			byte[] b = AES_128_KEY.getBytes();
			
			int len = b.length;
			
			System.arraycopy( b, 0, keyBytes, 0, len );
			SecretKeySpec spec = new SecretKeySpec( keyBytes, "AES" );
			keySpec = spec;
		}
	}
	
	/**
	 * plain text mix salt
	 * @param message
	 * @return
	 */
	private static String addSalt( String message ) {
		String res = "";
		char[] msgArr = message.toCharArray();
		for( int i = 0; i < msgArr.length; i ++ ) {
			res += msgArr[i] + SALT[ i % SALT.length ];
		}
		return res;
	}
}