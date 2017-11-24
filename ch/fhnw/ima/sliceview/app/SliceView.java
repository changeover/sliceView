package ch.fhnw.ima.sliceview.app;

import ch.fhnw.ima.sliceview.present.MainPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SliceView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String VERSION = "0.5";
        final String NAME = "SliceView FX";
        final int WINDOW_WIDTH = 1000;
        final int WINDOW_HEIGHT = 800;

        // Create the core data structures

        ApplicationContext applicationContext = new ApplicationContext(VERSION, NAME);

        // Create the main user interface panel and provide it with the application context

        Pane mainPane = new MainPanel(applicationContext, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Create and show the window

        Scene scene = new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle(applicationContext.getName() + " - v" + applicationContext.getVersion());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
