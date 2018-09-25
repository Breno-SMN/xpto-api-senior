package br.com.xpto.business;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import br.com.xpto.model.CityModel;
import br.com.xpto.model.StateModel;
import br.com.xpto.util.GenericBusiness;

public interface CityBusiness extends GenericBusiness<CityModel, Long>{

	List<CityModel> getCitiesByFile(MultipartFile file, Path citiesFileLocation);

	List<CityModel> saveAllCities(List<CityModel> cities);
	
}