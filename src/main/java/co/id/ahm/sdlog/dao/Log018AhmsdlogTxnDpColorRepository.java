package co.id.ahm.sdlog.dao;

import co.id.ahm.sdlog.model.AhmsdlogTxnDpColor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class Log018AhmsdlogTxnDpColorRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public Log018AhmsdlogTxnDpColorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Map<String, Object> getManageTable(String docNumber, String mdcode, Integer month, Integer year) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery(manageTableQuery())
                .setParameter("docNumber", docNumber)
                .setParameter("mdcode", mdcode)
                .setParameter("month", month)
                .setParameter("year", year)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        Map<String, Object> res = new HashMap<>();

        res.put("rows", query.getResultList());
        res.put("requestDo", new ArrayList<>());

        return res;
    }

    private String manageTableQuery() {
        return "SELECT\n" +
                "       \"GBX\" as unitGroup,\n" +
                "a.NDPCQTY as vinOld,\n" +
                "(\n" +
                "    select NDPCQTY from ahmsdlog_txndpcolors\n" +
                "    where ahmsdlog_txndpcolors.VMDCODE = a.VMDCODE\n" +
                "    and ahmsdlog_txndpcolors.VDOCNO = a.VDOCNO\n" +
                "    and ahmsdlog_txndpcolors.VMCTYPEID = a.VMCTYPEID\n" +
                "    and ahmsdlog_txndpcolors.NYEAR = a.NYEAR\n" +
                "    and ahmsdlog_txndpcolors.NMONTH = a.NMONTH\n" +
                "    and ahmsdlog_txndpcolors.VCOLORID = a.VCOLORID\n" +
                "    and ahmsdlog_txndpcolors.VVIN = 'New'\n" +
                ") as vinNew,\n" +
                "       a.VMCTYPEID as mcTypeId,\n" +
                "       a.VCOLORID as colorCode,\n" +
                "       concat(a.VMCTYPEID, a.VCOLORID) as mcTypeColor\n" +
                "FROM ahmsdlog_txndpcolors a\n" +
                "where a.VDOCNO = :docNumber\n" +
                "and a.VMDCODE = :mdcode\n" +
                "and a.NMONTH = :month\n" +
                "and a.NYEAR = :year\n" +
                "and a.VVIN = 'Old'";
    }
}
