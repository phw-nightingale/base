package xyz.frt.base.util;


import xyz.frt.base.model.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageInfo<T extends BaseEntity> implements Serializable {

    private Integer page = 1;
    private Integer totalPage;
    private Integer limit = 10;
    private Integer totalCount;
    private String orderBy;
    private String sort = "asc";
    private List<T> data = new ArrayList<>();

    public PageInfo(Integer page, Integer totalPage, Integer limit, Integer totalCount, String orderBy, String sort, List<T> data) {
        this.page = page;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.limit = limit;
        this.orderBy = orderBy;
        this.sort = sort;
        this.data = data;
    }

    public PageInfo(Integer page, Integer totalPage, Integer limit, Integer totalCount, String orderBy, List<T> data) {
        this.page = page;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.limit = limit;
        this.orderBy = orderBy;
        this.data = data;
    }

    public PageInfo(Integer page, Integer totalPage, Integer limit, Integer totalCount, List<T> data) {
        this.page = page;
        this.totalPage = totalPage;
        this.limit = limit;
        this.totalCount = totalCount;
        this.data = data;
    }

    public PageInfo(Integer page, List<T> data) {
        this.page = page;
        this.data = data;
    }

    public PageInfo() {

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
