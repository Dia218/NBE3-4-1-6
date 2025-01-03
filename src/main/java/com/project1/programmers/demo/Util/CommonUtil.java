package com.project1.programmers.demo.Util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class CommonUtil {
    public static <T> Page<T> convertListToPage(List<T> list, int page) {
        int itemsPerPage = 5;
        int totalItems = list.size();
        int totalPages = (int) Math.ceil((double)list.size() / itemsPerPage);
        int start = page * itemsPerPage;
        int end = Math.min((start+itemsPerPage), totalItems);

        List<T> pagedItems = list.subList(start, end);
        return new PageImpl<>(pagedItems, PageRequest.of(page, itemsPerPage), totalItems);
    }
}
