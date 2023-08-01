package org.nemesis.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.BiFunction;

public class TextureGenerator {
	private static final int TEXTURE_WIDTH = 512;
	private static final int TEXTURE_HEIGHT = 512;

	@SafeVarargs
	public static void createMultiNoise ( String outputPath, BiFunction<Double, Double, Double>... methods ) {
		BufferedImage noiseTextureImage = new BufferedImage( TEXTURE_WIDTH, TEXTURE_HEIGHT, BufferedImage.TYPE_INT_RGB );
		for ( int x = 0 ; x < TEXTURE_WIDTH ; x++ ) {
			for ( int y = 0 ; y < TEXTURE_HEIGHT ; y++ ) {
				double aDouble = methods[ 0 ].apply( x / ( double ) TEXTURE_WIDTH, y / ( double ) TEXTURE_HEIGHT );
				double bDouble = methods[ 1 ].apply( x / ( double ) TEXTURE_WIDTH, y / ( double ) TEXTURE_HEIGHT );
				double cDouble = methods[ 2 ].apply( x / ( double ) TEXTURE_WIDTH, y / ( double ) TEXTURE_HEIGHT );

				int aColor = ( int ) ( aDouble * 255 );
				int bColor = ( int ) ( bDouble * 255 );
				int cColor = ( int ) ( cDouble * 255 );
				int colorValue = ( aColor << 16 ) | ( bColor << 8 ) | cColor;
				noiseTextureImage.setRGB( x, y, colorValue );
			}
		}

		saveToFile( outputPath, noiseTextureImage );
	}

	public static void createSingleNoise ( String outputPath, BiFunction<Double, Double, Double> method ) {
		BufferedImage noiseTextureImage = new BufferedImage( TEXTURE_WIDTH, TEXTURE_HEIGHT, BufferedImage.TYPE_BYTE_GRAY );
		for ( int x = 0 ; x < TEXTURE_WIDTH ; x++ ) {
			for ( int y = 0 ; y < TEXTURE_HEIGHT ; y++ ) {
				double noiseValue = method.apply( x / ( double ) TEXTURE_WIDTH, y / ( double ) TEXTURE_HEIGHT );

				int noiseColor = ( int ) ( noiseValue * 255 );
				int perlinGray = ( noiseColor << 16 ) | ( noiseColor << 8 ) | noiseColor;
				noiseTextureImage.setRGB( x, y, perlinGray );
			}
		}

		saveToFile( outputPath, noiseTextureImage );
	}

	private static void saveToFile ( String outputPath, BufferedImage noiseTextureImage ) {
		try {
			File output = new File( outputPath );
			ImageIO.write( noiseTextureImage, "png", output );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
