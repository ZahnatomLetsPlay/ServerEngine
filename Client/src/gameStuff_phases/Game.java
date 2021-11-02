package gameStuff_phases;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import connection.ServerConnectionSource;
import connection.Utilities;
import gameobjects_states.GameState;
import imageloader.BufferedImageLoader;
import inputlisteners.KeyInputs;
import inputlisteners.MouseAdapter;
import musicmanagment.AudioPlayer;
import musicmanagment.MusicManager;
import rendering.GameScene;
import rendering.Render;
import saveAndLoad.LoadData;
import window.Window;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 8358263811084238555L;

	private boolean isRunning = false;

	private boolean mainMenuMusicRunning = true, mainMenuMusicReset = false;

	private Window window;

	private Thread gameThread;

	private Handler handler;

	private GameState gameState = GameState.Menu;

	private Menu menu;

	private QueuePhase queue;

	private BufferedImageLoader imageLoader;

	private KeyInputs keyAdapter;

	private MouseAdapter mouseAdapter;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private ServerConnectionSource connection;

	private PickPhase pickPhase;

	private GameScene gameScene;

	private Settings settings;

	private Utilities ipVerfier;

	private LoadData savedDataLoader;

	private SaveSettingsData savedData;

	private MusicManager musicManager;

	private AudioPlayer mainMenuThemeAudioPlayer, QueuePhaseAudioPlayer;

	public Game() {
		this.window = new Window(screenSize.width, screenSize.height, this);

		this.setSize(screenSize.width, screenSize.height);

		this.imageLoader = new BufferedImageLoader();

		this.handler = new Handler();

		this.gameScene = new GameScene(this);

		this.savedDataLoader = new LoadData();

		loadSavedSettingsData();

		this.menu = new Menu(this);

		this.queue = new QueuePhase(this);

		this.pickPhase = new PickPhase(this);

		this.settings = new Settings(this);

		this.mouseAdapter = new MouseAdapter(this);

		this.keyAdapter = new KeyInputs(this);

		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);

		this.addKeyListener(keyAdapter);

		this.ipVerfier = new Utilities();

		start();

		this.window.setVisible(true);

		this.musicManager = new MusicManager();
		this.musicManager.loadAudio(this.musicManager.mainMenu, "MainMenu.wav");
		this.musicManager.loadAudio(musicManager.queuePhase, "QueuePhase.wav");
		loadMainMenuTheme();

	}

	public void loadMainMenuTheme() {
		if (mainMenuThemeAudioPlayer == null) {
			this.mainMenuThemeAudioPlayer = new AudioPlayer(this.musicManager.mainMenu);
			this.mainMenuThemeAudioPlayer.setVolume(this.getSettingsMenu().getVolumeLevel());
		}
	}

	public void loadQueuePhaseTheme() {
		if (this.QueuePhaseAudioPlayer == null) {
			this.QueuePhaseAudioPlayer = new AudioPlayer(this.musicManager.queuePhase);
			this.QueuePhaseAudioPlayer.setVolume(this.getSettingsMenu().getVolumeLevel());
		}

	}

	public AudioPlayer[] getAvailableAudioPlayers() {
		AudioPlayer[] audioPlayers = new AudioPlayer[2];

		if (this.mainMenuThemeAudioPlayer != null) {
			audioPlayers[0] = this.mainMenuThemeAudioPlayer;
		}
		if (this.QueuePhaseAudioPlayer != null) {
			audioPlayers[1] = this.QueuePhaseAudioPlayer;
		}

		return audioPlayers;
	}

	public void loadSavedSettingsData() {
		savedData = (SaveSettingsData) this.savedDataLoader.loadObject("settingsData.ser");
	}

	public SaveSettingsData getSavedSettingsData() {
		return this.savedData;
	}

	public void setSavedSettingsData(SaveSettingsData data) {
		this.savedData = data;
	}

	private void start() {
		if (isRunning) {
			return;
		}
		gameThread = new Thread(this);
		gameThread.start();
		isRunning = true;
	}

	private void stop() {
		if (isRunning) {
			try {
				gameThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isRunning = false;
		}
	}

	private void tick() {
		switch (getGameState()) {
		case InGame:
			if (handler.isReady()) {
				handler.tick();
			}
			break;
		case Menu:
			if (mainMenuMusicRunning && musicManager != null) {

				if (this.mainMenuThemeAudioPlayer != null && !mainMenuMusicReset) {
					this.mainMenuThemeAudioPlayer.start();
					this.mainMenuMusicRunning = false;
				} else if (this.mainMenuMusicReset) {
					try {
						this.QueuePhaseAudioPlayer.stopAndErase();
						this.QueuePhaseAudioPlayer.join();
						this.QueuePhaseAudioPlayer = null;

					} catch (InterruptedException e) {
					}
					loadMainMenuTheme();
					this.mainMenuThemeAudioPlayer.start();
					this.mainMenuMusicReset = false;
					this.mainMenuMusicRunning = false;
				}
				muteMainMenuMusic(getMenu().isMuted());
			}
			break;
		case Pickphase:
			if (!this.mainMenuMusicRunning) {

				try {
					this.mainMenuThemeAudioPlayer.stopAndErase();
					this.mainMenuThemeAudioPlayer.join();
					this.mainMenuThemeAudioPlayer = null;
				} catch (InterruptedException e) {
				}

				this.loadQueuePhaseTheme();
				this.QueuePhaseAudioPlayer.start();

				this.mainMenuMusicRunning = true;
				this.mainMenuMusicReset = true;

			}
			break;
		default:
			break;
		}
	}

	public void muteMainMenuMusic(boolean mute) {
		if (this.mainMenuThemeAudioPlayer != null) {
			this.mainMenuThemeAudioPlayer.Mute(mute);
		}
	}

	public Settings getSettingsMenu() {
		return this.settings;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public QueuePhase getQueue() {
		return this.queue;
	}

	public BufferedImageLoader getImageLoader() {
		return this.imageLoader;
	}

	private void render() {
		Render renderer = new Render(this, handler);
		renderer.commenceRenderingSequence();
	}

	public Utilities getIPVerifier() {
		return this.ipVerfier;
	}

	public void setRunning(boolean running) {
		this.isRunning = running;
	}

	@SuppressWarnings("unused")
	@Override
	public void run() {

		requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}

		stop();
	}

	public void EndGame() {
		try {
			if (this.mainMenuThemeAudioPlayer != null && this.mainMenuThemeAudioPlayer.isAlive()) {

				this.mainMenuThemeAudioPlayer.join();
			}
			if (this.QueuePhaseAudioPlayer != null && this.QueuePhaseAudioPlayer.isAlive()) {
				this.QueuePhaseAudioPlayer.join();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}

	public GameScene getGameScene() {
		return gameScene != null ? this.gameScene : null;
	}

	public PickPhase getPickPhase() {
		return pickPhase;
	}

	public GameState getGameState() {
		return this.gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Handler getHandler() {
		return this.handler;
	}

	public void setServerConnectionSource(ServerConnectionSource scs) {
		this.connection = scs;
	}

	public ServerConnectionSource getConnection() {
		return connection != null ? this.connection : null;
	}

	public void setSettingsMenu(Settings settingsMenu) {
		this.settings = settingsMenu;
	}

	public LoadData getSavedDataLoader() {
		return savedDataLoader;
	}
}
