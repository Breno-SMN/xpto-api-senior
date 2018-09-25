package br.com.xpto.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.xpto.business.CityBusiness;
import br.com.xpto.business.CsvBusiness;
import br.com.xpto.response.FileResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/v1/city")
public class CityResource {
	
	@Autowired
	private final CityBusiness cityService;
	
	@Autowired
	public CityResource(CityBusiness cityService) {
		this.cityService = cityService;
	}

}
