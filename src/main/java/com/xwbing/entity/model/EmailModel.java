package com.xwbing.entity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * 说明:邮箱属性
 * 创建日期: 2016年12月7日 下午1:10:00
 * 作者: xiangwb
 */
public class EmailModel implements Serializable {
    private static final long serialVersionUID = -4511919091646089877L;

    public EmailModel() {
        //默认值
    }

    /**
     * 发送邮件协议名称，默认smtp
     */
    @SerializedName(value = "protocol")
    private String protocol;
    /**
     * 发送邮箱地址
     */
    @SerializedName(value = "fromEmail")
    private String fromEmail;
    /**
     * 邮箱密码
     */
    @SerializedName(value = "password")
    private String password;
    /**
     * 接收邮箱
     */
    @SerializedName(value = "toEmail")
    private String toEmail;
    /**
     * 设置发送时间，默认是即时发送
     */
    @SerializedName(value = "sendTime")
    private Date sendTime;
    /**
     * 发送邮件服务器主机
     */
    @SerializedName(value = "serverHost")
    private String serverHost;
    /**
     * 发送邮件服务器端口
     */
    @SerializedName(value = "serverPort")
    private Integer serverPort;
    /**
     * 是否需要身份验证
     */
    @SerializedName(value = "auth")
    private boolean auth;

    /**
     * 邮件主题
     */
    @SerializedName(value = "subject")
    private String subject;
    /**
     * 邮件内容
     */
    @SerializedName(value = "centent")
    private String centent;
    /**
     * 邮件附件的文件名
     */
    @SerializedName(value = "fromEmail")
    private String[] attachFileNames;

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public Date getSendTime() {
        if (sendTime == null)
            sendTime = new Date();
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCentent() {
        return centent;
    }

    public void setCentent(String centent) {
        this.centent = centent;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }

    public String getProtocol() {
        if (protocol == null || "".equals(protocol.trim()))
            this.protocol = "smtp";
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}
