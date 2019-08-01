package com.github.bjlhx15.common.http.webserver.controller;

public class Result {

    private String code;
    private String id;
    private String msg;
    private String message;
    private Person person;
    private Integer size;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", message='" + message + '\'' +
                ", person=" + person +
                ", size=" + size +
                '}';
    }
}
