package com.wlopez.service;

import java.util.List;

import com.wlopez.entity.RangeLocation;

public interface RangeLocationService {
	
	public abstract boolean addRecord(RangeLocation rangeLocation);
	public abstract List<RangeLocation> findRecordsWithIpInRange(long ipTarget);

}
