package eon.ebs.entities.dao;

import eon.ebs.entities.dto.Plant;
import eon.ebs.entities.dto.Tile;

public class OffshoreBig extends Tile implements Plant {

	private final int MAX_OUTCOME = 0;
	private final int FIX_COSTS = 0;
	private final int REMOVE_COSTS = 0;
	private final int BUILD_TIME = 0;
	private final int REMOVE_TIME = 0;
	private final int ACQUISITION_COST = 0;

	private final int IMAGE_INDEX = 0;

	public OffshoreBig(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public int getIMAGE_INDEX() {
		return IMAGE_INDEX;
	}

	public int getACQUISITION_COST() {
		return ACQUISITION_COST;
	}

	public int getMAX_OUTCOME() {
		return MAX_OUTCOME;
	}

	public int getFIX_COSTS() {
		return FIX_COSTS;
	}

	public int getREMOVE_COSTS() {
		return REMOVE_COSTS;
	}

	public int getBUILD_TIME() {
		return BUILD_TIME;
	}

	public int getREMOVE_TIME() {
		return REMOVE_TIME;
	}
}
