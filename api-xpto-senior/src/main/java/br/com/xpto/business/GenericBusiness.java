package br.com.xpto.business;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import br.com.xpto.model.CityModel;
import br.com.xpto.model.DistanceModel;
import br.com.xpto.model.StateModel;

public interface GenericBusiness<T, ID extends Serializable> {
	Optional<List<T>> list(Integer page, Integer size, T entity);
	
	Optional<T> create(T entity);

	Optional<T> findById(ID id);
	
	Optional<T> update(ID id, T entity);

	void deleteById(ID id);
	
	Optional<List<CityModel>> listCapitalOrdem(Integer offset, Integer limit, CityModel cityModel);
	
	Optional<List<StateModel>> listMaioresMenoresEstados();
	
	Optional<List<StateModel>> listQtdCidadesUf();
	
	Optional<List<CityModel>> findByEstado(String estado);

	Optional<CityModel> listGenericFilterColumn(String column);
	
	Optional<Long> countAll();
	
	Optional<DistanceModel> recuperaDistancia();
}