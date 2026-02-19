package com.study.profile_stack_api.global.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Page<T> {
    // 실제 데이터
    private List<T> content;
    // 현재 페이지 번호
    private int page;
    // 한 페이지당 개수
    private int size;
    // 전체 데이터 수
    private long totalElements;
    // 전체 페이지 수
    private int totalPages;

    public static <T> Page<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil(totalElements / (double) size);
        return new Page<>(content, page, size, totalElements, totalPages);
    }
}