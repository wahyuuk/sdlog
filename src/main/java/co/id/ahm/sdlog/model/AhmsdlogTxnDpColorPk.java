package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class AhmsdlogTxnDpColorPk extends DefaultEntity implements Serializable {

    @Column(name = "VDOCNO", nullable = false)
    private String documentNumber;

    @Column(name = "DUPLOAD", nullable = false)
    private LocalDate dateUpload;

    @Column(name = "VUPLOAD", nullable = false)
    private String uploadBy;

    @Column(name = "NMONTH", nullable = false)
    private Integer month;

    @Column(name = "NYEAR", nullable = false)
    private Integer year;

    @Column(name = "VMDCODE", nullable = false)
    private String mdCode;

    @Column(name = "VMCTYPEID", nullable = false)
    private String mcTypeId;

    @Column(name = "VCOLORID", nullable = false)
    private String colorId;

    @Column(name = "VVIN", nullable = false)
    private String vin;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDate getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(LocalDate dateUpload) {
        this.dateUpload = dateUpload;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMdCode() {
        return mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getMcTypeId() {
        return mcTypeId;
    }

    public void setMcTypeId(String mcTypeId) {
        this.mcTypeId = mcTypeId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
