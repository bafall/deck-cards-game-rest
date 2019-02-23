package com.deck.cards.game.model;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player> {

	private String playerId;

	private List<Card> cards = new ArrayList<Card>();

	private int score = 0;

	public Player(String playerId) {
		super();
		this.playerId = playerId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public int getScore() {
		this.score = cards.stream().mapToInt(card -> card.getFaceValue().getValue()).sum();
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Player otherPlayer) {
		return Integer.compare(otherPlayer.getScore(), this.getScore());
	}

}
