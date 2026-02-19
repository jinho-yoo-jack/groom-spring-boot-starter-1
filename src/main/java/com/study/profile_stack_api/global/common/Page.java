package com.study.profile_stack_api.global.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

/**
 * 페이징 응답 객체 (제네릭)
 *
 * @param <T> 페이지에 담길 데이터의 타입
 */
@Getter
@JsonPropertyOrder({
        "content", "page", "size",
        "totalElements", "totalPages",
        "first", "last",
        "hasNext", "hasPrevious"
})
public class Page<T> {
    private final List<T> content;      // 현재 페이지의 데이터 목록
    private final int page;             // 현재 페이지 번호 (0-based)
    private final int size;             // 페이지당 데이터 개수
    private final long totalElements;   // 전체 데이터 개수
    private final int totalPages;       // 전체 페이지 수
    private final boolean first;        // 첫 번째 페이지 여부
    private final boolean last;         // 마지막 페이지 여부
    private final boolean hasNext;      // 다음 페이지 존재 여부
    private final boolean hasPrevious;  // 이전 페이지 존재 여부

    public Page(List<T> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;

        // 전체 페이지 수 계산
        // (총 데이터 수 + 페이지 크기 - 1) / 페이지 크기 -> 올림 나눗셈
        this.totalPages = (int) Math.ceil((double) totalElements / size);

        // 페이지 위치 정보 계산
        this.first = (page == 0);
        this.last = (page >= totalPages - 1) || (totalPages == 0);
        this.hasNext = !this.last;
        this.hasPrevious = !this.first;
    }
}
