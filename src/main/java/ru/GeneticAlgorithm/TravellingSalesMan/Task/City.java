package ru.GeneticAlgorithm.TravellingSalesMan.Task;

public class City {
    private int xCoordinate;
    private int yCoordinate;


    public City(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public  double distanceFromCity(City city){
       // Find the difference in x, y coordinates
        double deltaX = Math.pow((city.getXCoordinate() - this.getXCoordinate()), 2);
        double deltaY = Math.pow((city.getYCoordinate() - this.getYCoordinate()), 2);
        // Calculate short distance
        double shortDistance = Math.sqrt(Math.abs(deltaX + deltaY));
        return  shortDistance;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
