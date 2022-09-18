package co.id.ahm.sdlog.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

@Repository
public class Log018AhmsdlogHdrMngqqdosRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Map<String, Object> getManageTableData(String docNumber, String mdcode) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sqlQuery = manageTableQuery();

        Query query = session.createNativeQuery(sqlQuery)
                .setParameter("docNumber", docNumber)
                .setParameter("mdcode", mdcode)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> listData = query.getResultList();
        List<Map<String, Object>> resData = new ArrayList<>();

        Map<String, Object> dataTemp = new HashMap<>();
        Set<Map<String, Object>> listOfqq = new HashSet<>();

        Integer rows = 0;
        for (Map<String, Object> data: listData){

            Object unitGroup = data.get("VCODE");
            Object mcTypeId = data.get("RSDMNGQQ_DSDMCT_RSDMCT_VMCTYPEID".toLowerCase());
            Object colorCode =  data.get("RSDMNGQQ_DSDMCT_VCOLORID".toLowerCase());
            String shipto = (String) data.get("DSDSHPQQ_MSDQQ_VSHIPTO".toLowerCase());

            Map<String, Object> qq = new HashMap<>();
            qq.put("shipto", data.get("VSHIPTO"));
            qq.put("mdCode", data.get("VMDCODE"));
            qq.put("city", data.get("VCITY"));
            qq.put("shiptoMd", data.get("SHIPTOMD"));

            listOfqq.add(qq);

            if(rows == 0) {
                setManageData(dataTemp, data, unitGroup, mcTypeId, colorCode, shipto);
                rows++;
                continue;
            } else if (rows == listData.size() - 1) {
                resData.add(dataTemp);
            }

            Object mcTypeIdTemp = dataTemp.get("mcTypeId");
            Object colorCodeTemp =  dataTemp.get("colorCode");
            Object unitGroupTemp =  dataTemp.get("unitGroup");

            if(Objects.equals(mcTypeIdTemp, mcTypeIdTemp)
                    && Objects.equals(colorCodeTemp, colorCode)
                    && Objects.equals(unitGroup, unitGroupTemp)) {

                dataTemp.put(shipto.concat("VinOld"), getValue(data.get("NDOVINOLD".toLowerCase())));
                dataTemp.put(shipto.concat("VinNew"), getValue(data.get("NDOVINNEW".toLowerCase())));

            } else {
                resData.add(dataTemp);
                dataTemp = new HashMap<>();

                setManageData(dataTemp, data, unitGroup, mcTypeId, colorCode, shipto);
            }

            rows ++;
        }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("rows", resData);
        responseData.put("requestDo", listOfqq);

        session.close();

        return responseData;
    }

    private Integer getValue(Object value) {
        return value == null ? 0 : (Integer) value;
    }

    private void setManageData(Map<String, Object> dataTemp, Map<String, Object> data,
                               Object unitGroup, Object mcTypeId, Object colorCode, String shipto) {

        dataTemp.put("unitGroup", unitGroup);
        dataTemp.put("unitGroupId", data.get("RSDMNGQQ_MSDUNTGP_NID".toLowerCase()));
        dataTemp.put("mcTypeId", mcTypeId);
        dataTemp.put("mcTypeColor", mcTypeId + "" + colorCode);
        dataTemp.put("colorCode", colorCode);
        dataTemp.put("marketingDesc", data.get("marketingDesc"));
        dataTemp.put("colorDesc", data.get("colorDesc"));
        dataTemp.put("vinOld", getValue(data.get("vin_old")));
        dataTemp.put("vinNew", getValue(data.get("vin_new")));

        dataTemp.put(shipto.concat("VinOld"), getValue(data.get("NDOVINOLD".toLowerCase())));
        dataTemp.put(shipto.concat("VinNew"), getValue(data.get("NDOVINNEW".toLowerCase())));
    }

    private String manageTableQuery() {
        return "select q.VSHIPTO, q.VMDCODE, q.VCITY, concat(q.VSHIPTO, q.VMDCODE) as SHIPTOMD,\n" +
                "       q.VFLAGREG,\n" +
                "       b.*, a.NDOVINOLD as vin_old, a.NDOVINNEW as vin_new, g.VUGDESC as marketingDesc,g.VCODE,\n" +
                "(\n" +
                "    SELECT VCOLORDESC FROM AHMSDLOG_DTLMCTYPES WHERE VCOLORID = b.rsdmngqq_dsdmct_vcolorid LIMIT 1\n" +
                ") as colorDesc\n" +
                "from ahmsdlog_hdrmngqqdos a, ahmsdlog_dtlmngqqdos b,\n" +
                "     ahmsdlog_mstunitgrps g, ahmsdlog_mstqqs q\n" +
                "where a.RSDSHPQQ_VDOCNOSHPQQ = b.RSDMNGQQ_RSDSHPQQ_VDOCNOSHPQQ\n" +
                "  and  a.RSDSHPQQ_VMDCODE = b.RSDMNGQQ_RSDSHPQQ_VMDCODE\n" +
                "  and a.DSDMCT_RSDMCT_VMCTYPEID = b.RSDMNGQQ_DSDMCT_RSDMCT_VMCTYPEID\n" +
                "  and a.MSDUNTGP_NID = b.RSDMNGQQ_MSDUNTGP_NID\n" +
                "  and a.DSDMCT_VCOLORID = b.RSDMNGQQ_DSDMCT_VCOLORID\n" +
                "  and a.msduntgp_nid = g.NID\n" +
                "  and q.VSHIPTO = b.dsdshpqq_msdqq_vshipto\n" +
                "  and q.VMDCODE = b.dsdshpqq_msdqq_vmdcode\n" +
                "  and b.RSDMNGQQ_RSDSHPQQ_VDOCNOSHPQQ = :docNumber\n" +
                "  and b.RSDMNGQQ_RSDSHPQQ_VMDCODE = :mdcode\n" +
                "order by b.rsdmngqq_msduntgp_nid, b.rsdmngqq_dsdmct_rsdmct_vmctypeid, b.rsdmngqq_dsdmct_vcolorid";
    }

    public Integer countManageTable(String docNumber, String mdCode) {
        String sqlQuery = "select count(*)\n" +
                            "from ahmsdlog_hdrmngqqdos a, ahmsdlog_dtlmngqqdos b\n" +
                            "where a.RSDSHPQQ_VDOCNOSHPQQ = b.RSDMNGQQ_RSDSHPQQ_VDOCNOSHPQQ\n" +
                            "and  a.RSDSHPQQ_VMDCODE = b.RSDMNGQQ_RSDSHPQQ_VMDCODE\n" +
                            "and a.DSDMCT_RSDMCT_VMCTYPEID = b.RSDMNGQQ_DSDMCT_RSDMCT_VMCTYPEID\n" +
                            "and a.MSDUNTGP_NID = b.RSDMNGQQ_MSDUNTGP_NID\n" +
                            "and a.DSDMCT_VCOLORID = b.RSDMNGQQ_DSDMCT_VCOLORID\n" +
                            "and b.RSDMNGQQ_RSDSHPQQ_VDOCNOSHPQQ = :docNumber\n" +
                            "and b.RSDMNGQQ_RSDSHPQQ_VMDCODE = :mdcode";

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery(sqlQuery);
        query.setParameter("docNumber", docNumber);
        query.setParameter("mdcode", mdCode);

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private String getHeaderManageForMaintainQuery(String docNumber, String mdCode) {
        return "";
    }
}
