package com.ed.reportservice.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class ReportService {

    private final static List<String> LIST_TABLE_PRODUCT = Arrays.asList("Id", "Title", "Price", "Available item");

    private static final Font TITLEFONT = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);


    /**
     * Создание PDF файла
     */
    public ByteArrayInputStream generationPDF() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            addTitle(document);
            addTable(document, 4);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * Добавление заголовка в файл
     */
    private void addTitle(Document document) throws DocumentException {
        String localDateString = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Paragraph paragraph = new Paragraph();
        leaveEmptyLine(paragraph, 1);
        Font font = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
        paragraph.add(new Paragraph("Example-Report", TITLEFONT));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        leaveEmptyLine(paragraph, 1);
        paragraph.add(new Paragraph("Report generated: " + localDateString, new Font(Font.FontFamily.COURIER, 12, Font.BOLD)));
        document.add(paragraph);

    }

    /**
     * Добавление таблицы в файл
     */
    private void addTable(Document document, int noOfColumns) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        leaveEmptyLine(paragraph, noOfColumns);
        document.add(paragraph);

        PdfPTable tableInPDF = new PdfPTable(noOfColumns);

        for (int i = 0; i < noOfColumns; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(LIST_TABLE_PRODUCT.get(i)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.CYAN);
            tableInPDF.addCell(cell);
        }

        tableInPDF.setHeaderRows(1);
        addData(tableInPDF);
        document.add(tableInPDF);
    }

    private static void leaveEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /**
     * Заполнение данных в таблицу
     * данные тестовые, нужно получать данные из репозитория или kafka
     */
    private void addData(PdfPTable table) {

        for (int i = 0; i < 5; i++) {

            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(Integer.toString(i));
            table.addCell("Bed-" + i);
            table.addCell("250$");
            table.addCell("25");
        }
    }

}
