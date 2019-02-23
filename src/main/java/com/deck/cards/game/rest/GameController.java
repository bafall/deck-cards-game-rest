package com.deck.cards.game.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deck.cards.game.model.Card;
import com.deck.cards.game.model.Player;
import com.deck.cards.game.model.enums.CardSuit;
import com.deck.cards.game.repository.IGameRepository;

@RestController
public class GameController {
	
	@Autowired
	private IGameRepository gameRepository;

	@RequestMapping(value = "/game/new", method = RequestMethod.GET)
	String createGame() {
		return gameRepository.createGame();
	}

	@RequestMapping(value = "/game/delete", method = RequestMethod.DELETE)
	void deleteGame(String gameId) {
		gameRepository.deleteGame(gameId);
	}

	@RequestMapping(value = "/game/deck/new", method = RequestMethod.GET)
	String createDeck() {
		return gameRepository.createDeck();
	}

	@RequestMapping(value = "/game/{id}/deck/add/{deckid}", method = RequestMethod.POST)
	void addDeck(@PathVariable("id") String gameId, @PathVariable("deckid") String deckId) {
		gameRepository.addDeck(gameId, deckId);
	}

	@RequestMapping(value = "/game/deal", method = RequestMethod.GET)
	void dealCards(String gameId, int nbCards) {
		gameRepository.dealCards(gameId, nbCards);
	}
	
	@RequestMapping(value = "/game/{id}/player/add/{playerid}", method = RequestMethod.POST)
	void addPlayer(String gameId, @PathVariable("playerid") String playerId) {
		gameRepository.addPlayer(gameId, playerId);
	}

	@RequestMapping(value = "/game/player/remove", method = RequestMethod.DELETE)
	void removePlayer(String gameId, String playerId) {
		gameRepository.removePlayer(gameId, playerId);
	}
	
	@RequestMapping(value = "/game/player/cards", method = RequestMethod.GET)
	public List<Card> getListCards(String gameId, String playerId) {
		return gameRepository.getListCards(gameId, playerId);
	}

	@RequestMapping(value = "/game/players", method = RequestMethod.GET)
	List<Player> getListPlayers(String gameId) {
		return gameRepository.getListPlayersOrderByScoreDesc(gameId);
	}

	@RequestMapping(value = "/game/undealtcardsuits", method = RequestMethod.GET)
	Map<CardSuit, Integer> getUndealtCardSuits(String gameId) {
		return gameRepository.getUndealtCardSuits(gameId);
	}

	@RequestMapping(value = "/game/undealtcards", method = RequestMethod.GET)
	Map<Card, Integer> getSortedUndealtCards(String gameId) {
		return gameRepository.getSortedUndealtCards(gameId);
	}

	@RequestMapping(value = "/game/{id}/shuffle", method = RequestMethod.POST)
	void shuffle(@PathVariable("id") String gameId) {
		gameRepository.shuffle(gameId);
	}

}
