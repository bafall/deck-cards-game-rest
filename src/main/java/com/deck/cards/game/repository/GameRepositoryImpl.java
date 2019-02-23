package com.deck.cards.game.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.deck.cards.game.exception.DeckNotFoundException;
import com.deck.cards.game.exception.GameNotFoundException;
import com.deck.cards.game.model.Card;
import com.deck.cards.game.model.Deck;
import com.deck.cards.game.model.Game;
import com.deck.cards.game.model.GameDeck;
import com.deck.cards.game.model.Player;
import com.deck.cards.game.model.enums.CardSuit;

/**
 * Implementation of Game Repository
 * 
 * @author bfall
 *
 */
@Component
public class GameRepositoryImpl implements IGameRepository {

	private Map<String, Game> gamesMap = new HashMap<String, Game>();

	private Map<String, Deck> decksMap = new HashMap<String, Deck>();

	@Override
	public String createGame() {
		Game game = new Game();
		gamesMap.put(game.getGameId(), game);
		return game.getGameId();
	}

	@Override
	public Optional<Game> getGame(String gameId) {
		return Optional.ofNullable(gamesMap.get(gameId));
	}

	@Override
	public void deleteGame(String gameId) throws GameNotFoundException {
		// Throw exception if game not found
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));
		this.gamesMap.remove(game.getGameId());
	}

	@Override
	public String createDeck() {
		Deck deck = new Deck();
		decksMap.put(deck.getDeckId(), deck);
		return deck.getDeckId();
	}

	@Override
	public Optional<Deck> getDeck(String deckId) {
		return Optional.ofNullable(decksMap.get(deckId));
	}

	@Override
	public void addDeck(String gameId, String deckId) throws GameNotFoundException {
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));

		Deck deck = this.getDeck(deckId)
				.orElseThrow(() -> new DeckNotFoundException(String.format("Deck[%s] not found.", deckId)));
		
		game.getGameDeck().getCards().addAll(deck.getCards());
	}

	@Override
	public void dealCards(String gameId, int nbCards) throws GameNotFoundException {
		if(nbCards <= 0) {
			throw new IllegalArgumentException("NbCards must be > 0");
		}

		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));

		IntStream cardStream = IntStream.range(0, nbCards);

		if (game.getGameDeck().getCards() != null && !game.getGameDeck().getCards().isEmpty()) {
			cardStream.forEach(cardIndex -> game.getPlayers().forEach(player -> {
				Optional<Card> cardToDeal = dealCard(game.getGameDeck());
				if (cardToDeal.isPresent()) {
					player.getCards().add(cardToDeal.get());
				}
				
			}));
		}
	}

	@Override
	public void addPlayer(String gameId, String playerId) throws GameNotFoundException {
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));

		Optional<Player> player = getPlayer(playerId, game);

		if (player.isPresent()) {
			throw new IllegalArgumentException(String.format("Player[%s] already exists.", playerId));
		}
		game.getPlayers().add(new Player(playerId));
	}

	@Override
	public void removePlayer(String gameId, String playerId) throws GameNotFoundException {
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));

		Player player = getPlayer(playerId, game)
				.orElseThrow(() -> new IllegalArgumentException(String.format("Player[%s] not found.", playerId)));

		game.getPlayers().remove(player);
	}

	@Override
	public List<Card> getListCards(String gameId, String playerId) throws GameNotFoundException {
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));

		Player player = getPlayer(playerId, game)
				.orElseThrow(() -> new IllegalArgumentException(String.format("Player[%s] not found.", playerId)));

		return player.getCards();
	}

	@Override
	public List<Player> getListPlayersOrderByScoreDesc(String gameId) throws GameNotFoundException {
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));
		List<Player> players = game.getPlayers();
		Collections.sort(players);
		return players;
	}

	@Override
	public Map<CardSuit, Integer> getUndealtCardSuits(String gameId) throws GameNotFoundException {
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));
		Map<CardSuit, Integer> map = new HashMap<CardSuit, Integer>();
		for (Card card : game.getGameDeck().getCards()) {
			if (!map.containsKey(card.getSuit())) {
				map.put(card.getSuit(), 1);
			} else {
				map.computeIfPresent(card.getSuit(), (key, value) -> value++);
			}
		}
		return map;
	}

	@Override
	public Map<Card, Integer> getSortedUndealtCards(String gameId) throws GameNotFoundException {
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));
		Map<Card, Integer> map = new HashMap<Card, Integer>();
		for (Card card : game.getGameDeck().getCards()) {
			if (!map.containsKey(card)) {
				map.put(card, 1);
			} else {
				map.computeIfPresent(card, (key, value) -> value++);
			}
		}
		// Sort the list from the High to low
		Map<Card, Integer> sortedDecMap = new TreeMap<Card, Integer>(new Comparator<Card>() {

			@Override
			public int compare(Card card1, Card card2) {
				return card2.compareTo(card1);
			}
		});
		sortedDecMap.putAll(map);
		return map;
	}

	@Override
	public void shuffle(String gameId) throws GameNotFoundException {
		Game game = this.getGame(gameId)
				.orElseThrow(() -> new GameNotFoundException(String.format("Game[%s] not found.", gameId)));

		Random random = new Random();
		int size = game.getGameDeck().getCards().size();
		for (int i = 0; i < game.getGameDeck().getCards().size(); i++) {
			Card card = game.getGameDeck().getCards().get(i);

			int indexRnd = random.nextInt(size - i) + i;
			game.getGameDeck().getCards().set(i, game.getGameDeck().getCards().get(indexRnd));
			game.getGameDeck().getCards().set(indexRnd, card);
		}
	}

	private Optional<Card> dealCard(GameDeck gameDeck) {
		return gameDeck.getCards().isEmpty() ? Optional.empty() : Optional.of(gameDeck.getCards().pop());
	}

	private Optional<Player> getPlayer(String playerId, Game game) {
		return game.getPlayers().stream().filter(p -> playerId.equals(p.getPlayerId())).findAny();
	}

	public Map<String, Game> getGamesMap() {
		return gamesMap;
	}

	public void setGamesMap(Map<String, Game> gamesMap) {
		this.gamesMap = gamesMap;
	}

	public Map<String, Deck> getDecksMap() {
		return decksMap;
	}

	public void setDecksMap(Map<String, Deck> decksMap) {
		this.decksMap = decksMap;
	}

}
