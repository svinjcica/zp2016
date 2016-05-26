package etf.bg.ac.rs.zp2016.alg;


import java.io.IOException;
import java.math.BigInteger;
import java.util.*;



import sun.security.x509.*;
import java.security.cert.*;
import java.security.*;



public class CertificateClass  
{
	
	/*. Кориснику треба понудити да унесе
	следеће информације: величину кључа, верзију сертификата, период важења, серијски
	број и информације о кориснику (CN, OU, O, L, ST, C, E). Верзију сертификата треба
	ограничити само на v3
	
	ориснику треба понудити да опционо може да унесе и следеће
	екстензије: основна ограничења (basic constraints), алтернативна имена издаваоца
	сертификата (issuer alternative name) и коришћење кључа (key usage). Омогућити за
	екстензије да се означи да ли су критичне или не. Корисник треба да има могућност да у
	апликацији сачува генерисани пар кључева под жељеним именом. */

	
	private int length, days; //VERSION SHOULD BE JUST V3
    private Date notBefore, noAfter;
    private int serialNum;
    private String CN, OU, O, L, ST, C, E;
    private ArrayList<String> altNames;
    private boolean altNamesCritical;
    
    
    
    public String getDNames()
    {
    	return "CN="+ CN + ", " + "OU=" + OU + ", " + "O=" + O + ", " + "L=" + L + ", " + "ST=" + ST + ", " + "C=" + C + ", " + "emailAddress=" + E;
    }

    
   public X509Certificate generateCertificate() throws GeneralSecurityException, IOException
    {
    	
    	KeyPairClass gen = new KeyPairClass();
		KeyPair pair = gen.genPairOfKeys(length);
		PrivateKey priv = pair.getPrivate();
		
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
		 // X509CertInfo.EXTENSIONS, new Certificate
		  
		  AlgorithmId alg = new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_OIW_oid);
		  info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(alg));
		  
		  CertificateExtensions ext = new CertificateExtensions();
		 /* ext.set(SubjectKeyIdentifierExtension.NAME,
		            new SubjectKeyIdentifierExtension(new KeyIdentifier(pair.getPublic()).getIdentifier()));
		    // CA public key is the same as our public key (self signed)
		    ext.set(AuthorityKeyIdentifierExtension.NAME,
		            new AuthorityKeyIdentifierExtension(new KeyIdentifier(pair.getPublic()), null, null));*/

		 /* GeneralNames g = new GeneralNames();
		  GeneralName gr= new GeneralName(new X500Name(altNames.get(0)));
		  g.add(gr);
		    
		    ext.set(SubjectAlternativeNameExtension.NAME, new SubjectAlternativeNameExtension(true,g));
       
	
		 
		    info.set(X509CertInfo.EXTENSIONS, ext);*/
		  
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
    
    
     
    public ArrayList<String> getAltNames() {
		return altNames;
	}


	public void setAltNames(ArrayList<String> altNames) {
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
	/*public void setNotBefore(Date notBefore) {
		this.notBefore = notBefore;
	}*/
	public Date getNoAfter() {
		return noAfter;
	}
	/*public void setNoAfter(Date noAfter) {
		this.noAfter = noAfter;
	}*/
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
	
   // private String [] issuerAlternativeNames;

	/*@Override
	public Set<String> getCriticalExtensionOIDs() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getExtensionValue(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> getNonCriticalExtensionOIDs() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasUnsupportedCriticalExtension()
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void checkValidity() throws CertificateExpiredException,
			CertificateNotYetValidException
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void checkValidity(Date arg0) throws CertificateExpiredException,
			CertificateNotYetValidException 
    {
		// TODO Auto-generated method stub
	}
	@Override
	public int getBasicConstraints() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Principal getIssuerDN() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean[] getIssuerUniqueID() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean[] getKeyUsage()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Date getNotAfter() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Date getNotBefore() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BigInteger getSerialNumber() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSigAlgName()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSigAlgOID() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getSigAlgParams() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getSignature() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Principal getSubjectDN()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean[] getSubjectUniqueID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] getTBSCertificate() throws CertificateEncodingException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public byte[] getEncoded() throws CertificateEncodingException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PublicKey getPublicKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void verify(PublicKey arg0) throws CertificateException,
			NoSuchAlgorithmException, InvalidKeyException,
			NoSuchProviderException, SignatureException {
		// TODO Auto-generated method stub
	}
	@Override
	public void verify(PublicKey arg0, String arg1)
			throws CertificateException, NoSuchAlgorithmException,
			InvalidKeyException, NoSuchProviderException, SignatureException {
		// TODO Auto-generated method stub
	}
*/

}
