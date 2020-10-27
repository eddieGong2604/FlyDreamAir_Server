package com.csit214.payload;

public class EnquiryPayload {
    private String subject;
    private String content;
    private String reply;
    public EnquiryPayload() {
    }

    public EnquiryPayload(String subject, String content, String reply) {
        this.subject = subject;
        this.content = content;
        this.reply = reply;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
