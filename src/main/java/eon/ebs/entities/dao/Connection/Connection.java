package eon.ebs.entities.dao.Connection;

import com.badlogic.gdx.math.Vector3;
import eon.ebs.entities.dao.Cities.City;
import eon.ebs.entities.dao.Plants.Plant;

public class Connection {

	private City city;
	private Plant plant;
	private boolean active = false;
	private String name;
	private double distance;
	private double sendedEnergy;
	private double recievedEnergy;

	public Connection(City city, Plant plant) {
		this.city = city;
		this.plant = plant;
		this.name = city.getCityName() + plant.getClass().getName();
		this.sendedEnergy = plant.getMAX_OUTCOME();
		this.recievedEnergy = calcIncome();
	}

	private double calcIncome() {
		double distance = calcDistance(getPlant(),getCity());
		if(distance > 0 && distance <= 20) {
			return getPlant().getMAX_OUTCOME();
		}
		if(distance > 20 && distance <= 40) {
			return getPlant().getMAX_OUTCOME() * 0.8;
		}
		if(distance > 40 && distance <= 60) {
			return getPlant().getMAX_OUTCOME() * 0.6;
		}
		if(distance > 60 && distance <= 80) {
			return getPlant().getMAX_OUTCOME() * 0.4;
		}
		if(distance > 80 && distance <= 100) {
			return getPlant().getMAX_OUTCOME() * 0.2;
		}
		return 0;
	}

	public double calcDistance(Plant plant, City city) {
		Vector3 d = new Vector3(plant.getX(), plant.getY(), 0).sub(new Vector3(city.getTile().getX(), city.getTile().getY(), 0));
		return Math.sqrt(Math.pow(d.x,2) + Math.pow(d.y,2) + Math.pow(d.z, 2));
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getSendedEnergy() {
		return sendedEnergy;
	}

	public void setSendedEnergy(double sendedEnergy) {
		this.sendedEnergy = sendedEnergy;
	}

	public double getRecievedEnergy() {
		return recievedEnergy;
	}

	public void setRecievedEnergy(double recievedEnergy) {
		this.recievedEnergy = recievedEnergy;
	}
}
