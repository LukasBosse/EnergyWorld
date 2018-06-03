package eon.ebs.entities.dao.Cities;

import eon.ebs.entities.dao.Connection.Connection;
import eon.ebs.entities.dto.Tile;

import java.util.List;

public class Hannover extends City  {

	public Hannover(Tile tile, List<Connection> connectionList) {
		super("Hannover", "Niedersachsen", 50, 40, "N/A", -3, tile, connectionList);
	}

}
