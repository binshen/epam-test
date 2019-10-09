package com.epam.test;

import com.epam.test.model.Card;
import com.epam.test.model.Poker;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Game {

	private int[] scores = { 0, 0, 0 }; // 初始化分数

	private int index = 0; // 记录发牌顺序

	private static Card[] cards; // 保存54张扑克牌
	
	Lock lock = new ReentrantLock(true); // 定义公平锁
	
	// 玩家号码（3个玩家分别标记成1，2，3）
	private int num = 1; 
	
	// 为每个玩家
	private final Condition p1Con = lock.newCondition();
    private final Condition p2Con = lock.newCondition();
    private final Condition p3Con = lock.newCondition();
	
	public static void main(String[] args) {
		
		Poker poker = new Poker(); //创建一副扑克牌（54张牌）
		poker.shuffle(); //洗牌（打乱顺序）
		cards = poker.getCards();
		
		final Game game = new Game();
		new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) game.process1();
        }, "player1").start();
		
		new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) game.process2();
        }, "player2").start();
		
		new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) game.process3();
        }, "player3").start();
	}

	// 判分
	private void judgeScore() throws InterruptedException {
        if (index >= 54) {
            Thread.currentThread().interrupt();
            return;
        }
        scores[num-1] += cards[index].value;
        
        if (scores[num-1] >= 50) { // 判断当前玩家是否累计超过50分
        	Thread.currentThread().interrupt();
        	
        	String threadName = Thread.currentThread().getName();
        	String scoreString = Arrays.toString(scores);
        	System.out.println(String.format("Player %d won the game! 【 Thread: %s, Round: %d, Scores: %s 】", num, threadName, index, scoreString));
        	System.exit(1);
            return;
        }
        index++;
    }
	
	// 玩家1
	public void process1() {
        lock.lock();
        try {
            while (num != 1) {
            	p1Con.await();
            }
            judgeScore();
            num = 2;
            p2Con.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
 
	// 玩家2
    public void process2() {
        lock.lock();
        try {
            while (num != 2) {
            	p2Con.await();
            }
            judgeScore();
            num = 3;
            p3Con.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
 
    // 玩家3
    public void process3() {
        lock.lock();
        try {
            while (num != 3) {
            	p3Con.await();
            }
            judgeScore();
            num = 1;
            p1Con.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
