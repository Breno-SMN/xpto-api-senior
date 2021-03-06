package br.com.xpto.resource;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.xpto.business.CityBusiness;
import br.com.xpto.model.CityModel;
import br.com.xpto.model.DistanceModel;
import br.com.xpto.model.StateModel;
import br.com.xpto.util.BaseResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	@ApiOperation(value ="Metodo para retornar todas capitais ordenadas por nome")
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
	
	@ApiOperation(value ="Metodo para retornar o estado com maior e menor quandidade de cidade e suas respectivas cidades")
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
	
	@ApiOperation(value ="Metodo para retornar a quantidade de cidades por estado")
	@RequestMapping(value ="/cidadesUf", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> listQtdCidadesUf(
			@RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(name = "limit", required = false, defaultValue = "50") Integer limit){

		Optional<List<StateModel>> estados;
		
		try {
			// BUSCA TODA A LISTA E DEVOLVE UM OPTIONAL
			estados = this.cityService.listQtdCidadesUf();
		
		}catch(Exception ex) {
			return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			// VERIFICA SE ESTA VAZIO
			checkNotNull(estados, "Estados");
			
			// DEVOLVE A RESPOSTA
			return buildResponse(OK, estados, offset, limit);
		
	}
	
	@ApiOperation(value ="Metodo para retornar cidades pelo codigo de ibge")
	@RequestMapping(value ="/ibge/{ibge}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> listCityByIbge(@PathVariable Long ibge,
			@RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(name = "limit", required = false, defaultValue = "50") Integer limit){

		Optional<CityModel> city;
		
		try {
			// BUSCA TODA A LISTA E DEVOLVE UM OPTIONAL
			city = this.cityService.findById(ibge);
		
		}catch(Exception ex) {
			return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			// VERIFICA SE ESTA VAZIO
			checkNotNull(city, "Cidade");
			
			// DEVOLVE A RESPOSTA
			return buildResponse(OK, city, offset, limit);
		
	}
	
	@ApiOperation(value ="Metodo para retornar cidades de acordo com o estado")
	@RequestMapping(value ="/estado/{estado}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> listCityByEstado(@PathVariable String estado,
			@RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(name = "limit", required = false, defaultValue = "50") Integer limit){

		Optional<List<CityModel>> cities;
		
		try {
			// BUSCA TODA A LISTA E DEVOLVE UM OPTIONAL
			cities = this.cityService.findByEstado(estado);
		
		}catch(Exception ex) {
			return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			// VERIFICA SE ESTA VAZIO
			checkNotNull(cities, "Cidades");
			
			// DEVOLVE A RESPOSTA
			return buildResponse(OK, cities, offset, limit);
		
	}
	
	@ApiOperation(value ="Metodo para adicionar uma cidade")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> create(@RequestBody @Valid CityModel cityModel) {

			// SOLICITA PARA O Service A INSERÇÃO
			Optional<CityModel> cityModelCreated = this.cityService.create(cityModel);

			return buildResponse(HttpStatus.CREATED, cityModelCreated);
		
	}
	
	@ApiOperation(value ="Metodo para deletar uma cidade")
	@RequestMapping(value ="/{ibge}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable Long ibge) {

			// SOLICITA PARA O Service A INSERÇÃO
			this.cityService.deleteById(ibge);

			return buildResponse(HttpStatus.OK);
		
	}
	
	@ApiOperation(value ="Metodo para retornar dados de acordo com qualquer coluna passada")
	@GetMapping("/genericFilter")
	public @ResponseBody Page<CityModel> listGenericFilter(@RequestParam Map<String, String> filtros,
			@RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(name = "limit", required = false, defaultValue = "50") Integer limit) {
		Page<CityModel> cities;
			cities = cityService.listGenericFilter(filtros, PageRequest.of(offset, limit));
			
		return cities;
	}
	
	@ApiOperation(value ="Metodo para retornar a contagem de registros de acordo com a coluna")
	@GetMapping("/genericFilter/{column}")
	public @ResponseBody ResponseEntity<?> listGenericFilterColumn(@PathVariable String column) {
		
			Optional<CityModel> quantityColumn = cityService.listGenericFilterColumn(column);
			// VERIFICA SE ESTA VAZIO
			checkNotNull(quantityColumn, "Column");
						
			// DEVOLVE A RESPOSTA
			return buildResponse(OK, quantityColumn);

	}
	
	@ApiOperation(value ="Metodo para retornar a contagem de todos registros")
	@GetMapping("/count")
	public @ResponseBody ResponseEntity<?> countAll() {
		
			Optional<Long> quantity = cityService.countAll();
			// VERIFICA SE ESTA VAZIO
			checkNotNull(quantity, "Registros");
						
			// DEVOLVE A RESPOSTA
			return buildResponse(OK, quantity);

	}
	
	@ApiOperation(value ="Metodo para retornar a maior distancia em km entre as cidades da base de dados")
	@GetMapping("/maiorDistancia")
	public @ResponseBody ResponseEntity<?> maxDistancia() {
		
			Optional<DistanceModel> distancia = cityService.recuperaDistancia();
			
			// VERIFICA SE ESTA VAZIO
			checkNotNull(distancia, "Distancia");
						
			// DEVOLVE A RESPOSTA
			return buildResponse(OK, distancia);

	}
	
}
