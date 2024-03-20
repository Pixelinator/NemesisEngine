package org.nemesis.renderer.systems.pipelines;

/**
 * <a href="https://www.khronos.org/opengl/wiki/Rendering_Pipeline_Overview">Rendering Pipeline Overview - OpenGL </a>
 */
public interface RenderPipeline {
	void initShaders () throws Exception;

	void initPipeline ();

	void setAttribLayout ();

	void setBlending ();

	void executePass ( int vaoId );

	void cleanup ();
}
