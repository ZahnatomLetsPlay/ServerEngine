package rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import gameStuff_phases.Game;
import gameStuff_phases.Handler;
import gameStuff_phases.Menu;

public class Render {

	private Game game;

	private Handler handler;

	private Menu menu;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public Render(Game game, Handler handler) {
		this.game = game;

		this.handler = handler;

		this.menu = game.getMenu();
	}

	public void commenceRenderingSequence() {
		BufferStrategy buffer = game.getBufferStrategy();
		if (buffer == null) {
			game.createBufferStrategy(3);
			return;
		}
		Graphics g = buffer.getDrawGraphics();

		//@formatter:off 					rendering 										//
		
	//	g.setColor(new Color(39,39,42));//background color
		g.setColor(new Color(7, 11, 20));
		//g.setColor(new Color(18, 28, 51));
		g.fillRect(0, 0, screenSize.width, screenSize.height);//rectangle as a background 			// load background image for menu right here
	
	
		switch(game.getGameState()) {
		case Menu:			
			menu.render(g);	
			break;		//load menu
		case InQueue:			
			g.setColor(Color.DARK_GRAY);	
			this.game.getQueue().Render(g);				//renders queue-phase and establishes connection to the server
			break;
		case Pickphase:		
			game.getPickPhase().render(g);	//render pick-phase to screen
			break;
		case Pickphase_confirm:		
			game.getPickPhase().render(g);  // render sub_Pick_Phase to screen
			break;
		case InGame:
			this.game.getGameScene().loadGameScene();
			if(handler.isReady()) {		
				handler.render(g); 			//render all Game-Objects including Characters
			}
			break;
		default:
			break;
		}
		//								 end of rending                          				//	
		//@formatter:on

		g.dispose();
		buffer.show();
	}

}
