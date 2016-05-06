package com.meajax.model.base2;

/**
 * 资源
 * @author ajax
 *
 */
public class Resource {
	public enum Type{
		WATER(1, ""),
		FOOD(2, "");
		
		private int id;
		private String info;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		Type(int id, String info){
			this.id = id;
			this.info = info;
		}
		
		/**
		 * 计算一定数量运输一定路径的成本
		 * @param amount
		 * @param distance
		 * @return
		 */
		public double getCost(Integer amount, double distance) {
			return distance * 1 * amount;
		}
	}
	
	private Type type;
	private int amount;
	
	
	
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
