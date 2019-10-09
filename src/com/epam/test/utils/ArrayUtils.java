package com.epam.test.utils;

import java.util.Random;

public class ArrayUtils {
	
	private static Random rand = new Random();
	 
	public static <T> void swap(T[] arrays, int i, int j) {
		T temp = arrays[i];
		arrays[i] = arrays[j];
		arrays[j] = temp;
	}
 
	public static <T> void shuffle(T[] arrays) {
		int length = arrays.length;
		for (int i = length; i > 0; i--) {
			int randInd = rand.nextInt(i);
			swap(arrays, randInd, i - 1);
		}
	}
}
