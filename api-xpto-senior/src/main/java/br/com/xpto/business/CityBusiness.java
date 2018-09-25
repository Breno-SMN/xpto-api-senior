package br.com.xpto.business;

import java.nio.file.Path;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.xpto.model.CityModel;

public interface CityBusiness {

	List<CityModel> getCitiesByFile(MultipartFile file, Path citiesFileLocation);

	List<CityModel> saveAllCities(List<CityModel> cities);
	
}