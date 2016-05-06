package com.meajax.model.base2;

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

	public final double MUTATE_RATE = 0.015;
	/**
	 * 资源点
	 */
	private List<Point> resourcePoints;
	/**
	 * 灾害点
	 */
	private List<Point> damagePoints;

	/**
	 * 基因矩阵
	 * -------------------------------------------------
	 *    		灾害点1	灾害点2	灾害点3 ...	灾害点n
	 * 资源点1
	 * 资源点2
	 * ...
	 * 资源点n
	 * -------------------------------------------------
	 */
	private int[][] genes;
	
	/**
	 * 缓存fitness值
	 */
	private double fitVal = 0;
	

	public double getFitVal() {
		return fitVal;
	}

	public void setFitVal(double fitVal) {
		this.fitVal = fitVal;
	}

	public int[][] getGenes() {
		return genes;
	}

	public void setGenes(int[][] genes) {
		this.genes = genes;
	}

	public List<Point> getResourcePoints() {
		return resourcePoints;
	}
	
	public void setResourcePoints(List<Point> resourcePoints) {
		this.resourcePoints = resourcePoints;
	}

	public List<Point> getDamagePoints() {
		return damagePoints;
	}

	public void setDamagePoints(List<Point> damagePoints) {
		this.damagePoints = damagePoints;
	}


	public void generateIndividual() {
		int resourcePointsSize = this.getResourcePoints().size();
		int damagePointsSize = this.getDamagePoints().size();
		
		int[][] arr = new int[resourcePointsSize][damagePointsSize];
		Random rd = new Random();
		
		for (int i = 0; i < resourcePointsSize; i++) {
			for (int j = 0; j < damagePointsSize; j++) {
				int amountLeft = this.getLeftResourceAmountOf(this.getResourcePoints().get(i), arr);
				int amountLack = this.getLackResourceAmountOf(this.getDamagePoints().get(j), arr);
				
				int limit;
				limit = (amountLeft > amountLack) ? amountLack : amountLeft;
				
				if (limit == 0) {
					arr[i][j] = 0;
				} else {
					arr[i][j] = rd.nextInt(limit); 
				}
			}
		}
		this.setGenes(arr);
		
	}

	/**
	 * 获取一个点剩余的资源数目
	 * @param point
	 * @return
	 */
	private int getLeftResourceAmountOf(Point point, int[][] genes) {
		int index = point.getId();
		int total = 0;
		for (int i = 0; i < genes[index].length; i++) {
			total += genes[index][i];
		}
		return point.getResourceAmount() - total;
	}
	
	/**
	 * 获取一个灾害点还缺少多少资源
	 * @param point
	 * @param genes
	 * @return
	 */
	private int getLackResourceAmountOf(Point point, int[][] genes) {
		int index = point.getId();
		int total = 0;
		for (int i = 0; i < genes.length; i++) {
			total += genes[i][index];
		}
		return point.getResourceAmount() - total;
	}

	public double getFitness() {
		if (this.getFitVal() != 0) {
			return this.getFitVal();
		}
		
		this.calculateFitness();
		
		return this.getFitVal();
	}
	
	/**
	 * 计算适应度并保存
	 */
	private void calculateFitness() {
		Point rPoint;
		Point dPoint;
		
		double distance;
		double cost = 0;
		
//		for (int i = 0; i < this.genes.length; i++) {
//			for (int j = 0; j < this.genes[i].length; j++) {
//				rPoint = this.getResourcePoints().get(i);
//				dPoint = this.getDamagePoints().get(j);
//				
//				distance = rPoint.getDistanceTo(dPoint);
//				cost  += distance * this.genes[i][j];
//			}
//		}
		int amountGet = 0;
		int amountNeed;
		double costForEachDamage = 0;
		double[] distances = new double[this.genes.length];
		
		for (int j = 0; j < this.genes[0].length; j++) {
			amountGet = 0;
			costForEachDamage = 0;
			amountNeed = this.getDamagePoints().get(j).getResourceAmount();
			
			for (int i = 0; i < this.genes.length; i++) {
				
				rPoint = this.getResourcePoints().get(i);
				dPoint = this.getDamagePoints().get(j);
				
				distance = rPoint.getDistanceTo(dPoint);
				distances[i] = distance;
				costForEachDamage  += distance * this.genes[i][j];
				amountGet += this.genes[i][j];
			}
			
			for (double distance2 : distances) {
				costForEachDamage += Math.abs(amountGet - amountNeed) * distance2;
			}
			
			cost += costForEachDamage;
		}
		
		this.setFitVal(1 / cost);
	}

	
	public void mutate() {
		Random rd = new Random();
		int[][] arr  = this.getGenes();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				
				if (rd.nextFloat() < this.MUTATE_RATE) {
					int result = arr[i][j];
					int offset = (int)Math.floor(result * 0.1);
					
					arr[i][j] = result + offset;
				}
				
			}
		}
		this.calculateFitness();
	}

	public Individual crossoverWith(Individual individual) {
		int[][] arr1 = this.getGenes();
		int[][] arr2 = individual.getGenes();
		int[][] newArr = new int[arr1.length][arr1[0].length];
		
		Random rd = new Random();
		
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++) {
				if (rd.nextInt(2) == 0) {
					newArr[i][j] = arr1[i][j];
				} else {
					newArr[i][j] = arr2[i][j];
				}
			}
		}
		Scheme scheme = new Scheme();
		scheme.setDamagePoints(damagePoints);
		scheme.setResourcePoints(resourcePoints);
		scheme.setGenes(newArr);
		return scheme;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int[][] arr = this.getGenes();
		int amount;
		sb.append("\t\t");
		for (int i = 0; i < this.getDamagePoints().size(); i ++) {
			sb.append(this.getDamagePoints().get(i).getName()  + "(" + this.getDamagePoints().get(i).getResourceAmount() + ")" + "\t");
		}
		sb.append("\n");
		
		for (int i = 0; i < arr.length; i++) {
			amount = 0;
			sb.append(this.getResourcePoints().get(i).getName() + "(" + this.getResourcePoints().get(i).getResourceAmount() + ")" + "\t\t");
			for (int j = 0; j < arr[i].length; j++) {
				amount += arr[i][j];
				sb.append(arr[i][j] + "\t");
			}
			sb.append(amount);
			sb.append("\n");
			
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		int[][] arr  = new int[3][4];
	}

	/**
	 * 是否是合理的个体, 资源点输出数量大于本身拥有的数量被认为是不合理的个体
	 */
	public boolean isEligible() {
		for (int i = 0; i < this.getResourcePoints().size(); i++) {
			if (this.getLeftResourceAmountOf(this.getResourcePoints().get(i), this.getGenes()) < 0) {
				return false;
			}
		}
		return true;
	}
}
 