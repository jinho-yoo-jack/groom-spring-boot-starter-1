package com.study.profile_stack_api.global.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Page<T> {
    private final List<T> content; // 현재 페이지 데이터 목록
    private final int page; // 현재 페이지 번호
    private final int size; // 페이지당 데이터 개수
    private final long totalElements; // 전체 데이터 개수
    private final int totalPages; // 전체 페이지 개수
    private final boolean first; // 첫 번째 페이지 여부
    private final boolean last; // 마지막 페이지 여부
    private final boolean hasNext; // 다음 페이지 존재 여부
    private final boolean hasPrevious; // 이전 페이지 존재 여부

    @Builder
    public Page(List<T> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.first = (page == 0);
        this.last = (page >= totalPages - 1) || (totalPages == 0);
        this.hasNext = !this.last;
        this.hasPrevious = !this.first;
    }
}
