package br.com.xpto.business.impl;


import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.xpto.business.CityBusiness;
import br.com.xpto.model.CityModel;
import br.com.xpto.repository.CityRepository;
import br.com.xpto.resource.exceptions.CsvConvertException;
import br.com.xpto.resource.exceptions.InternalServerErrorException;

@Service
public class CityBusinessImpl implements CityBusiness {
	
	private final CityRepository cityRepository;
	
	
	@Autowired
	public CityBusinessImpl(CityRepository cityRepository) {
		super();
		this.cityRepository = cityRepository;
	}
	
	
	// convert file in list of cities
	public List<CityModel> getCitiesByFile(MultipartFile file, Path citiesFileLocation) {
		List<CityModel> cities = null;
		try (Stream<String> lines = Files
				.lines(citiesFileLocation.resolve(file.getOriginalFilename().trim()).normalize())) {
			cities = lines.skip(1).map(mapToCity).collect(Collectors.toList());
		} catch (Exception e) {
			throw new CsvConvertException(new Object[] {e.getMessage()});
		}
		return cities;
	}

	// converts decimal degrees to radians
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	// map a line of file to a CityModel
	public static Function<String, CityModel> mapToCity = (line) -> {
		String[] c = line.split(",");
		return new CityModel(new Long(c[0]), c[1], c[2], c[3], new BigDecimal(c[4]), new BigDecimal(c[5]), c[6], c[7],
				c[8], c[9]);
	};

	@Override
	public List<CityModel> saveAllCities(List<CityModel> cities) {
		try {
			return cityRepository.saveAll(cities);
		} catch (Exception e) {
			throw new InternalServerErrorException(new Object[] {e.getMessage()});
		}
		
	}
	
}
