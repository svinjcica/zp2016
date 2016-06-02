package etf.bg.ac.rs.zp2016.alg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.InetAddress;
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
import sun.security.x509.DNSName;
import sun.security.x509.Extension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.IPAddressName;
import sun.security.x509.KeyUsageExtension;
import sun.security.x509.OIDName;
import sun.security.x509.OtherName;
import sun.security.x509.RFC822Name;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.URIName;
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
    private boolean altNamesCritical,keyUsageCritical,basicCritical;
    public boolean[] keyUsagePolicies = new boolean[9];
    //0-certificate signing,1-CRL sign,2-data  encipherment,3-decipher only,4-digital signature,5-encipher only,6-key agreement,7-key encipherment,8-non repudiation
    private int pathLength;
    private boolean CA;
    private boolean altNameExt,basicExt,keyExt;//indikatori za unesene ekstenzije
    
    private GeneralNames subAltNames = new GeneralNames();
    
    private KeyPair pair;
    
    public GeneralNames getSubAltNames() {
		return subAltNames;
	}


	public void setSubAltNames(GeneralNames subAltNames) {
		this.subAltNames = subAltNames;
	}


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
	
	public void subjectAltNames(int i,String value) throws IOException
	{ 
        //GeneralNames subAltNames = new GeneralNames(); 
    
          switch(i)
          {
          case 0:  subAltNames.add(new GeneralName(new DNSName(value)));//0
          break;
          
          case 1:  subAltNames.add(new GeneralName(new IPAddressName(value))); //1
          break;
          
          case 2: subAltNames.add(new GeneralName(new RFC822Name(value)));//2
          break;
          
          case 3:   subAltNames.add(new GeneralName(new URIName(value)));//3
          break;
          
          case 4: subAltNames.add(new GeneralName(new OIDName(value))); //4
          break;
          }
         
        //return subAltNames; 
    } 


	public X509Certificate generateCertificate() throws GeneralSecurityException, IOException
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
		  
		  //settin SubjectAlternativeNames extension
		  if(this.isAltNameExt())
		  {
 	      ext.set(SubjectAlternativeNameExtension.NAME, new SubjectAlternativeNameExtension(this.isAltNamesCritical(),this.subAltNames));
		  }
		  
		  //setting Key usage extension
		  if(this.isKeyExt())
		  {
		   KeyUsageExtension kue = new KeyUsageExtension(keyUsagePolicies);
		   ext.set(KeyUsageExtension.NAME, new KeyUsageExtension(this.isKeyUsageCritical(),kue.getExtensionValue()));
		  }

		  
		 // setting Basic contraints extension
		 if(this.isBasicExt())
		  {
		  BasicConstraintsExtension be = new BasicConstraintsExtension(CA,pathLength);
		  ext.set(BasicConstraintsExtension.NAME,  new BasicConstraintsExtension(this.isBasicCritical(),be.getExtensionValue()));
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
		  
		//  System.out.println(certificate.toString());
		  	  
		  return certificate;	 	
    	
    }
	
     
    public KeyPair getPair() {
		return pair;
	}


	public void setPair(KeyPair pair) {
		this.pair = pair;
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
