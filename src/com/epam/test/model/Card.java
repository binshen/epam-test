package com.epam.test.model;

public class Card {

	public final int value;   // 分值
	public final Suite suite; // 花色（黑桃，红桃，方块，梅花，鬼牌）

	public String face; // 牌面

	public Card(int value, Suite suite) {
		this.value = value;
		this.suite = suite;

		if (suite == Suite.JOKER) {
			this.face = "Joker";
		} else {
			switch (value) {
			case 1:
				this.face = "Ace";
			case 11:
				this.face = "Jack";
			case 12:
				this.face = "Queen";
			case 10:
				this.face = "King";
			default:
				this.face = String.valueOf(value);
			}
		}
	}

	@Override
	public String toString() {
		return face + " of " + suite.name();
	}
}
