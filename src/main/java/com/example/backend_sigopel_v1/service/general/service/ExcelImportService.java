package com.example.backend_sigopel_v1.service.general.service;

import com.example.backend_sigopel_v1.dto.ProgramacionExcelDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelImportService {

    /**
     * RF05: Importar programación preliminar desde archivo Excel
     */
    ProgramacionExcelDTO importarProgramacion(MultipartFile archivo) throws Exception;
}
