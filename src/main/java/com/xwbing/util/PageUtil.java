package com.xwbing.util;

import java.util.List;

public class PageUtil {
    private Integer pageNo = 1;
    private Integer pageSize = 20;
    private List<?> root;
    private boolean isSuccess = true;

    /**
     * 总页数
     */
    private Integer total;

    /**
     * 总数
     */
    private Integer count;
    /**
     * 错误提示
     */
    private String message;


    public PageUtil() {
    }

    public PageUtil(int pageSize, int pageNo) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageUtil(int pageSize) {
        this.pageNo = 1;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRoot() {
        return root;
    }

    public void setRoot(List<?> root) {
        this.root = root;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
