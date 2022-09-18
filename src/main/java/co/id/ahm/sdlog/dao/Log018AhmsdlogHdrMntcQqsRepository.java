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
import java.util.stream.Collectors;

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

        Set<Map<String, Object>> requestDo = new HashSet<>();
        Map<String, Object> dataTemp = new HashMap<>();

        Integer rows = 0;

        for (Map<String, Object> data : list) {
            String unitGroup = (String) data.get("VCODE");
            String mcTypeId = (String) data.get("dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid".toLowerCase());
            String colorCode = (String) data.get("dsdmntqq_rsdmngqq_dsdmct_vcolorid".toLowerCase());
            String shipto = (String) data.get("dsdmntqq_dsdshpqq_msdqq_vshipto".toLowerCase());

            Map<String, Object> qq = new HashMap<>();
            qq.put("shipto", data.get("dsdmntqq_dsdshpqq_msdqq_vshipto"));
            qq.put("mdCode", data.get("VMDCODE"));
            qq.put("city", data.get("VCITY"));
            qq.put("shiptoMd", data.get("SHIPTOMD"));

            requestDo.add(qq);

            if(rows == 0) {
                setMaintainData(dataTemp, data, mcTypeId, colorCode, shipto);
                rows++;
                continue;
            } else if (rows == list.size() - 1) {
                res.add(dataTemp);
            }

            String mcTypeIdTemp = (String) dataTemp.get("mcTypeId");
            String colorCodeTemp = (String) dataTemp.get("colorCode");
            String unitGroupTemp = (String) dataTemp.get("unitGroup");

            if(mcTypeIdTemp.equals(mcTypeIdTemp)
                    && colorCodeTemp.equals(colorCode)
                    && unitGroup.equals(unitGroupTemp)) {

                setMaintainData(dataTemp, data, mcTypeId, colorCode, shipto);

            } else {
                res.add(dataTemp);
                dataTemp = new HashMap<>();

                setMaintainData(dataTemp, data, mcTypeId, colorCode, shipto);
            }

            rows ++;
        }

        session.close();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("rows", res);
        responseData.put("requestDo", requestDo);

        return responseData;
    }

    public List<Date> getDateMaintainList(Log018VoMaintainTableRequest req) {
        String dateMaintainQuery = getDateMaintainQuery();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query queryDates = session.createNativeQuery(dateMaintainQuery)
                .setParameter("docNumber", req.getDocNumber())
                .setParameter("mdcode", req.getMdCode())
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> datesMap = queryDates.getResultList();
        List<Date> dates = datesMap
                .stream()
                .map(d -> {
                    Date date = (Date) d.get("dmntn");

                    return date;
                }).collect(Collectors.toList());
        session.close();

        return dates;
    }

    private void setMaintainData(Map<String, Object> dataTemp, Map<String, Object> data,
                                  String mcTypeId, String colorCode, String shipto) {

        dataTemp.put("unitGroupId", data.get("dsdmntqq_rsdmngqq_msduntgp_nid"));
        dataTemp.put("unitGroup", data.get("VCODE"));
        dataTemp.put("mcTypeId", mcTypeId);
        dataTemp.put("mcTypeColor", mcTypeId + "" + colorCode);
        dataTemp.put("colorCode", colorCode);
        dataTemp.put("marketingDesc", data.get("VUGDESC"));
        dataTemp.put("colorDesc", data.get("COLOR_DESC"));
        dataTemp.put("percentageReff", "10%");
        dataTemp.put("mpsVinOld", getValue(data.get("nrefvinold")));
        dataTemp.put("mpsVinNew", getValue(data.get("nrefvinnew")));
        dataTemp.put("ddsVinOld", getValue(data.get("ndpvinold")));
        dataTemp.put("ddsVinNew", getValue(data.get("ndpvinnew")));
        dataTemp.put("actualDoOpen", getValue(data.get("ndoactl".toLowerCase())));

        dataTemp.put(shipto.concat("VinOld"), getValue(data.get("NDOPLNOLD".toLowerCase())));
        dataTemp.put(shipto.concat("VinNew"), getValue(data.get("NDOPLNNEW".toLowerCase())));
        dataTemp.put(shipto.concat("HMinVinOld"), getValue(data.get("nrmngh1old".toLowerCase())));
        dataTemp.put(shipto.concat("HMinVinNew"), getValue(data.get("nrmngh1new".toLowerCase())));
        dataTemp.put(shipto.concat("HVinOld"), getValue(data.get("nrmngold".toLowerCase())));
        dataTemp.put(shipto.concat("HVinNew"), getValue(data.get("nrmngnew".toLowerCase())));
    }

    private Integer getValue(Object value) {
        return value == null ? 0 : (Integer) value;
    }

    private String getManageTableQuery() {
        return "SELECT G.VUGDESC, (" +
                "        SELECT VCOLORDESC FROM AHMSDLOG_DTLMCTYPES WHERE VCOLORID = " +
                "       B.dsdmntqq_rsdmngqq_dsdmct_vcolorid LIMIT 1\n" +
                "    ) AS COLOR_DESC," +
                "G.VCODE,  Q.VMDCODE, Q.VCITY, CONCAT(Q.VSHIPTO, Q.VMDCODE) AS SHIPTOMD, A.ndpvinold, A.ndpvinnew, A.ndoactl, A.nrefvinold, A.nrefvinnew, B.* FROM\n" +
                " ahmsdlog_hdrmntcqqs A, ahmsdlog_dtlmntcqqs B, ahmsdlog_mstqqs Q, ahmsdlog_mstunitgrps G\n" +
                "WHERE B.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = A.rsdmngqq_rsdshpqq_vdocnoshpqq\n" +
                "  AND B.dsdmntqq_dsdshpqq_rsdshpqq_vdocnoshpqq = A.rsdmngqq_rsdshpqq_vdocnoshpqq\n" +
                "  AND B.dsdmntqq_dsdshpqq_rsdshpqq_vmdcode = A.rsdmngqq_rsdshpqq_vmdcode\n" +
                "  AND B.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid = A.rsdmngqq_dsdmct_rsdmct_vmctypeid\n" +
                "  AND B.dsdmntqq_rsdmngqq_dsdmct_vcolorid = A.rsdmngqq_dsdmct_vcolorid\n" +
                "  AND B.dsdmntqq_rsdmngqq_msduntgp_nid = A.rsdmngqq_msduntgp_nid\n" +
                "  AND B.dmntn = A.dmntn\n" +
                "  AND Q.VSHIPTO = B.dsdmntqq_dsdshpqq_msdqq_vshipto\n" +
                "  AND Q.VMDCODE = B.dsdmntqq_dsdshpqq_msdqq_vmdcode\n" +
                "  AND G.NID = B.dsdmntqq_rsdmngqq_msduntgp_nid\n" +
                "  AND B.dmntn = :date\n" +
                "  AND B.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "  AND B.dsdmntqq_dsdshpqq_rsdshpqq_vmdcode = :mdcode\n" +
                "ORDER BY B.dmntn, B.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid, B.dsdmntqq_rsdmngqq_dsdmct_vcolorid";
    }

    private String getDateMaintainQuery() {
        return "select distinct dmntn\n" +
                "from AHMSDLOG_DTLMNTCQQS where\n" +
                "DSDMNTQQ_DSDSHPQQ_RSDSHPQQ_VDOCNOSHPQQ= :docNumber\n" +
                "and DSDMNTQQ_DSDSHPQQ_RSDSHPQQ_VMDCODE= :mdcode\n" +
                "order by dmntn";
    }
}
