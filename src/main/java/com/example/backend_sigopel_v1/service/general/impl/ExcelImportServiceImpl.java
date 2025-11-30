package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.dto.ProgramacionExcelDTO;
import com.example.backend_sigopel_v1.service.general.service.ExcelImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelImportServiceImpl implements ExcelImportService {

    @Override
    public ProgramacionExcelDTO importarProgramacion(MultipartFile archivo) throws Exception {
        try (InputStream inputStream = archivo.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Primera hoja
            List<ProgramacionExcelDTO.FilaProgramacion> filas = new ArrayList<>();

            // Estructura del Excel esperada:
            // Col 0: Placa Vehículo
            // Col 1: Razón Social Tienda
            // Col 2: Dirección Tienda
            // Col 3: Almacén
            // Col 4: Hora Inicio (ventana horaria)
            // Col 5: Hora Fin (ventana horaria)
            // Col 6: Volumen (m³ o kg)
            // Col 7: Observación

            // Iterar desde la fila 1 (saltar header en fila 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) {
                    continue; // Saltar filas vacías
                }

                try {
                    ProgramacionExcelDTO.FilaProgramacion fila = ProgramacionExcelDTO.FilaProgramacion.builder()
                            .vehiculoPlaca(getCellValueAsString(row.getCell(0)))
                            .tiendaRazonSocial(getCellValueAsString(row.getCell(1)))
                            .tiendaDireccion(getCellValueAsString(row.getCell(2)))
                            .almacenNombre(getCellValueAsString(row.getCell(3)))
                            .ventanaHorariaInicio(getCellValueAsString(row.getCell(4)))
                            .ventanaHorariaFin(getCellValueAsString(row.getCell(5)))
                            .volumen(getCellValueAsBigDecimal(row.getCell(6)))
                            .observacion(getCellValueAsString(row.getCell(7)))
                            .build();

                    filas.add(fila);

                } catch (Exception e) {
                    log.warn("Error al procesar fila {}: {}", i, e.getMessage());
                    // Puedes decidir si continuar o lanzar excepción
                }
            }

            log.info("Se importaron {} filas del Excel", filas.size());
            return ProgramacionExcelDTO.builder().filas(filas).build();

        } catch (Exception e) {
            log.error("Error al importar archivo Excel", e);
            throw new Exception("Error al procesar el archivo Excel: " + e.getMessage());
        }
    }

    /**
     * Obtiene el valor de una celda como String
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getLocalDateTimeCellValue().toString();
                } else {
                    // Evita notación científica
                    yield String.valueOf((long) cell.getNumericCellValue());
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    /**
     * Obtiene el valor de una celda como BigDecimal
     */
    private BigDecimal getCellValueAsBigDecimal(Cell cell) {
        if (cell == null) {
            return BigDecimal.ZERO;
        }

        try {
            return switch (cell.getCellType()) {
                case NUMERIC -> BigDecimal.valueOf(cell.getNumericCellValue());
                case STRING -> new BigDecimal(cell.getStringCellValue().trim());
                default -> BigDecimal.ZERO;
            };
        } catch (Exception e) {
            log.warn("Error al convertir celda a BigDecimal: {}", e.getMessage());
            return BigDecimal.ZERO;
        }
    }
}
