package co.id.ahm.sdlog.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class Log018ReadExcelService {

    private Workbook workbook;
    private Sheet sheet;
    private Map<String, Object> res;

    private void initWorkbook(MultipartFile file) throws IOException {
        this.workbook = new XSSFWorkbook(file.getInputStream());
        this.sheet = workbook.getSheetAt(0);
        this.res = new HashMap<>();
    }

    public Map<String, Object> readExcelFile(MultipartFile file) throws IOException {
        initWorkbook(file);
        setRes();

        return this.res;
    }

    private void setRes() {
        DataFormatter dataFormatter = new DataFormatter();

        List<String> listOfQq = new ArrayList<>();
        Row rowQq = sheet.getRow(6);

        for (int i = 9; i < rowQq.getLastCellNum() - 2; i++) {
            Cell cellQq = rowQq.getCell(i);

            if(dataFormatter.formatCellValue(cellQq).isEmpty()) {
                continue;
            }

            String qq = dataFormatter.formatCellValue(cellQq);
            listOfQq.add(qq);
        }

        Integer firstRow = 8;
        Integer lastRow = sheet.getLastRowNum();
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> dataRowTemp = new HashMap<>();

        Set<Map<String, Object>> qqRes = new HashSet<>();

        for (int i = firstRow; i < lastRow ; i++) {
            Row row = sheet.getRow(i);
            Cell cellHead = row.getCell(1);
            String unitGroup = dataFormatter.formatCellValue(cellHead);
            dataRowTemp.put("unitGroup", unitGroup.split("-")[1].trim());
            dataRowTemp.put("unitGroupId",
                    Integer.parseInt(unitGroup.split("-")[0].trim()));

            cellHead = row.getCell(3);
            String mcTypeColor = dataFormatter.formatCellValue(cellHead);
            dataRowTemp.put("mcTypeColor", mcTypeColor);

            cellHead = row.getCell(4);
            dataRowTemp.put("marketingDesc", dataFormatter.formatCellValue(cellHead));

            cellHead = row.getCell(5);
            String colorCode = dataFormatter.formatCellValue(cellHead);
            dataRowTemp.put("colorCode", colorCode);
            dataRowTemp.put("mcTypeId", mcTypeColor.substring(0, mcTypeColor.length() - colorCode.length()));

            cellHead = row.getCell(6);
            dataRowTemp.put("colorDesc", dataFormatter.formatCellValue(cellHead));

            cellHead = row.getCell(7);
            dataRowTemp.put("vinOld", Integer.parseInt(dataFormatter.formatCellValue(cellHead)));

            cellHead = row.getCell(8);
            dataRowTemp.put("vinNew", Integer.parseInt(dataFormatter.formatCellValue(cellHead)));

            Integer qqIndex = 0;
            for (int j = 9; j < row.getLastCellNum() - 2; j++) {
                Cell cell = row.getCell(j);

                String[] qq = listOfQq.get(qqIndex).split("-");
                String shipto = qq[0].trim()
                        .substring(0, qq[0].trim().length()-3);

                String mdCode = qq[0].trim()
                        .substring(qq[0].trim().length() - 3);

                String city = qq[1].trim();

                Map<String, Object> qqMap = new HashMap<>();
                qqMap.put("shipto", shipto);
                qqMap.put("mdCode", mdCode);
                qqMap.put("city", city);
                qqMap.put("shiptoMd", shipto.concat(mdCode));

                qqRes.add(qqMap);

                String vin = dataFormatter.formatCellValue(cell);
                Integer vinValue = 0;

                if(!vin.isEmpty()) {
                    vinValue = Integer.parseInt(vin);
                }

                if(j %2 != 0) {
                    dataRowTemp.put(shipto.concat("VinOld"), vinValue);
                } else {
                    dataRowTemp.put(shipto.concat("VinNew"), vinValue);
                    qqIndex++;
                }

                if(j == row.getLastCellNum() - 3) {
                    rows.add(dataRowTemp);

                    dataRowTemp = new HashMap<>();
                }
            }
        }

        this.res.put("rows", rows);
        this.res.put("requestDo", qqRes);
    }
}
