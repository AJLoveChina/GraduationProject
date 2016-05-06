package com.meajax.controller;


import com.meajax.model.interfaces.GA;
import com.meajax.model.interfaces.Population;

public class Run {
	private Population population;
	
	
	
	public Population getPopulation() {
		return population;
	}
	public void setPopulation(Population population) {
		this.population = population;
	}


	public Run(Population population) {
		this.setPopulation(population);
		System.out.println("Initial best individual: " + population.getFittest().toString());
	}
	
	
	private void evolution(int iterations) {
		for (int i = 0; i < iterations; i++) {
			this.setPopulation(population.evolution());
        }
		System.out.println("Finished (iterations = " + iterations + "): ");
        System.out.println("Best Individual : ");
        System.out.println(population.getFittest());
	}
	
	public void start(int iterations) {
		this.evolution(iterations);
	}
	
}
