package com.wlopez.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wlopez.entity.RangeLocation;
import com.wlopez.repository.RangeLocationRepository;
import com.wlopez.service.RangeLocationService;

@Service("rangeLocationServiceImpl")
public class RangeLocationServiceImpl implements RangeLocationService {

	@Autowired
	@Qualifier("rangeLocationRepository")
	private RangeLocationRepository rangeLocationRepository;

	@Override
	public boolean addRecord(RangeLocation rangeLocation) {

		try {
			rangeLocationRepository.save(rangeLocation);
		} catch (Exception recordException) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}


	@Override
	public List<RangeLocation> findRecordsWithIpInRange(long ipTarget) {
		return rangeLocationRepository.findByIpFromLessThanEqualAndIpToGreaterThanEqual(ipTarget, ipTarget);
	}

}
