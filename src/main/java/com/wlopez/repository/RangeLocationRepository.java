package com.wlopez.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlopez.entity.RangeLocation;

@Repository("rangeLocationRepository")
public interface RangeLocationRepository extends JpaRepository<RangeLocation, Serializable> {

	/**
	 * Busqueda de rangos de direcciones
	 * @param ipTarget
	 * @return
	 */
	public abstract List<RangeLocation>findByIpFromLessThanEqualAndIpToGreaterThanEqual(long ipFromP, long IpToP);
	
}
