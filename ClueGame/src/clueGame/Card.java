/*
 * Author: Wes Golliher
 * ClueGame
 * Card class that stores a single card
 */

package clueGame;

public class Card {
	private String cardName;
	public final static int DECK_SIZE = 21;
	private CardType cardType;

	//Constructor with fields
	public Card(String name, CardType type) {
		cardName = name;
		cardType = type;
	}

	//Return cards type
	public CardType getCardType() {
		return cardType;
	}

	public String getCardName() {
		return cardName;
	}

	//Check to see if 2 cards are equal
	public Boolean equals(Card card) {
		if (this.cardType == card.getCardType() && this.cardName.equals(card.getCardName())) {
			return true;
		} else {
			return false;
		}
	}
}
