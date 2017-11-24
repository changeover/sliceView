package ch.fhnw.ima.sliceview.present.image;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import javafx.scene.layout.StackPane;

public class ImagePanel extends StackPane {
    public ImagePanel(ApplicationContext applicationContext) {
        ImageView imageView = new ImageView(applicationContext);
        imageView.setStyle("-fx-background-color: white");

        getChildren().add(imageView);
    }
}