package com.moaguide.dto.NewDto;

import com.moaguide.domain.study.Roadmap;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class RoadmapResponseDto {

    private List<Roadmap> roadmaps;
    private int page;
    private int size;
    private int total;

    public RoadmapResponseDto(Page<Roadmap> roadmaps) {
        this.roadmaps = roadmaps.getContent();
        this.page = roadmaps.getNumber();
        this.size = roadmaps.getSize();
        this.total = roadmaps.getTotalPages();
    }

}
