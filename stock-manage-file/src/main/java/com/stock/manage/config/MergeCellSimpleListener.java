//package com.stock.manage.config;
//
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.enums.CellExtraTypeEnum;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.excel.metadata.CellExtra;
//import com.alibaba.excel.metadata.data.ReadCellData;
//import com.stock.manage.model.ExcelDataImportDTO;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//public class MergeCellSimpleListener extends AnalysisEventListener<ExcelDataImportDTO> {
//
//    // 保存需要的结果
//    private final List<ExcelDataImportDTO> VALID_DATA_LIST = new ArrayList<>();
//
//    @Override
//    public void invoke(ExcelDataImportDTO data, AnalysisContext context) {
//        if (data.getSkuName() != null && !data.getSkuName().trim().isEmpty()) {
//            VALID_DATA_LIST.add(data);
//        }
//    }
//
//    @Override
//    public void extra(CellExtra extra, AnalysisContext context) {
//    }
//
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext context) {
//        log.warn("所有数据处理完毕！");
//    }
//
//    /**
//     * 对外暴露方法，方便拿到读取到的数据
//     */
//    public List<ExcelDataImportDTO> getList() {
//        return VALID_DATA_LIST;
//    }
//}