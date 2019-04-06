package com.appsaga.opac1;

import java.io.Serializable;

public class Copies implements Serializable {

    String accession;
    String code;
    String reserved;
    String status;
    String type;

    public Copies(String accession, String code, String reserved, String status, String type) {
        this.accession = accession;
        this.code = code;
        this.reserved = reserved;
        this.status = status;
        this.type = type;
    }

    public Copies()
    {

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
