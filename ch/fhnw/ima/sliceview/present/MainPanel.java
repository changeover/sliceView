package ch.fhnw.ima.sliceview.present;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import ch.fhnw.ima.sliceview.persist.FileImageLoader;
import ch.fhnw.ima.sliceview.present.histo.HistogramPanel;
import ch.fhnw.ima.sliceview.present.image.ImagePanel;
import ch.fhnw.ima.sliceview.present.info.InfoPanel;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.prefs.Preferences;

public class MainPanel extends BorderPane {
    public MainPanel(ApplicationContext applicationContext, int windowWidth, int windowHeight) {
        final int INFO_AREA_WIDTH = 200;
        final int HISTO_AREA_HEIGHT = 300;

        // Left side
        //-----------

        StackPane leftPane = new StackPane(new InfoPanel(applicationContext));

        // Right side
        //------------

        // Top
        StackPane topPane = new StackPane(new ImagePanel(applicationContext));

        // Bottom
        StackPane bottomPane = new StackPane(new HistogramPanel(applicationContext));

        final SplitPane horizontalSplitPane = new SplitPane();
        horizontalSplitPane.setOrientation(Orientation.VERTICAL);
        horizontalSplitPane.setDividerPosition(0, (double)(windowHeight - HISTO_AREA_HEIGHT) / (double)windowHeight);
        horizontalSplitPane.getItems().addAll(topPane, bottomPane);

        StackPane rightPane = new StackPane(horizontalSplitPane);

        // Put things together
        //---------------------

        final SplitPane verticalSplitPane = new SplitPane();
        verticalSplitPane.setOrientation(Orientation.HORIZONTAL);
        verticalSplitPane.setDividerPosition(0, (double)INFO_AREA_WIDTH / (double)windowWidth);

        verticalSplitPane.getItems().addAll(leftPane, rightPane);

        setTop(createMenuBar(applicationContext));
        setCenter(verticalSplitPane);
    }

    private MenuBar createMenuBar(final ApplicationContext applicationContext) {
        MenuBar menuBar = new MenuBar();

        Menu menu;
        MenuItem menuItem;

        // File
        //------

        menu = new Menu("File");
        menuBar.getMenus().add(menu);

        menuItem = new MenuItem("Load Image...");
        menu.getItems().add(menuItem);
        menuItem.setOnAction(event -> loadObject(applicationContext, ImageType.IMAGE));

        menuItem = new MenuItem("Load Data...");
        menu.getItems().add(menuItem);
        menuItem.setOnAction(event -> loadObject(applicationContext, ImageType.DATA));

        menu.getItems().add(new SeparatorMenuItem());

        menuItem = new MenuItem("Exit");
        menu.getItems().add(menuItem);
        menuItem.setOnAction(event -> System.exit(0));

        // Help
        //------

        menu = new Menu("Help");
        menuBar.getMenus().add(menu);

        menuItem = new MenuItem("About");
        menu.getItems().add(menuItem);
        menuItem.setOnAction(event -> {
            String version = "This is " + applicationContext.getName() + "\nVersion: " + applicationContext.getVersion();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, version);
            alert.setTitle("About");
            alert.getDialogPane().setHeaderText(null);
            alert.showAndWait();
        });

        return menuBar;
    }

    private enum ImageType {IMAGE, DATA}
    private void loadObject(ApplicationContext appContext, ImageType imageType) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");

        // Initialize the system preferences
        final Preferences preferences = Preferences.userRoot().node("SliceView");
        final String currentDirectoryKey = "directory";

        // Retrieve the current directory from the system preferences,
        // and set it as the initial directory of the chooser, if possible
        String currentDirectoryName = preferences.get(currentDirectoryKey, "");
        if (currentDirectoryName.length() > 0) {
            File currentDirectory = new File(currentDirectoryName);
            if (currentDirectory.isDirectory()) {
                chooser.setInitialDirectory(currentDirectory);
            }
        }

        File selectedFile = chooser.showOpenDialog(getScene().getWindow());
        if (selectedFile != null) {
            int[][] data = null;
            switch (imageType) {
                case IMAGE:
                    data = FileImageLoader.loadImageFromFile(selectedFile.getPath());
                    break;
                case DATA:
                    data = FileImageLoader.loadDataFromFile(selectedFile.getPath());
                    break;
            }
            if (data != null) {
                appContext.getGridData().setData(data, selectedFile.getName());
            }

            // Store the current path in the system preferences
            String directoryName = selectedFile.getAbsolutePath();
            preferences.put(currentDirectoryKey, directoryName);
        }

    }

}