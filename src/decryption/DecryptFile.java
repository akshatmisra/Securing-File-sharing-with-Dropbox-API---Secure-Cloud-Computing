package decryption;

import java.io.File;

import javax.crypto.Cipher;

import utility.DbReturns;
import utility.SecurityApp;
import utility.Utils;

public class DecryptFile {

	static final int mode=Cipher.DECRYPT_MODE;
	String password;
	int numberOfBits;
	String algorithm;
	String transformation;
	String userName;
	
	public DecryptFile(int numberOfBits, String algorithm,String transformation,String userName,String password) {
		super();
		this.password = password;
		this.numberOfBits = numberOfBits;
		this.algorithm = algorithm;
		this.transformation=transformation;
		this.userName=userName;
	}

	
	/** used to decrypt a file
	 * @param inputFile
	 * @param outputFile
	 * @return
	 * @throws Exception
	 */
	public boolean  Decrypt(File inputFile,File outputFile) throws Exception{
		
		boolean equals=false;
		Utils ut = new Utils();
		
		// get the required details from db based on the user name and file name.
		SecurityApp dbConn= new SecurityApp();
		DbReturns dbr=dbConn.getData(userName, inputFile.getName());
		
		//decrypt the hashKey and the fileEncKey and the hash value for checking.
		byte[] fileEncKey = ut.generateMasterKey_EncryptAndDecrypt(password, 1, dbr.getFileEncKey());
		byte[] hashKey=ut.generateMasterKey_EncryptAndDecrypt(password, 1, dbr.getHashEncKey());
		String hashToCheck= dbr.getHashValue();
		
		// decrypt the file
		ut.encryptFile(transformation,mode, inputFile, outputFile, fileEncKey);
		
		// calculate the hash value of the file recieved after decryption.
	//	System.out.println("To String of the file " + outputFile.getPath());
		String calHash = ut.generateHash(outputFile, hashKey) ;
	
		// compare the hash key and then give the output
	//	System.out.println("calualted hash is:" + calHash);
	//	System.out.println("hash to Check:" + hashToCheck);
		if(hashToCheck.equals(calHash)){
			
			System.out.println("File is matching and decrypted");
			equals=true;
		}else{
			
			System.out.println("File has been modifeid or corrupted");
			equals=false;
		}
		
		return equals;
	}
	
	
	
	
	
	
}
