package br.com.xpto.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cities")
public class CityModel implements Comparable<CityModel>{
	@Id
	@Column(name = "ibge_id")
	private Long ibgeId;
	@NotBlank
	private String uf;
	@NotBlank
	public static String name;
	private String capital;
	@NotNull
	@Digits(integer = 10, fraction = 10)
	private BigDecimal lon;
	@NotNull
	@Digits(integer = 10, fraction = 10)
	private BigDecimal lat;
	@NotBlank
	@Column(name = "no_accents")
	private String noAccents;
	private String alternativeNames;
	@NotBlank
	private String microregion;
	@NotBlank
	private String mesoregion;

	public CityModel() {
		super();
	}

	public CityModel(Long ibgeId, String uf, String name, String capital, BigDecimal lon, BigDecimal lat,
			String noAccents, String alternativeNames, String microregion, String mesoregion) {
		super();
		this.ibgeId = ibgeId;
		this.uf = uf;
		this.name = name;
		this.capital = capital;
		this.lon = lon;
		this.lat = lat;
		this.noAccents = noAccents;
		this.alternativeNames = alternativeNames;
		this.microregion = microregion;
		this.mesoregion = mesoregion;
	}

	public Long getIbgeId() {
		return ibgeId;
	}

	public void setIbgeId(Long ibgeId) {
		this.ibgeId = ibgeId;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public String getNoAccents() {
		return noAccents;
	}

	public void setNoAccents(String noAccents) {
		this.noAccents = noAccents;
	}

	public String getAlternativeNames() {
		return alternativeNames;
	}

	public void setAlternativeNames(String alternativeNames) {
		this.alternativeNames = alternativeNames;
	}

	public String getMicroregion() {
		return microregion;
	}

	public void setMicroregion(String microregion) {
		this.microregion = microregion;
	}

	public String getMesoregion() {
		return mesoregion;
	}

	public void setMesoregion(String mesoregion) {
		this.mesoregion = mesoregion;
	}

	@Override
	public String toString() {
		return "CityModel [ibgeId=" + ibgeId + ", uf=" + uf + ", name=" + name + ", capital=" + capital + ", lon=" + lon
				+ ", lat=" + lat + ", noAccents=" + noAccents + ", alternativeNames=" + alternativeNames
				+ ", microregion=" + microregion + ", mesoregion=" + mesoregion + "]";
	}
	
	@Override
	 public int compareTo(CityModel s) {
	        return this.getName().compareTo(s.getName());
	    }

}
