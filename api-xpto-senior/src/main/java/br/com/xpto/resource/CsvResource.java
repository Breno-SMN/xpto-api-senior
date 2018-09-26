package br.com.xpto.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.xpto.business.CsvBusiness;
import br.com.xpto.model.FileModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/v1/csv")
public class CsvResource {
	
	@Autowired
	private final CsvBusiness csvService;
	
	@Autowired
	public CsvResource(CsvBusiness csvService) {
		this.csvService = csvService;
	}
	
	//END POINT PARA REALIZAR A INSERÇÃO DO ARQUIVO CSV
	@ApiOperation(value = "Fazer leitura de um arquivo cvs e salvar em um banco em memoria")
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam(name = "file", required = true) MultipartFile file) {
	
		return new ResponseEntity<FileModel>(csvService.storeFile(file), HttpStatus.ACCEPTED);
	}
	
}
