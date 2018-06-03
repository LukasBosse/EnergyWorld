package eon.ebs.entities.dao.Cities;

import eon.ebs.entities.dao.Connection.Connection;
import eon.ebs.entities.dto.Tile;

import java.util.List;

public class Bremen extends City {

	public Bremen(Tile tile, List<Connection> connectionList) {
		super("Bremen", "Bremen", 55, 50, "N/A", -2, tile,connectionList);
	}

}
