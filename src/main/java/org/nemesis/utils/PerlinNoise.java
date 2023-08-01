package org.nemesis.utils;

import java.util.Random;

/**
 * Beispielaufruf
 *	double x = 2.5;
 *	double y = 1.3;
 *	double noiseValue = perlinNoise(x, y);
 *	System.out.println("Perlin Noise Value at (" + x + ", " + y + "): " + noiseValue);
 */
public class PerlinNoise {
	private static final int P = 512;
	private static final int[] permutation = new int[P];

	static {
		Random random = new Random();
		for ( int i = 0; i < P; i++ ) {
			permutation[i] = i;
		}

		for ( int i = 0; i < P; i++ ) {
			int j = random.nextInt(P);
			int temp = permutation[i];
			permutation[i] = permutation[j];
			permutation[j] = temp;
		}
	}

	private static double fade(double t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	private static double grad(int hash, double x, double y) {
		int h = hash & 15;
		double u = h < 8 ? x : y;
		double v = h < 4 ? y : (h == 12 || h == 14) ? x : 0;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	public static double perlinNoise(double x, double y) {
		int X = (int) Math.floor(x) & 255;
		int Y = (int) Math.floor(y) & 255;

		x -= Math.floor(x);
		y -= Math.floor(y);

		double u = fade(x);
		double v = fade(y);

		int A = permutation[X] + Y;
		int AA = permutation[A];
		int AB = permutation[A + 1];
		int B = permutation[X + 1] + Y;
		int BA = permutation[B];
		int BB = permutation[B + 1];

		return lerp(v, lerp(u, grad(permutation[AA], x, y), grad(permutation[BA], x - 1, y)),
				lerp(u, grad(permutation[AB], x, y - 1), grad(permutation[BB], x - 1, y - 1)));
	}

	private static double lerp(double t, double a, double b) {
		return a + t * (b - a);
	}
}
