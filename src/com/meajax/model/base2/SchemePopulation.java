package com.meajax.model.base2;

import java.util.ArrayList;
import java.util.List;

import com.meajax.model.interfaces.Individual;
import com.meajax.model.interfaces.Population;

public class SchemePopulation implements Population{
	
	/**
	 * 种群个体数量
	 */
	public final int SCHEME_NUM = 50;
	private List<Individual> schemes = new ArrayList<Individual>();
	/**
	 * 资源点
	 */
	private List<Point> resources = new ArrayList<Point>();
	/**
	 * 灾害点
	 */
	private List<Point> damages = new ArrayList<Point>();
	
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
	public List<Individual> getSchemes() {
		return schemes;
	}

	public void setSchemes(List<Individual> schemes) {
		this.schemes = schemes;
	}
	
	

	public void init() {
		List<Individual> schemes = new ArrayList<Individual>();
		
		for (int i = 0; i < SCHEME_NUM; i ++) {
			Scheme scheme = new Scheme();
			scheme.setDamagePoints(damages);
			scheme.setResourcePoints(resources);
			scheme.generateIndividual();
			
			schemes.add(scheme);
		}
		
		this.setSchemes(schemes);
	}



	public Individual getIndividual(int index) {
		Individual individual = this.getSchemes().get(index);
		return individual;
	}

	public Individual getFittest() {
		Individual fittest = null;
		double  fitness = 0;
		for (Individual scheme: this.getSchemes()) {
			if (scheme.getFitness() > fitness) {
				fittest = scheme;
			}
		}
		return fittest;
	}

	public int populationSize() {
		return this.getSchemes().size();
	}


	public void saveIndividual(Individual individual) {
		this.getSchemes().add(individual);
	}

	
	public Population evolution() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
