package com.wlopez.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "rangelocation")
@IdClass(value = RangePk.class)
public class RangeLocation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ipfrom")
	private long  ipFrom;

	@Id
	@Column(name = "ipto")
	private long ipTo;

	@Column(name = "countrycode")
	private String countryCode;

	@Column(name = "country")
	private String country;

	@Column(name = "region")
	private String region;

	@Column(name = "city")
	private String city;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "company")
	private String company;

	public long getIpFrom() {
		return this.ipFrom;
	}

	public void setIpFrom(long ipFrom) {
		this.ipFrom = ipFrom;
	}

	public long getIpTo() {
		return this.ipTo;
	}

	public void setIpTo(long ipTo) {
		this.ipTo = ipTo;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public RangeLocation(long ipFrom, long ipTo, String countryCode, String country, String region, String city,
			String latitude, String longitude, String company) {
		this.ipFrom = ipFrom;
		this.ipTo = ipTo;
		this.countryCode = countryCode;
		this.country = country;
		this.region = region;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.company = company;
	}

	public RangeLocation() {

	}

}
