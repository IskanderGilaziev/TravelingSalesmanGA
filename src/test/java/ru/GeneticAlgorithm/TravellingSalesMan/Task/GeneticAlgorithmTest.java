package ru.GeneticAlgorithm.TravellingSalesMan.Task;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GeneticAlgorithmTest {
    public int numberCities =10;
    public int populationSize = 20;
    public double mutation = 0.1;
    public double crossover = 0.9;
    public int diffCount = 2;
    public int tournament =4;
    public  int maxGeneration = 50;
    public  City cities[];
    public  GeneticAlgorithm geneticAlgorithm;
    public  Population population;
    public  Chromosome chromosomes [];


    @Before
    public  void init(){
        cities = new City[numberCities];
        for (int cityIndex = 0; cityIndex < numberCities; cityIndex++) {
            int xPos = (int) (10 * Math.random());
            int yPos = (int) (10 * Math.random());
            cities[cityIndex] = new City(xPos, yPos);
        }
        geneticAlgorithm = new GeneticAlgorithm(populationSize,mutation,crossover,diffCount,tournament);
        population = geneticAlgorithm.initPopulation(cities.length);
        chromosomes = population.getChromosomePopulation();
    }

    @Test
    public void crossoverPopulationTest(){
        Population crossoverPopulation = geneticAlgorithm.crossoverPopulation(population);
        assertNotEquals(population,crossoverPopulation);
        assertTrue(crossoverPopulation.getPopulationPass()== population.getPopulationPass());

        geneticAlgorithm.evaluation(crossoverPopulation,cities);
        assertTrue(crossoverPopulation.getPopulationPass() != population.getPopulationPass());
    }

    @Test
    public  void mutatePopulationTest(){
       Population mutatePopulation = geneticAlgorithm.mutatePopulation(population);
       assertNotEquals(population,mutatePopulation);
       assertTrue(mutatePopulation.getPopulationPass() == population.getPopulationPass());

       geneticAlgorithm.evaluation(mutatePopulation,cities);
       assertTrue(mutatePopulation.getPopulationPass() != population.getPopulationPass());

    }

    @Test
    public  void evaluationTest(){

        geneticAlgorithm.evaluation(population,cities);
        double testPass1 = population.getPopulationPass();

        population = geneticAlgorithm.crossoverPopulation(population);
        population = geneticAlgorithm.mutatePopulation(population);
        geneticAlgorithm.evaluation(population,cities);
        double testPass2 = population.getPopulationPass();

        assertFalse(testPass1 == testPass2);
    }

    @Test
    public  void calculatePassTest(){
        for (Chromosome chromosome : chromosomes) {
            Route route = new Route(chromosome, cities);
            double pass = 1/route.getDistance();
            double passTest = geneticAlgorithm.calculatePass(chromosome,cities);
            assertTrue(passTest == pass);
        }
    }

    @Test
    public  void selectParentPopulation(){
        Chromosome parentChromosome = geneticAlgorithm.selectParentPopulation(population);
        assertEquals(parentChromosome,population.findFittest(0));

        population.mixPopulation();
        assertNotEquals(parentChromosome,population.findFittest(0));
    }

}
