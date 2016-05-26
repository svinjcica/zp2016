package etf.bg.ac.rs.zp2016.alg;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

public class KeyPairClass 
{

	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey priv;
	private PublicKey pub;

	/*public void setKeyGen(KeyPairGenerator keyGen) {
		this.keyGen = keyGen;
	}*/

	public KeyPairGenerator getKeyGen() {
		return keyGen;
	}

	public void setKeyGen(KeyPairGenerator keyGen) {
		this.keyGen = keyGen;
	}

	public KeyPair getPair() {
		return pair;
	}

	public void setPair(KeyPair pair) {
		this.pair = pair;
	}

	public PrivateKey getPriv() {
		return priv;
	}

	public void setPriv(PrivateKey priv) {
		this.priv = priv;
	}

	public PublicKey getPub() {
		return pub;
	}

	public void setPub(PublicKey pub) {
		this.pub = pub;
	}

	public KeyPair genPairOfKeys(int length)
	{
		KeyPairGenerator keyGen = null;
		
		try {
			//Security.
			keyGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.keyGen = keyGen;
		
		//initializing keyPairGenerator
		/*SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("Windows-PRNG", "SunMSCAPI");
			//SecureRandom.
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if(random!= null)
			this.keyGen.initialize(1024,random);
		// else  */
		//	keyGen.initialize(length);
		
		//generating keyPair
		this.pair = this.keyGen.generateKeyPair();
		this.priv = this.pair.getPrivate();
		this.pub = this.pair.getPublic();
		
		
		return this.pair;
	}
	
	
}
