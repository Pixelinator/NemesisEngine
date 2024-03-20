package org.nemesis.renderer.systems.pipelines;

public class RenderingTask implements Runnable {

	private final RenderPipeline renderPipeline;

	public RenderingTask ( RenderPipeline renderPipeline ) {
		this.renderPipeline = renderPipeline;
	}

	@Override
	public void run () {
		renderPipeline.executePass( 0 );
	}
}
