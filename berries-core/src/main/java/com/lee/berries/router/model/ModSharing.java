package com.lee.berries.router.model;
//取模分片
public class ModSharing extends BaseSharing{

	private int modCount; //取模系数

	public int getModCount() {
		return modCount;
	}

	public void setModCount(int modCount) {
		this.modCount = modCount;
	}
}
