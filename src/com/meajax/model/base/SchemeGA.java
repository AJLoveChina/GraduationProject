package com.meajax.model.base;

import java.util.Random;
import com.meajax.model.interfaces.GA;
import com.meajax.model.interfaces.Individual;
import com.meajax.model.interfaces.Population;


public class SchemeGA implements GA{
    /**
     * 突变概率
     */
    private static final double mutationRate = 0.015;
    /**
     * 从群体中随机选择多少个个体, 然后选择最优秀的个体交叉下一代
     */
    private static final int tournamentSize = 5;
    /**
     * elitism 为 true 表示最优秀的个体直接进入下一代
     */
    private static final boolean elitism = true;
    
    
	public Population evolvePopulation(Population population) {
		
		Population newPop = new SchemePopulation();
		
		// 最优秀的个体是否直接进入子代
		int offset = 0;
		if (elitism) {
			newPop.saveIndividual(population.getFittest());
			offset = 1;
		}
		
		while(newPop.populationSize() < population.populationSize()) {
			Individual father = tournamentSelection(population);
			Individual mother = tournamentSelection(population);
			
			Individual child = this.crossover(father, mother);
			newPop.saveIndividual(child);
		}
		
		
		for (int i = offset; i < newPop.populationSize(); i++) {
			this.mutate(newPop.getIndividual(i));
		}
		return newPop;
		
	}

	public Individual crossover(Individual father, Individual mother) {
		return father.crossoverWith(mother);
	}

	public void mutate(Individual individual) {
		if(Math.random() < mutationRate) {
			individual.mutate();
		}
	}

	public Individual tournamentSelection(Population population) {
		Population tournament = new SchemePopulation();
        Random rd = new Random();
        
        for (int i = 0; i < tournamentSize; i++) {
            int random = rd.nextInt(population.populationSize());
            tournament.saveIndividual(population.getIndividual(random));
        }
        
        Individual fittest = tournament.getFittest();
        return fittest;
	}
	
}
