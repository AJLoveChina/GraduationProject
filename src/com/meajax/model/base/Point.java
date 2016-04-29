package com.meajax.model.base;

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
	
	private Map<Resource.Type, Integer> resources;
	// private Map<Resource.Type, Double> resources;
	/**
	 * 送出去的资源
	 */
	private Map<Resource.Type, Integer> out = new HashMap<Resource.Type, Integer>();
	/**
	 * 获得的资源
	 */
	private Map<Resource.Type, Integer> in = new HashMap<Resource.Type, Integer>();
	
	private Map<Point, Map<Resource.Type, Integer>> outInfo = new HashMap<Point, Map<Resource.Type,Integer>>();
	private Map<Point, Map<Resource.Type, Integer>> inInfo = new HashMap<Point, Map<Resource.Type,Integer>>(); 
	
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

	public Map<Point, Map<Resource.Type, Integer>> getOutInfo() {
		return outInfo;
	}

	public void setOutInfo(Map<Point, Map<Resource.Type, Integer>> outInfo) {
		this.outInfo = outInfo;
	}

	public Map<Point, Map<Resource.Type, Integer>> getInInfo() {
		return inInfo;
	}

	public void setInInfo(Map<Point, Map<Resource.Type, Integer>> inInfo) {
		this.inInfo = inInfo;
	}

	public Map<Resource.Type, Integer> getResources() {
		return resources;
	}

	public void setResources(Map<Resource.Type, Integer> resources) {
		this.resources = resources;
	}

	public Map<Resource.Type, Integer> getOut() {
		return out;
	}

	public void setOut(Map<Resource.Type, Integer> out) {
		this.out = out;
	}

	public Map<Resource.Type, Integer> getIn() {
		return in;
	}

	public void setIn(Map<Resource.Type, Integer> in) {
		this.in = in;
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
		int amount;
		switch (type) {
		case DAMAGE:
			amount = 50;
			break;
		case RESOURCE:
			amount = 100;
			break;
		default:
			amount = 50;
			break;
		}
		
		this.setType(type);
		Random rd = new Random();
		Map<Resource.Type, Integer> resources = new HashMap<Resource.Type, Integer>();
		for (Resource.Type resourceType : Resource.Type.values()) {
			resources.put(resourceType, rd.nextInt(amount));
		}
		
		this.setLatitude(117 + Math.random());
		this.setLongitude(31 + Math.random());
		this.setResources(resources);
	}
	
	/**
	 * 是否还有指定类型的资源
	 * @param type
	 * @return
	 */
	public boolean hasLeftResource(Resource.Type type) {
		Integer have = this.getResources().get(type);
		if(have == null) {
			have = 0;
		}
		Integer out = this.getOut().get(type);
		if (out == null) {
			out = 0;
		}
		return  (have - out) > 0;
	}

	/**
	 * 是否缺少某种资源
	 * @param type
	 * @return
	 */
	public boolean isLackResource(Resource.Type type) {
		Integer need = this.getResources().get(type);
		if (need == null) {
			need = 0;
		}
		Integer in = this.getIn().get(type);
		if (in == null) {
			in = 0;
		}
		
		return (need - in) > 0;
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

	/**
	 * 剩余某种资源的数量
	 * @param type
	 * @return
	 */
	public Integer getLeftAmountOfResourceType(Resource.Type type) {
		Integer amountHave  = this.getResources().get(type);
		if (amountHave == null) {
			amountHave = 0;
		}
		Integer amountOut = this.getOut().get(type);
		if (amountOut == null) {
			amountOut = 0;
		}
		return amountHave - amountOut;
	}

	/**
	 * 需要某种资源的数量还差多少
	 * @param type
	 * @return
	 */
	public Integer getNeedAmountOfResourceType(Resource.Type type) {
		Integer amountNeed = this.getResources().get(type);
		if (amountNeed == null) {
			amountNeed = 0;
		}
		Integer amountHasGet = this.getIn().get(type);
		if (amountHasGet == null) {
			amountHasGet = 0;
		}
		return amountNeed - amountHasGet;
	}

	/**
	 * 参与某灾害点的救援工作, 分配一定数量的某种资源给受灾点
	 * @param to
	 * @param type2
	 * @param finalGivenAmount
	 */
	public void giveSomeResourceToDamagePoint(Point to, Resource.Type type,
			Integer finalGivenAmount) {
		
		// out
		int amount;
		try {
			amount = this.getOut().get(type);
		}catch(Exception ex) {
			amount = 0;
		}
		amount = amount - finalGivenAmount;
		this.getOut().put(type, amount);
		
		int amountHasGive = 0;
		try {
			amountHasGive = this.getOutInfo().get(to).get(type);
		}catch(Exception ex) {
			amountHasGive = 0;
		}
		Map<Resource.Type, Integer> outInfoForTo = this.getOutInfo().get(to);
		if (outInfoForTo == null) {
			outInfoForTo = new HashMap<Resource.Type, Integer>();
		}
		outInfoForTo.put(type, amountHasGive + finalGivenAmount);
		this.getOutInfo().put(to, outInfoForTo);
		
		// in
		int amount2;
		try {
			amount2 = to.getIn().get(type);
		}catch(Exception ex) {
			amount2 = 0;
		}
		amount2 = amount2 + finalGivenAmount;
		to.getIn().put(type, amount2);
		
		int amountHasGet = 0;
		try {
			amountHasGet = this.getInInfo().get(to).get(type);
		}catch(Exception ex) {
			amountHasGet = 0;
		}
		Map<Resource.Type, Integer> inInfoForTo = this.getInInfo().get(this);
		if (inInfoForTo == null) {
			inInfoForTo = new HashMap<Resource.Type, Integer>();
		}
		inInfoForTo.put(type, amountHasGet + finalGivenAmount);
		this.getInInfo().put(this, inInfoForTo);
		
	}

	/**
	 * 取消对某一灾害点的资源分配, 常用在个体变异中
	 * @param to
	 * @param type2
	 * @param finalGivenAmount
	 */
	public void removeSomeResourceToDamagePoint(Point to, Resource.Type type,
			int amount) {
		// out
		int amount1 = this.getOut().get(type);
		amount1 = amount1 + amount;
		this.getOut().put(type, amount1);
		
		// in
		int amount2 = to.getIn().get(type);
		amount2 = amount2 - amount;
		to.getIn().put(type, amount2);
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
