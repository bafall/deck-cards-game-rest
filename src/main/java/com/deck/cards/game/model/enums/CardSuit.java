package com.deck.cards.game.model.enums;

public enum CardSuit {
	CLUB(1), SPADE(2), HEART(3), DIAMOND(4);

	private final int value;

	private CardSuit(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
