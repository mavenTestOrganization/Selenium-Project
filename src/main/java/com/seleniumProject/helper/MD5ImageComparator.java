package com.seleniumProject.helper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

/**
 * This class provides comparing
 * images using MD5 hash operations
 * 
 */
public class MD5ImageComparator {

	/**
	 * 
	 * @param image1Path
	 * @param image2Path
	 * @return true if given images are same
	 */
	public static boolean compareIMAGE(String imageURL_1,String imageURL_2) {

		return compareHashes( getHash(imageURL_1) , getHash(imageURL_2) );

	}

	/**
	 * This function compare hashes
	 * @param hash1
	 * @param hash2
	 * @return
	 */
	private static boolean compareHashes(String hash1 , String hash2) {
		
		for(int i = 0 ; i < hash1.length() ; i++) {
			if( hash1.charAt(i) != hash2.charAt(i) ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws URISyntaxException 
	 */
	private static String getHash(String URL){

		MessageDigest md = null;
		BufferedImage buffImg;
		try {
			buffImg = ImageIO.read( new URL(URL.substring(0, URL.length()-4)) );

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			ImageIO.write(buffImg,URL.substring(URL.length()-3,URL.length()), outputStream);
			
			byte[] data = outputStream.toByteArray();

			md = MessageDigest.getInstance("MD5");
			md.update(data);
		}catch(IOException e) {
			e.printStackTrace();
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return returnHex( md.digest() );
	}
	/**
	 * 
	 * @param inBytes
	 * @return
	 */
	private static String returnHex(byte[] inBytes){
		String hexString = null;
		for (int i=0; i < inBytes.length; i++) { 
			hexString +=
					Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return hexString;
	}
}
