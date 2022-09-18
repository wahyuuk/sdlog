package co.id.ahm.sdlog.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class Log018GenerateExcelService {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private CellStyle cellStyle;

    private void initWorkbook() {
        this.workbook = new XSSFWorkbook();
        this.cellStyle = workbook.createCellStyle();
        initStyle();
    }

    private void initStyle() {
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
    }

    private CellStyle footerHeaderCellStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        font.setBold(true);
        cellStyle.setFont(font);

        return cellStyle;
    }

    public void export(HttpServletResponse response,
                       Map<String, Object> data) throws IOException {
        initWorkbook();
        this.sheet = workbook.createSheet("Manage Req Do");
        createHeaderRow(data);
        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportByMcType(HttpServletResponse response,
                               Map<String, Object> data) throws IOException {
        initWorkbook();
        this.sheet = workbook.createSheet("Report By MC Type Color");
        setHeaderMcType(data);
        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void setHeaderMcType(Map<String, Object> data) {
        List<Map<String, Object>> requestDo = (List<Map<String, Object>>) data.get("requestDo");

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("MC Type Color");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(0);

        cell = row.createCell(1);
        cell.setCellValue("MC Type Code");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(1);

        cell = row.createCell(2);
        cell.setCellValue("MC Type Description");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(2);

        cell = row.createCell(3);
        cell.setCellValue("MC Color Code");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(3);

        cell = row.createCell(4);
        cell.setCellValue("MC Color Description");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(4);

        cell = row.createCell(5);
        cell.setCellValue("Distribution Plan");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(5);

        cell = row.createCell(6);
        cell.setCellValue("Request Distribution Open");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(6);


        Row rowQq = sheet.createRow(1);

        Integer colIndex = 6;
        colIndex = setMcTypeHeader(requestDo, rowQq, colIndex, row);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, requestDo.size() + 7));

        Cell cellTotal = rowQq.createCell(colIndex);
        cellTotal.setCellValue("Total");
        cellTotal.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(colIndex);

        colIndex +=1;
        cellTotal = rowQq.createCell(colIndex);
        cellTotal.setCellValue("Selisih");
        cellTotal.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(colIndex);

        colIndex +=1;
        Integer colIndexDo = colIndex;

        colIndex = setMcTypeHeader(requestDo, rowQq, colIndex, row);

        cellTotal = rowQq.createCell(colIndex);
        cellTotal.setCellValue("Total");
        cellTotal.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(colIndex);

        colIndex +=1;
        cellTotal = rowQq.createCell(colIndex);
        cellTotal.setCellValue("Selisih");
        cellTotal.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(colIndex);

        cell = row.createCell(colIndexDo);
        cell.setCellValue("Distribution Open");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.addMergedRegion(new CellRangeAddress(0, 0, colIndexDo, colIndex));

        cell = row.createCell(colIndex+1);
        cell.setCellValue("DO Open Percentage");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(colIndex +1);

        Cell cellQq = rowQq.createCell(colIndex+1);
        cellQq.setCellStyle(footerHeaderCellStyle());

        sheet.addMergedRegion(new CellRangeAddress(0, 1, colIndex+1, colIndex+1));

        for(int i= 0; i<=5; i++) {
            Cell cellHead = rowQq.createCell(i);
            cellHead.setCellStyle(footerHeaderCellStyle());
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
        }

        serMcTypeBodyRow(data);
    }

    private Integer setMcTypeHeader(List<Map<String, Object>> requestDo, Row rowQq, Integer colIndex, Row row) {
        for(Map<String, Object> rowData : requestDo) {
            Cell cellQq = rowQq.createCell(colIndex);
            String shiptoMd = (String) rowData.get("shiptoMd");
            String city = (String) rowData.get("city");

            cellQq.setCellValue(shiptoMd.concat(" - " + city));
            cellQq.setCellStyle(footerHeaderCellStyle());
            sheet.autoSizeColumn(colIndex);

            if(colIndex != 6 && colIndex != requestDo.size() + 6) {
                Cell cell = row.createCell(colIndex);
                cell.setCellStyle(footerHeaderCellStyle());
            }
            colIndex++;
        }
        return colIndex;
    }

    private void serMcTypeBodyRow(Map<String, Object> data) {
        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");
        List<Map<String, Object>> requestDo = (List<Map<String, Object>>) data.get("requestDo");

        Integer rowIndex = 2;
        for(Map<String, Object> rowData: rows) {
            Row row = sheet.createRow(rowIndex);
            Cell cell = row.createCell(0);
            cell.setCellValue((String) rowData.get("mcTypeColor"));

            cell = row.createCell(1);
            cell.setCellValue((String) rowData.get("mcTypeId"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue((String) rowData.get("mcTypeDesc"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue((String) rowData.get("colorCode"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue((String) rowData.get("colorDesc"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellValue((Integer) rowData.get("disPlan"));
            cell.setCellStyle(cellStyle);

            Integer qqIndex = 6;
            qqIndex = setMcTypeRow(requestDo, rowData, row, qqIndex,true);

            Integer reqTotal = (Integer) rowData.get("totalReqDo");
            Integer reqSelisih = (Integer) rowData.get("selisihReqDo");

            cell = row.createCell(qqIndex);
            cell.setCellValue(reqTotal);
            cell.setCellStyle(cellStyle);

            qqIndex++;
            cell = row.createCell(qqIndex);
            cell.setCellValue(reqSelisih);
            cell.setCellStyle(cellStyle);

            qqIndex = setMcTypeRow(requestDo, rowData, row, qqIndex + 1, false);

            reqTotal = (Integer) rowData.get("totalDo");
            reqSelisih = (Integer) rowData.get("selisihDo");
            String percent = (String) rowData.get("percentageDo");

            cell = row.createCell(qqIndex);
            cell.setCellValue(reqTotal);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(qqIndex + 1);
            cell.setCellValue(reqSelisih);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(qqIndex + 2);
            cell.setCellValue(percent);
            cell.setCellStyle(cellStyle);

            rowIndex++;
        }
    }

    private Integer setMcTypeRow(List<Map<String, Object>> requestDo, Map<String, Object> rowData,
                                 Row row, Integer qqIndex, Boolean isDo) {
        Cell cell;
        for(Map<String, Object> qq: requestDo) {
            String shipto = (String) qq.get("shipto");
            String name = !isDo ? "Do" : "ReqDo";

            Integer reqDoOpen = (Integer) rowData.get(shipto.concat(name));

            cell = row.createCell(qqIndex);
            cell.setCellValue(reqDoOpen);
            cell.setCellStyle(cellStyle);

            qqIndex++;
        }
        return qqIndex;
    }

    private void createHeaderRow(Map<String, Object> data) {
        for (int i = 1; i <= 3; i++) {
            String headerName = null;
            String headerValue = null;
            switch (i) {
                case 1:
                    headerName = "Main Dealer";
                    headerValue = (String) data.get("mainDealer");
                    break;
                case 2:
                    headerName = "Document Number";
                    headerValue = (String) data.get("docNumber");
                    break;
                case 3:
                    headerName = "Period";
                    headerValue = (String) data.get("period");
                    break;
                default:
                    break;
            }

            Row row = sheet.createRow(i);
            Cell cell = row.createCell(1);
            cell.setCellValue(headerName);
            cell.setCellStyle(footerHeaderCellStyle());
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue(headerValue);
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(2);
        }

        Row row = sheet.createRow(5);

        Cell cell = row.createCell(1);
        cell.setCellValue("Unit Group");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(1);

        cell = row.createCell(2);
        cell.setCellValue("Dp Color");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(2);

        cell = row.createCell(3);
        cell.setCellValue("MC Type Color");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(3);

        cell = row.createCell(4);
        cell.setCellValue("MC Type Description");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(4);

        cell = row.createCell(5);
        cell.setCellValue("MC Type Code");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(5);

        cell = row.createCell(6);
        cell.setCellValue("Color Description");
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.autoSizeColumn(6);

        cell = row.createCell(7);
        cell.setCellValue("Distribution Plan");
        cell.setCellStyle(footerHeaderCellStyle());
        cell = row.createCell(8);
        cell.setCellStyle(footerHeaderCellStyle());
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 7, 8));

        createDoHeader(data, row);
    }

    private void createDoHeader(Map<String, Object> data, Row row) {
        List<Map<String, Object>> requestDo = (List<Map<String, Object>>) data.get("requestDo");

        Integer lastCol = (requestDo.size() * 2) + 9;
        Cell cell = row.createCell(9);
        cell.setCellValue("Request Do");
        cell.setCellStyle(footerHeaderCellStyle());

        int doIndex = 0;

        Row rowDo = sheet.createRow(6);
        Row rowVin = sheet.createRow(7);

        Cell cellDp = rowDo.createCell(7);
        cellDp.setCellValue("Quantity");
        cellDp.setCellStyle(footerHeaderCellStyle());
        cellDp = rowDo.createCell(8);
        cellDp.setCellStyle(footerHeaderCellStyle());
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 7, 8));

        Cell cellDpVin = rowVin.createCell(7);
        cellDpVin.setCellValue("Vin Old");
        cellDpVin.setCellStyle(footerHeaderCellStyle());

        cellDpVin = rowVin.createCell(8);
        cellDpVin.setCellValue("Vin New");
        cellDpVin.setCellStyle(footerHeaderCellStyle());

        for (int i = 9; i < lastCol; i++) {
            if(i > 9) {
                cell = row.createCell(i);
                cell.setCellStyle(footerHeaderCellStyle());
            }

            Cell cellDo = rowDo.createCell(i);
            cellDo.setCellStyle(footerHeaderCellStyle());
            Cell cellVin = rowVin.createCell(i);
            cellVin.setCellStyle(footerHeaderCellStyle());

            if (i % 2 != 0) {
                String shiptoMd = (String) requestDo.get(doIndex).get("shiptoMd");
                String city = (String)  requestDo.get(doIndex).get("city");
                cellDo.setCellValue(shiptoMd.concat(" - " + city));
                cellVin.setCellValue("Vin Old");

                doIndex++;
                continue;
            }

            cellVin.setCellValue("Vin New");

            if(i == lastCol-1) {
                cell = row.createCell(lastCol);
                cell.setCellStyle(footerHeaderCellStyle());

                cellDo = rowDo.createCell(lastCol);
                cellDo.setCellValue("Total Request DO");
                cellDo.setCellStyle(footerHeaderCellStyle());
                sheet.autoSizeColumn(lastCol);

                cellVin = rowVin.createCell(lastCol);
                cellVin.setCellStyle(footerHeaderCellStyle());

                cell = row.createCell(lastCol + 1);
                cell.setCellValue("Selisih");
                cell.setCellStyle(footerHeaderCellStyle());
                sheet.autoSizeColumn(lastCol + 1);

                cellVin = rowVin.createCell(lastCol+1);
                cellVin.setCellStyle(footerHeaderCellStyle());

                cellDo = rowDo.createCell(lastCol + 1);
                cellDo.setCellStyle(footerHeaderCellStyle());
            }

            sheet.addMergedRegion(new CellRangeAddress(6, 6, i-1, i));
        }

        sheet.addMergedRegion(new CellRangeAddress(5, 5, 9, lastCol));
        sheet.addMergedRegion(new CellRangeAddress(6, 7, lastCol, lastCol));
        sheet.addMergedRegion(new CellRangeAddress(5, 7, lastCol + 1, lastCol + 1));
        addHeadRowMerged(rowDo, rowVin);
        createBodyRow(data, lastCol);
    }

    private void createBodyRow(Map<String, Object> req, Integer lastCol) {
        Integer startRow = 8;
        List<Map<String, Object>> rows = (List<Map<String, Object>>) req.get("rows");
        List<Map<String, Object>> requstDo = (List<Map<String, Object>>) req.get("requestDo");

        for (Map<String, Object> data : rows) {
            Integer vinOld = (Integer) data.get("vinOld");
            Integer vinNew = (Integer) data.get("vinNew");

            Row row = sheet.createRow(startRow);
            Cell cell = row.createCell(1);
            cell.setCellValue((String) data.get("unitGroup"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(vinOld + vinNew);
            cell.setCellStyle(cellStyle);


            cell = row.createCell(3);
            cell.setCellValue((String) data.get("mcTypeColor"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue((String) data.get("marketingDesc"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellValue((String) data.get("colorCode"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue((String) data.get("colorDesc"));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(7);
            cell.setCellValue(vinOld);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(8);
            cell.setCellValue(vinNew);
            cell.setCellStyle(cellStyle);

            Integer qqIndex = 0;

            for(int j = 9; j < lastCol; j++) {
                cell = row.createCell(j);
                String shipto = (String) requstDo.get(qqIndex).get("shipto");

                if (j %2 == 0) {
                    Integer vvin = (Integer) data.get(shipto.concat("VinNew"));
                    cell.setCellValue(vvin == null ? 0 : vvin);
                    cell.setCellStyle(cellStyle);
                    qqIndex++;
                } else {
                    Integer vvin = (Integer) data.get(shipto.concat("VinOld"));
                    cell.setCellValue(vvin == null ? 0 : vvin);
                    cell.setCellValue(vvin);
                    cell.setCellStyle(cellStyle);
                }

                if(j == lastCol - 1) {
                    String cellFirstAddress = (new CellReference(row.getCell(9))).formatAsString();
                    String cellLastAddress = (new CellReference(row.getCell(lastCol - 1))).formatAsString();

                    cell = row.createCell(lastCol);
                    cell.setCellFormula(String.format("SUM(%s:%s)", cellFirstAddress, cellLastAddress));
                    cell.setCellStyle(cellStyle);

                    String cellVinOld = (new CellReference(row.getCell(7))).formatAsString();
                    String cellVinNew = (new CellReference(row.getCell(8))).formatAsString();
                    String cellTotalAddress =  (new CellReference(row.getCell(lastCol))).formatAsString();
                    cell = row.createCell(lastCol + 1);
                    cell.setCellFormula(String.format("SUM(%s:%s) - %s",
                            cellVinOld, cellVinNew, cellTotalAddress));

                    cell.setCellStyle(cellStyle);
                }
            }

            if(startRow == rows.size()-1 + 8) {
                Row footerRow = sheet.createRow(startRow + 1);
                Cell footerCell = footerRow.createCell(1);
                footerCell.setCellValue("Total");
                footerCell.setCellStyle(footerHeaderCellStyle());

                for (int j = 2; j < lastCol; j++) {
                    if(j == 2 || j < 6) {
                        footerCell = footerRow.createCell(j);
                        footerCell.setCellStyle(footerHeaderCellStyle());
                        continue;
                    }

                    Row startRowAddress = sheet.getRow(8);
                    Row endRowAddress = sheet.getRow(startRow);

                    String cellStart = (new CellReference(startRowAddress.getCell(j))).formatAsString();
                    String cellEnd = (new CellReference(endRowAddress.getCell(j))).formatAsString();
                    footerCell = footerRow.createCell(j);
                    footerCell.setCellFormula(String.format("SUM(%s:%s)", cellStart, cellEnd));
                    footerCell.setCellStyle(footerHeaderCellStyle());

                    String cellSelisihStart = null;
                    String cellSelisihEnd = null;

                    if(j == lastCol - 1) {
                        cellStart = (new CellReference(startRowAddress.getCell(lastCol))).formatAsString();
                        cellEnd = (new CellReference(endRowAddress.getCell(lastCol))).formatAsString();
                        footerCell = footerRow.createCell(lastCol);
                        footerCell.setCellFormula(String.format("SUM(%s:%s)", cellStart, cellEnd));
                        footerCell.setCellStyle(footerHeaderCellStyle());

                        cellSelisihStart = (new CellReference(startRowAddress.getCell(lastCol + 1))).formatAsString();
                        cellSelisihEnd = (new CellReference(endRowAddress.getCell(lastCol + 1))).formatAsString();
                        footerCell = footerRow.createCell(lastCol + 1);
                        footerCell.setCellFormula(String.format("SUM(%s:%s)", cellSelisihStart, cellSelisihEnd));
                        footerCell.setCellStyle(footerHeaderCellStyle());
                    }
                }

                sheet.addMergedRegion(new CellRangeAddress(startRow+1, startRow+1, 1, 5));
            }

            startRow++;
        }

        XSSFDataValidationHelper helper = new XSSFDataValidationHelper(sheet);

        XSSFDataValidationConstraint  validationConstraint = (XSSFDataValidationConstraint)
                helper.createNumericConstraint(
                        DataValidationConstraint.ValidationType.INTEGER,
                        DataValidationConstraint.OperatorType.BETWEEN,
                        String.valueOf(0),
                        String.valueOf(Integer.MAX_VALUE)
                );

        CellRangeAddressList addressList = new CellRangeAddressList(
                8, rows.size() + 7, 7, lastCol - 1);

        XSSFDataValidation validation =(XSSFDataValidation)
                helper.createValidation(
                        validationConstraint, addressList);
        validation.createPromptBox("Validasi", "Inputan hanya diperbolehkan angka");
        validation.setSuppressDropDownArrow(false);
        validation.setShowErrorBox(true);
        validation.setShowPromptBox(true);

        sheet.addValidationData(validation);
        sheet.createFreezePane(9, 8);
    }

    private void addHeadRowMerged(Row rowDo, Row rowVin) {
        for(int i = 1; i <= 6; i++) {
            Cell cellDo = rowDo.createCell(i);
            cellDo.setCellStyle(footerHeaderCellStyle());
            Cell cellVin = rowVin.createCell(i);
            cellVin.setCellStyle(footerHeaderCellStyle());

            sheet.addMergedRegion(new CellRangeAddress(5, 7, i, i));
        }
    }
}
