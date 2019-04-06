package com.appsaga.opac1;

import java.io.Serializable;

public class Copies implements Serializable {

    String accession;
    String code;
    String reserved;
    String status;
    String type;
    String issued_by;

    public Copies(String accession, String code, String reserved, String status, String type,String issued_by) {
        this.accession = accession;
        this.code = code;
        this.reserved = reserved;
        this.status = status;
        this.type = type;
        this.issued_by=issued_by;
    }

    public Copies()
    {

    }

    public String getIssued_by() {
        return issued_by;
    }

    public void setIssued_by(String issued_by) {
        this.issued_by = issued_by;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
