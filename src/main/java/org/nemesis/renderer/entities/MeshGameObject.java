package org.nemesis.renderer.entities;

import org.joml.Vector3f;
import org.nemesis.renderer.resources.Mesh;
import org.nemesis.renderer.systems.pipelines.PipelinePass;
import org.nemesis.renderer.systems.pipelines.RenderPipeline;

import java.util.HashMap;
import java.util.Map;

public class MeshGameObject implements RenderGameObject {

	private Map<PipelinePass, RenderPipeline> pipelinePassMap = new HashMap<>();

	private final Mesh mesh;

	private final Vector3f position;
	private final float scale;
	private final Vector3f rotation;

	public MeshGameObject ( Mesh mesh, Vector3f position, float scale, Vector3f rotation ) {
		this.mesh = mesh;
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}

	@Override
	public void addPipelineToPass ( PipelinePass pipelinePass, RenderPipeline renderPipeline ) {
		pipelinePassMap.put( pipelinePass, renderPipeline);
	}

	@Override
	public RenderPipeline getPipelineForPass (PipelinePass pipelinePass) {
		return pipelinePassMap.get( pipelinePass );
	}

	@Override
	public Mesh getMesh () {
		return mesh;
	}
}
