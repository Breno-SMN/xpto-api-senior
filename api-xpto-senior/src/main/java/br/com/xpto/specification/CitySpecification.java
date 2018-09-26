package br.com.xpto.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.xpto.model.CityModel;
import br.com.xpto.resource.exceptions.CityException;
import br.com.xpto.resource.exceptions.CityFieldInvalidException;

public class CitySpecification {
	
	public static Specification<CityModel> filtrosTodosCampos(final Map<String, String> params) {
		List<Predicate> predicados = new ArrayList<>();
		
		return (root, query, criteriaBuilder) -> {
			
				for (String campo : params.keySet()) {
						try {
							if (CityModel.class.getDeclaredField(campo) != null) {
								predicados.add(criteriaBuilder.equal(root.get(campo), params.get(campo)));
							}else {
								throw new CityFieldInvalidException(new Object[] {campo});
							}
						} catch (NoSuchFieldException e) {
							throw new CityFieldInvalidException(new Object[] {campo});
						} catch (SecurityException e) {
							throw new CityException(new Object[] {campo});
						}
				}
				return criteriaBuilder.and(predicados.toArray(new Predicate[predicados.size()]));
				
		};
    }
}
