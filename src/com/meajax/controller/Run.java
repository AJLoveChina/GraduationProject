package com.meajax.controller;


import com.meajax.model.interfaces.GA;
import com.meajax.model.interfaces.Individual;
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
		Individual scheme = population.getFittest();
		System.out.println("Initial best individual: fit val = " + scheme.getFitness());
		System.out.println(scheme.toString());
	}
	
	
	private void evolution(int iterations) {
		for (int i = 0; i < iterations; i++) {
			this.setPopulation(population.evolutionByNSGA2());
        }
		System.out.println("Finished (iterations = " + iterations + "): ");
        Individual scheme = population.getFittest();
		System.out.println("Best Individual : fit val = " + scheme.getFitness());
		System.out.println(scheme.toString());
	}
	
	public void start(int iterations) {
		this.evolution(iterations);
	}
	
}
