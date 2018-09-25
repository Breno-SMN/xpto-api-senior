package br.com.xpto.business.impl;


import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import br.com.xpto.business.CityBusiness;
import br.com.xpto.model.CityModel;
import br.com.xpto.model.StateModel;
import br.com.xpto.repository.CityRepository;
import br.com.xpto.resource.exceptions.CityException;
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
	
	
	// Metodo que converte o arquivo em lista de cidades
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
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public static Function<String, CityModel> mapToCity = (linha) -> {
		String[] c = linha.split(",");
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


	@Override
	public Optional<List<CityModel>> list(Integer page, Integer size, CityModel entity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<CityModel> create(CityModel entity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<CityModel> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<CityModel> update(Long id, CityModel entity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Optional<List<CityModel>> listCapitalOrdem(Integer offset, Integer limit, CityModel cityModel) {
		Example<CityModel> example = Example.of(cityModel, ExampleMatcher.matching().withIgnoreCase());
		List<CityModel> cities;
		cities = this.cityRepository.findAll(example, new PageRequest(offset, limit)).getContent();
		cities = new ArrayList<>(cities);
		
		Iterator<CityModel> iterator = cities.iterator();
		
		while(iterator.hasNext()) {
			CityModel s = iterator.next();
			if(!s.getCapital().trim().equalsIgnoreCase("true")) {
				iterator.remove();
			}else {
				System.out.println(s.getCapital());
			}
		}
		//Ordena capitais por nome
		Collections.sort(cities);
		
		return Optional.ofNullable(cities);
	}


	@Override
	public Optional<List<StateModel>> listMaioresMenoresEstados() {
		try {
			List<StateModel> estadosFiltrados = new ArrayList<>();
			
			// busca no banco uma consulta personalizada e traz de acordo com o model referenciado
			List<StateModel> aux = cityRepository.getQtdeCityEstados().stream().sorted(Comparator.comparing(StateModel::getEstado))
					.collect(Collectors.toList());
			
			// filtro o estado com mais cidades
			StateModel stateMaior = aux.stream().max((p1, p2) -> Long.compare(p1.getQtdeCidades(), p2.getQtdeCidades())).get();
			stateMaior.setDescricao("Maior estado");
			estadosFiltrados.add(stateMaior);
			
			// filtro o estado com menos cidades
			StateModel stateMenor = aux.stream().min((p1, p2) -> Long.compare(p1.getQtdeCidades(), p2.getQtdeCidades())).get();
			stateMenor.setDescricao("Menor estado");
			
			
			estadosFiltrados.add(stateMenor);
			
			return Optional.ofNullable(estadosFiltrados);
		} catch (Exception e) {
			throw new CityException(new Object[] {e.getMessage()});
		}
	}
	
}
