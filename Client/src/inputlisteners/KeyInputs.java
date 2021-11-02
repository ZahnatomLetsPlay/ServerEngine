package inputlisteners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import gameStuff_phases.Game;
import gameobjects_states.MoveState;
import push_Data_toServer.DataOutputChannel;

public class KeyInputs extends KeyAdapter {

	private Game game;

	private DataOutputChannel data;

	private ArrayList<Integer> keys;

	private boolean init = false;

	private long cooldownTime = System.currentTimeMillis();

	private final MoveState[][] moves = new MoveState[][] {
			new MoveState[] { MoveState.up_left, MoveState.up, MoveState.up_right },
			new MoveState[] { MoveState.left, MoveState.none, MoveState.right },
			new MoveState[] { MoveState.down_left, MoveState.down, MoveState.down_right } };

	private int[] virtualJoystick = new int[] { 1, 1 };

	public KeyInputs(Game game) {
		this.game = game;
		this.data = new DataOutputChannel();
		this.keys = new ArrayList<>();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		super.keyTyped(e);
		switch (this.game.getGameState()) {
		case Menu:
			this.game.getMenu().onkeyPressedEvent(e);
			break;
		case InGame:
			if (this.game.getConnection() != null) {
				if (!init) {
					this.game.getConnection().setDataOutputChannel(data);
					init = true;
				}
			}

			this.virtualJoystick[0] = 1;
			this.virtualJoystick[1] = 1;
			if (!this.keys.contains(e.getKeyCode())) {
				this.keys.add(e.getKeyCode());
			}

			if (this.keys.contains(KeyEvent.VK_W)) {
				this.virtualJoystick[0] -= 1;

			}
			if (this.keys.contains(KeyEvent.VK_S)) {
				this.virtualJoystick[0] += 1;
			}
			if (this.keys.contains(KeyEvent.VK_A)) {
				this.virtualJoystick[1] -= 1;
			}
			if (this.keys.contains(KeyEvent.VK_D)) {
				this.virtualJoystick[1] += 1;
			}

			this.data.setData("move:" + this.moves[this.virtualJoystick[0]][this.virtualJoystick[1]].toString());

			break;

		default:
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);

		switch (this.game.getGameState()) {
		case Menu:
			this.game.getMenu().onkeyPressedEvent(e);
			break;

		case InGame:
			if (this.game.getConnection() != null) {
				if (!init) {
					this.game.getConnection().setDataOutputChannel(data);
					init = true;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (System.currentTimeMillis() - this.cooldownTime > 2000l) {
					this.game.getConnection().sendMsg("action:fire");
					this.cooldownTime = System.currentTimeMillis();
				}
			}

			this.virtualJoystick[0] = 1;
			this.virtualJoystick[1] = 1;

			if (!this.keys.contains(e.getKeyCode())) {
				this.keys.add(e.getKeyCode());
			}

			if (this.keys.contains(KeyEvent.VK_W)) {
				this.virtualJoystick[0] -= 1;
			}

			if (this.keys.contains(KeyEvent.VK_S)) {
				this.virtualJoystick[0] += 1;
			}

			if (this.keys.contains(KeyEvent.VK_A)) {
				this.virtualJoystick[1] -= 1;
			}

			if (this.keys.contains(KeyEvent.VK_D)) {
				this.virtualJoystick[1] += 1;
			}

			this.data.setData("move:" + this.moves[this.virtualJoystick[0]][this.virtualJoystick[1]].toString());
			
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		if (this.keys.contains(e.getKeyCode())) {
			this.keys.remove(this.keys.indexOf(e.getKeyCode()));
		}
		if (!this.keys.contains(KeyEvent.VK_W) && !this.keys.contains(KeyEvent.VK_A)
				&& !this.keys.contains(KeyEvent.VK_S) && !this.keys.contains(KeyEvent.VK_D)) {
			this.virtualJoystick[0] = 1;
			this.virtualJoystick[1] = 1;
			this.data.setData("move:" + this.moves[this.virtualJoystick[0]][this.virtualJoystick[1]].toString());
		}

	}
}
