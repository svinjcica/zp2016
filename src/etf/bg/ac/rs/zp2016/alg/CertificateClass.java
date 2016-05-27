package zp2016;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import sun.security.util.DerValue;
import sun.security.x509.AlgorithmId;
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.Extension;
import sun.security.x509.KeyUsageExtension;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;
import java.io.File;


public class CertificateClass  
{
	

	private int length, days; //VERSION SHOULD BE JUST V3
    private Date notBefore, noAfter;
    private int serialNum;
    private String CN, OU, O, L, ST, C, E;
    //za dodavanje-PLUS GETERI I SETERI ZA PRIVATE POLJA
    private String altNames;
    private boolean altNamesCritical,keyUsageCritical,basicCritical;
    public boolean[] keyUsagePolicies = new boolean[9];
    //0-certificate signing,1-CRL sign,2-data  encipherment,3-decipher only,4-digital signature,5-encipher only,6-key agreement,7-key encipherment,8-non repudiation
    private int pathLength;
    private boolean CA;
    private boolean altNameExt,basicExt,keyExt;//indikatori za unesene ekstenzije
    
    private KeyPair pair;
    
    public boolean isAltNameExt() {
		return altNameExt;
	}


	public void setAltNameExt(boolean altNameExt) {
		this.altNameExt = altNameExt;
	}


	public boolean isBasicExt() {
		return basicExt;
	}


	public void setBasicExt(boolean basicExt) {
		this.basicExt = basicExt;
	}


	public boolean isKeyExt() {
		return keyExt;
	}


	public void setKeyExt(boolean keyExt) {
		this.keyExt = keyExt;
	}


	public int getPathLength() {
		return pathLength;
	}


	public void setPathLength(int pathLength) {
		this.pathLength = pathLength;
	}


	public boolean isBasicCritical() {
		return basicCritical;
	}


	public void setBasicCritical(boolean basicCritical) {
		this.basicCritical = basicCritical;
	}


	public boolean isCA() {
		return CA;
	}


	public void setCA(boolean cA) {
		CA = cA;
	}


	public String getDNames()
    {
    	return "CN="+ CN + ", " + "OU=" + OU + ", " + "O=" + O + ", " + "L=" + L + ", " + "ST=" + ST + ", " + "C=" + C + ", " + "emailAddress=" + E;
    }

    
    public boolean isKeyUsageCritical() {
		return keyUsageCritical;
	}


	public void setKeyUsageCritical(boolean keyUsageCritical) {
		this.keyUsageCritical = keyUsageCritical;
	}


	public boolean[] getKeyUsagePolicies() {
		return keyUsagePolicies;
	}


	public void setKeyUsagePolicies(boolean[] keyUsagePolicies) {
		this.keyUsagePolicies = keyUsagePolicies;
	}


	X509Certificate generateCertificate() throws GeneralSecurityException, IOException
    {
    	
    	KeyPairClass gen = new KeyPairClass();
		KeyPair pair = gen.genPairOfKeys(length);
		PrivateKey priv = pair.getPrivate();
		
		this.pair = pair;
		
		 X509CertInfo info = new X509CertInfo();
		 
		 this.notBefore = new Date();
		 this.noAfter = new Date(this.notBefore.getTime() + days * 86400000l);
		 
		 CertificateValidity validity = new CertificateValidity(notBefore, noAfter);
		 String dName = getDNames();
		 X500Name owner = new X500Name(dName);
		 BigInteger serialN = new BigInteger(Integer.toString(serialNum));
		 
		  info.set(X509CertInfo.VALIDITY, validity);
		  info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(serialN));
		  info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(owner));
		  info.set(X509CertInfo.ISSUER, new CertificateIssuerName(owner));
		  info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
		  info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
		  
		  AlgorithmId alg = new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_OIW_oid);
		  info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(alg));
		  
		  CertificateExtensions ext = new CertificateExtensions();
	
		  /*GeneralNames g = new GeneralNames();
 	      GeneralName gr= new GeneralName(new DerValue(DerValue.tag_OctetString,"AAAAaaaaaaaaaaa".getBytes()));
	 	  g.add(gr);
		    
		   ext.set(SubjectAlternativeNameExtension.NAME, new SubjectAlternativeNameExtension(g));*/
         
		  //setting Key usage extension
		  if(this.isKeyExt())
		  {
		   KeyUsageExtension kue = new KeyUsageExtension(keyUsagePolicies);
		   ext.set(KeyUsageExtension.NAME, new KeyUsageExtension(this.isKeyUsageCritical(),kue.getValue()));
		  }
		  
		  //setting Alternative names extension-glup nacin ali trenutno ne znam drugacije :(
		  if(this.isAltNameExt())
		  {
		   SubjectAlternativeNameExtension san = new SubjectAlternativeNameExtension();
		   byte[] altNamesValue = new DerValue(DerValue.tag_OctetString, altNames.getBytes()).toByteArray();
		   ext.set(SubjectAlternativeNameExtension.NAME, new Extension(san.getExtensionId(),altNamesCritical,altNamesValue));
		  }
		  
		 // setting Basic contraints extension
		 if(this.isBasicExt())
		  {
		  BasicConstraintsExtension be = new BasicConstraintsExtension(CA,pathLength);
		  ext.set(BasicConstraintsExtension.NAME,  new BasicConstraintsExtension(this.isBasicCritical(),be.getValue()));
		  }
		
		  info.set(X509CertInfo.EXTENSIONS, ext);
		  
		  // Sign the certificate to identify the algorithm that's used.
		  X509CertImpl certificate = new X509CertImpl(info);
		  
		  String algorithm = "SHA1withRSA";
		  certificate.sign(priv, algorithm);
		  
		// Update the algoritham, and resign.
		  alg = (AlgorithmId)certificate.get(X509CertImpl.SIG_ALG);
		  info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, alg);
		  certificate = new X509CertImpl(info);
		  certificate.sign(priv, algorithm);
		  	  
		  return certificate;	 	
    	
    }
	
     
    public KeyPair getPair() {
		return pair;
	}


	public void setPair(KeyPair pair) {
		this.pair = pair;
	}


	public String getAltNames() {
		return altNames;
	}


	public void setAltNames(String altNames) {
		this.altNames = altNames;
	}


	public boolean isAltNamesCritical() {
		return altNamesCritical;
	}


	public void setAltNamesCritical(boolean altNamesCritical) {
		this.altNamesCritical = altNamesCritical;
	}


	public int getDays() {
		return days;
	}


	public void setDays(int days) {
		this.days = days;
	}


	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public Date getNotBefore() {
		return notBefore;
	}

	public Date getNoAfter() {
		return noAfter;
	}
	
	public int getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}
	public String getCN() {
		return CN;
	}
	public void setCN(String cN) {
		CN = cN;
	}
	public String getOU() {
		return OU;
	}
	public void setOU(String oU) {
		OU = oU;
	}
	public String getO() {
		return O;
	}
	public void setO(String o) {
		O = o;
	}
	public String getL() {
		return L;
	}
	public void setL(String l) {
		L = l;
	}
	public String getST() {
		return ST;
	}
	public void setST(String sT) {
		ST = sT;
	}
	public String getC() {
		return C;
	}
	public void setC(String c) {
		C = c;
	}
	public String getE() {
		return E;
	}
	public void setE(String e) {
		E = e;
	}
	
}
