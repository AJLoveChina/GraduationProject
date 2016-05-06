package com.meajax.model.base2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.meajax.model.Baidu;
import com.meajax.model.base.Resource.Type;

/**
 * 城市, 资源点, 灾害点
 * @author ajax
 *
 */
public class Point {
	/**
	 * 经度
	 */
	private double longitude;
	
	/**
	 * 纬度
	 */
	private double latitude;
	/**
	 * 点的唯一标识
	 */
	private int id;
	/**
	 * 点的名称
	 */
	private String name;
	/**
	 * 点的类型
	 */
	private Type type;
	/**
	 * 资源数量
	 */
	private int resourceAmount;
	
	
	/**
	 * 到某点的路程 存储表
	 */
	private Map<Point, Double> distances = new HashMap<Point, Double>();
	
	
	public enum Type{
		RESOURCE(1, "资源点"),
		DAMAGE(2, "灾害点");
		
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
		
		Type(int id, String info) {
			this.setId(id);
			this.setInfo(info);
		}
	}
	
	public Point(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Point(double longitude, double latitude, Type type) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.type = type;
	}
	
	/**
	 * 该构造器随机初始化一个点
	 * @param type
	 */
	public Point(Type type, int id) {
		Random rd = new Random();
		int amount;
		String name;
		
		this.setLatitude(117 + Math.random());
		this.setLongitude(31 + Math.random());
		this.setId(id);
		this.setType(type);
		
		switch (type) {
		case DAMAGE:
			amount = rd.nextInt(10);
			name = "D" + id;
			break;

		case RESOURCE:
			amount = rd.nextInt(20);
			name = "R" + id;
			break;
		default:
			amount = rd.nextInt(15);
			name = "U" + id;
			break;
		}
		this.setResourceAmount(amount);
		this.setName(name);
	}




	public Map<Point, Double> getDistances() {
		return distances;
	}

	public void setDistances(Map<Point, Double> distances) {
		this.distances = distances;
	}

	
	public int getResourceAmount() {
		return resourceAmount;
	}

	public void setResourceAmount(int resourceAmount) {
		this.resourceAmount = resourceAmount;
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}

	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	


	


	/**
	 * 是否是资源点
	 * @return
	 */
	public boolean isReourcePoint() {
		return this.getType().getId() == Type.RESOURCE.getId();
	}
	
	/**
	 * 是否是灾害点
	 * @return
	 */
	public boolean isDamagePoint() {
		return this.getType().getId() == Type.DAMAGE.getId();
	}

	public double getDistanceTo(Point to) {
		Double distance = this.getDistances().get(to);
		if (distance == null) {
			Baidu baidu = new Baidu();
			Baidu.Point start = baidu.new Point(this.getLatitude(), this.getLongitude());
			Baidu.Point end = baidu.new Point(to.getLatitude(), this.getLongitude());
			
			distance = baidu.getDistance(start, end);
			
			this.getDistances().put(to, distance);
		}
		return distance;
	}


	
}
