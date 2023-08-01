package org.nemesis.utils;

import java.util.Random;

/**
 * Beispielaufruf
 *	double x = 2.5;
 *	double y = 1.3;
 *	double noiseValue = worleyNoise(x, y);
 *	System.out.println("Worley Noise Value at (" + x + ", " + y + "): " + noiseValue);
 */
public class WorleyNoise {
	private static final int NUM_POINTS = 8;
	private static final double[] randomPointsX = new double[NUM_POINTS];
	private static final double[] randomPointsY = new double[NUM_POINTS];

	static {
		Random rand = new Random();

		for (int i = 0; i < NUM_POINTS; i++) {
			randomPointsX[i] = rand.nextDouble();
			randomPointsY[i] = rand.nextDouble();
		}
	}

	private static double euclideanDistance(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public static double worleyNoise(double x, double y) {
		double minDistance = Double.MAX_VALUE;

		for (int i = 0; i < NUM_POINTS; i++) {
			double distance = euclideanDistance(x, y, randomPointsX[i], randomPointsY[i]);
			minDistance = Math.min(minDistance, distance);
		}

		return minDistance;
	}
}
