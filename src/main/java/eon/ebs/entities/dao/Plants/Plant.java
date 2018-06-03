package eon.ebs.entities.dao.Plants;

import eon.ebs.entities.dto.Tile;

public class Plant extends Tile {

	private int tileID_Construction;
	private int tileID;

	private int MAX_OUTCOME; // Max. Erzeugung in EE
	private int FIX_COSTS; // Fixkosten in GE
	private int REMOVE_COSTS; // Rückbaukosten in GE
	private double BUILD_TIME; // Bauzeit in min
	private double REMOVE_TIME; // Rückbauzeit in min
	private double PRICE_PER_WORKLOAD_PERCENT; // Preis pro Auslastungsprozent in GE
	private int ACQUISITION_COST; // Anschaffungskosten in GE

	private int radius;
	private int effectShortRadius;
	private int effectMiddleRadius;
	private int effectLongRadius;
	private int plantHeight;
	private int plantWidth;

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

	private double workload = 1d;
	private double outcome = PRICE_PER_WORKLOAD_PERCENT * workload;

	public Plant(int x, int y, int width, int height, int MAX_OUTCOME, int FIX_COSTS, int REMOVE_COSTS, double BUILD_TIME, double REMOVE_TIME,
			double PRICE_PER_WORKLOAD_PERCENT, int ACQUISITION_COST, int radius, int effectLongRadius, int effectMiddleRadius, int effectShortRadius, int plantWidth, int plantHeight, int minX, int maxX, int minY, int maxY, int tileID, int tileID_Construction) {
		super(x, y, width, height);
		this.MAX_OUTCOME = MAX_OUTCOME;
		this.FIX_COSTS = FIX_COSTS;
		this.REMOVE_COSTS = REMOVE_COSTS;
		this.BUILD_TIME = BUILD_TIME;
		this.REMOVE_TIME = REMOVE_TIME;
		this.PRICE_PER_WORKLOAD_PERCENT = PRICE_PER_WORKLOAD_PERCENT;
		this.ACQUISITION_COST = ACQUISITION_COST;
		this.radius = radius;
		this.effectLongRadius = effectLongRadius;
		this.effectMiddleRadius = effectMiddleRadius;
		this.effectShortRadius = effectShortRadius;
		this.plantHeight = plantHeight;
		this.plantWidth = plantWidth;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.tileID = tileID;
		this.tileID_Construction = tileID_Construction;
		this.setWidth(calcWidth());
		this.setHeight(calcHeight());
	}

	public int getTileID_Construction() { return tileID_Construction; }

	public void setTileID_Construction(int tileID_Construction) { this.tileID_Construction = tileID_Construction; }

	public int getTileID() { return tileID; }

	public void setTileID(int tileID) {	this.tileID = tileID; }

	public int getMinX() { return minX; }

	public void setMinX(int minX) { this.minX = minX; }

	public int getMaxX() { return maxX; }

	public void setMaxX(int maxX) { this.maxX = maxX; }

	public int getMinY() { return minY; }

	public void setMinY(int minY) {	this.minY = minY; }

	public int getMaxY() { return maxY; }

	public void setMaxY(int maxY) { this.maxY = maxY; }

	private int calcWidth() { return getPlantWidth(); }

	private int calcHeight() { return getPlantHeight(); }

	public int getPlantWidth() { return plantWidth; }

	public int getPlantHeight() { return plantHeight; }

	public int calcCosts() {
		outcome = MAX_OUTCOME * workload;
		return (int) (FIX_COSTS + (workload * 100) * PRICE_PER_WORKLOAD_PERCENT);
	}

	public int getEffectShortRadius() { return effectShortRadius;  }

	public void setEffectShortRadius(int effectShortRadius) {
		this.effectShortRadius = effectShortRadius;
	}

	public int getEffectMiddleRadius() {
		return effectMiddleRadius;
	}

	public void setEffectMiddleRadius(int effectMiddleRadius) {
		this.effectMiddleRadius = effectMiddleRadius;
	}

	public int getEffectLongRadius() { return effectLongRadius; }

	public void setEffectLongRadius(int effectLongRadius) {
		this.effectLongRadius = effectLongRadius;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getMAX_OUTCOME() {
		return MAX_OUTCOME;
	}

	public void setMAX_OUTCOME(int MAX_OUTCOME) {
		this.MAX_OUTCOME = MAX_OUTCOME;
	}

	public int getFIX_COSTS() {
		return FIX_COSTS;
	}

	public void setFIX_COSTS(int FIX_COSTS) {
		this.FIX_COSTS = FIX_COSTS;
	}

	public int getREMOVE_COSTS() {
		return REMOVE_COSTS;
	}

	public void setREMOVE_COSTS(int REMOVE_COSTS) {
		this.REMOVE_COSTS = REMOVE_COSTS;
	}

	public double getBUILD_TIME() {
		return BUILD_TIME;
	}

	public void setBUILD_TIME(double BUILD_TIME) {
		this.BUILD_TIME = BUILD_TIME;
	}

	public double getREMOVE_TIME() {
		return REMOVE_TIME;
	}

	public void setREMOVE_TIME(double REMOVE_TIME) {
		this.REMOVE_TIME = REMOVE_TIME;
	}

	public double getPRICE_PER_WORKLOAD_PERCENT() {
		return PRICE_PER_WORKLOAD_PERCENT;
	}

	public void setPRICE_PER_WORKLOAD_PERCENT(double PRICE_PER_WORKLOAD_PERCENT) {
		this.PRICE_PER_WORKLOAD_PERCENT = PRICE_PER_WORKLOAD_PERCENT;
	}

	public int getACQUISITION_COST() {
		return ACQUISITION_COST;
	}

	public void setACQUISITION_COST(int ACQUISITION_COST) {
		this.ACQUISITION_COST = ACQUISITION_COST;
	}
}
