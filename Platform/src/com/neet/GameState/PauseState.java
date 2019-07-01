package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.neet.Audio.JukeBox;
import com.neet.Entity.PlayerSave;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;

public class PauseState extends GameState {
	
	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Quit",
		"Quit2"
	};
	
	private Color titleColor;
	private Font titleFont;
	private Background bg;
	
	private Font font;
	private Font font2;
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			
			// load floating head
			head = ImageIO.read(
				getClass().getResourceAsStream("/HUD/Hud.gif")
			).getSubimage(0, 12, 12, 11);
			
			//background, titles and fonts
			bg=new Background("/Backgrounds/d.jpg");
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 28);
			font = new Font("Arial", Font.PLAIN, 14);
			font2 = new Font("Arial", Font.PLAIN, 10);
			
			// load sound fx
			JukeBox.load("/SFX/menuoption.mp3", "menuoption");
			JukeBox.load("/SFX/menuselect.mp3", "menuselect");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		
		// check keys
		handleInput();
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		//g.drawString("A R T I F A C T", 70, 90);
		
		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Resume", 145, 165);
		g.drawString("Quit to Menu", 145, 185);
		g.drawString("Quit to Windows", 145, 205);
		
		// draw floating head
		if(currentChoice == 0) g.drawImage(head, 125, 154, null);
		else if(currentChoice == 1) g.drawImage(head, 125, 174, null);
		else if(currentChoice == 2) g.drawImage(head, 125, 194, null);
		
	
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setPaused(false);
		}
		else if(currentChoice == 1) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
		else if(currentChoice == 2) {
			System.exit(0);
		}
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(false);
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
				JukeBox.play("menuoption", 0);
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
				JukeBox.play("menuoption", 0);
			}
		}
	}
	
}










