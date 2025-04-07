package studiplayer.ui;

import studiplayer.ui.SongTable;
import studiplayer.ui.Song;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;
import studiplayer.audio.SortCriterion;

public class Player extends Application {
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
//	public static final String DEFAULT_PLAYLIST = "playlists/playList.cert.m3u";
	private static final String INITIAL_PLAY_TIME_LABEL = "00:00";
	private static final String PLAYLIST_DIRECTORY = "";
	private static final String NO_CURRENT_SONG = " - ";
	private PlayList playList;
	private boolean useCertPlayList = false;
	private PlayerThread playerThread;
	private TimerThread timerThread;

	private Button playButton;
	private Button pauseButton;
	private Button stopButton;
	private Button nextButton;

	private SongTable songTable;

	private Label playListLabel = new Label("");
	private Label playTimeLabel = new Label(INITIAL_PLAY_TIME_LABEL);
	private Label currentSongLabel = new Label(NO_CURRENT_SONG);

	private ChoiceBox<SortCriterion> sortChoiceBox;
	private TextField searchTextField;
	private Button filterButton;

	public Player() {

	}

	public void setUseCertPlayList(boolean value) {
		this.useCertPlayList = value;
	}

	public static void main(String[] args) throws Exception {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("APA Player");
		BorderPane root = new BorderPane();
		stage.setScene(new Scene(root, 600, 400));

		String playlistFilePath = "";
		if (!useCertPlayList) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Wähle m3u Playlist");
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				playlistFilePath = file.getAbsolutePath();
				this.playList = new PlayList(playlistFilePath);
			}
		} else {
			playlistFilePath = DEFAULT_PLAYLIST;
			this.playList = new PlayList(playlistFilePath);
		}
		this.playListLabel.setText(playlistFilePath);

		HBox controlsBox = new HBox(0);
		VBox bottomRootBox = new VBox();
		VBox mainContentBox = new VBox(0);

		/* START FILTER */
		VBox filterRootBox = new VBox(5);
		HBox searchBox = new HBox(15);
		HBox sortBox = new HBox(15);

		Label searchLabel = new Label("Suchtext");
		searchLabel.setPrefWidth(80);
		searchTextField = new TextField();
		searchTextField.setPrefWidth(200);

		Label sortLabel = new Label("Sortierung");
		sortLabel.setPrefWidth(80);
		sortChoiceBox = new ChoiceBox<SortCriterion>();
		sortChoiceBox.getItems().setAll(SortCriterion.values());
		sortChoiceBox.setPrefWidth(200);

		filterButton = new Button("Anzeigen");
		filterButton.setOnAction(e -> {
			this.setSortSearch(this.sortChoiceBox.getValue(), this.searchTextField.getText());
		});

		searchBox.getChildren().addAll(searchLabel, searchTextField);
		sortBox.getChildren().addAll(sortLabel, sortChoiceBox, filterButton);

		filterRootBox.getChildren().addAll(searchBox, sortBox);

		TitledPane filterPane = new TitledPane("Filter", filterRootBox);

		mainContentBox.getChildren().add(filterPane);
		/* END FILTER */

		/* START TABLE */
		songTable = new SongTable(playList);
		
		songTable.setRowSelectionHandler(e -> {
		    this.stopCurrentSong();
		    Song selectedSong = this.songTable.getSelectionModel().getSelectedItem();
		    if (selectedSong != null) {
		        AudioFile selectedAudioFile = selectedSong.getAudioFile();
		        this.playList.jumpToAudioFile(selectedAudioFile);
		        this.playCurrentSong();
		    }
		});
		/* END TABLE */

		/* START INFO BOX */
		Label currentPlaylistDescLabel = new Label("Playlist");
		Label currentSongDescLabel = new Label("Aktuelles Lied");
		Label currentDurationDescLabel = new Label("Abspielzeit");

		GridPane infoBoxGridPane = new GridPane();
		infoBoxGridPane.setPadding(new Insets(10, 10, 10, 10));
		infoBoxGridPane.setVgap(5);
		infoBoxGridPane.setHgap(5);

		infoBoxGridPane.add(currentPlaylistDescLabel, 0, 0);
		infoBoxGridPane.add(this.playListLabel, 1, 0);

		infoBoxGridPane.add(currentSongDescLabel, 0, 1);
		infoBoxGridPane.add(this.currentSongLabel, 1, 1);

		infoBoxGridPane.add(currentDurationDescLabel, 0, 2);
		infoBoxGridPane.add(this.playTimeLabel, 1, 2);
		bottomRootBox.getChildren().add(infoBoxGridPane);
		/* END INFO BOX */

		/* START CONTROLS */
		controlsBox.setAlignment(Pos.CENTER);

		playButton = createButton("play.jpg");
		playButton.setOnAction(e -> {
			this.playCurrentSong();
		});

		pauseButton = createButton("pause.jpg");
		pauseButton.setDisable(true);
		pauseButton.setOnAction(e -> {
			this.pauseCurrentSong();
		});

		stopButton = createButton("stop.jpg");
		stopButton.setDisable(true);
		stopButton.setOnAction(e -> {
			this.stopCurrentSong();
		});

		nextButton = createButton("next.jpg");
		nextButton.setOnAction(e -> {
			this.skipCurrentSong();
		});

		controlsBox.getChildren().addAll(playButton, pauseButton, stopButton, nextButton);
		bottomRootBox.getChildren().addAll(controlsBox);
		/* END CONTROLS */

		root.setTop(mainContentBox);
		root.setCenter(this.songTable);
		root.setBottom(bottomRootBox);

		stage.show();
	}

	public void loadPlayList(String pathname) {
		if (pathname == null || pathname.isBlank()) {
			this.playList = new PlayList(DEFAULT_PLAYLIST);
		} else {
			this.playList = new PlayList(pathname);
		}
		this.playListLabel.setText(pathname);
	}

	private void setSortSearch(SortCriterion sort, String search) {
		if(search != null) {
			this.playList.setSearch(search);
		}
		if(sort != null) {
			this.playList.setSortCriterion(sort);
		}
		this.songTable.refreshSongs();
	}

	private void playCurrentSong() {
		this.setButtonStates(true, false, false, false);
		AudioFile currentAudioFile = this.playList.currentAudioFile();


	    if (this.playerThread != null || this.timerThread != null) {
	        this.playerThread.terminate();
	        this.timerThread.terminate();
	    }

	    this.playerThread = new PlayerThread();
	    this.timerThread = new TimerThread();

	    this.playerThread.start();
	    this.timerThread.start();

	}

	private void pauseCurrentSong() {
		this.setButtonStates(true, false, false, false);
        
		AudioFile currentAudioFile = this.playList.currentAudioFile();
		
        currentAudioFile.togglePause();

	}

	private void stopCurrentSong() {
		this.setButtonStates(false, true, true, false);
		this.updateSongInfo(null);

		if(this.playerThread == null || this.timerThread == null) {
			return;
		}
		
		this.playerThread.terminate();
		this.timerThread.terminate();
		this.playList.currentAudioFile().stop();
	}

	private void skipCurrentSong() {
		this.setButtonStates(true, false, false, false);
        this.playerThread.terminate();
        this.timerThread.terminate();
        this.playList.currentAudioFile().stop();
		this.playList.nextSong();
		this.playCurrentSong();

	}
	
	class PlayerThread extends Thread {
		
		private boolean stopped = false;
		
		@Override
		public void run() {
			while(!stopped) {
				AudioFile currentAudioFile = playList.currentAudioFile();
				
				try {
					songTable.selectSong(currentAudioFile);
					currentAudioFile.play();
				} catch (NotPlayableException e) {
					e.printStackTrace();
				}
				if(!stopped) {
					playList.nextSong();
					updateSongInfo(playList.currentAudioFile());
				}
			}
		}
		
		public void terminate() {
			this.stopped = true;
		}
	}
	
	class TimerThread extends Thread {
		
		private boolean stopped = false;
		
		@Override
		public void run() {
			while(!stopped) {
				AudioFile currentAudioFile = playList.currentAudioFile();
				updateSongInfo(currentAudioFile);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void terminate() {
			this.stopped = true;
		}
	}

	private Button createButton(String iconfile) {
		Button button = null;
		try {
			URL url = getClass().getResource("/icons/" + iconfile);
			Image icon = new Image(url.toString());
			ImageView imageView = new ImageView(icon);
			imageView.setFitHeight(20);
			imageView.setFitWidth(20);
			button = new Button("", imageView);
			button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			button.setStyle("-fx-background-color: #fff;");
		} catch (Exception e) {
			System.out.println("Image " + "icons/" + iconfile + " not found!");
			System.exit(-1);
		}
		return button;
	}

	private void setButtonStates(boolean play, boolean pause, boolean stop, boolean next) {
		this.playButton.setDisable(play);
		this.pauseButton.setDisable(pause);
		this.stopButton.setDisable(stop);
		this.nextButton.setDisable(next);
	}

	private void updateSongInfo(AudioFile af) {
		Platform.runLater(() -> {
			if (af == null) {
				this.currentSongLabel.setText(this.NO_CURRENT_SONG);
				this.playTimeLabel.setText(this.INITIAL_PLAY_TIME_LABEL);
			} else {
				this.currentSongLabel.setText(af.toString());
				this.playTimeLabel.setText(af.formatPosition());
			}
		});
	}

}
