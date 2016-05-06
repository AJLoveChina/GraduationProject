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
	private int id;
	private String name;
	private Type type;
	
	
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
	public Point(Type type) {
		this.initByRandom(type);
	}




	public Map<Point, Double> getDistances() {
		return distances;
	}

	public void setDistances(Map<Point, Double> distances) {
		this.distances = distances;
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
	 * 根据指定的点类型, 随机初始化一个点
	 */
	private void initByRandom(Type type) {
		//TODO
	}
	


	
	public static void main(String[] args) {
		Map<Resource.Type, Double> map = new HashMap<Resource.Type, Double>();
		map.put(Resource.Type.FOOD, 120.0);
		
		Double val = map.get(Resource.Type.WATER);
		System.out.println(val);
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
