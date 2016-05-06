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

	/**
	 * 资源点
	 */
	private List<Point> resourcePoints;
	/**
	 * 灾害点
	 */
	private List<Point> damagePoints;
	


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
		//TODO
	}

	public double getFitness() {
		//TODO
		return 0;
	}

	
	public void mutate() {
		//TODO
	}

	public Individual crossoverWith(Individual individual) {
		//TODO
		return null;
	}

	@Override
	public String toString() {
		return "Scheme [resources=" + resourcePoints + ", damages=" + damagePoints + "]";
	}
}
