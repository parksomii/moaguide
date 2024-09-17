package com.moaguide.service.content;

import com.moaguide.domain.detail.ContentDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class ContentService {
    private final ContentDetailRepository contentRepository;


}
