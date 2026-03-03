package com.study.profile_stack_api.global.common;

import java.util.List;

/**
 * 페이징 응답 객체.
 * "목록을 '몇 페이지, 몇 개씩' 나눠서 줄 때 쓰는 틀"이다.
 *
 * 언제 쓰나? → 프로필 목록 조회, 기술 스택 목록 조회처럼 "한 번에 다 주지 않고,
 *             page=0, size=10 이렇게 잘라서 줄 때" 이 Page로 감싸서 보낸다.
 *
 * @param <T> 한 페이지에 들어가는 요소 타입 (예: ProfileResponse, TechStackResponse)
 */
public class Page<T> {

    // ---------- 필드 ----------
    private final List<T> content;       // 실제 데이터 = 이번 페이지에 들어 있는 목록 (예: 프로필 10개)
    private final int pageNumber;        // 현재 페이지 번호 (0이 첫 페이지, 1이 두 번째 페이지...)
    private final int pageSize;          // 페이지 크기 = 한 페이지당 몇 개씩 (예: 10이면 10개씩)
    private final long totalElements;    // 전체 데이터 개수 = DB에 있는 총 개수 (예: 53명이면 53)
    private final int totalPages;        // 전체 페이지 수 = totalElements를 pageSize로 나눈 값 (53/10 → 6페이지)
    private final boolean first;        // 첫 페이지 여부 (pageNumber == 0이면 true)
    private final boolean last;         // 마지막 페이지 여부 (지금 마지막 페이지면 true)

    // ---------- 생성자 ----------
    /**
     * @param content       현재 페이지 데이터 (DB에서 LIMIT, OFFSET으로 가져온 결과)
     * @param pageNumber    클라이언트가 요청한 페이지 번호
     * @param pageSize      클라이언트가 요청한 "한 페이지당 개수"
     * @param totalElements DB에 있는 전체 개수 (COUNT 쿼리로 알아낸 값)
     */
    public Page(List<T> content, int pageNumber, int pageSize, long totalElements) {
        // Page 객체를 만들 때 값 4개를 받겠다는 뜻.
        // 넘겨받은 4개 값 → 이 객체의 필드에 그대로 저장
        // this.필드명 = 이 객체가 가진 필드, 오른쪽 = 방금 받은 값.
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;

        // totalPages = (전체 개수 ÷ 페이지 크기) 올림. 예: 53÷10 → 5.3 → 올림 6
        // pageSize가 0이면 나눗셈 안 함 → 0으로 둠
        // "전체 페이지 수"를 직접 안 받고, 위에서 받은 값으로 계산해서 넣는다.
        this.totalPages = pageSize > 0 ? (int) Math.ceil((double) totalElements / pageSize) : 0;

        // first = "지금 페이지 번호가 0이면 첫 페이지"
        this.first = (pageNumber == 0);

        // last = "전체 페이지가 0이거나, 지금 번호가 (totalPages-1) 이상이면 마지막 페이지"
        this.last = (totalPages == 0 || pageNumber >= totalPages - 1);
    }

    // ---------- 정적 팩토리 메서드 ----------
    // 정적 팩토리 메서드 =  new Page(...) 대신 이름 있는 static 메서드로 객체를 만드는 방식.
    // static = 객체(new Page...)를 만들지 않고, 클래스 이름(Page)으로 바로 부른다.
    // of(...) = "이 값들로 Page 하나 만들어줘" 하고 부르는 짧은 이름. 생성자 대신 씀.
    public static <T> Page<T> of(List<T> content, int pageNumber, int pageSize, long totalElements) {
        return new Page<>(content, pageNumber, pageSize, totalElements);  // 위 생성자 호출 → 새 Page 만들어서 돌려줌
    }

    // ---------- Getter ----------
    // Getter = 필드가 private이라 밖에서 못 보니까, 그 필드 값만 읽을 수 있게 열어 둔 메서드.
    // 이름 규칙: get + 필드 이름 (대문자로 시작).
    // JSON으로 응답 보낼 때, 스프링이 이 getter들을 써서 content, pageNumber... 값을 꺼냄.

    public List<T> getContent() {
        return content;  // "이번 페이지 목록이 뭐야?" → content 돌려줌
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isFirst() {
        return first;  // boolean은 get 대신 is 붙이는 게 관례 (isFirst, isLast)
    }

    public boolean isLast() {
        return last;
    }

    // ---------- 편의 메서드 ----------
    // first, last만 있으면 구할 수 있지만, "다음/이전 있어?" 물어보기 편하게 이름 붙여 둔 것.

    /** 다음 페이지가 있나? (마지막 페이지가 아니면 true) */
    public boolean hasNext() {
        return !last;  // last가 false면 → 아직 마지막 아님 → 다음 페이지 있음 → true
    }

    /** 이전 페이지가 있나? (첫 페이지가 아니면 true) */
    public boolean hasPrevious() {
        return !first;  // first가 false면 → 첫 페이지 아님 → 이전 페이지 있음 → true
    }
}
