package ru.GeneticAlgorithm.TravellingSalesMan.Task;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Population {
	private Chromosome population[];
	private double populationPass = -1;

	/**
	 * Initializes blank population of salesman
	 * @param populationSize - The size of the population
	 */
	public Population(int populationSize) {
		this.population = new Chromosome[populationSize];
	}

	/**
	 * Initializes population of salesman
	 * @param populationSize -  The size of the population
	 * @param chromosomeLength - The length of the chromosome
	 */
	public Population(int populationSize, int chromosomeLength) {
		this.population = new Chromosome[populationSize];
		for (int count = 0; count < populationSize; count++) {
			Chromosome chromosome = new Chromosome(chromosomeLength);
			this.population[count] = chromosome;
		}
	}

	/**
	 * Get population
	 * @return population
	 */
	public Chromosome[] getChromosomePopulation() {
		return population;
	}

	/**
	 * Find fittest salesman in the population
	 * @param offset
	 * @return salesman - Fittest salesman at offset
	 */
	public Chromosome findFittest(int offset) {
		Arrays.sort(population, new Comparator<Chromosome>() {
			public int compare(Chromosome o1, Chromosome o2) {
				if (o1.getPass() > o2.getPass()) {
					return -1;
				} else if (o1.getPass() < o2.getPass()) {
					return 1;
				}
				return 0;
			}
		});
		// Return the fittest population salesman
		return population[offset];
	}

	/**
	 * Set population pass
	 * @param pass - The population's total pass
	 */
	public void setPopulationPass(double pass) {
		this.populationPass = pass;
	}

	/**
	 * Get population pass
	 * @return populationPass The population's total pass
	 */
	public double getPopulationPass() {
		return populationPass;
	}

	/**
	 * Get population length
	 * @return size - The population length
	 */
	public int size() {
		return population.length;
	}

	/**
	 * Set chromosome at offset
	 * @param chromosome
	 * @param offset
	 * @return chromosome
	 */
	public Chromosome setSalesMan(int offset, Chromosome chromosome) {
		return population[offset] = chromosome;
	}

	/**
	 * Get salesman at offset
	 * @param offset
	 * @return salesman
	 */
	public Chromosome getSalesMan(int offset) {
		return population[offset];
	}

	// Mix the population
	public void mixPopulation() {
		Random random = new Random();
		for (int i = population.length - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);
			Chromosome output = population[index];
			population[index] = population[i];
			population[i] = output;
		}
	}
}