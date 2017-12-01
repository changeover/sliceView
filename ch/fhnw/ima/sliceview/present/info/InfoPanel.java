package ch.fhnw.ima.sliceview.present.info;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import ch.fhnw.ima.sliceview.logic.SelectionInformationListener;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InfoPanel extends StackPane {
    private ApplicationContext applicationContext;
    private Label nameLabel;
    private Label coordinateLabel;
    private Label lableValueRange;
    private Label valueLabel;
    private Double value;
    private Double startValue;
    private Double endValue;
    private boolean isRange;

    public InfoPanel(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        nameLabel = new Label();
        coordinateLabel = new Label();
        valueLabel = new Label();
        lableValueRange = new Label();

        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Image Name:"));
        vBox.getChildren().add(nameLabel);
        vBox.getChildren().add(new Label("Coordinates:"));
        vBox.getChildren().add(coordinateLabel);
        vBox.getChildren().add(lableValueRange);
        vBox.getChildren().add(valueLabel);

        vBox.setPadding(new Insets(10));
        getChildren().add(vBox);

        refreshDataInformation();

        applicationContext.getGridData().addListener(this::refreshDataInformation);
        applicationContext.getSelectionInformation().addListener(new SelectionInformationListener() {
            @Override
            public void selectionInformationChanged() {
                refreshDataInformation();
                isRange = false;
            }

            @Override
            public void rangeInformationChanged() {
                isRange = true;
                refreshDataInformation();
            }
        });
    }

    private void refreshDataInformation() {
        nameLabel.setText(applicationContext.getGridData().getName());
        coordinateLabel.setText(applicationContext.getSelectionInformation().getCoordinates().toString());
        value = applicationContext.getSelectionInformation().getValue();
        startValue = applicationContext.getSelectionInformation().getStartValue();
        endValue = applicationContext.getSelectionInformation().getEndValue();
        if(isRange){
            lableValueRange.setText("Range:");
            valueLabel.setText(startValue.toString() + " - "+endValue.toString());
        }
        else {
            lableValueRange.setText("Value:");
            valueLabel.setText(value.toString());
        }
    }

}