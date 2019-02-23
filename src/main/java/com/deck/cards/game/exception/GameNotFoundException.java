package com.deck.cards.game.exception;

public class GameNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameNotFoundException() {
		super();
	}

	public GameNotFoundException(String message) {
		super(message);
	}

	public GameNotFoundException(Throwable cause) {
		super(cause);
	}
}