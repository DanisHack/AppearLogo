/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psign;

/**
 *
 * @author DanishM
 */
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.xml.xmp.DublinCoreSchema;
import com.itextpdf.text.xml.xmp.PdfSchema;
import com.itextpdf.text.xml.xmp.XmpArray;
import com.itextpdf.text.xml.xmp.XmpSchema;
import com.itextpdf.text.xml.xmp.XmpWriter;
 
public class MetaDataXMP {
 
    
    public static final String RESULT1
        = "E:xmp_metadata.pdf";
    
    public static final String RESULT2
        = "E:xmp_metadata_automatic.pdf";
    
    public static final String RESULT3
        = "E:xmp_metadata_added.pdf";
    
    public static final String RESULT4
        = "E:/xmp.xml";
 
    
    public void createPdf(String filename) throws IOException, DocumentException {
        
        Document document = new Document();
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT1));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XmpWriter xmp = new XmpWriter(os);
        XmpSchema dc = new com.itextpdf.text.xml.xmp.DublinCoreSchema();
        XmpArray subject = new XmpArray(XmpArray.UNORDERED);
        subject.add("Hello World");
        subject.add("XMP & Metadata");
        subject.add("Metadata");
        dc.setProperty(DublinCoreSchema.SUBJECT, subject);
        xmp.addRdfDescription(dc);
        PdfSchema pdf = new PdfSchema();
        pdf.setProperty(PdfSchema.KEYWORDS, "Hello World, XMP, Metadata");
        pdf.setProperty(PdfSchema.VERSION, "1.4");
        xmp.addRdfDescription(pdf);
        xmp.close();
        writer.setXmpMetadata(os.toByteArray());
        
        document.open();
        
        document.add(new Paragraph("Hello World"));
        
        document.close();
    }
    
    public void createPdfAutomatic(String filename) throws IOException, DocumentException {
        
        Document document = new Document();
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.addTitle("Hello World ");
        document.addSubject("To add metadata & XMP");
        document.addKeywords("Metadata, XMP");
        document.addCreator("ProjectMMS");
        document.addAuthor("DanishMohd, Khan");
        writer.createXmpMetadata();
        
        document.open();
        
        document.add(new Paragraph("Hello World"));
       
        document.close();
    }
 
    
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        HashMap<String, String> info = reader.getInfo();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XmpWriter xmp = new XmpWriter(baos, info);
        xmp.close();
        stamper.setXmpMetadata(baos.toByteArray());
        stamper.close();
    }
 
    
    public void readXmpMetadata(String src, String dest) throws IOException {
        PdfReader reader = new PdfReader(src);
        FileOutputStream fos = new FileOutputStream(dest);
        byte[] b = reader.getMetadata();
        fos.write(b, 0, b.length);
        fos.flush();
        fos.close();
    }
 
    
     
    public static void main(String[] args) throws IOException, DocumentException {
        MetaDataXMP metadata = new MetaDataXMP();
        metadata.createPdf(RESULT1);
        metadata.createPdfAutomatic(RESULT2);
        new MetaData().createPdf(MetaData.RESULT1);
        metadata.manipulatePdf(MetaData.RESULT1, RESULT3);
        metadata.readXmpMetadata(RESULT3, RESULT4);
    }
}
 