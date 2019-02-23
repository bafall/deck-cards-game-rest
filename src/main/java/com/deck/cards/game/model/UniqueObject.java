package com.deck.cards.game.model;

import java.util.UUID;

public abstract class UniqueObject {

	private final String uID;

	public UniqueObject() {
		uID = UUID.randomUUID().toString();
	}

	public String getUID() {
		return uID;
	}
}
