package br.com.xpto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import br.com.xpto.model.CityModel;
import br.com.xpto.model.StateModel;

@Repository
public interface CityRepository extends JpaRepository<CityModel, Long> {

	@Query("select new br.com.xpto.model.StateModel(c.uf, count(c)) from CityModel c group by c.uf")
	List<StateModel> getQtdeCityEstados();
}
