package org.team6.coffeebeanery.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageDTO<T> {
    private int currentPageNumber;
    private int pageSize;
    private long totalPages;
    private long totalItems;
    private List<T> items;

    public PageDTO(Page<T> page) {
        this.currentPageNumber = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
        this.items = page.getContent();
    }
}
