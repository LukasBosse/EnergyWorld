package eon.ebs.entities.dao.Market;

import eon.ebs.entities.dao.Connection.MarketConnection;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Market {

	private final double BUY_MAX = 1;
	private final double BUY_MIN = -4d;
	private final double SELL_MAX = 4d;
	private final double SELL_MIN = -1d;

	private double sellPrice = 1;
	private double buyPrice = 1;
	private List<MarketConnection> customers;

	public Market() {
		customers = new LinkedList<>();
	}

	public int update() {
		int saldoSum = 0;
		for(MarketConnection mC : customers) {
			int saldoSolo = 0;
			int effectCity = mC.getCity().getEnergyDifference();
			if(effectCity > 0) {
				saldoSolo = (int) (effectCity * sellPrice);
			} else if(effectCity < 0) {
				saldoSolo = (int) (effectCity * buyPrice);
			}
			mC.setSaldo(saldoSolo);
			saldoSum += saldoSolo;
		}
		return saldoSum;
	}

	private void updatePrice() {
		sellPrice = Math.random() * (SELL_MAX - SELL_MIN) + SELL_MIN;
		buyPrice = Math.random() * (BUY_MAX - BUY_MIN) + BUY_MIN;
	}

	public void addCustomer(MarketConnection customer) {
		customers.add(customer);
	}

	public void removeCustomer(MarketConnection customer) {
		customers.remove(customer);
	}

	public List<MarketConnection> getCustomers() {
		return customers;
	}

	public void setCustomers(List<MarketConnection> customers) {
		this.customers = customers;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
}
