package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ApplicationException;
import com.final_project.cargo_delivery.service.interfaces.ReceiptPaymentService;
import com.final_project.cargo_delivery.service.util.PdfUtil;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * ReceiptPaymentService implementation
 *
 * @author Mykhailo Hryb
 */
@Service
public class ReceiptPaymentServiceImpl implements ReceiptPaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptPaymentServiceImpl.class);

    private static final int COUNT_COLUMNS = 7;

    private PdfUtil pdfUtil = new PdfUtil();

    @Override
    public String createReceiptPaymentForUser(LocaleApplication localeApplication, OrderViewDto orderViewDto) {

        Random rand = new Random();
        int randomNumber = rand.nextInt(1_000_000_000);

        String pdfFilePath = "src/main/webapp/pdfs/receipt_order_" + orderViewDto.getId() + "_" + randomNumber + ".pdf";
        String pdfFilePathWeb = "/pdfs/receipt_order_" + orderViewDto.getId() + "_" + randomNumber + ".pdf";
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        Document document = new Document();
        pdfUtil.setPdfWriterInstance(document, pdfFilePath, messages);

        document.open();

        BaseFont baseFont = pdfUtil.getFontArial(messages);

        Font fontTitle = new Font(baseFont, 16, Font.NORMAL);
        Font fontNormal = new Font(baseFont, 12, Font.NORMAL);
        Font fontTableCell = new Font(baseFont, 10, Font.NORMAL);

        Paragraph companyCredentials = new Paragraph(messages.getString("order.pdf.company"), fontNormal);
        Paragraph title = new Paragraph(messages.getString("order.pdf.title"), fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setPaddingTop(10.f);
        title.setSpacingAfter(10.f);

        PdfPTable table = new PdfPTable(COUNT_COLUMNS);
        table.setWidthPercentage(100.f);
        addTableHeader(table, messages, fontTableCell);
        addRows(table, messages, orderViewDto, fontTableCell);
        table.setSpacingAfter(10.f);

        Paragraph credentials = new Paragraph(messages.getString("order.pdf.company.credentials")
                + " __________                    "
                + messages.getString("order.pdf.company.user.credentials")
                + " __________", fontNormal);
        credentials.setPaddingTop(10.f);

        try {
            document.add(companyCredentials);
            document.add(title);
            document.add(table);
            document.add(credentials);
        } catch (DocumentException documentException) {
            LOGGER.error("Error while adding element to pdf, {}", documentException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.adding_element_to_pdf"));
        }
        document.close();
        return pdfFilePathWeb;
    }


    private void addTableHeader(PdfPTable table, ResourceBundle messages, Font font) {
        Stream.of(messages.getString("main.calculation.cities_form"),
                messages.getString("main.calculation.cities_to"),
                messages.getString("main.calculation.weight"),
                messages.getString("main.calculation.volume"),
                messages.getString("order.price"),
                messages.getString("order.address"),
                messages.getString("main.delivery.date_receiving")
        )
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, ResourceBundle messages, OrderViewDto orderViewDto, Font fontCell) {
        table.addCell(new Phrase(new Paragraph(orderViewDto.getCityFromViewDto().getName(), fontCell)));
        table.addCell(new Phrase(new Paragraph(orderViewDto.getCityToViewDto().getName(), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getWeight()), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getVolume()), fontCell)));
        table.addCell(new Phrase(new Paragraph(orderViewDto.getPrice()
                + " " + messages.getString("main.calculation.currency"), fontCell)));
        table.addCell(new Phrase(new Paragraph(orderViewDto.getAddress(), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getDateDelivery()), fontCell)));
    }
}
