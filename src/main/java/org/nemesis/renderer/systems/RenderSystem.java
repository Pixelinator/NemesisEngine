package org.nemesis.renderer.systems;

import org.nemesis.renderer.entities.RenderGameObject;
import org.nemesis.renderer.systems.pipelines.RenderPipeline;

import java.util.ArrayList;
import java.util.List;

public class RenderSystem {
	private List<RenderPipeline> pipelines = new ArrayList<>();
	private List<RenderGameObject> renderGameObjects = new ArrayList<>();

	public void init() {
		try {
			for ( RenderPipeline renderPipeline : pipelines ) {
				renderPipeline.initShaders();
				renderPipeline.initPipeline();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	public void render() {
		//		// Create a shared context
		//		GLContext sharedContext = GLContext.createFromCurrent();
		//
		//// Create a thread pool
		//		ExecutorService executor = Executors.newFixedThreadPool(4);
		//
		//// Submit rendering tasks to the thread pool
		//		for (RenderingTask task : tasks) {
		//			executor.submit(() -> {
		//				// Make the shared context current in this thread
		//				sharedContext.makeCurrent();
		//
		//				// Execute the rendering task
		//				task.execute();
		//
		//				// Release the context
		//				sharedContext.release();
		//			});
		//		}
		//
		//// Shutdown the executor when done
		//		executor.shutdown();

		int vaoId = renderGameObjects.get( 0 ).getMesh().getVao().getVaoId();
		for ( RenderPipeline renderPipeline : pipelines ) {
			renderPipeline.executePass( vaoId );
		}
	}

	public void addPipeline(RenderPipeline pipeline) {
		this.pipelines.add( pipeline );
	}

	public void addRenderGameObject(RenderGameObject renderGameObject) {
		this.renderGameObjects.add( renderGameObject );
	}
}
