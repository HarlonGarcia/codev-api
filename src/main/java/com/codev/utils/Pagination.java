package com.codev.utils;

import lombok.Data;

@Data
public class Pagination {
    Integer page;
    Integer size;
    Long total;

    public Pagination() {
    }

    public Pagination(Long total) {
        this.total = total;
    }

    public Pagination(Integer page, Integer size, Long total) {
        this.page = page;
        this.size = size;
        this.total = total;
    }
}
