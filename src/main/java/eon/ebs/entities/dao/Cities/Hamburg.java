package eon.ebs.entities.dao.Cities;

import eon.ebs.entities.dao.Connection.Connection;
import eon.ebs.entities.dto.Tile;

import java.util.List;

public class Hamburg extends City {

	public Hamburg(Tile tile, List<Connection> connectionList) {
		super("Hamburg", "Hamburg", 170, 80, "N/A", -2, tile, connectionList);
	}

}
