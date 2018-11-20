package ru.GeneticAlgorithm.TravellingSalesMan.Task;

public class Route {
	private City route[];
	private double distance = 0;

	/**
	 * Initialize Route
	 * @param chromosome - Genetic algorithm  chromosome
	 * @param cities - The cities referenced
	 */
	public Route(Chromosome chromosome, City cities[]) {
		// Get chromosome
		int chromosomes[] = chromosome.getChromosome();
		// Create route
		route = new City[cities.length];
		for (int geneIndex = 0; geneIndex < chromosomes.length; geneIndex++) {
			route[geneIndex] = cities[chromosomes[geneIndex]];
		}
	}

	/**
	 * Get route distance
	 * @return distance - The route distance
	 */
	public double getDistance() {
		if (distance > 0) {
			return distance;
		}
		// Calculate total route distance
		double totalRouteDistance = 0;
		for (int cityIndex = 0; cityIndex + 1 < route.length; cityIndex++) {
			totalRouteDistance += route[cityIndex].distanceFromCity(route[cityIndex + 1]);
		}
		totalRouteDistance += route[route.length - 1].distanceFromCity(route[0]);
		distance = totalRouteDistance;
		return totalRouteDistance;
	}
}