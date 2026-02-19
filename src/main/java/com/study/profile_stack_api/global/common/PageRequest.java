package com.study.profile_stack_api.global.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {
    private static final int MAX_SIZE = 100;

    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}
