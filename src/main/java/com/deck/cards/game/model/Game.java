package com.deck.cards.game.model;

import java.util.ArrayList;
import java.util.List;

public class Game extends UniqueObject {

	private String gameId;

	private final GameDeck gameDeck;

	private List<Player> players;

	public Game() {
		super();
		this.setGameId(getUID());
		gameDeck = new GameDeck();
		players = new ArrayList<Player>();
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public GameDeck getGameDeck() {
		return gameDeck;
	}

}
