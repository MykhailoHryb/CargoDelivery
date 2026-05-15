package com.final_project.cargo_delivery.service.util;

import com.final_project.cargo_delivery.exception.ApplicationException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * PdfUtil has some util methods to work with pdf files
 *
 * @author Mykhailo Hryb
 */
public class PdfUtil {

    public final String FONT_PATH = "src/main/webapp/fonts/arial.ttf";

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * Gets custom arial font
     *
     * @param messages
     * @return
     */
    public BaseFont getFontArial(ResourceBundle messages) {
        BaseFont baseFont;
        try {
            baseFont = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException exception) {
            LOGGER.error("Error while creating font {}", exception.getMessage());
            throw new ApplicationException(messages.getString("exception.error.creating_receipt_font_pdf"));
        }
        return baseFont;
    }

    /**
     * Sets instance of pdf writer
     *
     * @param document
     * @param pdfFilePath
     * @param messages
     */
    public void setPdfWriterInstance(Document document, String pdfFilePath, ResourceBundle messages) {
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(pdfFilePath));
        } catch (DocumentException | FileNotFoundException exception) {
            LOGGER.error("Error while creating pdf file {}", exception.getMessage());
            throw new ApplicationException(messages.getString("exception.error.creating_receipt_pdf"));
        }
    }

    /**
     * Gets fonts for special blocks of texts
     *
     * @param messages
     * @return
     */
    public Map<String, Font> getFontsForReport(ResourceBundle messages) {
        BaseFont baseFont = getFontArial(messages);
        Map<String, Font> fontsForReport = new HashMap<>();
        fontsForReport.put("fontMainTitle", new Font(baseFont, 18, Font.NORMAL));
        fontsForReport.put("fontSubTitle", new Font(baseFont, 16, Font.NORMAL));
        fontsForReport.put("fontNormal", new Font(baseFont, 10, Font.NORMAL));
        fontsForReport.put("fontTableCell", new Font(baseFont, 8, Font.NORMAL));

        return fontsForReport;
    }
}
