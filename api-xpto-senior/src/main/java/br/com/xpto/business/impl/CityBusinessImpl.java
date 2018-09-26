package br.com.xpto.business.impl;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.xpto.business.CityBusiness;
import br.com.xpto.model.CityModel;
import br.com.xpto.model.DistanceModel;
import br.com.xpto.model.StateModel;
import br.com.xpto.repository.CityRepository;
import br.com.xpto.resource.exceptions.CityException;
import br.com.xpto.resource.exceptions.CityFieldInvalidException;
import br.com.xpto.resource.exceptions.CsvConvertException;
import br.com.xpto.resource.exceptions.InternalServerErrorException;
import br.com.xpto.specification.CitySpecification;

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
		CityModel cityModel;
		try {
			cityModel = cityRepository.save(entity);
		}catch(Exception ex){
			throw new CityException(new Object[] {ex.getMessage()});
		}
		return Optional.ofNullable(cityModel);
	}

	// pesquisa a cidade pelo IBGE
	@Override
	public Optional<CityModel> findById(Long id) {
		Optional<CityModel> city;
		try {
			city = cityRepository.findById(id);
		}catch (Exception ex) {
			throw new CityException(new Object[] {ex.getMessage()});
		}
		return city;
	}


	@Override
	public Optional<CityModel> update(Long id, CityModel entity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteById(Long id) {
		
		cityRepository.deleteById(id);
		
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


	@Override
	public Optional<List<StateModel>> listQtdCidadesUf() {
		
		try {
			List<StateModel> estadoQtdCity = new ArrayList<>();
			
			// busca no banco uma consulta personalizada e traz de acordo com o model referenciado
			estadoQtdCity = cityRepository.getQtdeCityEstados();
			
			return Optional.ofNullable(estadoQtdCity);
		} catch (Exception e) {
			throw new CityException(new Object[] {e.getMessage()});
		}
	}


	@Override
	public Optional<List<CityModel>> findByEstado(String estado) {
		List<CityModel> cities;
		try {
			cities = cityRepository.findByEstado(estado);
		
		} catch (Exception e) {
			throw new CityException(new Object[] {e.getMessage()});
		}
		return Optional.ofNullable(cities);
	}
	
	@Override
    public Page<CityModel> listGenericFilter(Map<String, String> filtros, Pageable pageable) {
		Page<CityModel> cities;
		cities = cityRepository.findAll(CitySpecification.filtrosTodosCampos(filtros), pageable);
        return cities;
    }
	
	private Object buscaColuna(CityModel c, String field) {
		try {
			Class<?> clazz = Class.forName("br.com.xpto.model.CityModel");
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			Object value = f.get(c);
			return value;
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			throw new CityException(new Object[] {e.getMessage()});
		} catch (NoSuchFieldException e) {
			throw new CityFieldInvalidException(new Object[] {field});
		} catch (ClassNotFoundException e) {
			throw new CityFieldInvalidException(new Object[] {field});
		}

	}
	
	@Override
	public Optional<CityModel> listGenericFilterColumn(String column) {
		Long quantidade = (long) 0;
		try {
			
				quantidade = (long) cityRepository.findAll().stream().map(x -> {
					return buscaColuna(x, column);
				}).distinct().collect(Collectors.toList()).size();
			
			CityModel countColumn = new CityModel(quantidade, column);
			
			return Optional.ofNullable(countColumn);
		} catch (Exception e) {
			throw new CityException(new Object[] {e.getMessage()});
		}
	}


	@Override
	public Optional<Long> countAll() {
			
		return Optional.ofNullable(cityRepository.countAll());
	}
	
	public Optional<DistanceModel> recuperaDistancia() {
		List<CityModel> cities;

		try {

			 cities= cityRepository.findAll();

			double auxDistanceKm = 0;
			CityModel cidadeInicial = null, cidadeFinal = null;
			
			for (CityModel c1 : cities) {
				for (CityModel c2 : cities) {
					if (!c2.equals(c1)) {
						// calcula a distancia com a latitude e longitude e altura(ignorar altura passar 0.0)
						double aux = calculaDistancia(c1.getLat().doubleValue(), c1.getLon().doubleValue(),
								c2.getLat().doubleValue(), c2.getLon().doubleValue(), 0.0,0.0);
						if (aux > auxDistanceKm) {
							auxDistanceKm = aux;
							cidadeInicial = c1;
							cidadeFinal = c2;
						}
					}
				}
			}
			DistanceModel distanceModel = new DistanceModel(cidadeInicial.getName(), cidadeFinal.getName(), auxDistanceKm);
			
			return Optional.ofNullable(distanceModel);
		} catch (Exception e) {
			throw new CityException(new Object[] {e.getMessage()});
		}

	}
	
	/* Calculate distance between two points in latitude 
	and longitude taking into account height difference. 
	If you are not interested in height difference pass 0.0.
	 Uses Haversine method as its base. lat1, lon1 Start point 
	lat2, lon2 End point el1 Start altitude in meters el2 End altitude 
	in meters */

	private double calculaDistancia(double lat1, double lat2, double lon1, double lon2,
	        double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    Double latDistance = deg2rad(lat2 - lat1);
	    Double lonDistance = deg2rad(lon2 - lon1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;
	    distance = Math.pow(distance, 2) + Math.pow(height, 2);
	    return metersToKm(Math.sqrt(distance));
	}
	
	private double metersToKm(double metros) {
		double km = 1000;
		km = metros/km;
		
		return km;
	}
	
	private double deg2rad(double deg) {
	    return (deg * Math.PI / 180.0);
	}
	
}
