package com.project.StudentsManagement.utils;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.Normalizer;

public class CertificateGenerator {

    public static void generateCertificate(String studentName, double note, String mention) {
        try {
            String outputDirectory = "src/main/resources/certificates";

            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String normalizedStudentName = Normalizer.normalize(studentName, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "");
            String fileName = outputDirectory + "/" + normalizedStudentName.replaceAll(" ", "_") + "_Certificate.pdf";

            PdfWriter writer = new PdfWriter(fileName);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4.rotate()); // Mode paysage
            Document document = new Document(pdfDoc);

            document.setMargins(50, 50, 50, 50);

            Paragraph title = new Paragraph("Certificate of Achievement")
                    .setFontSize(28)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            LineSeparator separator = new LineSeparator(new SolidLine());
            document.add(separator);

            document.add(new Paragraph("\n"));

            Paragraph subtitle = new Paragraph("This is to certify that:")
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(subtitle);

            Paragraph name = new Paragraph(studentName.toUpperCase())
                    .setFontSize(22)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(10);
            document.add(name);

            document.add(new Paragraph("\n"));

            Paragraph description = new Paragraph("Has successfully achieved the required performance in their academic assessments.")
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(description);

            document.add(new Paragraph("\n\n"));

            Paragraph gradeParagraph = new Paragraph("Grade: " + note)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(gradeParagraph);

            Paragraph mentionParagraph = new Paragraph("Mention: " + mention)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(mentionParagraph);

            document.add(new Paragraph("\n\n"));

            Paragraph message = new Paragraph("Congratulations on your achievement!")
                    .setFontSize(14)
                    .setItalic()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(message);



            document.close();
            System.out.println("PDF successfully generated at: " + fileName);

        } catch (FileNotFoundException e) {
            System.err.println("Error: Unable to create PDF file - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }


}
