package ru.GeneticAlgorithm.TravellingSalesMan.Task;
import java.util.Arrays;

public class GeneticAlgorithm {
	
    private int populationSize;
    private double mutation;
    private double crossover;
    private int diffCount;
	private int tournament;

	public GeneticAlgorithm(int populationSize, double mutation,
                            double crossover, int diffCount, int tournamentSize) {
		
        this.populationSize = populationSize;
        this.mutation = mutation;
        this.diffCount = diffCount;
        this.crossover = crossover;
        this.tournament = tournamentSize;
	}

    /**
     * Check the excess of the permissible number of generations.
     * @param generationCount - Number of generations passed
     * @param maxGenerations - Max number of generations to terminate after
     * @return
     */
    public boolean isTermination(int generationCount, int maxGenerations) {
        return (generationCount > maxGenerations);
    }
    /**
     * Initialize population
     * @param chromosomeLength - The length of the salesman chromosome
     * @return population - The initial population generated
     */
    public Population initPopulation(int chromosomeLength){
        Population population = new Population(populationSize, chromosomeLength);
        return population;
    }

	/**
	 * Calculate chromosome pass value
	 * @param chromosome - the chromosome to evaluate
	 * @param cities - the cities being referenced
	 * @return double - The pass value for chromosome
	 */
    public double calculatePass(Chromosome chromosome, City cities[]){
        Route route = new Route(chromosome, cities);
        double pass = 1/route.getDistance();
        chromosome.setPass(pass);
        return pass;
    }

    /**
     * Population estimate
     * @param population - population to evaluate
     * @param cities - cities being referenced
     */
    public void evaluation(Population population, City cities[]){
        double populationPass = 0;
        for (Chromosome chromosome : population.getChromosomePopulation()) {
            populationPass += calculatePass(chromosome, cities);
        }
        double midPass = populationPass / population.size();
        population.setPopulationPass(midPass);
    }
 
	/**
	 * Select parent for crossover using tournament selection
	 * @param population
	 * @return The salesman selected as a parent
	 */
	public Chromosome selectParentPopulation(Population population) {
		Population parentSalesMan = new Population(tournament);
		population.mixPopulation();
		for (int i = 0; i < tournament; i++) {
			Chromosome parentChromosome = population.getSalesMan(i);
            parentSalesMan.setSalesMan(i, parentChromosome);
		}
		return parentSalesMan.findFittest(0);
	}

    /**
	 * Ordered crossover mutation
	 * Chromosomes in the travel salesman require that each city is visited exactly once.
     * This would lead to one city being visited twice and another city being skipped altogether.
	 * A uniform or random crossover doesn't really save specific order group of cities.
	 * @param population
	 * @return new population
	 */
    public Population crossoverPopulation(Population population){
        Population newPopulation = new Population(population.size());
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            // Get first parent
            Chromosome firstParent = population.findFittest(populationIndex);
            if (crossover > Math.random() && populationIndex >= diffCount) {

                // Find second parent with tournament selection
                Chromosome secondParent = selectParentPopulation(population);

                // Create offspring chromosome
                int offspringChromosome[] = new int[firstParent.getChromosomeLength()];
                Arrays.fill(offspringChromosome, -1);
                Chromosome offspring = new Chromosome(offspringChromosome);

                // Get subset of parent chromosomes
                // Minimum start and maximum end
                int subSetPositionOne = (int) (Math.random() * firstParent.getChromosomeLength());
                int subSetPositionTwo = (int) (Math.random() * firstParent.getChromosomeLength());
                int start = Math.min(subSetPositionOne, subSetPositionTwo);
                int end = Math.max(subSetPositionOne, subSetPositionTwo);

                // Add the sub tour from first parent to our child
                for (int i = start; i < end; i++) {
                    offspring.setGene(i, firstParent.getGene(i));
                }

                // Walk through the city of the second parent
                for (int i = 0; i < secondParent.getChromosomeLength(); i++) {
                    int secondParentGene = i + end;
                    if (secondParentGene >= secondParent.getChromosomeLength()) {
                        secondParentGene -= secondParent.getChromosomeLength();
                    }

                    // Add a city for posterity in case of its absence
                    if (!offspring.containsGene(secondParent.getGene(secondParentGene))) {
                        for (int j = 0; j < offspring.getChromosomeLength(); j++) {
                            // Free position found and add city
                            if (offspring.getGene(j) == -1) {
                                offspring.setGene(j, secondParent.getGene(secondParentGene));
                                break;
                            }
                        }
                    }
                }

                // Add new population
                newPopulation.setSalesMan(populationIndex, offspring);
            } else {
                // Add salesman to new population without applying crossover
                newPopulation.setSalesMan(populationIndex, firstParent);
            }
        }
        return newPopulation;
    }

    /**
	 * Apply mutation to population
     * The first step of this method is to create a new population to hold the mutated salesmans.
     * Next, the population is looped over starting with the fittest salesmans.
     * If elitism is enabled, the first few salesmans are skipped and added to the new population unaltered.
     * The chromosomes from the remaining salesmans are then looped over and each gene is considered for mutation
     * individually depending on the mutation rate.
     * If a gene is to be mutated, another random gene from the salesman is picked and the genes are swapped.
     * Finally, the mutated salesmans is added to the new population.
	 * @param population - The population to apply mutation
	 * @return  mutated population
	 */
    public Population mutatePopulation(Population population){

        Population mutatePopulation = new Population(populationSize);
        for (int indexPopulation = 0; indexPopulation < population.size(); indexPopulation++) {
            Chromosome chromosome = population.findFittest(indexPopulation);
            // Skip mutation if this is an different chromosome
            if (indexPopulation >= diffCount) {
                for (int mutateGene = 0; mutateGene < chromosome.getChromosomeLength(); mutateGene++) {
                    if (mutation > Math.random()) {
                        // Get new gene random position
                        int newGenePosition = (int) (Math.random() * chromosome.getChromosomeLength());
                        // Get genes to swap
                        int newGene = chromosome.getGene(newGenePosition);
                        int mutGene = chromosome.getGene(mutateGene);
                        // Swap genes
                        chromosome.setGene(mutateGene, newGene);
                        chromosome.setGene(newGenePosition, mutGene);
                    }
                }
            }
            // Add chromosome to population
            mutatePopulation.setSalesMan(indexPopulation, chromosome);
        }
        // Return mutated population
        return mutatePopulation;
    }
}
