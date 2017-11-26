package ch.fhnw.ima.sliceview.present.info;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InfoPanel extends StackPane {
    private ApplicationContext applicationContext;
    private Label nameLabel;
    private Label coordinateLabel;
    private Label valueLabel;

    public InfoPanel(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        nameLabel = new Label();
        coordinateLabel = new Label();
        valueLabel = new Label();

        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Image Name:"));
        vBox.getChildren().add(nameLabel);
        vBox.getChildren().add(new Label("Coordinates:"));
        vBox.getChildren().add(coordinateLabel);
        vBox.getChildren().add(new Label("Value:"));
        vBox.getChildren().add(valueLabel);

        vBox.setPadding(new Insets(10));
        getChildren().add(vBox);

        refreshDataInformation();

        applicationContext.getGridData().addListener(this::refreshDataInformation);
        applicationContext.getSelectionInformation().addListener(this::refreshDataInformation);
    }

    private void refreshDataInformation() {
        nameLabel.setText(applicationContext.getGridData().getName());
        coordinateLabel.setText(applicationContext.getSelectionInformation().getCoordinates().toString());
        Double value = applicationContext.getSelectionInformation().getValue();
        valueLabel.setText(value.toString());
    }

}