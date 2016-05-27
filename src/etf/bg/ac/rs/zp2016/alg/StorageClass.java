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
	private String keyStoreName;
	private String keyStorePass;
	
	public StorageClass(String name,String pass)
	{
		this.keyStoreName = name;
		this.keyStorePass = pass;
		
		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance("jks");
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		char[] storePass = keyStorePass.toCharArray();
		try {
			keyStore.load(null,null);
		} catch (NoSuchAlgorithmException | CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(this.keyStoreName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			keyStore.store(fos, storePass);
		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addToMyKeyStore(String key) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		String keyPass="pass"; 
		
		KeyStore keyStore = KeyStore.getInstance("jks");
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
	
	public void viewKeysFromKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException
	{
		Certificate cert=null;
		String keyPass="pass";

	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	    keystore.load(new FileInputStream(this.keyStoreName), this.keyStorePass.toCharArray());
	    //is.close();

	    Enumeration aliases = keystore.aliases();
	    while(aliases.hasMoreElements()) {
	      String alias = (String)aliases.nextElement();
          Key key = keystore.getKey(alias, keyPass.toCharArray());
	    if (key instanceof PrivateKey) 
	      cert = keystore.getCertificate(alias);
	     System.out.println(cert.toString());
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
