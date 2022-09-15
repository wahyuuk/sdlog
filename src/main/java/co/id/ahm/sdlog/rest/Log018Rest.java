package co.id.ahm.sdlog.rest;

import co.id.ahm.sdlog.dao.AhmsdlogHdrMngPldosRepository;
import co.id.ahm.sdlog.dao.Log018AhmsdlogHdrMngqqdosRepository;
import co.id.ahm.sdlog.dao.Log018AhmsdlogHdrMntcQqsRepository;
import co.id.ahm.sdlog.dao.Log018AhmsdlogTxnDpColorRepository;
import co.id.ahm.sdlog.service.Ahmsdlog018ServiceImpl;
import co.id.ahm.sdlog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/dp-color")
    public @ResponseBody ResponseEntity<?> getAllDpColor(
            @RequestParam(name = "no") String no,
            @RequestParam(name = "code") String code
    ) {
        if(hdrMngqqdosRepository.countManageTable(no, code) > 0) {
            return ResponseEntity.ok(hdrMngqqdosRepository.getManageTableData(no, code));
        }

        return ResponseEntity.ok(dpColorRepository.getManageTable(no, code, 4, 2021));
    }

    @PostMapping("/manage-table")
    public @ResponseBody ResponseEntity<?> updateManageTable(@RequestBody List<Log018VoManageTableUpdateRequest> req) {
        return ahmsdlog018Service.saveOrUpdateManageTable(req);
    }

    @PostMapping("/maintain")
    public @ResponseBody ResponseEntity<?> getMaintainTable(@RequestBody Log018VoMaintainTableRequest req) {
        return ResponseEntity.ok(headerMaintainRepository.getManageTable(req));
    }

    @PostMapping("/pembukaan-save")
    public @ResponseBody ResponseEntity<?> pembukaanSave(@RequestBody Log018VoPembukaanDoRequest req) {
        return ahmsdlog018Service.savePembukaan(req);
    }

    @PostMapping("/pembukaan")
    public @ResponseBody ResponseEntity<?> getPembukaan() {
        return ResponseEntity
                .ok(hdrMngPldosRepository
                        .getPlanDo("a", "b", 12, 2022, Arrays.asList("S039", "S063")));
    }

    @GetMapping("/")
    public String manageTable() {
        return "manage-table";
    }

    @PostMapping
    public @ResponseBody ResponseEntity<?>
            updateMaintainTable(@RequestBody List<Log018VoMaintainUpdateTableRequest> req) {

        return null;
    }
}
