/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psign;

/**
 *
 * @author DanishM
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;
 
import org.bouncycastle.jce.provider.BouncyCastleProvider;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
 
public class EncrypCert {
 
    
    public static String RESULT1 = "E:/MMS/certificate_encryption.pdf";
    
    public static String RESULT2 = "E:/MMS/certificate_decrypted.pdf";
    
    public static String RESULT3 = "E:/MMS/certificate_encrypted.pdf";
 
    
    public static String PATH = "E:/MMS/key.properties";
    
    public static Properties properties = new Properties();
 
    
    public void createPdf(String filename)
        throws IOException, DocumentException, GeneralSecurityException {
        
        Document document = new Document();
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT1));
        Certificate cert1 = getPublicCertificate("D:/foobar.cer");
       Certificate cert2 = getPublicCertificate(properties.getProperty("PUBLIC"));
        writer.setEncryption(new Certificate[]{cert1, cert2},
            new int[]{PdfWriter.ALLOW_PRINTING, PdfWriter.ALLOW_COPY}, PdfWriter.ENCRYPTION_AES_128);
        
        document.open();
        
        document.add(new Paragraph("Hello World!"));
        
        document.close();
    }
 
    
    public Certificate getPublicCertificate(String path)
        throws IOException, CertificateException {
        FileInputStream is = new FileInputStream(path);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
        return cert;
    }
 
    
    public PrivateKey getPrivateKey() throws GeneralSecurityException, IOException {
        String path = "D:/.keystore";
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(new FileInputStream(path), "Danish".toCharArray());
        PrivateKey pk = (PrivateKey)ks.getKey("dankey", "Danish".toCharArray());
        return pk;
    }
 
   
    public void decryptPdf(String src, String dest)
        throws IOException, DocumentException, GeneralSecurityException {
        PdfReader reader = new PdfReader(src,
            getPublicCertificate("D:/dan.txt"), getPrivateKey(), "BC");
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
    }
 
   
    public void encryptPdf(String src, String dest)
        throws IOException, DocumentException, CertificateException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        Certificate cert = getPublicCertificate("E:/foobar.cer");
        stamper.setEncryption(new Certificate[]{cert},
            new int[]{PdfWriter.ALLOW_PRINTING}, PdfWriter.ENCRYPTION_AES_128);
        stamper.close();
    }
 
    
    public static void main(String[] args)
        throws IOException, DocumentException, GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        properties.load(new FileInputStream(PATH));
        EncrypCert hello = new EncrypCert();
        hello.createPdf(RESULT1);
        hello.decryptPdf(RESULT1, RESULT2);
        hello.encryptPdf(RESULT2, RESULT3);
    }
}
