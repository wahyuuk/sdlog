package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AhmsdlogHdrShipQqsPk implements Serializable {

    @Column(name = "VDOCNOSHPQQ", nullable = false)
    private String docNumber;

    @Column(name = "VMDCODE", nullable = false)
    private String mdCode;

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
}
