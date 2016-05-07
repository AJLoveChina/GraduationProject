package com.meajax.model.base2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.meajax.model.interfaces.Individual;
import com.meajax.model.interfaces.Population;

public class SchemePopulation implements Population{
	
	/**
	 * 种群个体数量
	 */
	public final int SCHEME_NUM = 50;
	
	/**
	 * 从几个个体中选取较好的父代
	 */
	public final int CROSS_NUM = 5;
	private List<Individual> schemes = new ArrayList<Individual>();
	/**
	 * 资源点
	 */
	private List<Point> resourcesPoints = new ArrayList<Point>();
	/**
	 * 灾害点
	 */
	private List<Point> damagesPoints = new ArrayList<Point>();
	
	/**
	 * 种群中最优秀的个体
	 */
	private Individual bestOne;
	


	public Individual getBestOne() {
		return bestOne;
	}

	public void setBestOne(Individual bestOne) {
		this.bestOne = bestOne;
	}

	public List<Point> getResourcesPoints() {
		return resourcesPoints;
	}

	public void setResourcesPoints(List<Point> resourcesPoints) {
		this.resourcesPoints = resourcesPoints;
	}

	public List<Point> getDamagesPoints() {
		return damagesPoints;
	}

	public void setDamagesPoints(List<Point> damagesPoints) {
		this.damagesPoints = damagesPoints;
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
			scheme.setDamagePoints(damagesPoints);
			scheme.setResourcePoints(resourcesPoints);
			scheme.generateIndividual();
			
			schemes.add(scheme);
		}
		
		this.setSchemes(schemes);
	}



	public Individual getIndividual(int index) {
		Individual individual = this.getSchemes().get(index);
		return individual;
	}

	/**
	 * 获取种群最佳的个体
	 */
	public Individual getFittest() {
		
		this.setBestOne(this.getFittest(this.getSchemes()));
		return this.getBestOne();
		
	}
	/**
	 * 从一群个体中选取适应度最高的个体
	 */
	public Individual getFittest(List<Individual> individuals) {
		Individual fittest = null;
		double  fitness = - Double.MAX_VALUE;
		for (Individual scheme: individuals) {
			if (scheme.getFitness() > fitness) {
				fittest = scheme;
				fitness = fittest.getFitness();
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
		// 杂交繁衍下一代
		SchemePopulation pop = new SchemePopulation();
		pop.setResourcesPoints(resourcesPoints);
		pop.setDamagesPoints(damagesPoints);
		List<Individual> schemes = new ArrayList<Individual>();

		// 最好的个体直接进入下一代
		schemes.add(this.getFittest());
		
		while(schemes.size() < this.SCHEME_NUM) {
			Individual scheme = this.generateANewIndividual();
			scheme.mutate();
			if (scheme.isEligible()) {
				schemes.add(scheme);
			}
			
		}
		
		pop.setSchemes(schemes);
		
		// 后代与父代非支配排序
		// ....
		
		return pop;
	}
	
	/**
	 * 使用NSGA2算法的种群迭代
	 * @return
	 */
	public Population evolutionByNSGA2() {
		// 杂交繁衍下一代
		SchemePopulation pop = new SchemePopulation();
		pop.setResourcesPoints(resourcesPoints);
		pop.setDamagesPoints(damagesPoints);
		List<Individual> schemes = new ArrayList<Individual>();

		// 最好的个体直接进入下一代
		schemes.add(this.getFittest());
		
		while(schemes.size() < this.SCHEME_NUM) {
			Individual scheme = this.generateANewIndividual();
			scheme.mutate();
			if (scheme.isEligible()) {
				schemes.add(scheme);
			}
			
		}
		
		schemes.addAll(this.getSchemes());
		
		System.out.println(schemes.size());
		
		// 后代与父代非支配排序
		List<Front> fronts = this.nonDominatedSort(schemes);
		
		return pop;
	}

	private class Front {
		List<Individual> schemes = new ArrayList<Individual>();
		
		void add(Individual scheme) {
			this.schemes.add(scheme);
		}
		
		int size() {
			return this.schemes.size();
		}
	}
	/**
	 * 快速非支配排序
	 * @param schemes2
	 */
	private List<Front> nonDominatedSort(List<Individual> schemes) {
		List<Front> fronts = new ArrayList<SchemePopulation.Front>();
		
		for (Individual p : schemes) {
			for (Individual q : schemes) {
				if (p.isDominate(q)) {
					p.addToDominatedList(q);
				} else {
					p.increaseMembersDominateMe(1);
				}
			}
			
			if (p.getDominateMe() == 0) {
				p.setDominateRank(0);
				fronts.get(0).add(p);
			}
		}
		
		int i = 0;
		while(fronts.get(i).size() > 0) {
			Front front = new Front();
			
			for (Individual p : fronts.get(i).schemes) {
				for (Individual q : p.getSchemesDominated()) {
					q.increaseMembersDominateMe(-1);
					if (q.getDominateMe() == 0) {
						q.setDominateRank(i + 1);
						front.add(q);
					}
				}
			}
			i += 1;
			fronts.set(i, front);
		}
		
		return fronts;
	}

	/**
	 * 杂交生成新个体
	 * @return
	 */
	private Individual generateANewIndividual() {
		List<Individual> list1 = this.genenrateIndividualListByRandom();
		List<Individual> list2 = this.genenrateIndividualListByRandom();
		
		Individual fittestOfList1 = this.getFittest(list1);
		Individual fittestOfList2 = this.getFittest(list2);
		Individual child = fittestOfList1.crossoverWith(fittestOfList2);
		return child;
	}

	private List<Individual> genenrateIndividualListByRandom() {
		List<Individual> schemes = new ArrayList<Individual>();
		Random rd = new Random();
		
		for (int i = 0; i < this.CROSS_NUM; i++) {
			schemes.add(this.getIndividual(rd.nextInt(this.SCHEME_NUM)));
		}
		return schemes;
	}
	@Override
	public String toString() {
		return "SchemePopulation [SCHEME_NUM=" + SCHEME_NUM + ", schemes="
				+ schemes + ", resources=" + resourcesPoints + ", damages=" + damagesPoints
				+ "]";
	}
	
	
	
}
