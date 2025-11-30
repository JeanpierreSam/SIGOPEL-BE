package com.example.backend_sigopel_v1.service.general.service;

import com.example.backend_sigopel_v1.dto.VehiculoDTO;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
public class PdfExportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Color HEADER_COLOR = new DeviceRgb(14, 36, 81); // #0e2451
    private static final Color ALT_ROW_COLOR = new DeviceRgb(248, 249, 250); // #f8f9fa

    public byte[] exportVehiculosToPdf(Iterable<VehiculoDTO> vehiculos) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Márgenes del documento
            document.setMargins(30, 30, 30, 30);

            // Logo (si tienes una imagen en resources)
            try {
                URL logoUrl = getClass().getClassLoader().getResource("static/images/login.png");
                if (logoUrl != null) {
                    Image logo = new Image(ImageDataFactory.create(logoUrl));
                    logo.setWidth(80);
                    logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    document.add(logo);
                }
            } catch (Exception e) {
                log.warn("No se pudo cargar el logo", e);
            }

            // Título principal
            Paragraph title = new Paragraph("Reporte de Vehículos")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(22)
                    .setFontColor(HEADER_COLOR)
                    .setMarginTop(10)
                    .setMarginBottom(5);
            document.add(title);

            // Subtítulo con fecha
            Paragraph subtitle = new Paragraph("Generado el " + LocalDateTime.now().format(DATETIME_FORMATTER))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY)
                    .setMarginBottom(20);
            document.add(subtitle);

            // Crear la tabla con 6 columnas (sin ID)
            float[] columnWidths = {15, 15, 15, 12, 15, 15};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            // Encabezados de la tabla
            addTableHeader(table);

            // Llenar la tabla con los datos
            int rowIndex = 0;
            for (VehiculoDTO v : vehiculos) {
                addTableRow(table, v, rowIndex % 2 == 0);
                rowIndex++;
            }

            document.add(table);

            // Pie de página
            Paragraph footer = new Paragraph("SIGOPEL - Sistema de Gestión Operacional de Logística")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(8)
                    .setFontColor(ColorConstants.GRAY)
                    .setMarginTop(20);
            document.add(footer);

            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            log.error("Error al generar el PDF de vehículos", e);
            throw new RuntimeException("No se pudo generar el reporte PDF", e);
        }
    }

    private void addTableHeader(Table table) {
        String[] headers = {"Placa", "Marca", "Modelo", "Capacidad", "Estado", "Venc. SOAT"};

        for (String header : headers) {
            Cell cell = new Cell()
                    .add(new Paragraph(header)
                            .setBold()
                            .setFontSize(11)
                            .setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(HEADER_COLOR)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(8)
                    .setBorder(Border.NO_BORDER);
            table.addHeaderCell(cell);
        }
    }

    private void addTableRow(Table table, VehiculoDTO v, boolean isEvenRow) {
        Color bgColor = isEvenRow ? ColorConstants.WHITE : ALT_ROW_COLOR;

        // Placa
        addCell(table, v.getPlaca(), bgColor, TextAlignment.CENTER, true);

        // Marca
        addCell(table, v.getMarca(), bgColor, TextAlignment.LEFT);

        // Modelo
        addCell(table, v.getModelo(), bgColor, TextAlignment.LEFT);

        // Capacidad
        addCell(table, String.valueOf(v.getCapacidad()), bgColor, TextAlignment.CENTER);

        // Estado (con color según el estado)
        addEstadoCell(table, v.getEstadoVehiculoNombre(), bgColor);

        // Vencimiento SOAT
        String vencimientoSoat = Optional.ofNullable(v.getVencimientoSoat())
                .map(DATE_FORMATTER::format)
                .orElse("N/A");
        addCell(table, vencimientoSoat, bgColor, TextAlignment.CENTER);
    }

    private void addCell(Table table, String content, Color bgColor, TextAlignment alignment) {
        addCell(table, content, bgColor, alignment, false);
    }

    private void addCell(Table table, String content, Color bgColor, TextAlignment alignment, boolean bold) {
        Paragraph p = new Paragraph(content != null ? content : "-")
                .setFontSize(10)
                .setTextAlignment(alignment);

        if (bold) {
            p.setBold();
        }

        Cell cell = new Cell()
                .add(p)
                .setBackgroundColor(bgColor)
                .setPadding(6)
                .setBorder(Border.NO_BORDER);

        table.addCell(cell);
    }

    private void addEstadoCell(Table table, String estado, Color bgColor) {
        Color estadoColor = ColorConstants.BLACK;
        Color estadoBgColor = bgColor;

        if (estado != null) {
            switch (estado.toLowerCase()) {
                case "disponible":
                    estadoColor = new DeviceRgb(13, 104, 50); // Verde oscuro
                    estadoBgColor = new DeviceRgb(209, 244, 224); // Verde claro
                    break;
                case "mantenimiento":
                    estadoColor = new DeviceRgb(194, 24, 91); // Rosa oscuro
                    estadoBgColor = new DeviceRgb(252, 228, 236); // Rosa claro
                    break;
                case "en ruta":
                    estadoColor = new DeviceRgb(133, 100, 4); // Amarillo oscuro
                    estadoBgColor = new DeviceRgb(255, 243, 205); // Amarillo claro
                    break;
            }
        }

        Paragraph p = new Paragraph(estado != null ? estado : "-")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(estadoColor)
                .setBold();

        Cell cell = new Cell()
                .add(p)
                .setBackgroundColor(estadoBgColor)
                .setPadding(6)
                .setBorder(Border.NO_BORDER);

        table.addCell(cell);
    }
}
