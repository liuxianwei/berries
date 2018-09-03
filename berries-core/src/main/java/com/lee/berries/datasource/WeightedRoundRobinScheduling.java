package com.lee.berries.datasource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 权重轮询调度算法(WeightedRound-RobinScheduling)-Java实现
 * 
 * @author huligong
 */
public class WeightedRoundRobinScheduling {

	private List<Server> serverList = new ArrayList<>(); // 服务器集合

	public Server GetBestServer() {
		Server server = null;
		Server best = null;
		int total = 0;
		for (int i = 0, len = serverList.size(); i < len; i++) {
			// 当前服务器对象
			server = serverList.get(i);

			// 当前服务器已宕机，排除
			if (server.down) {
				continue;
			}

			server.currentWeight += server.effectiveWeight;
			total += server.effectiveWeight;

			if (server.effectiveWeight < server.weight) {
				server.effectiveWeight++;
			}

			if (best == null || server.currentWeight > best.currentWeight) {
				best = server;
			}

		}

		if (best == null) {
			return null;
		}

		best.currentWeight -= total;
		best.checkedDate = new Date();
		return best;
	}

	class Server {
		public String name;
		public int weight;
		public int effectiveWeight;
		public int currentWeight;
		public boolean down = false;
		public Date checkedDate;

		public Server(String name, int weight) {
			super();
			this.name = name;
			this.weight = weight;
			this.effectiveWeight = this.weight;
			this.currentWeight = 0;
			if (this.weight <= 0) {
				this.down = true;
			} else {
				this.down = false;
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public int getEffectiveWeight() {
			return effectiveWeight;
		}

		public void setEffectiveWeight(int effectiveWeight) {
			this.effectiveWeight = effectiveWeight;
		}

		public int getCurrentWeight() {
			return currentWeight;
		}

		public void setCurrentWeight(int currentWeight) {
			this.currentWeight = currentWeight;
		}

		public boolean isDown() {
			return down;
		}

		public void setDown(boolean down) {
			this.down = down;
		}

		public Date getCheckedDate() {
			return checkedDate;
		}

		public void setCheckedDate(Date checkedDate) {
			this.checkedDate = checkedDate;
		}

	}

	public void add(String name, int weight) {
		Server s = new Server(name, weight);
		serverList.add(s);
	}

	public Server getServer(int i) {
		if (i < serverList.size()) {
			return serverList.get(i);
		}
		return null;
	}
}