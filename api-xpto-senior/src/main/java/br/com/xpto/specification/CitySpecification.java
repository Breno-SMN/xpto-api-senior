package br.com.xpto.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.xpto.model.CityModel;

public class CitySpecification {
	
	public static Specification<CityModel> filtrosTodosCampos(final Map<String, String> params) {
		List<Predicate> predicados = new ArrayList<>();
		
		return (root, query, criteriaBuilder) -> {
			try {
				for (String campo : params.keySet()) {
						if (CityModel.class.getDeclaredField(campo) != null) {
							predicados.add(criteriaBuilder.equal(root.get(campo), params.get(campo)));
						}	
				}
				return criteriaBuilder.and(predicados.toArray(new Predicate[predicados.size()]));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		};
    }

	
}
