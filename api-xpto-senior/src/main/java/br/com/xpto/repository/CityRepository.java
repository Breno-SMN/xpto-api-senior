package br.com.xpto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.xpto.model.CityModel;
import br.com.xpto.model.StateModel;

@Repository
public interface CityRepository extends JpaRepository<CityModel, Long>, JpaSpecificationExecutor<CityModel> {

	@Query("select new br.com.xpto.model.StateModel(c.uf, count(c)) from CityModel c group by c.uf")
	List<StateModel> getQtdeCityEstados();
	
	@Query("SELECT NEW br.com.xpto.model.CityModel(c.ibgeId , c.uf, c.name, c.capital, c.lon, c.lat, c.noAccents, c.alternativeNames, c.microregion, c.mesoregion) "
			+ "FROM CityModel c where c.uf = :estado order by name")
	List<CityModel> findByEstado(@Param("estado") String estado);
}
