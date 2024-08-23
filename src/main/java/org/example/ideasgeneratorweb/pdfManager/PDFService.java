package org.example.ideasgeneratorweb.pdfManager;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.ideasgeneratorweb.model.ProjectIdea;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFService {

    final String PATH = "src/main/resources/static";

    public String createPdf(String name, String content) throws FileNotFoundException, DocumentException {
        Document document = new Document();

        String cleanedName = cleanName(name);
        PdfWriter.getInstance(document, new FileOutputStream(PATH + "/" + cleanedName));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.startsWith("**") && line.endsWith("**")) {
                // Title
                Paragraph para = new Paragraph(line.replace("**", ""), titleFont);
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
            } else if (line.startsWith("*") && line.endsWith("*")) {
                // Subtitle or bullet point
                Paragraph para = new Paragraph(line.replace("*", ""), subtitleFont);
                document.add(para);
            } else {
                // Normal text
                Paragraph para = new Paragraph(line, normalFont);
                document.add(para);
            }
            document.add(new Paragraph("\n"));
        }

        document.close();
        return cleanedName;
    }

    private String cleanName(String inputName) {
        // Remove or replace invalid characters
        String cleaned = inputName.replaceAll("[\\\\/:*?\"<>|']", "");
        // Trim whitespace and limit length
        cleaned = cleaned.trim();
        if (cleaned.length() > 255) {
            cleaned = cleaned.substring(0, 255);
        }
        // Ensure the filename ends with .pdf
        if (!cleaned.toLowerCase().endsWith(".pdf")) {
            cleaned += ".pdf";
        }
        return cleaned;
    }


    public ResponseEntity<FileSystemResource> downloadPdf( String pdfText,ProjectIdea idea)
    {
        try {
            String fileName = createPdf(idea.getTitle(),pdfText);
            File pdfFile = new File(PATH + "/" + fileName);

            if(pdfFile.exists())
            {
                FileSystemResource resource = new FileSystemResource(pdfFile);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                        .header("X-Filename", pdfFile.getName())
                        .body(resource);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }
}
