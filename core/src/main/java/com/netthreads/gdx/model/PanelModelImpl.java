package com.netthreads.gdx.model;

import aurelienribon.tweenengine.equations.Linear;

import com.google.inject.Singleton;
import com.netthreads.audio.note.service.NoteModelImpl;
import com.netthreads.gdx.app.definition.AppTextures;
import com.netthreads.gdx.scene.PanelScene;
import com.netthreads.gdx.sprite.NavigationButtonSprite.Direction;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.scene.transition.MoveInBLTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInBRTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInBTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInLTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInRTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInTLTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInTRTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInTTransitionScene;
import com.netthreads.libgdx.scene.transition.TransitionScene;

/**
 * Managed multiple panel scenes.
 * 
 */
@Singleton
public class PanelModelImpl implements PanelModel
{
	public static final int DEFAULT_PANEL = 0;
	
	private static final int DURATION_TRANSITION_TO_PANEL_SCENE = 500;
	
	// The one and only director.
	private Director director;
	
	private PanelScene[] scene;
	
	/**
	 * This defines how panels are connected together. A -1 indicates no
	 * connection in that direction.
	 * 
	 * Values define direction clockwise starting from top left.
	 */
	private int[][] configuration =
	{
	        {
	                -1, -1, -1, 1, 2, 3, -1, -1
	        },
	        {
	                -1, -1, -1, -1, -1, 2, 3, 0
	        },
	        {
	                0, 1, -1, -1, -1, -1, -1, 3
	        },
	        {
	                -1, 0, 1, 2, -1, -1, -1, -1
	        }
	};
	
	/**
	 * Button texture definitions for panel.
	 * 
	 */
	private String[] definitions =
	{
	        AppTextures.TEXTURE_ARROW_TL, AppTextures.TEXTURE_ARROW_U, AppTextures.TEXTURE_ARROW_TR, AppTextures.TEXTURE_ARROW_R, AppTextures.TEXTURE_ARROW_BR, AppTextures.TEXTURE_ARROW_D, AppTextures.TEXTURE_ARROW_BL, AppTextures.TEXTURE_ARROW_L
	};
	
	/**
	 * Button direction definitions for panel.
	 * 
	 */
	private Direction[] directions =
	{
	        Direction.TOP_LEFT, Direction.UP, Direction.TOP_RIGHT, Direction.RIGHT, Direction.BOTTOM_RIGHT, Direction.DOWN, Direction.BOTTOM_LEFT, Direction.LEFT
	};
	
	/**
	 * Construct instance.
	 * 
	 */
	public PanelModelImpl()
	{
		director = AppInjector.getInjector().getInstance(Director.class);

		this.scene = new PanelScene[NoteModelImpl.COUNT_PANELS];
		
		for (int i = 0; i < NoteModelImpl.COUNT_PANELS; i++)
		{
			this.scene[i] = null;
		}
	}
	
	/**
	 * Return panel connection definition.
	 * 
	 * @param panel
	 *            The panel number.
	 * 
	 * @return The panel connection definition.
	 */
	@Override
	public int[] getConfiguration(int panel)
	{
		return configuration[panel];
	}
	
	/**
	 * Get button definition.
	 * 
	 * @return The definitions.
	 */
	@Override
	public String[] getDefinition()
	{
		return definitions;
	}
	
	/**
	 * Get button directions.
	 * 
	 * @return The directions.
	 */
	@Override
	public Direction[] getDirections()
	{
		return directions;
	}
	
	/**
	 * Run transition to target panel.
	 * 
	 * @param targetPanel
	 *            The target panel id.
	 * @param direction
	 *            The direction.
	 */
	@Override
	public void transitionToPanel(int targetPanel, Direction direction)
	{
		// Get incoming panel scene.
		Scene inScene = getPanel(targetPanel);
		
		// get outgoing scene.
		Scene outScene = director.getScene();
		
		TransitionScene transitionScene = null;
		
		switch (direction)
		{
			case UP:
				transitionScene = MoveInTTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_PANEL_SCENE, Linear.INOUT);
				break;
			case DOWN:
				transitionScene = MoveInBTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_PANEL_SCENE, Linear.INOUT);
				break;
			case LEFT:
				transitionScene = MoveInLTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_PANEL_SCENE, Linear.INOUT);
				break;
			case RIGHT:
				transitionScene = MoveInRTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_PANEL_SCENE, Linear.INOUT);
				break;
			case TOP_LEFT:
				transitionScene = MoveInTLTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_PANEL_SCENE, Linear.INOUT);
				break;
			case TOP_RIGHT:
				transitionScene = MoveInTRTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_PANEL_SCENE, Linear.INOUT);
				break;
			case BOTTOM_LEFT:
				transitionScene = MoveInBLTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_PANEL_SCENE, Linear.INOUT);
				break;
			case BOTTOM_RIGHT:
				transitionScene = MoveInBRTransitionScene.$(inScene, outScene, DURATION_TRANSITION_TO_PANEL_SCENE, Linear.INOUT);
				break;
		}
		
		if (transitionScene != null)
		{
			this.director.setScene(transitionScene);
		}
	}
	
	/**
	 * Get current panel and if none then create one.
	 * 
	 * @return The panel.
	 */
	private PanelScene getPanel(int panel)
	{
		if (scene[panel] == null)
		{
			scene[panel] = new PanelScene(panel);
		}
		
		return scene[panel];
	}
}
