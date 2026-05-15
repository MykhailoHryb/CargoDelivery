package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ApplicationException;
import com.final_project.cargo_delivery.service.interfaces.ReportService;
import com.final_project.cargo_delivery.service.util.PdfUtil;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ReportService implementation
 *
 * @author Mykhailo Hryb
 */
@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final PdfUtil pdfUtil = new PdfUtil();

    private static final int COUNT_COLUMNS = 11;

    @Override
    public String makeReportByCity(LocaleApplication localeApplication, List<OrderViewDto> orderViewDtoList) {
        String pdfFilePathWeb = "/pdfs/manager/reportByCities.pdf";
        String pdfFilePath = "src/main/webapp" + pdfFilePathWeb;

        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        Map<CityViewDto, List<OrderViewDto>> ordersByCity = orderViewDtoList.stream()
                .collect(Collectors.groupingBy(OrderViewDto::getCityFromViewDto));

        Document document = new Document();
        pdfUtil.setPdfWriterInstance(document, pdfFilePath, messages);
        document.open();
        Map<String, Font> fontsForReport = pdfUtil.getFontsForReport(messages);

        Paragraph mainTitle = new Paragraph(messages.getString("pdf.report.title_by_city"),
                fontsForReport.get("fontMainTitle"));
        mainTitle.setAlignment(Element.ALIGN_CENTER);
        mainTitle.setSpacingAfter(10.f);

        try {
            document.add(mainTitle);
        } catch (DocumentException documentException) {
            LOGGER.error("Error while adding element to pdf, {}", documentException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.adding_element_to_pdf"));
        }

        ordersByCity.forEach((cityViewDto, orderViewDtoListPdf) -> {
            Paragraph titleTable = new Paragraph(messages.getString("main.delivery.city_short") + " "
                    + cityViewDto.getName(), fontsForReport.get("fontSubTitle"));
            makeTableReport(messages, document, fontsForReport, titleTable, orderViewDtoListPdf);
        });
        document.close();
        return pdfFilePathWeb;
    }

    @Override
    public String makeReportByDate(LocaleApplication localeApplication, List<OrderViewDto> orderViewDtoList) {
        String pdfFilePathWeb = "/pdfs/manager/reportByDate.pdf";
        String pdfFilePath = "src/main/webapp" + pdfFilePathWeb;

        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        orderViewDtoList.sort(Comparator.comparing(OrderViewDto::getDateDelivery));

        LOGGER.info("orderViewDtoList = {}", orderViewDtoList);

        Map<LocalDate, List<OrderViewDto>> ordersByCity = orderViewDtoList.stream()
                .collect(Collectors.groupingBy(OrderViewDto::getDateDelivery, TreeMap::new, Collectors.toList()));

        Document document = new Document();
        pdfUtil.setPdfWriterInstance(document, pdfFilePath, messages);
        document.open();
        Map<String, Font> fontsForReport = pdfUtil.getFontsForReport(messages);

        Paragraph mainTitle = new Paragraph(messages.getString("pdf.report.title_by_date"),
                fontsForReport.get("fontMainTitle"));
        mainTitle.setAlignment(Element.ALIGN_CENTER);
        mainTitle.setSpacingAfter(10.f);

        try {
            document.add(mainTitle);
        } catch (DocumentException documentException) {
            LOGGER.error("Error while adding element to pdf, {}", documentException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.adding_element_to_pdf"));
        }

        ordersByCity.forEach((dateDelivery, orderViewDtoListPdf) -> {
            Paragraph titleTable = new Paragraph(messages.getString("pdf.count_deliveries.date") + ": "
                    + dateDelivery, fontsForReport.get("fontSubTitle"));

            makeTableReport(messages, document, fontsForReport, titleTable, orderViewDtoListPdf);
        });
        document.close();
        return pdfFilePathWeb;
    }

    private void addTableHeader(PdfPTable table, ResourceBundle messages, Font font) {
        Stream.of(
                "id",
                messages.getString("main.calculation.cities_form"),
                messages.getString("main.calculation.cities_to"),
                messages.getString("main.calculation.weight"),
                messages.getString("main.calculation.volume"),
                messages.getString("main.calculation.type_cargo"),
                messages.getString("order.price"),
                messages.getString("order.address"),
                messages.getString("main.delivery.date_creating"),
                messages.getString("main.delivery.date_receiving"),
                messages.getString("order.order_status")
        )
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, ResourceBundle messages, OrderViewDto orderViewDto, Font fontCell) {
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getId()), fontCell)));
        table.addCell(new Phrase(new Paragraph(orderViewDto.getCityFromViewDto().getName(), fontCell)));
        table.addCell(new Phrase(new Paragraph(orderViewDto.getCityToViewDto().getName(), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getWeight()), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getVolume()), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getTypeCargoViewDto().getName()), fontCell)));
        table.addCell(new Phrase(new Paragraph(orderViewDto.getPrice()
                + " " + messages.getString("main.calculation.currency"), fontCell)));
        table.addCell(new Phrase(new Paragraph(orderViewDto.getAddress(), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getDateCreated()), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getDateDelivery()), fontCell)));
        table.addCell(new Phrase(new Paragraph(String.valueOf(orderViewDto.getOrderStatusViewDto().getName()), fontCell)));
    }

    public void makeTableReport(ResourceBundle messages, Document document, Map<String, Font> fontsForReport,
                                Paragraph titleTable, List<OrderViewDto> orderViewDtoListPdf) {

        titleTable.setPaddingTop(10.f);
        titleTable.setSpacingAfter(10.f);

        PdfPTable table = new PdfPTable(COUNT_COLUMNS);
        table.setWidthPercentage(100.f);
        addTableHeader(table, messages, fontsForReport.get("fontTableCell"));
        orderViewDtoListPdf.forEach(orderViewDto -> addRows(table, messages, orderViewDto,
                fontsForReport.get("fontTableCell")));
        table.setSpacingAfter(10.f);

        int countDeliveries = orderViewDtoListPdf.size();
        Paragraph countDeliveriesParagraph = new Paragraph(messages.getString("pdf.count_deliveries") + ": "
                + countDeliveries, fontsForReport.get("fontNormal"));
        int totalPrice = orderViewDtoListPdf.stream().mapToInt(OrderViewDto::getPrice).sum();
        Paragraph priceParagraph = new Paragraph(messages.getString("pdf.total_price") + ": "
                + totalPrice, fontsForReport.get("fontNormal"));
        priceParagraph.setSpacingAfter(20.f);

        try {
            document.add(titleTable);
            document.add(table);
            document.add(countDeliveriesParagraph);
            document.add(priceParagraph);
        } catch (DocumentException documentException) {
            LOGGER.error("Error while adding element to pdf, {}", documentException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.adding_element_to_pdf"));
        }

    }
}
