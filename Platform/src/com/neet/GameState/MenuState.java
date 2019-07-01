package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.omg.CORBA.SystemException;

import com.neet.Audio.JukeBox;
import com.neet.Entity.PlayerSave;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;

public class MenuState extends GameState {
	
	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	private Background bg;
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			
			// load floating head
			head = ImageIO.read(
				getClass().getResourceAsStream("/HUD/Hud.gif")
			).getSubimage(0, 12, 12, 11);
			
			//background, titles and fonts
			bg=new Background("/Backgrounds/d.jpg");
			titleColor = Color.WHITE;
			titleFont = new Font("Elephant",Font.PLAIN, 28);
			font = new Font("Tahoma", Font.PLAIN, 15);
			
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
		bg.update();
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("P L A T F O R M E R", 340, 90);
		
		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Start", 340, 300);
		g.drawString("Quit", 340, 320);
		
		// draw floating head
		if(currentChoice == 0) g.drawImage(head, 320, 290, null);
		else if(currentChoice == 1) g.drawImage(head, 320, 310, null);
		
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setState(GameStateManager.TRANSITIONSTATE);
		}
		else if(currentChoice == 1) {
			System.exit(0);
		}
	}
	
	public void handleInput() {
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










