package eon.ebs.entities.dao.Connection;

import eon.ebs.entities.dao.Cities.City;

public class MarketConnection {

	private City city;
	private double saldo = 0.0;
	private boolean status = false;

	public MarketConnection(City city) {
		this.city = city;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
