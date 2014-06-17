/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psign;

/**
 *
 * @author DanishM
 */
import java.io.FileOutputStream;
import java.io.IOException;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
 
 
public class EncrypPDF {
    
    public static byte[] USER = "Hello".getBytes();
    
    public static byte[] OWNER = "World".getBytes();
 
    
    public static final String RESULT1
        = "E:/Movies/MMS/encryption.pdf";
   
    public static final String RESULT2
        = "E:/Movies/MMS/encryption_decrypted.pdf";
    
    public static final String RESULT3
        = "E:/Movies/MMS/encryption_encrypted.pdf";
 
    
    public void createPdf(String filename) throws IOException, DocumentException {
        
        Document document = new Document();
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        writer.setEncryption(USER, OWNER, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        writer.createXmpMetadata();
        
        document.open();
        
        document.add(new Paragraph("Encryption Needed"));
        
        document.close();
    }
 
    
    public void decryptPdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src, OWNER);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
    }
 
    
    public void encryptPdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.setEncryption(USER, OWNER,
            PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
        stamper.close();
    }
 
   
    public static void main(String[] args) throws IOException, DocumentException {
        EncrypPDF metadata = new EncrypPDF();
        metadata.createPdf(RESULT1);
        metadata.decryptPdf(RESULT1, RESULT2);
        metadata.encryptPdf(RESULT2, RESULT3);
    }
}
 


