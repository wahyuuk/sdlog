package co.id.ahm.sdlog.dao;

import co.id.ahm.sdlog.model.AhmsdlogDtlMntcQqs;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Log018AhmsdlogDtlMntcQqsRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Map<String, Object> getReportByMcTypeColor(String docNumber, String mdCode) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sqlQuery = reportByMcTypeColorQuery();
        Query query = session.createNativeQuery(sqlQuery)
                .setParameter("docNumber", docNumber)
                .setParameter("mdcode", mdCode)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);


        List<Map<String, Object>> list = query.getResultList();
        Map<String, Object> response = new HashMap<>();

        Set<Map<String, Object>> qqList = new HashSet<>();
        List<Map<String, Object>> rows = new ArrayList<>();

        Map<String, Object> dataTemp = new HashMap<>();

        Integer rowIndex = 0;
        for (Map<String, Object> data : list) {
            String mcTypeId = (String) data.get("MCTYPEID");
            String colorCode = (String) data.get("COLORCODE");
            String mcTypeDesc = (String) data.get("VUGDESC");
            String colorDesc = (String) data.get("COLOR_DESC");
            String shipto = (String) data.get("QQ");

            Map<String, Object> qq = new HashMap<>();
            qq.put("shipto", data.get("QQ"));
            qq.put("mdCode", data.get("VMDCODE"));
            qq.put("city", data.get("VCITY"));
            qq.put("shiptoMd", data.get("SHIPTOMD"));
            qq.put("isReqular", data.get("VFLAGREG"));

            qqList.add(qq);

            if(rowIndex == 0) {
                setReportByMcTypeData(dataTemp, data, mcTypeId, colorCode, mcTypeDesc, colorDesc, shipto);

                rowIndex++;

                continue;
            } else if(rowIndex == list.size() - 1) {
                rows.add(dataTemp);
            }

            String mcTypeIdTemp = (String) dataTemp.get("mcTypeId");
            String colorCodeTemp = (String) dataTemp.get("colorCode");

            if(mcTypeIdTemp.equals(mcTypeId) && colorCodeTemp.equals(colorCode)) {
                setReportByMcTypeData(dataTemp, data, mcTypeId, colorCode, mcTypeDesc, colorDesc, shipto);
            } else {
                rows.add(dataTemp);

                dataTemp = new HashMap<>();

                setReportByMcTypeData(dataTemp, data, mcTypeId, colorCode, mcTypeDesc, colorDesc, shipto);
            }

            rowIndex++;
        }

        response.put("rows", rows);
        response.put("requestDo", qqList);

        return response;
    }

    private void setReportByMcTypeData(Map<String, Object> dataTemp, Map<String, Object> data,
                                       String mcTypeId, String colorCode,
                                       String mcTypeDesc, String colorDesc, String shipto) {
        dataTemp.put("mcTypeColor", mcTypeId.concat(colorCode));
        dataTemp.put("mcTypeId", mcTypeId);
        dataTemp.put("mcTypeDesc", mcTypeDesc);
        dataTemp.put("colorCode", colorCode);
        dataTemp.put("colorDesc", colorDesc);
        dataTemp.put("distPlan", ((BigDecimal) data.get("DIST_PLAN")).intValue());

        Integer mngVinOld = (Integer) data.get("MNG_VIN_OLD");
        Integer mngVinNew = (Integer) data.get("MNG_VIN_NEW");
        Integer mntVinOld = (Integer) data.get("MNT_VIN_OLD");
        Integer mntVinNew = (Integer) data.get("MNT_VIN_NEW");

        dataTemp.put(shipto.concat("requestDo"), mngVinOld + mngVinNew);
        dataTemp.put(shipto.concat("Do"), mntVinOld + mntVinNew);
    }

    private String reportByMcTypeColorQuery() {
        return "SELECT\n" +
                "DISTINCT A.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid AS MC_TYPE,\n" +
                "Q.VFLAGREG,\n" +
                "Q.VCITY,\n" +
                "Q.VMDCODE, CONCAT(Q.VSHIPTO, Q.VMDCODE) AS SHIPTOMD,\n" +
                "A.dsdmntqq_dsdshpqq_msdqq_vshipto AS QQ,\n" +
                "A.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid AS MCTYPEID,\n" +
                "A.dsdmntqq_rsdmngqq_dsdmct_vcolorid AS COLORCODE,\n" +
                "(\n" +
                "    SELECT SUM(M.ndovinold+M.ndovinnew) FROM ahmsdlog_hdrmngqqdos M\n" +
                "    WHERE M.rsdshpqq_vdocnoshpqq = A.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq\n" +
                "    AND M.rsdshpqq_vmdcode = A.dsdmntqq_rsdmngqq_rsdshpqq_vmdcode\n" +
                "    AND M.dsdmct_rsdmct_vmctypeid = A.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid\n" +
                "    AND M.dsdmct_vcolorid = A.dsdmntqq_rsdmngqq_dsdmct_vcolorid\n" +
                "    AND M.msduntgp_nid = A.dsdmntqq_rsdmngqq_msduntgp_nid\n" +
                ") AS DIST_PLAN,\n" +
                "H.ndovinold AS MNG_VIN_OLD,\n" +
                "H.ndovinnew AS MNG_VIN_NEW,\n" +
                "A.ndoplnold AS MNT_VIN_OLD,\n" +
                "A.ndoplnnew AS MNT_VIN_NEW, G.VUGDESC,\n" +
                "(\n" +
                "    SELECT VCOLORDESC FROM AHMSDLOG_DTLMCTYPES WHERE VCOLORID =\n" +
                "    A.dsdmntqq_rsdmngqq_dsdmct_vcolorid LIMIT 1\n" +
                ") AS COLOR_DESC\n" +
                "FROM ahmsdlog_dtlmntcqqs A,\n" +
                "     ahmsdlog_dtlmngqqdos H,\n" +
                "     ahmsdlog_mstqqs Q,\n" +
                "     ahmsdlog_mstunitgrps G\n" +
                "WHERE\n" +
                "H.rsdmngqq_rsdshpqq_vdocnoshpqq = A.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq\n" +
                "AND H.rsdmngqq_rsdshpqq_vmdcode = A.dsdmntqq_rsdmngqq_rsdshpqq_vmdcode\n" +
                "AND H.rsdmngqq_dsdmct_rsdmct_vmctypeid = A.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid\n" +
                "AND H.rsdmngqq_dsdmct_vcolorid = A.dsdmntqq_rsdmngqq_dsdmct_vcolorid\n" +
                "AND H.rsdmngqq_msduntgp_nid = A.dsdmntqq_rsdmngqq_msduntgp_nid\n" +
                "AND H.dsdshpqq_msdqq_vshipto = A.dsdmntqq_dsdshpqq_msdqq_vshipto\n" +
                "AND H.dsdshpqq_msdqq_vmdcode = A.dsdmntqq_dsdshpqq_msdqq_vmdcode\n" +
                "AND Q.VSHIPTO = A.dsdmntqq_dsdshpqq_msdqq_vshipto\n" +
                "AND Q.VMDCODE = A.dsdmntqq_dsdshpqq_msdqq_vmdcode\n" +
                "AND G.NID = A.dsdmntqq_rsdmngqq_msduntgp_nid\n" +
                "AND A.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "AND A.dsdmntqq_rsdmngqq_rsdshpqq_vmdcode = :mdcode\n" +
                "ORDER BY A.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid,\n" +
                "         A.dsdmntqq_rsdmngqq_dsdmct_vcolorid";
    }

    public Map<String, Object> reportByDate(String docNumber, String mdCode) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sqlQuery = getReportByDateQuery();

        Query query = session.createNativeQuery(sqlQuery)
                .setParameter("docNumber", docNumber)
                .setParameter("mdcode", mdCode)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> resList = query.getResultList();
        Map<String, Object> response = new HashMap<>();
        Set<Map<String, Object>> qqList = new HashSet<>();

        List<Map<String, Object>> rows = new ArrayList<>();
        Integer totalReqDoOpen = 0;

        Map<String, Object> dataTemp = new HashMap<>();

        Integer row = 0;
        for (Map<String, Object> data: resList) {
            String shipto = (String) data.get("SHIPTO");
            String vmdCode = (String) data.get("VMDCODE");
            String city = (String) data.get("VCITY");
            String shiptoMd = (String) data.get("SHIPTOMD");
            Date dateMaintain = (Date) data.get("DMNTN");
            DayOfWeek dayOfWeek = DayOfWeek.of(dateMaintain.getDay() + 1);

            Map<String, Object> qq = new HashMap<>();
            qq.put("shipto", shipto);
            qq.put("shiptoMd", shiptoMd);
            qq.put("mdCode", vmdCode);
            qq.put("city", city);
            qq.put("isRegular", data.get("VFLAGREG"));

            qqList.add(qq);

            if(row == 0) {
                dataTemp.put("dateMaintain", dateMaintain);
                dataTemp.put("day", dayOfWeek);
                dataTemp.put(shipto, ((BigDecimal) data.get("TOTAL_DO")).intValue());
                totalReqDoOpen = ((BigDecimal) data.get("TOTAL_REQ_DO")).intValue();

                row++;
                continue;
            } else if(row == resList.size() - 1) {
                rows.add(dataTemp);
            }

            Date dateMaintainTemp = (Date) dataTemp.get("dateMaintain");

            if(dateMaintainTemp.equals(dateMaintain)) {
                dataTemp.put("dateMaintain", dateMaintain);
                dataTemp.put("day", dayOfWeek);
                dataTemp.put(shipto, ((BigDecimal) data.get("TOTAL_DO")).intValue());
            } else {
                rows.add(dataTemp);

                dataTemp = new HashMap<>();

                dataTemp.put("dateMaintain", dateMaintain);
                dataTemp.put("day", dayOfWeek);
                dataTemp.put(shipto, ((BigDecimal) data.get("TOTAL_DO")).intValue());
            }

            row++;
        }

        response.put("rows", rows);
        response.put("requestDo", qqList);
        response.put("totalReqDo", totalReqDoOpen);

        return response;
    }

    private List<Map<String, Object>> getQQList(String docNumber, String mdCode) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sqlQuery = getMaintainListQuery();

        Query query = session.createNativeQuery(sqlQuery)
                .setParameter("docNumber", docNumber)
                .setParameter("mdcode", mdCode)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.getResultList();

        session.close();

        return list;
    }

    private String getMaintainListQuery() {
        return "SELECT DISTINCT dsdmntqq_dsdshpqq_msdqq_vshipto AS shipto\n" +
                "FROM ahmsdlog_dtlmntcqqs A\n" +
                "WHERE A.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "  and A.dsdmntqq_dsdshpqq_msdqq_vmdcode = :mdcode";
    }

    private String getReportByDateQuery() {
        return "SELECT DISTINCT A.DMNTN,\n" +
                "(\n" +
                "    SELECT DISTINCT dsdmntqq_dsdshpqq_msdqq_vshipto AS SHIPTO\n" +
                "        FROM ahmsdlog_dtlmntcqqs B\n" +
                "    WHERE B.dmntn = A.dmntn\n" +
                "    AND B.dsdmntqq_dsdshpqq_msdqq_vshipto = A.dsdmntqq_dsdshpqq_msdqq_vshipto\n" +
                "    AND B.dsdmntqq_dsdshpqq_msdqq_vmdcode = A.dsdmntqq_dsdshpqq_msdqq_vmdcode\n" +
                ") AS SHIPTO,\n" +
                "Q.VFLAGREG,\n" +
                "Q.VCITY,\n" +
                "Q.VMDCODE,\n" +
                "CONCAT(Q.VSHIPTO, Q.VMDCODE) AS SHIPTOMD,\n" +
                "(\n" +
                "    SELECT SUM(C.ndoplnold + C.ndoplnnew) FROM ahmsdlog_dtlmntcqqs C\n" +
                "    WHERE C.dmntn = A.dmntn\n" +
                "    AND C.dsdmntqq_dsdshpqq_msdqq_vshipto = A.dsdmntqq_dsdshpqq_msdqq_vshipto\n" +
                "    AND C.dsdmntqq_dsdshpqq_msdqq_vmdcode = A.dsdmntqq_dsdshpqq_msdqq_vmdcode\n" +
                ") AS TOTAL_DO,\n" +
                "(\n" +
                "    SELECT SUM(D.ndovinold+D.ndovinnew)\n" +
                "    FROM ahmsdlog_dtlmngqqdos D\n" +
                "    WHERE D.rsdmngqq_rsdshpqq_vdocnoshpqq = A.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq\n" +
                "    AND D.rsdmngqq_rsdshpqq_vmdcode = A.dsdmntqq_dsdshpqq_msdqq_vmdcode\n" +
                ") AS TOTAL_REQ_DO\n" +
                "FROM ahmsdlog_dtlmntcqqs A,\n" +
                "     ahmsdlog_mstqqs Q\n" +
                "WHERE A.dsdmntqq_dsdshpqq_msdqq_vshipto = Q.VSHIPTO\n" +
                "AND A.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "and a.dsdmntqq_dsdshpqq_msdqq_vmdcode = :mdcode\n" +
                "ORDER BY A.dmntn";
    }

    public List<AhmsdlogDtlMntcQqs> getMaintainList(String docNumber, String mdCode) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT D FROM AhmsdlogDtlMntcQqs D WHERE D.id.docNumberHeader = :docNumber " +
                        "AND D.id.mdCodeHeader = :mdcode"
                    ).setParameter("docNumber", docNumber)
                    .setParameter("mdcode", mdCode);

        List<AhmsdlogDtlMntcQqs> res = query.getResultList();

        return res;
    }

    public List<AhmsdlogDtlMntcQqs> getMaintainForUpdate(String documentNumber, String mdCode, Date startdate,
                                                         Date endDate, String mcTypeId, String colorId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT D FROM AhmsdlogDtlMntcQqs D WHERE D.id.dateMaintain BETWEEN :startDate AND :endDate AND D.id.docNumber = :docNumber AND D.id.mdCode = :mdCode AND D.id.mcTypeId = :mcTypeId AND D.id.colorId = :color"
        ).setParameter("docNumber", documentNumber)
                .setParameter("startDate", startdate)
                .setParameter("endDate", endDate)
                .setParameter("mdCode", mdCode)
                .setParameter("mcTypeId", mcTypeId)
                .setParameter("color", colorId);

        List<AhmsdlogDtlMntcQqs> res = query.getResultList();

        return res;
    }

    public List<Map<String, Object>> getAllMcTypeByDocNumber(String docNumber, String mdCode) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery("SELECT DISTINCT A.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid, A.dsdmntqq_rsdmngqq_dsdmct_vcolorid, G.VUGDESC\n" +
                        "FROM ahmsdlog_dtlmntcqqs A\n" +
                        "INNER JOIN ahmsdlog_mstunitgrps G\n" +
                        "ON G.NID = A.dsdmntqq_rsdmngqq_msduntgp_nid " +
                        "WHERE dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = :docNum AND dsdmntqq_rsdmngqq_rsdshpqq_vmdcode = :mdcode")
                .setParameter("docNum", docNumber)
                .setParameter("mdcode", mdCode)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.getResultList();

        return list.stream()
                .map(r ->  {
                    Map<String, Object> data = new HashMap<>();

                    data.put("mcType", (String) r.get("dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid"));
                    data.put("colorId", (String) r.get("dsdmntqq_rsdmngqq_dsdmct_vcolorid"));
                    data.put("desc", (String) r.get("VUGDESC"));

                    return data;
                }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getMps(String mcType, String colorId, Integer month, Integer year) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery("SELECT * FROM ahmsdlog_txnfixplans WHERE DSDMCT_RSDMCT_VMCTYPEID = :mcType AND DSDMCT_VCOLORID = :colorId AND NPRODMONTH = :month AND NPRODYEAR = :year")
                .setParameter("mcType", mcType)
                .setParameter("colorId", colorId)
                .setParameter("month", month)
                .setParameter("year", year)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.getResultList();

        return list;
    }

    public List<Map<String, Object>> getDDs(String mcType, String colorId, String docNumber) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery("SELECT * FROM ahmsdlog_txnddss WHERE VMCTYPEID = :mcType AND VCOLORID = :colorId AND VDOCDPWARNA = :docNum")
                .setParameter("mcType", mcType)
                .setParameter("colorId", colorId)
                .setParameter("docNum", docNumber)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.getResultList();

        return list;
    }

    public Double getSumDo(String docNumber, String mdCode, Date startDate, Date endDate, String mcType, String color) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery(
                    "SELECT\n" +
                            "coalesce(SUM(A.ndoplnold + A.ndoplnnew) ,0) AS TOTAL\n" +
                            "FROM ahmsdlog_dtlmntcqqs A\n" +
                            "INNER JOIN ahmsdlog_hdrmngpldos P\n" +
                            "ON P.rsdshpqq_vdocnoshpqq = A.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq\n" +
                            "AND P.msdqq_vmdcode = A.dsdmntqq_rsdmngqq_rsdshpqq_vmdcode\n" +
                            "AND P.dmntn = A.dmntn\n" +
                            "AND P.msdqq_vshipto = A.dsdmntqq_dsdshpqq_msdqq_vshipto\n" +
                            "AND P.msdqq_vmdcode = A.dsdmntqq_dsdshpqq_msdqq_vmdcode\n" +
                            "where A.dsdmntqq_rsdmngqq_rsdshpqq_vmdcode = :mdcode\n" +
                            "  AND A.dsdmntqq_rsdmngqq_rsdshpqq_vdocnoshpqq = :docNum\n" +
                            "AND A.dsdmntqq_rsdmngqq_dsdmct_rsdmct_vmctypeid = :mcType\n" +
                            "  AND A.dsdmntqq_rsdmngqq_dsdmct_vcolorid = :color\n" +
                            "  AND P.vstatshpqq = 'Y'\n" +
                            "AND A.dmntn BETWEEN :startDate AND :endDate"
                )
                .setParameter("docNum", docNumber)
                .setParameter("mdcode", mdCode)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("mcType", mcType)
                .setParameter("color", color);

        Double res = ((BigDecimal) query.getSingleResult()).doubleValue();

        return res;
    }

    public Double getSumPerMD(String docNumber, String mdCode) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery(
                        "select SUM(ndovinold + ndovinnew) AS TOTAL from ahmsdlog_dtlmngqqdos\n" +
                                "WHERE rsdmngqq_rsdshpqq_vdocnoshpqq = :docNum\n" +
                                "AND rsdmngqq_rsdshpqq_vmdcode = :mdcode"
                )
                .setParameter("docNum", docNumber)
                .setParameter("mdcode", mdCode);

        Double res = ((BigDecimal) query.getSingleResult()).doubleValue();

        return res;
    }
}
