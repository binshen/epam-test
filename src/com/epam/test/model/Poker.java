package com.epam.test.model;

import com.epam.test.utils.ArrayUtils;

public class Poker {
	
	private Card[] pokers;

	// 初始化扑克牌
	public Poker() {
		pokers = new Card[54];
		int index = 0;
		for ( int value = 1; value <= 13; value++ ) {
			pokers[index++] = new Card(value, Suite.SPADES);
			pokers[index++] = new Card(value, Suite.HEARTS);
			pokers[index++] = new Card(value, Suite.DIAMONDS);
			pokers[index++] = new Card(value, Suite.CLUBS);
		}
		pokers[52] = new Card(20, Suite.JOKER);
		pokers[53] = new Card(20, Suite.JOKER);
	}

	// 洗牌
	public void shuffle() {
		ArrayUtils.shuffle(pokers);
	}

	// 获取整副扑克牌
	public Card[] getCards() {
		return pokers;
	}
}
