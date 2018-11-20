package ru.GeneticAlgorithm.TravellingSalesMan.Task;

public class Chromosome {
	
	// Chromosome array
	private int[] chromosome;
	private double pass = -1;

	/**
	 * Initializes specific chromosome
	 * @param chromosome - Specific chromosome  */
	public Chromosome(int[] chromosome) {
		this.chromosome = chromosome;
	}

	/**
	 * Initializes random chromosome
	 *@param chromosomeLength - Chromosome length
	 */
	public Chromosome(int chromosomeLength) {
		// Create random individual
		int[] individualSalesMan;
		individualSalesMan = new int[chromosomeLength];
		// Set a gene to each sales man
		for (int gene = 0; gene < chromosomeLength; gene++) {
			individualSalesMan[gene] = gene;
		}
		chromosome = individualSalesMan;
	}

	/**
	 * Get chromosome
	 * @return chromosome
	 */
	public int[] getChromosome() {
		return this.chromosome;
	}

	/**
	 * Get chromosome length
	 * @return chromosome length
	 */
	public int getChromosomeLength() {
		return this.chromosome.length;
	}

	/**
	 * Set gene at offset
	 * @param gene - gene
	 * @param offset - offset
	 */
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}

	/**
	 * Get gene at offset
	 * @param offset - offset
	 * @return gene
	 */
	public int getGene(int offset) {
		return chromosome[offset];
	}

	/**
	 * Store individual pass
	 * @param pass - individual pass
	 */
	public void setPass(double pass) {
		this.pass = pass;
	}

	/**
	 * Get individual pass
	 * @return pass
	 */
	public double getPass() {
		return pass;
	}

	public String toString() {
		String output = "";
		for (int gene = 0; gene < chromosome.length; gene++) {
			output += chromosome[gene] + ",";
		}
		return output;
	}

	/**
	 * Search for a specific integer gene in this individual
	 * @param gene - specific integer gene
	 */
	public boolean containsGene(int gene) {
		for (int i = 0; i < chromosome.length; i++) {
			if (chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}
}
