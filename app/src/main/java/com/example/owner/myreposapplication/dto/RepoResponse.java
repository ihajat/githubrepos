package com.example.owner.myreposapplication.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class RepoResponse {
    final private int page;
    final private int limit;

    @Expose
    final private List<RepoDto> list;

    public RepoResponse(int page, int limit, List<RepoDto> list) {
        this.page = page;
        this.limit = limit;
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public List<RepoDto> getList() {
        return list;
    }
}
