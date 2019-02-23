package com.deck.cards.game.model;

import java.util.Stack;

public class GameDeck {

	private Stack<Card> cards;

	public GameDeck() {
		cards = new Stack<Card>();
	}

	public Stack<Card> getCards() {
		return cards;
	}

	public void setCards(Stack<Card> cards) {
		this.cards = cards;
	}

}
