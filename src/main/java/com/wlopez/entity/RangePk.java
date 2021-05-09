package com.wlopez.entity;

import java.io.Serializable;
import java.util.Objects;

public class RangePk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long ipFrom;
	private long ipTo;

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + Objects.hashCode(this.ipFrom);
		hash = 59 * hash + Objects.hashCode(this.ipTo);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RangePk other = (RangePk) obj;
		if (!Objects.equals(this.ipFrom, other.ipFrom)) {
			return false;
		}
		if (!Objects.equals(this.ipTo, other.ipTo)) {
			return false;
		}
		return true;
	}

	public long getIpFrom() {
		return ipFrom;
	}

	public void setIpFrom(long ipFrom) {
		this.ipFrom = ipFrom;
	}

	public long getIpTo() {
		return ipTo;
	}

	public void setIpTo(long ipTo) {
		this.ipTo = ipTo;
	}

	public RangePk(long ipFrom, long ipTo) {
		this.ipFrom = ipFrom;
		this.ipTo = ipTo;
	}

	public RangePk() {

	}

}
