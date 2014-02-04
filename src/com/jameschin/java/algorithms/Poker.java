package com.jameschin.java.algorithms;

import java.util.Random;
import java.util.TreeSet;

/**
 * Poker
 * Note some of the checking logic is not speed optimized but left as-is for stand-alone readability.
 * //TODO tie-breakers, scoring, wagering, folding, discard and select, community cards (Texas Hold'Em).
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class Poker {
	private int[] deck = new int[52]; // stores which player holds each card
	private Player[] players;
	private int numOfPlayers;
	private int turn = 0; // current turn, 0 to numOfPlayers - 1
	private int counter = 0; // total turns taken
	private Random random = new Random();
	
	Poker(String[] playerNames) {
		numOfPlayers = playerNames.length;
		
		// initialize players
		players = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++) {
			players[i] = new Player(playerNames[i]);
		}
		
		// initialize deck
		for (int i = 0; i < deck.length; i++) {
			deck[i] = -1; // card which has not yet been dealt
		}
	}
	
	private class Player {
		String name;
		TreeSet<Integer> cards = new TreeSet<Integer>();
		int score = 0;
		boolean gameIn = true;
		
		Player(String n) {
			name = n;
		}
	}
	
	public void startGame() {
		while (true) {
			if (counter == numOfPlayers * 5)
				break;
			next();
			counter++;
		}
		
		// show all hands
		for (int i = 0; i < numOfPlayers; i++) {
			System.out.println(players[i].name + "'s cards:");
			showHand(i);
			System.out.println(players[i].name + " has: " + checkHand(i) + "!\n");
		}
		
		// find winner
		int winner = 0;
		int highScore = players[0].score;
		for (int i = 1; i < numOfPlayers; i++) {
			if (players[i].score > highScore) {
				highScore = players[i].score;
				winner = i;
			}
		}
		System.out.println("The winner is: " + players[winner].name + "!");
	}
	
	public void testGame() {
		
		// manually assign cards to test specific hands
		players[0].cards.add(13);
		players[0].cards.add(22);
		players[0].cards.add(24);
		players[0].cards.add(25);
		players[0].cards.add(23);
		
		players[1].cards.add(0);
		players[1].cards.add(1);
		players[1].cards.add(2);
		players[1].cards.add(3);
		players[1].cards.add(4);
		
		players[2].cards.add(12);
		players[2].cards.add(11);
		players[2].cards.add(10);
		players[2].cards.add(9);
		players[2].cards.add(26);
		
		players[3].cards.add(34);
		players[3].cards.add(38);
		players[3].cards.add(51);
		players[3].cards.add(25);
		players[3].cards.add(47);
		
		// show all hands
		for (int i = 0; i < numOfPlayers; i++) {
			System.out.println(players[i].name + "'s cards:");
			showHand(i);
			System.out.println(players[i].name + " has: " + checkHand(i) + "!\n");
		}
	}
	
	private void next() {
		deal(turn);
		
		while (true) { // set whose turn is next, skip over those who have folded
			turn = (++turn) % numOfPlayers;
			if (players[turn].gameIn == true)
				break;
		}
	}
	
	private void deal(int player) {
		int newCard;
		
		while (true) {
			newCard = random.nextInt(52); // return 0-51
			if (deck[newCard] == -1) {
				deck[newCard] = player; // assign card to player
				players[player].cards.add(newCard); // add card to player's hand
				break;
			}
		}
		
		System.out.println("Dealt " + cardName(newCard) + " to Player " + players[player].name);
	}
	
	private String cardName(int number) {
		String name = "";
		
		name += cardRank(number);
		name += " of ";
		
		switch(number / 13) {
		case(0): name += "Clubs"; break;
		case(1): name += "Diamonds"; break;
		case(2): name += "Hearts"; break;
		case(3): name += "Spades"; break;
		}
		
		return name;
	}
	
	private String cardRank(int number) {
		String name = "";
		
		switch(number % 13) {
		case(0): name += "Ace"; break;
		case(1): name += "2"; break;
		case(2): name += "3"; break;
		case(3): name += "4"; break;
		case(4): name += "5"; break;
		case(5): name += "6"; break;
		case(6): name += "7"; break;
		case(7): name += "8"; break;
		case(8): name += "9"; break;
		case(9): name += "10"; break;
		case(10): name += "Jack"; break;
		case(11): name += "Queen"; break;
		case(12): name += "King"; break;
		}
		
		return name;
	}
	
	private void showHand(int player) {
		for (int i : players[player].cards){
			System.out.println(cardName(i));
		}
	}
	
	private String checkHand(int player) {
		int flush = checkFlush(player);
		boolean royalStraight = checkRoyalStraight(player);
		int straight = checkStraight(player);
		
		// check Royal Flush
		if (royalStraight == true && flush > -1) {
			players[player].score = 400;
			switch(flush) {
			case(0): return "Clubs Royal Flush";
			case(1): return "Diamonds Royal Flush";
			case(2): return "Hearts Royal Flush";
			case(3): return "Spades Royal Flush";
			}
		}
		
		// check Straight Flush
		if (flush > -1 && straight > -1) {
			players[player].score = straight + 300;
			switch(flush) {
			case(0): return "Clubs Straight Flush with highest card " + cardRank(straight);
			case(1): return "Diamonds Straight Flush with highest card " + cardRank(straight);
			case(2): return "Hearts Straight Flush with highest card " + cardRank(straight);
			case(3): return "Spades Straight Flush with highest card " + cardRank(straight);
			}
		}
		
		// check Four Of A Kind
		int fourOfAKind = checkFourOfAKind(player);
		if (fourOfAKind > -1) {
			players[player].score = (fourOfAKind == 0) ? 213 : fourOfAKind + 200;
			return "Four Of A Kind of " + cardRank(fourOfAKind);
		}
		
		// check Full House
		int fullHouse = checkFullHouse(player);
		if (fullHouse > -1) {
			players[player].score = (fullHouse == 0) ? 113 : fullHouse + 100;
			return "Full House of " + cardRank(fullHouse);
		}
		
		// check Flush
		if (flush > -1) {
			players[player].score = 66;
			switch(flush) {
			case(0): return "Clubs Flush";
			case(1): return "Diamonds Flush";
			case(2): return "Hearts Flush";
			case(3): return "Spades Flush";
			}
		}
		
		// check Royal Straight
		if (royalStraight == true) {
			players[player].score = 65;
			return "Royal Straight";
		}
		
		// check Straight
		if (straight > -1) {
			players[player].score = straight + 52;
			return "Straight with highest card " + cardRank(straight);
		}
		
		// check Three Of A Kind
		int threeOfAKind = checkThreeOfAKind(player);
		if (threeOfAKind > -1) {
			players[player].score = (threeOfAKind == 0) ? 52 : threeOfAKind + 39;
			return "Three Of A Kind of " + cardRank(threeOfAKind);
		}
		
		// check Two Pair
		int twoPair = checkTwoPair(player);
		if (twoPair > -1) {
			players[player].score = (twoPair == 0) ? 39 : twoPair + 26;
			return "Two Pair with higher pair of " + cardRank(twoPair);
		}
		
		// check One Pair
		int onePair = checkOnePair(player);
		if (onePair > -1) {
			players[player].score = (onePair == 0) ? 26 : onePair + 13;
			return "One Pair of " + cardRank(onePair);
		}
		
		// return High Card
		int highCard = checkHighCard(player);
		players[player].score = (highCard == 0) ? 13 : highCard; // Ace counts as 13
		return "High Card " + cardRank(checkHighCard(player));
	}
	
	private int checkFourOfAKind(int player) {
		int[] hand = new int[players[player].cards.size()];
		int i = 0;
		for (int card : players[player].cards) {
			hand[i++] = card % 13;
		}
		
		int matchCount = 0;
		for (int j = 0; j < hand.length; j++) {
			for (int k = 0; k < hand.length; k++) {
				
				if (j != k) {
					if (hand[j] == hand[k]) {
						matchCount++;
						if (matchCount == 3) return hand[j];
					}
				}
			}
			
			matchCount = 0;
		}
		return -1;
	}
	
	private int checkFullHouse(int player) {
		int[] hand = new int[players[player].cards.size()];
		int i = 0;
		for (int card : players[player].cards) {
			hand[i++] = card % 13;
		}
		
		int matchCount = 0;
		int threeOf = -1;
		
		// first find threeOf if it exists
		for (int j = 0; j < hand.length; j++) {
			for (int k = 0; k < hand.length; k++) {
				
				if (j != k) {
					if (hand[j] == hand[k]) {
						matchCount++;
						if (matchCount == 2) {
							threeOf = hand[j];
							break;
						}
					}
				}
			}
			
			matchCount = 0;
		}
		
		if (threeOf > -1) { // if threeOf already found
			for (int j = 0; j < hand.length; j++) {
				for (int k = 0; k < hand.length; k++) {
					
					if (j != k && hand[j] != threeOf) {
						if (hand[j] == hand[k]) { // found second match, Full House
							return threeOf;
						}
					}
				}
			}
		}
		
		return -1;
	}
	
	private int checkFlush(int player) {
		int[] hand = new int[5];
		int i = 0;
		for (int card : players[player].cards) {
			hand[i++] = card;
		}
		
		int suit = hand[0] / 13;
		
		for (int j = 1; j < hand.length; j++) {
			if (hand[j] / 13 != suit)
				return -1;
		}
		return suit;
	}
	
	private boolean checkRoyalStraight(int player) {
		TreeSet<Integer> set = new TreeSet<Integer>();
		for (int card : players[player].cards) {
			set.add(card % 13);
		}
		
		int[] hand = new int[5];
		int i = 0;
		for (int card : set) {
			hand[i++] = card;
		}
		
		// Royal Straight: Ace, King, Queen, Jack, 10
		if (hand[0] == 0 && hand[4] == 12 && hand[3] == 11 && hand[2] == 10 && hand[1] == 9)
			return true;
		
		return false;
	}
	
	private int checkStraight(int player) {
		TreeSet<Integer> set = new TreeSet<Integer>();
		for (int card : players[player].cards) {
			set.add(card % 13);
		}
		
		int[] hand = new int[5];
		int i = 0;
		for (int card : set) {
			hand[i++] = card;
		}
		
		if (hand[1] == hand[0] + 1 && hand[2] == hand[1] + 1 && hand[3] == hand[2] + 1 && hand[4] == hand[3] + 1)
			return hand[4];
		
		return -1;
	}
	
	private int checkThreeOfAKind(int player) {
		int[] hand = new int[players[player].cards.size()];
		int i = 0;
		for (int card : players[player].cards) {
			hand[i++] = card % 13;
		}
		
		int matchCount = 0;
		for (int j = 0; j < hand.length; j++) {
			for (int k = 0; k < hand.length; k++) {
				
				if (j != k) {
					if (hand[j] == hand[k]) {
						matchCount++;
						if (matchCount == 2) return hand[j];
					}
				}
			}
			
			matchCount = 0;
		}
		return -1;
	}
	
	private int checkTwoPair(int player) {
		int[] hand = new int[players[player].cards.size()];
		int i = 0;
		for (int card : players[player].cards) {
			hand[i++] = card % 13;
		}
		
		int twoOf = -1;
		
		// first find twoOf if it exists
		for (int j = 0; j < hand.length; j++) {
			for (int k = 0; k < hand.length; k++) {
				
				if (j != k) {
					if (hand[j] == hand[k]) {
						twoOf = hand[j];
						break;
					}
				}
			}
			
			if (twoOf > -1) break;
		}
		
		if (twoOf > -1) { // if twoOf already found
			for (int j = 0; j < hand.length; j++) {
				for (int k = 0; k < hand.length; k++) {
					
					if (j != k && hand[j] != twoOf) {
						if (hand[j] == hand[k]) { // found second match, Two Pair
							if (twoOf == 0 || hand[j] == 0)
								return 0; // Ace
							else
								return twoOf > hand[j] ? twoOf : hand[j]; // return higher pair
						}
					}
				}
			}
		}
		
		return -1;
	}
	
	private int checkOnePair(int player) {
		int[] hand = new int[players[player].cards.size()];
		int i = 0;
		for (int card : players[player].cards) {
			hand[i++] = card % 13;
		}
		
		for (int j = 0; j < hand.length; j++) {
			for (int k = 0; k < hand.length; k++) {
				
				if (j != k) {
					if (hand[j] == hand[k]) return hand[j];
				}
			}
		}
		return -1;
	}
	
	private int checkHighCard(int player) {
		TreeSet<Integer> set = new TreeSet<Integer>();
		for (int card : players[player].cards) {
			set.add(card % 13);
		}
		
		if (set.first() == 0)
			return 0; // Ace
		else
			return set.last();
	}
}