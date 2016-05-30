package zp2016;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class StorageClass 
{
	private KeyPair pair;
	private X509Certificate certificate;
	private static final String name = "MyKeyStorage.p12";
	private static final String pass = "mystorage";
	private String keyStoreName;
	private String keyStorePass;
	
	public StorageClass()
	{
		this.keyStoreName = name;
		this.keyStorePass = pass;//ako se ne setuju rucno imaju podrazumevane vrednosti MyStorage
		
		KeyStore keyStore = null;
		try{
		    keyStore = KeyStore.getInstance("PKCS12");
		    keyStore.load(null, keyStorePass.toCharArray());
		     
		    FileOutputStream fos = new FileOutputStream(this.keyStoreName);
		    keyStore.store(fos, this.keyStorePass.toCharArray());
		    fos.close();
		    
		} catch (Exception ex){
		    ex.printStackTrace();
		}
	}
	
	public StorageClass(String name,String pass)
	{
		this.keyStoreName = name;
		this.keyStorePass = pass;
		
		KeyStore keyStore = null;
		try{
		    keyStore = KeyStore.getInstance("PKCS12");
		    keyStore.load(null, keyStorePass.toCharArray());
		     
		    FileOutputStream fos = new FileOutputStream(this.keyStoreName);
		    keyStore.store(fos, this.keyStorePass.toCharArray());
		    fos.close();
		    
		} catch (Exception ex){
		    ex.printStackTrace();
		}
	}
	
	public void saveKey(String key) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		String keyPass="pass"; 
		
		KeyStore keyStore = KeyStore.getInstance("pkcs12");
		char[] storePass = this.keyStorePass.toCharArray();
		FileInputStream is = new FileInputStream(this.keyStoreName);
		keyStore.load(is,storePass); 
		is.close();
		
		Certificate[] certChain = new Certificate[1];  
		certChain[0] = this.certificate;  
		keyStore.setKeyEntry(key, this.pair.getPrivate(), keyPass.toCharArray(), certChain); 
		
		FileOutputStream fos = new FileOutputStream(this.keyStoreName);
		keyStore.store(fos, storePass);
		fos.close();
	}
	
	public void viewAllKeys() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException
	{
		Certificate cert=null;
		String keyPass="pass";

	    KeyStore keystore = KeyStore.getInstance("pkcs12");
	    keystore.load(new FileInputStream(this.keyStoreName), this.keyStorePass.toCharArray());

	    int i=0;
	    Enumeration aliases = keystore.aliases();
	    while(aliases.hasMoreElements()) 
	    {
	      String alias = (String)aliases.nextElement();
          Key key = keystore.getKey(alias, keyPass.toCharArray());
	    if (key instanceof PrivateKey) 
	      cert = keystore.getCertificate(alias);
	     System.out.println("=============================================");
	    System.out.println(alias);
	     System.out.println(cert.toString());
	  }
	    	   
	}
	
	  public String getKeyStorePass() {
		return keyStorePass;
	}

	public void setKeyStorePass(String keyStorePass) {
		this.keyStorePass = keyStorePass;
	}

	public void importKeys(String storeName,String pass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException
	{
		Certificate cert=null;
		String keyPass="pass";

	    KeyStore keystore = KeyStore.getInstance("pkcs12");
	    keystore.load(new FileInputStream(storeName), pass.toCharArray());
	    
	    int i=0;
	    Enumeration aliases = keystore.aliases();
	    while(aliases.hasMoreElements()) 
	    {
	      String alias = (String)aliases.nextElement();
          Key key = keystore.getKey(alias, keyPass.toCharArray());
          
  		  KeyStore myKeyStore = KeyStore.getInstance("pkcs12");
   		  myKeyStore.load(new FileInputStream(this.keyStoreName), this.keyStorePass.toCharArray());
  		
  		  Certificate[] certChain = new Certificate[1];  
  		  certChain[0] = keystore.getCertificate(alias);  
  		  myKeyStore.setKeyEntry(alias, key, keyPass.toCharArray(), certChain); 
  		
  		  FileOutputStream fos = new FileOutputStream(this.keyStoreName);
  		  myKeyStore.store(fos, this.keyStorePass.toCharArray());
  		  fos.close();
          
          
 
	    }
	    
	    
	}

    
	
	
	
	public KeyPair getPair() {
		return pair;
	}
	public void setPair(KeyPair pair) {
		this.pair = pair;
	}
	public X509Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
	public String getKeyStoreName() {
		return keyStoreName;
	}
	public void setKeyStoreName(String keyStoreName) {
		this.keyStoreName = keyStoreName;
	}
	
	

}
