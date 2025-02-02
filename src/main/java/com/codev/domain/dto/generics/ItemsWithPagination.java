package com.codev.domain.dto.generics;

import com.codev.utils.Pagination;

import lombok.Data;

@Data
public class ItemsWithPagination<T> {
    T items;
    Pagination pagination;

    public ItemsWithPagination(T items, Pagination pagination) {
        this.items = items;
        this.pagination = pagination;
    }
}
