package com.deck.cards.game.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.deck.cards.game.exception.GameNotFoundException;
import com.deck.cards.game.model.Card;
import com.deck.cards.game.model.Deck;
import com.deck.cards.game.model.Game;
import com.deck.cards.game.model.Player;
import com.deck.cards.game.model.enums.CardSuit;

public interface IGameRepository {

	String createGame();

	Optional<Game> getGame(String gameId);

	void deleteGame(String gameId) throws GameNotFoundException;

	String createDeck();

	Optional<Deck> getDeck(String deckId);

	void addDeck(String gameId, String deckId) throws GameNotFoundException;

	void dealCards(String gameId, int nbCards) throws GameNotFoundException;
	
	void addPlayer(String gameId, String playerId) throws GameNotFoundException;

	void removePlayer(String gameId, String playerId) throws GameNotFoundException;
	
	List<Card> getListCards(String gameId, String playerId) throws GameNotFoundException;

	List<Player> getListPlayersOrderByScoreDesc(String gameId) throws GameNotFoundException;

	Map<CardSuit, Integer> getUndealtCardSuits(String gameId) throws GameNotFoundException;

	Map<Card, Integer> getSortedUndealtCards(String gameId) throws GameNotFoundException;

	void shuffle(String gameId) throws GameNotFoundException;

}
