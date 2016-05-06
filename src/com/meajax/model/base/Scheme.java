package com.meajax.model.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.meajax.model.base.Resource.Type;
import com.meajax.model.interfaces.Individual;

/**
 * 方案 个体
 * @author ajax
 *
 */
public class Scheme implements Individual{

	/**
	 * 资源点
	 */
	private List<Point> resources;
	/**
	 * 灾害点
	 */
	private List<Point> damages;
	


	public List<Point> getResources() {
		return resources;
	}

	public void setResources(List<Point> resources) {
		this.resources = resources;
	}

	public List<Point> getDamages() {
		return damages;
	}

	public void setDamages(List<Point> damages) {
		this.damages = damages;
	}




	private void allocateResources(Point from, Point to, Resource.Type type){
		if(from.isDamagePoint() || to.isReourcePoint()) {
			try {
				throw new Exception();
			} catch (Exception e) {
				System.out.println("灾害点不可以给资源点配送资源!");
				e.printStackTrace();
			}
		}
		
		Integer resourceLeft = from.getLeftAmountOfResourceType(type);
		Integer resourceNeed = to.getNeedAmountOfResourceType(type);
		Integer finalGivenAmount;
		
		finalGivenAmount = (int)Math.floor(resourceLeft * Math.random());
		
		if (finalGivenAmount == 0) {
			// 该救援点不参与该灾害点的救援工作
			return;
		}
		if (finalGivenAmount > resourceNeed) {
			finalGivenAmount = resourceNeed;
		}
		
		from.giveSomeResourceToDamagePoint(to, type, finalGivenAmount);
		
	}


	
	/**
	 * 某种资源是否还有剩余
	 * @return
	 */
	public boolean haveLeftResources(Resource.Type type) {
		
		for (Point point : this.getResources()) {
			if (point.hasLeftResource(type)) {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * 是否还有受灾点需要某种资源
	 */
	public boolean haveDamagesNeedResources(Resource.Type type) {
		for (Point point : this.getDamages()) {
			if (point.isLackResource(type)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取剩余某种资源的救援点  集合
	 * @param type
	 * @return
	 */
	public List<Point> getResourcePointsHaveThisResource(Resource.Type type) {
		List<Point> points = new ArrayList<Point>();
		for (Point point : this.getResources()) {
			if (point.hasLeftResource(type)) {
				points.add(point);
			}
		}
		return points;
	}
	/**
	 * 从具有某种资源的 资源点集合中随机获得一个资源点
	 * @param type
	 * @return null if not suitable point
	 */
	public Point getOnePointHaveThisResourceByRandom(Resource.Type type) {
		Random rd = new Random();
		List<Point> points = this.getResourcePointsHaveThisResource(type);
		
		if (points.size() == 0) {
			return null;
		} else {
			return points.get(rd.nextInt(points.size()));
		}
	}
	
	
	/**
	 * 获取需要某种资源的灾害点  集合
	 * @param type
	 * @return
	 */
	public List<Point> getDamagePointsNeedThisResource(Resource.Type type) {
		List<Point> points = new ArrayList<Point>();
		for (Point point : this.getDamages()) {
			if (point.isLackResource(type)) {
				points.add(point);
			}
		}
		return points;
	}
	
	/**
	 * 从需要某种资源的灾害点集合中随机获得一个灾害点
	 * @param type
	 * @return null if not suitable point
	 */	
	private Point getOnePointNeedThisResourceByRandom(Type type) {
		Random rd = new Random();
		List<Point> points = this.getDamagePointsNeedThisResource(type);
		
		if (points.size() == 0) {
			return null;
		} else {
			return points.get(rd.nextInt(points.size()));
		}		
	}

	public void generateIndividual() {
		Resource.Type[] types = Resource.Type.values();
		
		for (Resource.Type type : types) {
			while (this.haveLeftResources(type) && this.haveDamagesNeedResources(type)) {
				Point point1 = this.getOnePointHaveThisResourceByRandom(type);
				Point point2 = this.getOnePointNeedThisResourceByRandom(type);
				
				if (point1 == null && point2 == null) {
					// 已经木有该种资源的 资源点   或者 木有需要该种资源的资源点了;
					break;
				}
				
				this.allocateResources(point1, point2, type);
			}
		}
	}

	public double getFitness() {
		double cost = 0;
		// 遍历每个资源点
		for (Point from : this.getResources()) {
			// 获取每个资源点的给灾害点的分配情况
			Map<Point, Map<Resource.Type, Integer>> map = from.getOutInfo();
			
			// 获取该资源点分配的灾害点
			for (Point to : map.keySet()) {
				// 计算出到资源点与灾害点的路程
				double distance = from.getDistanceTo(to);
				// 资源点分配给灾害点的物资种类及数目
				Map<Resource.Type, Integer> map2 = map.get(to);
				
				// 对于每一种要运输的物资计算成本
				for (Resource.Type type : map2.keySet()) {
					cost += type.getCost(map2.get(type), distance);
				}
			}
		}
		
		// 如果cost为0, 发生异常
		return 1 / cost;
	}

	
	public void mutate() {
//		Random rd = new Random();
//		Point p1 = this.getResources().get(rd.nextInt(this.getResources().size() - 1));
//		Point p2 = this.getResources().get(rd.nextInt(this.getResources().size() - 1));
//		
//		Point damage = this.getDamages().get(rd.nextInt(this.getDamages().size() - 1));
//		Point 
//		
//		if(p1 != p2) {
//			Map<Resource.Type, Integer> map1 = p1.getOutInfo().get(damage);
//			Map<Resource.Type, Integer> map2 = p2.getOutInfo().get(damage);
//			
//			for (Resource.Type type : Resource.Type.values()) {
//				try {
//					int amount1 = map1.get(type);
//					int amount2 = map2.get(type);
//					
//					int exchangeAmount = (amount1 > amount2) ? amount2 : amount1;
//					
//					p1.removeSomeResourceToDamagePoint(damage, type, exchangeAmount);
//					p2.removeSomeResourceToDamagePoint(damage, type, exchangeAmount);
//					
//					p1.giveSomeResourceToDamagePoint(to, type, finalGivenAmount);
//				}catch(Exception ex) {
//					
//				}
//			}
//		}
	}

	public Individual crossoverWith(Individual individual) {
		List<Point> r1 = this.getResources();
		List<Point> d1 = this.getDamages();
		
			
		Scheme other = (Scheme) individual;
		List<Point> r2 = other.getResources();
		List<Point> d2 = other.getDamages();
		
		Individual child = new Scheme();
		
		
		for(int i = 0, len = r1.size(); i < len; i++) {
			
		}
		
		
		return null;
	}

	@Override
	public String toString() {
		return "Scheme [resources=" + resources + ", damages=" + damages + "]";
	}

	public int[][] getGenes() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEligible() {
		// TODO Auto-generated method stub
		return false;
	}
}
