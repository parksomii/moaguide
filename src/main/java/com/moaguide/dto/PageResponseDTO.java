package com.moaguide.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@ToString
public class PageResponseDTO<T> {
    private List<T> list;
    private PageRequestDTO pageRequestDTO; // 페이지 요청 정보 (page, size)
    private Long totalCount;  // 전체 글의 개수
    private int startPage, endPage; // 화면상 페이지 시작 번호, 페이지 끝 번호
    private List<Integer> pageNumList; // 화면에 뿌려줄 페이지번호들
    private boolean prev, next; // 이전 페이지, 다음페이지 여부
    private int totalPage;
    private boolean lastPage; // 마지막페이지인지 여부
    private int currentPage; // 현재 페이지
    private int size; // 페이지당 글 개수

    public PageResponseDTO(List<T> list, PageRequestDTO pageRequestDTO, Long totalCount) {
        this.list = list;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = totalCount;
        this.currentPage = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.endPage = (int)Math.ceil((double)pageRequestDTO.getPage() / pageRequestDTO.getSize())
                * pageRequestDTO.getSize();
        this.startPage = endPage - (pageRequestDTO.getSize() - 1);

        this.totalPage = (int)Math.ceil((double) this.totalCount / pageRequestDTO.getSize());
        if(this.totalPage < this.endPage) {
            this.endPage = this.totalPage;
        }
        this.prev = this.startPage > 1;
        this.next = this.endPage < totalPage;

        this.pageNumList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());

        this.lastPage = pageRequestDTO.getPage() == totalPage;
    }
}
