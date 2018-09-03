package com.lee.berries.router.model;
//范围分片
public class RangeSharing extends BaseSharing{
	
	private int rangeCount; //切片 范围值 

	public int getRangeCount() {
		return rangeCount;
	}

	public void setRangeCount(int rangeCount) {
		this.rangeCount = rangeCount;
	}
}
