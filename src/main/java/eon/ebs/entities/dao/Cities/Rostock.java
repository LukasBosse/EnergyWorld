package eon.ebs.entities.dao.Cities;

import eon.ebs.entities.dao.Connection.Connection;
import eon.ebs.entities.dto.Tile;

import java.util.List;

public class Rostock extends City {

	public Rostock(Tile tile, List<Connection> connectionList) {
		super("Rostock", "Mecklenburg-Vorpommern", 20, 10, "N/A", -1, tile, connectionList);
	}
}
