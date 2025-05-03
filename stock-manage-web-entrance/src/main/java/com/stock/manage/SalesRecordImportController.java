package com.stock.manage;

import com.stock.manage.service.SalesRecordImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/data")
@Slf4j
public class SalesRecordImportController {

    @Autowired
    private SalesRecordImportService salesRecordImportService;

    @PostMapping("/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "uploadTemplateName",required = false) String uploadTemplateName) {
        try {
            salesRecordImportService.importSalesData(file, uploadTemplateName);
            return ResponseEntity.ok("导入成功");
        } catch (Exception e) {
            log.error("导入失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入失败: " + e.getMessage());
        }
    }
}