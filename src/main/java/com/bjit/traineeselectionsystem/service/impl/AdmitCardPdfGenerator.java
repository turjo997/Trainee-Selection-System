package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AdmitCardPdfGenerator {
    public static byte[] generateAdmitCardPdf(AdmitCardEntity admitCard) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.setLeading(14.5f);
                contentStream.beginText();

                // Set positions for writing content
                float x = 50;
                float y = page.getMediaBox().getHeight() - 50;

                // Write applicant details
                contentStream.newLineAtOffset(x, y);
                contentStream.showText("Applicant Name: " + admitCard.getApplicant().getFirstName() + " " + admitCard.getApplicant().getLastName());
                contentStream.newLine();
                contentStream.showText("Exam Date: " + admitCard.getExamDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                contentStream.newLine();
                contentStream.showText("Exam Time: " + admitCard.getExamTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                contentStream.newLine();
                contentStream.showText("Exam Venue: " + admitCard.getExamVenue());
                contentStream.newLine();

                // Add more content as needed

                contentStream.endText();
            }

            // Convert the generated PDF document to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
}
