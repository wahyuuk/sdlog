package co.id.ahm.sdlog.service;

import co.id.ahm.sdlog.dao.Log018AhmsdlogHdrMngqqdosRepository;
import co.id.ahm.sdlog.dao.Log018AhmsdlogTxnDdsRepository;
import co.id.ahm.sdlog.dao.Log018AhmsdlogTxnDpColorRepository;
import co.id.ahm.sdlog.model.*;
import co.id.ahm.sdlog.vo.Log018VoMaintainUpdateRequest;
import co.id.ahm.sdlog.vo.Log018VoManageTableUpdateRequest;
import co.id.ahm.sdlog.vo.Log018VoPembukaanDoRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Ahmsdlog018ServiceImpl {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Log018AhmsdlogHdrMngqqdosRepository hdrMngqqdosRepository;

    @Autowired
    private Log018AhmsdlogTxnDpColorRepository dpColorRepository;

    @Autowired
    private Log018AhmsdlogTxnDdsRepository ddsRepository;

    @Transactional
    public ResponseEntity<?> saveOrUpdateManageTable(List<Log018VoManageTableUpdateRequest> req) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            saveHeaderShiptoQq(req);
            saveDtlShiptoQq(req);
            saveHeaderManageQq(req);
            saveDtlManageQq(req);
        } catch (Exception e) {
            ResponseEntity.badRequest();
        }

        return ResponseEntity.ok("Success");
    }

    private void saveHeaderShiptoQq(List<Log018VoManageTableUpdateRequest> req) {
        req.forEach(d -> {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            AhmsdlogHdrShipQqs data = new AhmsdlogHdrShipQqs();
            AhmsdlogHdrShipQqsPk pk = new AhmsdlogHdrShipQqsPk();

            pk.setDocNumber(d.getDocNumber());
            pk.setMdCode(d.getMdCode());

            data.setId(pk);
            data.setDeviation(d.getDeviation());
            data.setDpColorQty(d.getDpQty());
            data.setMonth(d.getMonth());
            data.setYear(d.getYear());
            data.setDpSumQty(d.getSumDpQty());

            AhmsdlogHdrShipQqs dataTemp = (AhmsdlogHdrShipQqs) session.get(AhmsdlogHdrShipQqs.class, pk);

            if(dataTemp != null) {
                session.update(dataTemp);
            } else {
                session.save(data);
            }

            transaction.commit();
            session.close();
        });
    }

    private void saveDtlShiptoQq(List<Log018VoManageTableUpdateRequest> req) {
        req.forEach(d -> {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            AhmsdlogDtlShipQqs data = new AhmsdlogDtlShipQqs();
            AhmsdlogDtlShipQqsPk pk = new AhmsdlogDtlShipQqsPk();

            pk.setDocNumber(d.getDocNumber());
            pk.setMdCode(d.getMdCode());
            pk.setShipto(d.getShipto());
            pk.setShiptoMdCode(d.getShiptoMd());
            AhmsdlogDtlShipQqs dataTemp = (AhmsdlogDtlShipQqs) session.get(AhmsdlogDtlShipQqs.class, pk);

            data.setId(pk);

            if(dataTemp != null) {
                session.update(dataTemp);
            } else {
                session.save(data);
            }

            transaction.commit();
            session.close();
        });
    }

    private void saveHeaderManageQq(List<Log018VoManageTableUpdateRequest> req) {
        req.forEach(d -> {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            AhmsdlogHdrMngQqDos data = new AhmsdlogHdrMngQqDos();
            AhmsdlogHdrMngQqDosPk pk = new AhmsdlogHdrMngQqDosPk();

            pk.setDocNumber(d.getDocNumber());
            pk.setMdCode(d.getMdCode());
            pk.setColorId(d.getColorId());
            pk.setUnitGroupId(d.getUnitGroupId());
            pk.setMcTypeId(d.getMcTypeId());
            AhmsdlogHdrMngQqDos dataTemp = (AhmsdlogHdrMngQqDos) session.get(AhmsdlogHdrMngQqDos.class, pk);

            if(dataTemp != null) {
                dataTemp.setDoQtyVinNew(d.getHeadVinNew());
                dataTemp.setDoQtyVinOld(d.getHeadVinOld());
                session.update(dataTemp);
            } else {
                data.setId(pk);
                data.setDoQtyVinNew(d.getHeadVinNew());
                data.setDoQtyVinOld(d.getHeadVinOld());
                session.save(data);
            }

            transaction.commit();
            session.close();
        });
    }

    private void saveDtlManageQq(List<Log018VoManageTableUpdateRequest> req) {
        req.forEach(d -> {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            AhmsdlogDtlMngQqDos data = new AhmsdlogDtlMngQqDos();
            AhmsdlogDtlMngQqDosPk pk = new AhmsdlogDtlMngQqDosPk();

            pk.setDocNumber(d.getDocNumber());
            pk.setMdCode(d.getMdCode());
            pk.setColorId(d.getColorId());
            pk.setUnitGroupId(d.getUnitGroupId());
            pk.setMcTypeId(d.getMcTypeId());
            pk.setDocNumberHeader(d.getDocNumber());
            pk.setMdCodeHeader(d.getMdCode());
            pk.setShipto(d.getShipto());
            pk.setShiptoMdCode(d.getShiptoMd());

            AhmsdlogDtlMngQqDos dataTemp = (AhmsdlogDtlMngQqDos) session.get(AhmsdlogDtlMngQqDos.class, pk);

            if(dataTemp != null) {
                dataTemp.setDoVinNew(d.getVinNew());
                dataTemp.setDoVinOld(d.getVinOld());
                session.update(dataTemp);
            } else {
                data.setId(pk);
                data.setDoVinNew(d.getVinNew());
                data.setDoVinOld(d.getVinOld());
                session.save(data);
            }

            transaction.commit();
            session.close();
        });
    }

    @Transactional
    public ResponseEntity<?> savePembukaan(Log018VoPembukaanDoRequest req) throws Exception {
        saveHeaderManagePlDo(req);

        return ResponseEntity.ok("Success");
    }

    private void saveHeaderManagePlDo(Log018VoPembukaanDoRequest req) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dateMaintains = new ArrayList<>();

        for (Map<String, Object> row : req.getRows()) {
            for (Map<String, Object> d : req.getRequestDo()) {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                String shipto = (String) d.get("shipto");
                Date dateMaintain = format.parse((String) row.get("date"));

                AhmsdlogHdrMngPldos data = new AhmsdlogHdrMngPldos();
                AhmsdlogHdrMngPldosPk pk = new AhmsdlogHdrMngPldosPk();

                pk.setDocNumber(req.getDocNumber());
                pk.setMdCode(req.getMdCode());
                pk.setShipto(shipto);
                pk.setShiptoMdCode((String) d.get("mdCode"));
                pk.setDateMaintain(dateMaintain);

                AhmsdlogHdrMngPldos dataTemp = session.get(AhmsdlogHdrMngPldos.class, pk);

                if(dataTemp != null) {
                    dataTemp.setStatus((String) row.get(shipto));
                    session.update(dataTemp);

                    if(!row.get(shipto).equals("T") && dataTemp.getStatus().equals("Y")) {
                        dateMaintains.add(dateMaintain);
                    }
                } else {
                    data.setId(pk);
                    data.setStatus((String) row.get(shipto));
                    session.save(data);
                }

                transaction.commit();
                session.close();

                if(row.get(shipto).equals("Y")) {
                    updateMaintainDataOnUpdatePembukaan(req.getDocNumber(),
                            req.getMdCode(), dateMaintain, pk.getShipto());
                }

            }
        }

        if (!dateMaintains.isEmpty()) {
            deleteManageHeaderAndDetailMaintain(req.getDocNumber(),
                    req.getMdCode(), dateMaintains);
        }
    }

    private void deleteManageHeaderAndDetailMaintain(String documentNumber, String mdCode,
                                                     List<Date> dateMaintains) {

        Date minDate = Collections.min(dateMaintains);

        System.out.println(minDate);
    }

    private void updateMaintainDataOnUpdatePembukaan(String documentNumber, String mdCode,
                                                     Date dateMaintain, String shipto) {

        Map<String, Object> data = hdrMngqqdosRepository.getManageTableData(documentNumber, mdCode);

        if(data == null) {
            data = dpColorRepository.getManageTable(documentNumber, mdCode, dateMaintain.getMonth(),
                    dateMaintain.getYear());
        }

        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");
        String dd = data.get("requestDo").toString();
        List<String> requestDo = Arrays.asList(dd
                .substring(1, dd.length() - 1)
                .replace(" ", "")
                .split(","));

        List<Map<String, Object>> dataRow = new ArrayList<>();
        List<AhmsdlogHdrMntcQqsPk> hdrMntcQqsPks = new ArrayList<>();

        rows.forEach(row -> {

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            AhmsdlogHdrMntcQqs dataMnt = new AhmsdlogHdrMntcQqs();
            AhmsdlogHdrMntcQqsPk pk = new AhmsdlogHdrMntcQqsPk();

            pk.setDocNumber(documentNumber);
            pk.setMdCode(mdCode);
            pk.setMcTypeId((String) row.get("mcTypeId"));
            pk.setColorId((String) row.get("colorCode"));
            pk.setUnitGroupId((Integer) row.get("unitGroup"));
            pk.setDateMaintain(dateMaintain);

            AhmsdlogHdrMntcQqs dataMntTemp = session.get(AhmsdlogHdrMntcQqs.class, pk);

            Map<String, Object> dds = ddsRepository
                    .getDDsVin(documentNumber, mdCode, pk.getMcTypeId(), pk.getColorId(), pk.getDateMaintain());

            if(dataMntTemp == null) {
                dataMnt.setId(pk);
                dataMnt.setActualDo(0);
                dataMnt.setQtyReffMpsVinNew(1);
                dataMnt.setQtyReffMpsVinOld(1);
                dataMnt.setDistPlanVinOld((Integer) dds.get("vinOld"));
                dataMnt.setDistPlanVinNew((Integer) dds.get("vinNew"));
                dataMnt.setPlanDoOpen((Integer) dds.get("vinOld") + (Integer) dds.get("vinNew"));
                dataMnt.setQtyDifferent(0);
                session.save(dataMnt);
            } else {
                dataMntTemp.setActualDo(0);
                dataMntTemp.setQtyReffMpsVinNew(1);
                dataMntTemp.setQtyReffMpsVinOld(1);
                dataMntTemp.setDistPlanVinOld((Integer) dds.get("vinOld"));
                dataMntTemp.setDistPlanVinNew((Integer) dds.get("vinNew"));
                dataMntTemp.setPlanDoOpen((Integer) dds.get("vinOld") + (Integer) dds.get("vinNew"));
                dataMntTemp.setQtyDifferent(0);
                session.update(dataMntTemp);
            }

            transaction.commit();
            session.close();

            saveDetailMaintain(row, requestDo, pk, shipto);
        });
    }

    private void saveDetailMaintain(Map<String, Object> dataHeader, List<String> requestDo,
                                    AhmsdlogHdrMntcQqsPk hdpk,
                                    String shipto) {
        for(String row: requestDo) {
            if(!shipto.equals(row)) {
                continue;
            }

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            AhmsdlogDtlMntcQqs data = new AhmsdlogDtlMntcQqs();
            AhmsdlogDtlMntcQqsPk pk = new AhmsdlogDtlMntcQqsPk();

            pk.setDocNumberHeader(hdpk.getDocNumber());
            pk.setDocNumber(hdpk.getDocNumber());
            pk.setMdCodeHeader(hdpk.getMdCode());
            pk.setShipto(row);
            pk.setShiptoMdCode("G5Z");
            pk.setMdCode(hdpk.getMdCode());
            pk.setUnitGroupId(hdpk.getUnitGroupId());
            pk.setMcTypeId(hdpk.getMcTypeId());
            pk.setColorId(hdpk.getColorId());
            pk.setDateMaintain(hdpk.getDateMaintain());

            data.setQtyRemainHminVinOld((Integer) dataHeader.get(row.concat("VinOld")));
            data.setQtyRemainHminVinNew((Integer) dataHeader.get(row.concat("VinNew")));
            data.setDoVinOld(0);
            data.setDoVinNew(0);
            data.setQtyRemainHVinOld(data.getQtyRemainHminVinOld() - data.getDoVinOld());
            data.setQtyRemainHVinNew(data.getQtyRemainHminVinNew() - data.getDoVinNew());
            data.setId(pk);

            AhmsdlogDtlMntcQqs dataTemp = session.get(AhmsdlogDtlMntcQqs.class, pk);

            if(dataTemp == null) {
                session.save(data);
            } else {
                dataTemp.setDoVinOld(0);
                dataTemp.setDoVinNew(0);
                session.update(dataTemp);
            }

            transaction.commit();
            session.close();
        }
    }

    public void updateMaintainTable(List<Log018VoMaintainUpdateRequest> req) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for (Log018VoMaintainUpdateRequest row: req) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            AhmsdlogDtlMntcQqs data = new AhmsdlogDtlMntcQqs();
            AhmsdlogDtlMntcQqsPk pk = new AhmsdlogDtlMntcQqsPk();
            Date dateMaintain = format.parse(row.getDateMaintain());

            pk.setDocNumber(row.getDocNumber());
            pk.setMdCode(row.getMdCode());
            pk.setMdCodeHeader(row.getMdCode());
            pk.setDateMaintain(dateMaintain);
            pk.setDocNumberHeader(row.getDocNumber());
            pk.setUnitGroupId(row.getUnitGroupId());
            pk.setMcTypeId(row.getMcTypeId());
            pk.setColorId(row.getColorId());
            pk.setShipto(row.getShipto());
            pk.setShiptoMdCode(row.getShiptoMd());
            data.setId(pk);

            data = session.get(AhmsdlogDtlMntcQqs.class, pk);

            data.setDoVinOld(row.getDoVinOld());
            data.setDoVinNew(row.getDoVinNew());
            data.setQtyRemainHminVinOld(row.gethVinOld());
            data.setQtyRemainHminVinNew(row.gethVinNew());
            data.setQtyRemainHVinOld(row.gethVinOld());
            data.setQtyRemainHVinNew(row.gethVinNew());

            session.update(data);
            transaction.commit();
            session.close();
        }
    }
}
