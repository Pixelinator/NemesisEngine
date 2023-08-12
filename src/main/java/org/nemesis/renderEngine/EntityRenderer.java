package org.nemesis.renderEngine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.nemesis.entities.Entity;
import org.nemesis.entities.TransformComponent;
import org.nemesis.models.RawModel;
import org.nemesis.models.TexturedModel;
import org.nemesis.shaders.StaticShader;
import org.nemesis.textures.ModelTexture;
import org.nemesis.utils.Maths;

import java.util.List;
import java.util.Map;

/**
 * Handles the rendering of a model to the screen.
 */
public class EntityRenderer {


	private StaticShader shader;

	public EntityRenderer ( StaticShader shader, Matrix4f projectionMatrix ) {
		this.shader = shader;

		shader.start();
		shader.loadProjectionMatrix( projectionMatrix );
		shader.stop();
	}

	public void render ( Map<TexturedModel, List<Entity>> entities ) {
		for ( TexturedModel model : entities.keySet() ) {
			prepareTexturedModel( model );
			List<Entity> batch = entities.get( model );
			for ( Entity entity : batch ) {
				prepareInstance( entity );
				GL11.glDrawElements( GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0 );
			}
			unbindTexturedModel();
		}
	}

	private void prepareTexturedModel ( TexturedModel texturedModel ) {
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray( model.getVaoID() );
		GL20.glEnableVertexAttribArray( 0 );
		GL20.glEnableVertexAttribArray( 1 );
		GL20.glEnableVertexAttribArray( 2 );

		ModelTexture texture = texturedModel.getTexture();
		if( texture.isHasTransparency() )
		{
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable( texture.isUseFakeLighting() );
		shader.loadShineVariables( texture.getShineDamper(), texture.getReflectivity() );
		GL13.glActiveTexture( GL13.GL_TEXTURE0 );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, texture.getTextureID() );
	}

	private void unbindTexturedModel () {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray( 0 );
		GL20.glDisableVertexAttribArray( 1 );
		GL20.glDisableVertexAttribArray( 2 );
		GL30.glBindVertexArray( 0 );
	}

	private void prepareInstance ( Entity entity ) {
		TransformComponent transform = entity.getComponent( TransformComponent.class );
		Matrix4f transformationMatrix = Maths.createTransformationMatrix( transform.position, transform.rotation.x, transform.rotation.y, transform.rotation.z, transform.scale.x );
		shader.loadTransformationMatrix( transformationMatrix );
	}

}
