package br.com.xpto.resource;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.xpto.business.CityBusiness;
import br.com.xpto.model.CityModel;
import br.com.xpto.model.StateModel;
import br.com.xpto.util.BaseResource;
import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/v1/city")
public class CityResource extends BaseResource{
		
	@Autowired
	private final CityBusiness cityService;
	
	@Autowired
	public CityResource(CityBusiness cityService) {
		this.cityService = cityService;
	}
	
	@RequestMapping(value ="/capitais", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> listCapitais(
			@RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(name = "limit", required = false, defaultValue = "6000") Integer limit){

		Optional<List<CityModel>> capitais;
		
		try {
			// BUSCA TODA A LISTA E DEVOLVE UM OPTIONAL
			capitais = this.cityService.listCapitalOrdem(offset, limit, new CityModel());
		
		}catch(Exception ex) {
			return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			// VERIFICA SE ESTA VAZIO
			checkNotNull(capitais, "Capitais");
			
			// DEVOLVE A RESPOSTA
			return buildResponse(OK, capitais, offset, limit);
		
	}
	
	@RequestMapping(value ="/max_min_uf", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> listMaioresMenoresUF(
			@RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(name = "limit", required = false, defaultValue = "50") Integer limit){

		Optional<List<StateModel>> estados;
		
		try {
			// BUSCA TODA A LISTA E DEVOLVE UM OPTIONAL
			estados = this.cityService.listMaioresMenoresEstados();
		
		}catch(Exception ex) {
			return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			// VERIFICA SE ESTA VAZIO
			checkNotNull(estados, "Estados");
			
			// DEVOLVE A RESPOSTA
			return buildResponse(OK, estados, offset, limit);
		
	}
	
	
}
