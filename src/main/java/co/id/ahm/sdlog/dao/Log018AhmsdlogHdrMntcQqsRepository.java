package co.id.ahm.sdlog.dao;

import co.id.ahm.sdlog.vo.Log018VoMaintainTableRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.*;

@Repository
public class Log018AhmsdlogHdrMntcQqsRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Map<String, Object> getManageTable(Log018VoMaintainTableRequest req) {
        String sqlQuery = getManageTableQuery();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery(sqlQuery)
                .setParameter("date", req.getDateMaintain())
                .setParameter("docNumber", req.getDocNumber())
                .setParameter("mdcode", req.getMdCode())
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.getResultList();

        List<Map<String, Object>> res = new ArrayList<>();

        Set<String> requestDo = new HashSet<>();
        Map<String, Object> dataTemp = new HashMap<>();

        Integer rows = 0;

        for (Map<String, Object> data : list) {
            Object unitGroup = data.get("dsdmntqq_rsdmngqq_msduntgp_nid".toLowerCase());
            Object mcTypeId = data.get("dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid".toLowerCase());
            Object colorCode =  data.get("dsdmntqq_rsdmngqq_dsdmct_vcolorid".toLowerCase());
            String shipto = (String) data.get("dsdmntqq_dsdshpqq_msdqq_vshipto".toLowerCase());

            requestDo.add(shipto);

            if(rows == 0) {
                setMaintainData(dataTemp, data, unitGroup, mcTypeId, colorCode, shipto);
                rows++;
                continue;
            } else if (rows == list.size() - 1) {
                res.add(dataTemp);
            }

            Object mcTypeIdTemp = dataTemp.get("mcTypeId");
            Object colorCodeTemp =  dataTemp.get("colorCode");
            Object unitGroupTemp =  dataTemp.get("unitGroup");

            if(Objects.equals(mcTypeIdTemp, mcTypeIdTemp)
                    && Objects.equals(colorCodeTemp, colorCode)
                    && Objects.equals(unitGroup, unitGroupTemp)) {

                setMaintainData(dataTemp, data, unitGroup, mcTypeId, colorCode, shipto);

            } else {
                res.add(dataTemp);
                dataTemp = new HashMap<>();

                setMaintainData(dataTemp, data, unitGroup, mcTypeId, colorCode, shipto);
            }

            rows ++;
        }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("rows", res);
        responseData.put("requestDo", requestDo);

        return responseData;
    }

    private void setMaintainData(Map<String, Object> dataTemp, Map<String, Object> data,
                                 Object unitGroup, Object mcTypeId, Object colorCode, String shipto) {

        dataTemp.put("unitGroup", unitGroup);
        dataTemp.put("mcTypeId", mcTypeId);
        dataTemp.put("mcTypeColor", mcTypeId + "" + colorCode);
        dataTemp.put("colorCode", colorCode);
        dataTemp.put("marketingDesc", "Revo Fit");
        dataTemp.put("colorDesc", "Black Blue");
        dataTemp.put("percentageReff", "10%");
        dataTemp.put("mpsVinOld", getValue(data.get("nrefvinold")));
        dataTemp.put("mpsVinNew", getValue(data.get("nrefvinnew")));
        dataTemp.put("ddsVinOld", getValue(data.get("ndpvinold")));
        dataTemp.put("ddsVinNew", getValue(data.get("ndpvinnew")));
        dataTemp.put("actualDoOpen", getValue(data.get("ndoactl".toLowerCase())));

        dataTemp.put(shipto.concat("VinOld"), getValue(data.get("NDOVINOLD".toLowerCase())));
        dataTemp.put(shipto.concat("VinNew"), getValue(data.get("NDOVINNEW".toLowerCase())));
        dataTemp.put(shipto.concat("HMinVinOld"), getValue(data.get("nrmngh1old".toLowerCase())));
        dataTemp.put(shipto.concat("HMinVinNew"), getValue(data.get("nrmngh1new".toLowerCase())));
        dataTemp.put(shipto.concat("HVinOld"), getValue(data.get("nrmngold".toLowerCase())));
        dataTemp.put(shipto.concat("HVinNew"), getValue(data.get("nrmngnew".toLowerCase())));
    }

    private Integer getValue(Object value) {
        return value == null ? 0 : (Integer) value;
    }

    private String getManageTableQuery() {
        return "SELECT A.ndpvinold, A.ndpvinnew, A.ndoactl, A.nrefvinold, A.nrefvinnew, B.* FROM\n" +
                "ahmsdlog_hdrmntcqqs A, ahmsdlog_dtlmntcqqs B\n" +
                "WHERE B.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = A.rsdmngqq_rsdshpqq_vdocnoshpqq\n" +
                "AND B.dsdmntqq_dsdshpqq_rsdshpqq_vdocnoshpqq = A.rsdmngqq_rsdshpqq_vdocnoshpqq\n" +
                "AND B.dsdmntqq_dsdshpqq_rsdshpqq_vmdcode = A.rsdmngqq_rsdshpqq_vmdcode\n" +
                "AND B.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid = A.rsdmngqq_dsdmct_rsdmct_vmctypeid\n" +
                "AND B.dsdmntqq_rsdmngqq_dsdmct_vcolorid = A.rsdmngqq_dsdmct_vcolorid\n" +
                "AND B.dsdmntqq_rsdmngqq_msduntgp_nid = A.rsdmngqq_msduntgp_nid\n" +
                "AND B.dmntn = A.dmntn\n" +
                "AND B.dmntn = :date\n" +
                "AND B.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "AND B.dsdmntqq_dsdshpqq_rsdshpqq_vmdcode = :mdcode\n" +
                "ORDER BY B.dmntn, B.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid, B.dsdmntqq_rsdmngqq_dsdmct_vcolorid";
    }
}
