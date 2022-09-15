package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AhmsdlogDtlShipQqsPk implements Serializable {

    @Column(name = "RSDSHPQQ_VDOCNOSHPQQ", nullable = false)
    private String docNumber;

    @Column(name = "RSDSHPQQ_VMDCODE", nullable = false)
    private String mdCode;

    @Column(name = "MSDQQ_VSHIPTO", nullable = false)
    private String shipto;

    @Column(name = "MSDQQ_VMDCODE", nullable = false)
    private String shiptoMdCode;

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getMdCode() {
        return mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getShipto() {
        return shipto;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public String getShiptoMdCode() {
        return shiptoMdCode;
    }

    public void setShiptoMdCode(String shiptoMdCode) {
        this.shiptoMdCode = shiptoMdCode;
    }
}
