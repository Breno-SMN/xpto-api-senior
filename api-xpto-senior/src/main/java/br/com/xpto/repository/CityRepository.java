package br.com.xpto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xpto.model.CityModel;

@Repository
public interface CityRepository extends JpaRepository<CityModel, Long> {


}
