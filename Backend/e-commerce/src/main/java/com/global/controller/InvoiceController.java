//package com.global.controller;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.global.service.OrderService;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.pdf.languages.ArabicLigaturizer;
//import com.itextpdf.text.pdf.languages.LanguageProcessor;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/invoice")
//@Log4j2
//public class InvoiceController {
//
//    private final OrderService orderService;
//
//    @GetMapping("/{orderId}/download")
//    public ResponseEntity<?> downloadInvoice(@PathVariable Long orderId) {
//        String arabicText = "مرحبا بكم في الفاتورة"; // Your Arabic text
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Document document = new Document();
//
//        try {
//            // Initialize PdfWriter
//            PdfWriter.getInstance(document, outputStream);
//
//            // Open the document
//            document.open();
//
//            // Load an Arabic font (TTF font that supports Arabic)
//            BaseFont arabicFont = BaseFont.createFont("src/main/resources/fonts/aldhabi.ttf", BaseFont.IDENTITY_H,
//                    BaseFont.EMBEDDED);
//            
//            
//
//            // Check if the font is loaded correctly
//            if (arabicFont == null) {
//                log.error("Failed to load the Arabic font.");
//                return ResponseEntity.status(500).body("Failed to load the font.");
//            }
//
//            // Create a LanguageProcessor for Arabic
//            LanguageProcessor lp = new ArabicLigaturizer();
//            String processedText = lp.process(arabicText); // Process the Arabic text
//
//            // Create a Paragraph with the Arabic text
//            Font font = new Font(arabicFont, 12);
//            
//            
//            Paragraph paragraph = new Paragraph(processedText, font);
//            paragraph.setAlignment(Element.ALIGN_RIGHT); // Align text to the right for Arabic
//
//            // Add the paragraph to the document
//            document.add(paragraph);
//
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error creating PDF: " + e.getMessage());
//        } finally {
//            // Close the document
//            document.close();
//            System.out.println("PDF created successfully!");
//        }
//
//        byte[] pdfContent = outputStream.toByteArray();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=invoice_" + orderId + ".pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(pdfContent.length)
//                .body(new InputStreamResource(new ByteArrayInputStream(pdfContent)));
//    }
//}

//package com.global.controller;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDFont;
//import org.apache.pdfbox.pdmodel.font.PDType0Font;
//
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.global.config.ArabicTextUtil;
//import com.global.service.OrderService;
//import com.ibm.icu.text.ArabicShapingException;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/invoice")
//@Log4j2
//public class InvoiceController {
//
//    private final OrderService orderService;
//
//    @GetMapping("/{orderId}/download")
//    public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable Long orderId) throws ArabicShapingException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        
//        // Create a new document
//        try (PDDocument document = new PDDocument()) {
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            // Load an Arabic font (ensure the font file is in your resources)
//            PDFont arabicFont = PDType0Font.load(document, new File("src/main/resources/fonts/arial.ttf")); // Update with your font path
//
//            // Create a content stream to write to the page
//            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                contentStream.setFont(arabicFont, 12);
//                contentStream.beginText();
//                contentStream.newLineAtOffset(100, 700); // Position the text
////                contentStream.showText("مرحبا بكم في الفاتورة"); // Your Arabic text
//                String shapedText = ArabicTextUtil.shapeArabicText("مرحبا بكم في الفاتورة");
//                contentStream.showText(shapedText);
//                contentStream.endText();
//            }
//
//            // Save the document to the output stream
//            document.save(outputStream);
//        } catch (IOException e) {
//            log.error("Error generating PDF: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle error appropriately
//        }
//
//        byte[] pdfContent = outputStream.toByteArray();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=invoice_" + orderId + ".pdf");
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentLength(pdfContent.length)
//                .body(new InputStreamResource(new ByteArrayInputStream(pdfContent)));
//    }
//}

//package com.global.controller;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//
////import javax.swing.text.Document;
//
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.global.projection.OrderDetails;
//import com.global.service.OrderService;
//import com.itextpdf.io.font.PdfEncodings;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.properties.BaseDirection;
//import com.itextpdf.layout.properties.TextAlignment;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/invoice")
//@Log4j2
//public class InvoiceController {
//
//	private final OrderService orderService;
//
//	@GetMapping("/{orderId}/download")
//	public ResponseEntity<?> downloadInvoice(@PathVariable Long orderId) {
////		String dest = "path/to/your/output.pdf"; // Change this to your desired output path
//		String arabicText = "مرحبا بكم في الفاتورة"; // Your Arabic text
//		
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		LanguageProcessor lp = new ArabicLigaturizer();
//		String processedText = lp.process(arabicText);
//
//		Document document = new Document();
//
//		try {
//			// Initialize PdfWriter
//			PdfWriter.getInstance(document, outputStream);
//
//			// Open the document
//			document.open();
//
//			// Load an Arabic font (TTF font that supports Arabic)
//			BaseFont arabicFont = BaseFont.createFont("src/main/resources/fonts/aldhabi.ttf", BaseFont.IDENTITY_H,
//					BaseFont.EMBEDDED);
//
//			// Create a Paragraph with the Arabic text
//			Font font = new Font(arabicFont, 12);
//			Paragraph paragraph = new Paragraph(processedText, font);
//			paragraph.setAlignment(Element.ALIGN_RIGHT); // Align text to the right for Arabic
//
//			// Add the paragraph to the document
//			document.add(paragraph);
//		} catch (DocumentException | IOException e) {
//			e.printStackTrace();
//		} finally {
//			// Close the document
//			document.close();
//			System.out.println("PDF created successfully!");
//		}
//
//		byte[] pdfContent = outputStream.toByteArray();
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Disposition", "attachment; filename=invoice_" + orderId + ".pdf");
//
//		return ResponseEntity.ok().headers(headers).contentLength(pdfContent.length)
//				.body(new InputStreamResource(new ByteArrayInputStream(pdfContent)));

