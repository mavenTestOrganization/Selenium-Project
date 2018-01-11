package com.seleniumProject.helper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
	public static boolean compareIMAGE(String image1Path,String image2Path) {
		
		return compareHashes( getHash(image1Path) , getHash(image2Path) );
		
	}
	
	/**
	 * This function compare hashes
	 * @param hash1
	 * @param hash2
	 * @return
	 */
	private static boolean compareHashes(byte[] hash1 , byte[] hash2) {
		
		for(int i = 0 ; i < hash1.length ; i++)
			if( hash1[i] != hash2[i] )
				return false;
		
		return true;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private static byte[] getHash(String path) {
		
		MessageDigest md = null;
		try {
			File input = new File(path);
			BufferedImage buffImg = ImageIO.read(input);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			ImageIO.write(buffImg, path.
					substring(path.length()-4, path.length()-1), outputStream);

			byte[] data = outputStream.toByteArray();

			md = MessageDigest.getInstance("MD5");
			md.update(data);
		}catch(IOException e) {
			e.printStackTrace();
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md.digest();
	}
}
