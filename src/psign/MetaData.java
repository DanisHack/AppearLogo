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
import java.util.Map;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
 
 
public class MetaData {
    
    public static final String RESULT1
        = "E:/pdf_metadata.pdf";
    
    public static final String RESULT2
        = "E:/pdf_metadata_changed.pdf";
 
    
    public void createPdf(String filename) throws IOException, DocumentException {
        
        Document document = new Document();
       
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        
        document.addTitle("Hello World example");
        document.addAuthor("Danish");
        document.addSubject("add metadata");
        document.addKeywords("Metadata, PDF");
        document.addCreator("My program using opensource library");
        document.open();
        
        document.add(new Paragraph("Hello World"));
        
        document.close();
    }
 
    
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        Map<String, String> info = reader.getInfo();
        info.put("Title", "Hello World stamped");
        info.put("Subject", "Hello World with changed metadata");
        info.put("Keywords", "In Action, PdfStamper");
        info.put("Creator", "projectMMS");
        info.put("Author", "Khan DanishMohd");
        stamper.setMoreInfo(info);
        stamper.close();
    }
 
    
    public static void main(String[] args) throws IOException, DocumentException {
        MetaData metadata = new MetaData();
        metadata.createPdf(RESULT1);
        metadata.manipulatePdf(RESULT1, RESULT2);
    }
}
