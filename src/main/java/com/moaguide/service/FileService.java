package com.moaguide.service;

import com.moaguide.domain.pdf.PdfList;
import com.moaguide.domain.pdf.PdfListRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FileService {
    private final PdfListRepository pdfListRepository;

    public void save(String id, String name) throws IOException, DuplicateKeyException {
        pdfListRepository.save(new PdfList(id, name));
    }

    public String getFileName(String id) {
        return pdfListRepository.getReferenceById(id).getName();
    }
}
