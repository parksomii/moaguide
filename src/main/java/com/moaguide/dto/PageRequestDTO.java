package com.moaguide.dto;

import lombok.Data;

@Data
public class PageRequestDTO {
    private int page = 1; // 게시판 페이지번호
    private int size = 10; // 한페이지에 보여줄 글 개수
}
