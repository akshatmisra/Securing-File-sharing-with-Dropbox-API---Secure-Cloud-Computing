package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
	
	
	// This is the method for encrypting the file.
	/**
	 * @param transformation
	 * @param cipherMode
	 * @param inputFile
	 * @param outputFile
	 * @param key
	 * @throws Exception
	 */
	public void encryptFile(String transformation,int cipherMode,File inputFile,File outputFile,byte[] key) throws Exception{
		
		try {
		Key secretKey = new SecretKeySpec(key, transformation);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(cipherMode, secretKey);
        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);
         
        byte[] outputBytes = cipher.doFinal(inputBytes);
 
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);
         
        
        inputStream.close();
        outputStream.close();
        
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
	}
	
	/** generates a random key.
	 * @param length
	 * @return
	 */
	public byte[] generateKey(int length){
		
		SecureRandom random;
		byte bytes[]=null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			bytes = new byte[length];
			byte[] seed = random.generateSeed(length);
			random.setSeed(seed);
		    random.nextBytes(bytes);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return bytes;   
	}

	
	/** used for file encryption and decryption
	 * @param input
	 * @param mode1
	 * @param key
	 * @return
	 */
	public byte[] keyEncryptAndDecrypt(byte[] input,int  mode1,byte[] key){
		int mode;
		String transformation="AES";
		byte[] encryptedKey=null;
			
		if(mode1 ==0 ){
		mode=Cipher.ENCRYPT_MODE;
		}else{
		mode=Cipher.DECRYPT_MODE;		
		}
		
		try {
			Key secretKey = new SecretKeySpec(key, transformation);
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(mode, secretKey);
			encryptedKey = cipher.doFinal(input);
			 
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedKey;
		
	}
	
	/** generate master key and encrypt the input key.
	 * @param password
	 * @param mode1
	 * @param key
	 * @return
	 */
	public byte[] generateMasterKey_EncryptAndDecrypt(String password,int mode1,byte[] key){
		
		byte[] salt = {
	            (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
	            (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
	        };
		int mode;
		
		if(mode1 ==0 ){
			mode=Cipher.ENCRYPT_MODE;
		}else{
			mode=Cipher.DECRYPT_MODE;		
		}
		
		PBEKeySpec pbeKeySpec;
        PBEParameterSpec pbeParamSpec;
        SecretKeyFactory keyFac;
        
        int count=20;
        byte[] EncDec=null;
        // set the salt and the number of iterations.
        try {
			pbeParamSpec = new PBEParameterSpec(salt, count);
			pbeKeySpec = new PBEKeySpec(password.toCharArray());
			keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
			pbeCipher.init(mode, pbeKey, pbeParamSpec);
			EncDec = pbeCipher.doFinal(key);
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	return EncDec;
	
	}

	/** generate the hash of a file.
	 * @param inputFile
	 * @param key
	 * @return
	 */
	public String generateHash(File inputFile,byte[] key){
		  final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
		  String hashValue=null;
		try {
			
			FileInputStream inputStream = new FileInputStream(inputFile);
	        byte[] inputBytes = new byte[(int) inputFile.length()];
	        inputStream.read(inputBytes);
			SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] macBytes = mac.doFinal(inputBytes);
		    hashValue = new String(macBytes,"UTF-8");
		    inputStream.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hashValue;
		
	}
	
	/** generates the hash of a string input.
	 * @param input
	 * @return
	 */
	public String stringHash(String input)
        {
            MessageDigest md;
            byte[] buffer;
            String hexStr = "";
			try {
				md = MessageDigest.getInstance("SHA1");
			
            md.reset();
            
				buffer = input.getBytes("UTF-8");
				md.update(buffer);
	            byte[] digest = md.digest();
	            for (int i = 0; i < digest.length; i++) {
	                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
	            }
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            return hexStr;
        }
	
	
	
}
