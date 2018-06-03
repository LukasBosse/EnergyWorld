package eon.ebs.entities.dao.Player;

public class Player {

	private String name;
	private String mood = "N/A";
	private int price = 6;
	private double budget = 200000d;
	private double ep = 0;
	private double neededEnergy = 0.0;
	private double maxEnergy = 0.0;
	private int moodNum = 0;

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getPrice() { return price; }

	public void setPrice(int price) { this.setPrice(price); }

	public void setName(String name) {
		this.name = name;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public double getEp() {
		return ep;
	}

	public void setEp(double ep) {
		this.ep = ep;
	}

	public double getNeededEnergy() {
		return neededEnergy;
	}

	public void setNeededEnergy(double neededEnergy) {
		this.neededEnergy = neededEnergy;
	}

	public double getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(double maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	public int getMoodNum() {
		return moodNum;
	}

	public void setMoodNum(int moodNum) {
		this.moodNum = moodNum;
	}

}
