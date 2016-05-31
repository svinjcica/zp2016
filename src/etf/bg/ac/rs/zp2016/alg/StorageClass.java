package etf.bg.ac.rs.zp2016.alg;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Encoder;

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
	
	public void viewAllKeys(String storageName,String pass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException
	{
		Certificate cert=null;
		String keyPass="pass";

	    KeyStore keystore = KeyStore.getInstance("pkcs12");
	    keystore.load(new FileInputStream(storageName), pass.toCharArray());
	   
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
	
	public void importKey(String storeName,String storePass,String keyName,String newKeyName) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException
	{
		Certificate cert=null;
		String keyPass="pass";

	    KeyStore keystore = KeyStore.getInstance("pkcs12");
	    keystore.load(new FileInputStream(storeName), storePass.toCharArray());
	    
          Key key = keystore.getKey(keyName, keyPass.toCharArray());
          
          KeyStore myKeyStore = KeyStore.getInstance("pkcs12");
   		  myKeyStore.load(new FileInputStream(this.name), this.pass.toCharArray());
  		
  		  Certificate[] certChain = new Certificate[1];  
  		  certChain[0] = keystore.getCertificate(keyName);  
  		  myKeyStore.setKeyEntry(newKeyName, key, keyPass.toCharArray(), certChain); 
  		
  		  FileOutputStream fos = new FileOutputStream(this.name);
  		  myKeyStore.store(fos, this.pass.toCharArray());
  		  fos.close();
            
	}
	
	public ArrayList<String> viewKeyAlies(String storeName,String storePass)
	{
		 ArrayList<String> keys = new ArrayList<String>();
		 KeyStore keystore = null;
		try {
			keystore = KeyStore.getInstance("pkcs12");
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			keystore.load(new FileInputStream(storeName), pass.toCharArray());
		} catch (NoSuchAlgorithmException | CertificateException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		    int i=0;
		    Enumeration aliases = null;
			try {
				aliases = keystore.aliases();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    while(aliases.hasMoreElements()) 
		    {
		      String alias = (String)aliases.nextElement();
	          keys.add(alias);
	          
		    }
	  	if(!keys.isEmpty()) return keys;
	  	else return null;
	}
	
	
	public void exportKey(String storeName,String storePass,String keyAlias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		
		Certificate cert=null;
		String keyPass="pass";
		Cipher c = Cipher.getInstance("AES");
      
		//pravimo novi p12 fajl za export
	    KeyStore keystore = KeyStore.getInstance("pkcs12");
	    keystore.load(null, null);
	    
	    //ucitamo stari KeyStore i iz njega uzmemo zeljeni kljuc
	    KeyStore mykeystore = KeyStore.getInstance("pkcs12");
	    mykeystore.load(new FileInputStream(this.name), this.pass.toCharArray());
	    Key key =  mykeystore.getKey(keyAlias, "pass".toCharArray());
	    
	    
  		  Certificate[] certChain = new Certificate[1];  
  		  cert = mykeystore.getCertificate(keyAlias); 
  		  PublicKey pkey = mykeystore.getCertificate(keyAlias).getPublicKey();
  		  c.init(Cipher.ENCRYPT_MODE, pkey);
  		  
  		  cert = mykeystore.getCertificate(keyAlias); ;
  		  byte[] contentC = cert.toString().getBytes();
  		  contentC = c.doFinal(contentC);
  		  Certificate cipheredC = CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(contentC));
  		 certChain[0] = cipheredC;
  		  
  		  keystore.setKeyEntry(keyAlias, key, keyPass.toCharArray(), certChain); 
  		
  		  FileOutputStream fos = new FileOutputStream(storeName + ".p12");
  		  keystore.store(fos, storePass.toCharArray());
  		  fos.close();
        }    
	
	
	
	
	public void exportCertificate(String path,X509Certificate cert) throws IOException, CertificateException
	{
		FileOutputStream fos = new FileOutputStream(path+".cer");
		String b64 = new BASE64Encoder().encode(cert.getEncoded());
		fos.write("-----BEGIN CERTIFICATE-----\n".getBytes());
	    fos.write((b64+"\n").getBytes());
	    fos.write("-----END CERTIFICATE-----".getBytes());
		fos.close();
		
		
		//provera da li radi export - import sertifikata i ispis na standardnom izlazu
	   /* InputStream certIn = new FileInputStream(path+".cer");
	    BufferedInputStream bis = new BufferedInputStream(certIn);
	    CertificateFactory cf = CertificateFactory.getInstance("X.509");
	    while (bis.available() > 0) 
	     {
	
	       Certificate certificate = cf.generateCertificate(bis);
	       System.out.print(certificate.toString());
	    }*/
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
