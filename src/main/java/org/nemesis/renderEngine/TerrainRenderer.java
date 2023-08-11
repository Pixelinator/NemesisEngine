package org.nemesis.renderEngine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.nemesis.models.RawModel;
import org.nemesis.shaders.TerrainShader;
import org.nemesis.terrains.Terrain;
import org.nemesis.textures.ModelTexture;
import org.nemesis.utils.Maths;

import java.util.List;

public class TerrainRenderer {

	private TerrainShader shader;

	public TerrainRenderer ( TerrainShader shader, Matrix4f projectionMatrix ) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix( projectionMatrix );
		shader.stop();
	}

	public void render ( List<Terrain> terrains ) {
		for ( Terrain terrain : terrains ) {
			prepareTerrain( terrain );
			loadModelMatrix( terrain );

			GL11.glDrawElements( GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0 );

			unbindTexturedModel();
		}
	}

	private void prepareTerrain ( Terrain terrain ) {
		RawModel model = terrain.getModel();
		GL30.glBindVertexArray( model.getVaoID() );
		GL20.glEnableVertexAttribArray( 0 );
		GL20.glEnableVertexAttribArray( 1 );
		GL20.glEnableVertexAttribArray( 2 );

		ModelTexture texture = terrain.getTexture();
		shader.loadShineVariables( texture.getShineDamper(), texture.getReflectivity() );
		GL13.glActiveTexture( GL13.GL_TEXTURE0 );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, texture.getTextureID() );
	}

	private void unbindTexturedModel () {
		GL20.glDisableVertexAttribArray( 0 );
		GL20.glDisableVertexAttribArray( 1 );
		GL20.glDisableVertexAttribArray( 2 );
		GL30.glBindVertexArray( 0 );
	}

	private void loadModelMatrix ( Terrain terrain ) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix( new Vector3f( terrain.getX(), 0, terrain.getZ() ), 0, 0, 0, 1 );
		shader.loadTransformationMatrix( transformationMatrix );
	}
}
