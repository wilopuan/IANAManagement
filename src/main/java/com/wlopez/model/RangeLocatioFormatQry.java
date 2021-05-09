package com.wlopez.model;

import com.wlopez.entity.RangeLocation;

/**
 * Formato con resultados de la consulta
 * 
 * @author wilop
 *
 */
public class RangeLocatioFormatQry {

	private String countryCode;
	private String region;
	private String city;
	private String company;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public RangeLocatioFormatQry(String countryCode, String region, String city, String company) {
		this.countryCode = countryCode;
		this.region = region;
		this.city = city;
		this.company = company;
	}

	public RangeLocatioFormatQry() {

	}

	/**
	 * Convertidor de formato
	 * 
	 * @param target
	 * @return
	 */
	public void convertFromRangeLocation(RangeLocation target) {
		this.countryCode = target.getCountryCode();
		this.region = target.getRegion();
		this.city = target.getCity();
		this.company = target.getCompany();
	}

}
