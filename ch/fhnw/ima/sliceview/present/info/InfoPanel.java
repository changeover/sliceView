package ch.fhnw.ima.sliceview.present.info;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InfoPanel extends StackPane {
    private ApplicationContext applicationContext;
    private Label nameLabel;

    public InfoPanel(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        nameLabel = new Label();

        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Image Name:"));
        vBox.getChildren().add(nameLabel);

        vBox.setPadding(new Insets(10));
        getChildren().add(vBox);

        refreshDataInformation();

        applicationContext.getGridData().addListener(this::refreshDataInformation);
    }

    private void refreshDataInformation() {
        nameLabel.setText(applicationContext.getGridData().getName());
    }

}