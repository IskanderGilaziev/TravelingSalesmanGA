package ru.GeneticAlgorithm.TravellingSalesMan.Task;

public class TravellingSalesman {

	public static void main(String[] args) {
		int numberCities = 10;
		int maxGenerations = 50;
		int populationSize = 20;
		double mutation = 0.1;
		double crossover = 0.9;
		int diffCount = 2;
		int tournamentSize = 4;

		City cities[] = new City[numberCities];
		// Create random cities
		for (int cityIndex = 0; cityIndex < numberCities; cityIndex++) {
			int xPos = (int) (10 * Math.random());
			int yPos = (int) (10 * Math.random());
			cities[cityIndex] = new City(xPos, yPos);
		}

		GeneticAlgorithm algorithm = new GeneticAlgorithm(populationSize,mutation,crossover,diffCount,tournamentSize);
		Population population = algorithm.initPopulation(cities.length);

		// Evaluate population
		algorithm.evaluation(population, cities);

		Route startRoute = new Route(population.findFittest(0), cities);
		System.out.println("Start distance: " + startRoute.getDistance());

		// Generation counter
		int generationCount = 1;
		while (!algorithm.isTermination(generationCount, maxGenerations)) {
			// Print fittest salesman from population
			Route route = new Route(population.findFittest(0), cities);
			System.out.println("SalesMan generation "+generationCount+" distance covered: " + route.getDistance());

			// Apply crossover and mutation
			population = algorithm.crossoverPopulation(population);
			population = algorithm.mutatePopulation(population);

			// Evaluate population
			algorithm.evaluation(population, cities);
			generationCount++;
		}

		Route route = new Route(population.findFittest(0), cities);
		System.out.println("SalesMan best distance: " + route.getDistance());

	}
}
