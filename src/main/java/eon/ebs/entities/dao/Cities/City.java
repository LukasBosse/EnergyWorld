package eon.ebs.entities.dao.Cities;

import eon.ebs.entities.dao.Connection.Connection;
import eon.ebs.entities.dao.Plants.Plant;
import eon.ebs.entities.dto.Tile;

import java.util.List;

public class City {

	private String cityName;
	private String area;
	private String status;

	private int maxCustomers;
	private int neededEnergy;
	private int growth;
	private int effect;
	private int energyDifference;
	private int price;

	private Tile tile;
	private List<Connection> connectionList;

	public City(String cityName, String area, int maxCustomers, int neededEnergy, String status, double growth, Tile tile, List<Connection> connectionList) {
		this.cityName = cityName;
		this.area = area;
		this.maxCustomers = maxCustomers;
		this.neededEnergy = neededEnergy;
		this.status = status;
		this.tile = tile;
		this.connectionList = connectionList;
	}

	public void update() {
		if(connectionList != null) {
			growth = 0;
			effect = 0;
			for (Connection c : connectionList) {
				growth += calcEffect(c);
				effect += (int) c.getRecievedEnergy();
			}
			neededEnergy = neededEnergy + growth;
			energyDifference = neededEnergy - effect;
			if(growth > 0) {
				status = "Green";
			} else if(growth == 0) {
				status = "Yellow";
			} else {
				status = "Red";
			}
		}
	}

	private void setPrice(int price) {
		this.price = price;
	}

	public int calcIncome() {
		return effect * price;
	}

	private int calcEffect(Connection c) {
		Plant plant = c.getPlant();
		double distance = c.calcDistance(plant, this);
		double radius = plant.getRadius();
		double radiusShort = radius / 3;
		double radiusMiddle = radius / 2;
		if(distance > 0 && distance <= radiusShort) {
			return plant.getEffectShortRadius();
		}
		if(distance > radiusShort && distance <= radiusMiddle) {
			return plant.getEffectMiddleRadius();
		}
		if(distance > radiusMiddle && distance <= radius) {
			return plant.getEffectLongRadius();
		}
		return 0;
	}

	public int getEnergyDifference() { return energyDifference; }

	public int getEffect() { return effect; }

	public void setEffect(int effect) {	this.effect = effect; }

	public String getCityName() { return cityName; }

	public void setCityName(String cityName) { this.cityName = cityName; }

	public String getArea() { return area; }

	public void setArea(String area) { this.area = area; }

	public int getMaxCustomers() {	return maxCustomers; }

	public void setMaxCustomers(int maxCustomers) {	this.maxCustomers = maxCustomers; }

	public int getNeededEnergy() { return neededEnergy; }

	public void setNeededEnergy(int neededEnergy) { this.neededEnergy = neededEnergy; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }

	public double getGrowth() { return growth; }

	public void setGrowth(int growth) { this.growth = growth; }

	public Tile getTile() { return tile; }

	public void setTile(Tile tile) { this.tile = tile; }

}
