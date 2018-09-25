package br.com.xpto.business;

import org.springframework.web.multipart.MultipartFile;

import br.com.xpto.response.FileResponse;


public interface CsvBusiness {
	
	 FileResponse storeFile(MultipartFile file);
}
