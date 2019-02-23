package com.deck.cards.game.exception;

public class DeckNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeckNotFoundException() {
		super();
	}

	public DeckNotFoundException(String message) {
		super(message);
	}

	public DeckNotFoundException(Throwable cause) {
		super(cause);
	}
}