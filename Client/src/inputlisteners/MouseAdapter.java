package inputlisteners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import gameStuff_phases.Game;

public class MouseAdapter implements MouseInputListener {

	private Game game;

	public MouseAdapter(Game game) {
		this.game = game;
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		switch (this.game.getGameState()) {
		case InGame:
			break;
		case Pickphase_confirm:
			this.game.getPickPhase().onMouseMovedEvent(e);
			break;
		case Pickphase:
			this.game.getPickPhase().onMouseMovedEvent(e);
			break;
		case Menu:
			this.game.getMenu().onMouseMovedEvent(e);
			break;
		case InQueue:
			break;
		case Endphase:
			break;
		default:
			break;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	//@formatter:off
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(this.game.getGameState()) {
		case InGame:			
			break;	
		case Pickphase_confirm:
			this.game.getPickPhase().onMouseClickedEvent(e);
			break;
		case Pickphase: 
			this.game.getPickPhase().onMouseClickedEvent(e);
			break;
		case Menu:
			this.game.getMenu().onMouseClickedEvent(e);
			break;
		case InQueue:
			break;
			
		case Endphase:
			break;
		default:
			break;
		}
	
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch(this.game.getGameState()) {
		case InGame:			
			break;	
		case Pickphase_confirm:
			break;
		case Pickphase: 
			break;
		case Menu:
			this.game.getMenu().onMouseDraggedEvent(e);
			break;
		case InQueue:
			break;
			
		case Endphase:
			break;
		default:
			break;
		}
	}

}
