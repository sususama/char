package edu.xatu.util;

import java.util.Date;

public class MessageVO {
    private String mesg;
    private Date date;
public MessageVO(){}
    public MessageVO(String mesg, Date date) {
        this.mesg = mesg;
        this.date = date;
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(String mesg) {
        this.mesg = mesg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
