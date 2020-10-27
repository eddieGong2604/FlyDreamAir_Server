package com.csit214.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
@Entity
@Table(name = "Enquiry")
public class Enquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enquiryId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties("enquiries")
    private FrequentFlyerAccount from;
    private String subject;
    private String content;
    @Nullable
    private String reply;
    private boolean status;

    public Enquiry() {
    }

    public Enquiry(FrequentFlyerAccount from, String subject, String content, @Nullable String reply, boolean status) {
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.reply = reply;
        this.status = status;
    }

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
    }

    public FrequentFlyerAccount getFrom() {
        return from;
    }

    public void setFrom(FrequentFlyerAccount from) {
        this.from = from;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
