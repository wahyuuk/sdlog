package co.id.ahm.sdlog.rest;

import co.id.ahm.sdlog.dao.*;
import co.id.ahm.sdlog.service.Ahmsdlog018ServiceImpl;
import co.id.ahm.sdlog.service.Log018GenerateExcelService;
import co.id.ahm.sdlog.service.Log018ReadExcelService;
import co.id.ahm.sdlog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class Log018Rest {

    @Autowired
    private Log018AhmsdlogTxnDpColorRepository dpColorRepository;

    @Autowired
    private Log018AhmsdlogHdrMngqqdosRepository hdrMngqqdosRepository;

    @Autowired
    private Ahmsdlog018ServiceImpl ahmsdlog018Service;

    @Autowired
    private AhmsdlogHdrMngPldosRepository hdrMngPldosRepository;

    @Autowired
    private Log018AhmsdlogHdrMntcQqsRepository headerMaintainRepository;

    @Autowired
    private Log018GenerateExcelService generateExcelService;

    @Autowired
    private Log018ReadExcelService readExcelService;

    @Autowired
    private Log018AhmsdlogDtlMntcQqsRepository dtlMntcQqsRepository;

    @GetMapping("/dp-color")
    public @ResponseBody
    ResponseEntity<?> getAllDpColor(
            @RequestParam(name = "no") String no,
            @RequestParam(name = "code") String code
    ) {
        if (hdrMngqqdosRepository.countManageTable(no, code) > 0) {
            return ResponseEntity.ok(hdrMngqqdosRepository.getManageTableData(no, code));
        }

        return ResponseEntity.ok(dpColorRepository.getManageTable(no, code, 4, 2021));
    }

    @PostMapping("/manage-table")
    public @ResponseBody
    ResponseEntity<?> updateManageTable(@RequestBody List<Log018VoManageTableUpdateRequest> req) {
        return ahmsdlog018Service.saveOrUpdateManageTable(req);
    }

    @PostMapping("/maintain")
    public @ResponseBody
    ResponseEntity<?> getMaintainTable(@RequestBody Log018VoMaintainTableRequest req) {
        return ResponseEntity.ok(headerMaintainRepository.getMaintainTable(req));
    }

    @PostMapping("/pembukaan-save")
    public @ResponseBody
    ResponseEntity<?> pembukaanSave(@RequestBody Log018VoPembukaanDoRequest req) throws Exception {
        return ahmsdlog018Service.savePembukaan(req);
    }

    @PostMapping("/pembukaan")
    public @ResponseBody
    ResponseEntity<?> getPembukaan() {
        return ResponseEntity
                .ok(hdrMngPldosRepository
                        .getPlanDo("DPC-202212001", "G5Z", 12, 2022, Arrays.asList("S039", "S063")));
    }

    @GetMapping("/")
    public String manageTable() {
        return "manage-table";
    }

    @PostMapping("/maintain-update")
    public @ResponseBody
    ResponseEntity<?>
    updateMaintainTable(@RequestBody List<Log018VoMaintainUpdateRequest> req) throws ParseException {

        ahmsdlog018Service.updateMaintainTable(req);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/get-date-maintain")
    public @ResponseBody
    ResponseEntity<?> getDateMaintainList(
            @RequestBody Log018VoMaintainTableRequest req) {

        return ResponseEntity.ok(headerMaintainRepository.getDateMaintainList(req));
    }

    @PostMapping("/upload")
    public @ResponseBody ResponseEntity<?> upload(
            @RequestParam(name = "file", required = true) MultipartFile file) throws IOException {
        return ResponseEntity.ok(readExcelService.readExcelFile(file));
    }

    @PostMapping("/downloadexcelshiptoqqlist")
    public void generateExcel(@RequestBody Map<String, Object> req,
                              HttpServletResponse res) throws IOException {

        res.setContentType("application/octet-stream");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dt = sdf.format(new Date());

        String header = "Content-Disposition";
        String headerValue = "attachment; filename=TEMPLATE_SHIPTO_QQ_" + dt + ".xlsx";

        res.setHeader(header, headerValue);

        generateExcelService.export(res, req);
    }

    @PostMapping("/report-mctype")
    public @ResponseBody ResponseEntity<?> getReportByMcType() {
        return ResponseEntity.ok(dtlMntcQqsRepository.getReportByMcTypeColor("DPC-202212001", "G5Z"));
    }

    @PostMapping("/generate-excel-mctype")
    public void generateExcelMcType(@RequestBody Map<String, Object> req,
                              HttpServletResponse res) throws IOException {

        res.setContentType("application/octet-stream");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dt = sdf.format(new Date());

        String header = "Content-Disposition";
        String headerValue = "attachment; filename=REPORT_BY_MC_TYPE" + dt + ".xlsx";

        res.setHeader(header, headerValue);

        generateExcelService.exportByMcType(res, req);
    }

    @PostMapping("/report-by-date")
    public @ResponseBody ResponseEntity<?> getByDate() {
        return ResponseEntity.ok(dtlMntcQqsRepository.reportByDate("DPC-202212001", "G5Z"));
    }

    @PostMapping("/submit-maintain")
    public @ResponseBody ResponseEntity<?> submitMaintain(@RequestBody Log018VoHistoryRequest req) {


        return ResponseEntity.ok(ahmsdlog018Service.submitMaintain(req));
    }

    @PostMapping("/history")
    public @ResponseBody ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(ahmsdlog018Service.historyRes("DPC-202212001", "G5Z", 12, 2022));
    }
}
