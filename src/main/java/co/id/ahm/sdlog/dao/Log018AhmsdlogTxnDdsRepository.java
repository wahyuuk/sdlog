package co.id.ahm.sdlog.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class Log018AhmsdlogTxnDdsRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Map<String, Object> getDDsVin(String docNumber, String mdCode, String mcTypeId,
                                         String colorId, LocalDate dateMaintain) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sqlQuery = String.format(getDDsVinQuery(), dateMaintain.getDayOfMonth());
        System.out.println(sqlQuery);

        Query query = session.createNativeQuery(sqlQuery)
                .setParameter("docNum", docNumber)
                .setParameter("mctype", mcTypeId)
                .setParameter("color", colorId)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String, Object>> list = query.getResultList();
        Map<String, Object> res = new HashMap<>();

        for (Map<String, Object> row: list) {
            String vin = (String) row.get("VVIN");
            if(vin.equals("Old")) {
                res.put("vinOld", (Integer) row.get("NDAY"
                        .concat(String.valueOf(dateMaintain.getDayOfMonth()))));
            } else {
                res.put("vinNew", (Integer) row.get("NDAY"
                        .concat(String.valueOf(dateMaintain.getDayOfMonth()))));
            }
        }

        return res;
    }

    private String getDDsVinQuery() {
        return "select distinct NDAY%s, VVIN from ahmsdlog_txnddss where VDOCDPWARNA = :docNum\n" +
                "and VMCTYPEID = :mctype and VCOLORID = :color\n" +
                "order by VMCTYPEID, VCOLORID";
    }
}
