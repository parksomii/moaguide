package com.moaguide.refactor.article.service;

import com.moaguide.refactor.article.entity.PdfList;
import com.moaguide.refactor.article.repository.PdfListRepository;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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
