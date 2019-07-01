package com.neet.GameState;

import com.neet.Audio.JukeBox;
import com.neet.Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 9;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1ASTATE = 1;
	public static final int GAMEOVERSTATE = 5;
	public static final int PAUSESTATE = 6;
	public static final int TRANSITIONSTATE = 7;
	public static final int TUTORIALSTATE = 8;

	
	public GameStateManager() {
		
		JukeBox.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if(state == LEVEL1ASTATE)
			gameStates[state] = new Level1AState(this);
		else if(state == GAMEOVERSTATE)
			gameStates[state] = new GameOverState(this);
		else if(state == PAUSESTATE)
			gameStates[state] = new PauseState(this);
		else if(state == TRANSITIONSTATE)
			gameStates[state] = new TransitionState(this);
		else if(state == TUTORIALSTATE)
			gameStates[state] = new Tutorial(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public int getCurrentState() {
		return currentState;
	}

	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}