//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		 // Create a new document
//        try (PDDocument document = new PDDocument()) {
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            // Load an Arabic font (ensure the font file is in your resources)
//            PDFont arabicFont = PDType0Font.load(document, new File("src/main/resources/fonts/aldhabi.ttf")); // Update with your font path
//
//            // Create a content stream to write to the page
//            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                contentStream.setFont(arabicFont, 12);
//                contentStream.beginText();
//                contentStream.newLineAtOffset(100, 700); // Position the text
//                contentStream.showText("مرحبا بكم في الفاتورة"); // Your Arabic text
//                contentStream.endText();
//            }
//            
//            document.close();
//            
//            
//          byte[] pdfContent = outputStream.toByteArray();
//          HttpHeaders headers = new HttpHeaders();
//          headers.add("Content-Disposition", "attachment; filename=invoice_" + orderId + ".pdf");
//          
//          return ResponseEntity
//          		.ok()
//          		.headers(headers)
//          		.contentLength(pdfContent.length)
//          		.body(new InputStreamResource(new ByteArrayInputStream(pdfContent)));
//            
//            
//            // Save the document
////            document.save("invoice.pdf");
////            System.out.println("PDF created successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//		
//        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.CONFLICT);

//        // Create PDF
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
//        	
//        	OrderDetails order = orderService.getOrderDetails(orderId);
//        	
//            PdfWriter writer = new PdfWriter(outputStream);
//            PdfDocument pdfDoc = new PdfDocument(writer);
//            Document document = new Document(pdfDoc);
//            
//            
//            // Load the aldhabi font that supports Arabic
//            PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/aldhabi.ttf", PdfEncodings.UTF8, EmbeddingStrategy.PREFER_EMBEDDED);
//            
//       
//            // Create a paragraph with Arabic text
//            Paragraph paragraph = new Paragraph("مرحبا بكم")
//                    .setFont(font) // Set the font
//                    .setFontSize(12)
//                    .setTextAlignment(TextAlignment.RIGHT)
//                    .setBaseDirection(BaseDirection.RIGHT_TO_LEFT); 
////            document.add(new Paragraph("Invoice for Order ID: " + orderId).setFontFamily(StandardFonts.HELVETICA_BOLD));
//            document.add(paragraph);
////            document.add(new Paragraph("Hello, " + orderId));
//            
//            // Add more content to your invoice as needed
//            document.close();
//            
//            byte[] pdfContent = outputStream.toByteArray();
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "attachment; filename=invoice_" + orderId + ".pdf");
//            
//            return ResponseEntity
//            		.ok()
//            		.headers(headers)
//            		.contentLength(pdfContent.length)
//            		.body(new InputStreamResource(new ByteArrayInputStream(pdfContent)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.CONFLICT);
//
//	}
//
//}

package com.global.controller;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.BaseDirection;
import com.itextpdf.layout.properties.TextAlignment;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/invoice")
@Log4j2
public class InvoiceController {

	@GetMapping("/{orderId}/download")
	public ResponseEntity<?> downloadInvoice(@PathVariable Long orderId) {

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			PdfWriter writer = new PdfWriter(outputStream);
			PdfDocument pdfDoc = new PdfDocument(writer);
			Document document = new Document(pdfDoc);

			// Load the aldhabi font that supports Arabic
			PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/aldhabi.ttf", PdfEncodings.IDENTITY_H,
					EmbeddingStrategy.PREFER_EMBEDDED);

			String text = "مرحبا بكم";

			// Create a paragraph with Arabic text
			Paragraph paragraph = new Paragraph(text).setFont(font) // Set the font
					.setFontSize(12).setTextAlignment(TextAlignment.RIGHT)
					.setBaseDirection(BaseDirection.RIGHT_TO_LEFT);

			// Add paragraph to the document
			document.add(paragraph);

			// Check if content was added
			System.out.println("Paragraph added. Current content size: " + outputStream.size());

			// Close the document
			document.close();

			// Check PDF content length
			byte[] pdfContent = outputStream.toByteArray();
			System.out.println("PDF content length: " + pdfContent.length);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=invoice_" + orderId + ".pdf");

			return ResponseEntity.ok().headers(headers).contentLength(pdfContent.length)
					.body(new InputStreamResource(new ByteArrayInputStream(pdfContent)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
}
