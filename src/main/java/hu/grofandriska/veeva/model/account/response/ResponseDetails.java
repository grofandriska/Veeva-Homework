package hu.grofandriska.veeva.model.account.response;

import lombok.Data;

@Data
public class ResponseDetails {

    private int pagesize;
    private int pageoffset;
    private int size;
    private int total;

}
