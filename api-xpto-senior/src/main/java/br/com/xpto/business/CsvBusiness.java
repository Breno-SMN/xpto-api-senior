package br.com.xpto.business;

import org.springframework.web.multipart.MultipartFile;

import br.com.xpto.model.FileModel;


public interface CsvBusiness {
	
	 FileModel storeFile(MultipartFile file);
}
