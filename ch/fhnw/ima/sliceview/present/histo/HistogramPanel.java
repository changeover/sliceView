package ch.fhnw.ima.sliceview.present.histo;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import ch.fhnw.ima.sliceview.logic.Histogram;
import ch.fhnw.ima.sliceview.logic.ImageModelListener;
import ch.fhnw.ima.sliceview.logic.impl.SimpleHistogram;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class HistogramPanel extends StackPane {
    public HistogramPanel(final ApplicationContext applicationContext) {

        // Create the histogram view
        //---------------------------

        final Histogram histogram = new SimpleHistogram(applicationContext.getGridData());
        final HistogramView histogramView = new HistogramView(applicationContext, histogram);

        final StackPane histogramPanel = new StackPane();
        histogramPanel.setStyle("-fx-border-color: black");
        histogramPanel.getChildren().add(histogramView);

        // Create the panel with the controls
        //------------------------------------

        final HBox controlPanel = new HBox();
        controlPanel.setPadding(new Insets(0, 0, 10, 0));
        controlPanel.setAlignment(Pos.CENTER);

        // Logarithmic scale
        final CheckBox logCheckBox = new CheckBox("Logarithmic Scale");
        logCheckBox.setSelected(histogramView.isLogarithmicScale());
        logCheckBox.setOnAction(event -> histogramView.setLogarithmicScale(logCheckBox.isSelected()));
        controlPanel.getChildren().add(logCheckBox);

        Region fillerRegion = new Region();
        controlPanel.getChildren().add(fillerRegion);
        HBox.setHgrow(fillerRegion, Priority.ALWAYS);

        // Min max
        controlPanel.getChildren().add(new Label("Min: "));

        final TextField minTextField = new TextField();
        minTextField.setPrefColumnCount(10);
        int min = applicationContext.getImageModel().getMin();
        minTextField.setText(Integer.toString(min));
        minTextField.setOnAction(event -> {
            int minValue = Integer.parseInt(minTextField.getText());
            applicationContext.getImageModel().setMin(minValue);
        });
        controlPanel.getChildren().add(minTextField);

        controlPanel.getChildren().add(new Label("  Max: "));

        final TextField maxTextField = new TextField();
        maxTextField.setPrefColumnCount(10);
        int max = applicationContext.getImageModel().getMax();
        maxTextField.setText(Integer.toString(max));
        maxTextField.setOnAction(event -> {
            int maxValue = Integer.parseInt(maxTextField.getText());
            applicationContext.getImageModel().setMax(maxValue);
        });
        controlPanel.getChildren().add(maxTextField);

        applicationContext.getImageModel().addListener(new ImageModelListener() {
            public void imageModelChanged() {
                int min = applicationContext.getImageModel().getMin();
                minTextField.setText(Integer.toString(min));
                int max = applicationContext.getImageModel().getMax();
                maxTextField.setText(Integer.toString(max));
            }
        });

        // Create the main layout
        //------------------------

        VBox vBox = new VBox();
        vBox.getChildren().add(controlPanel);
        vBox.getChildren().add(histogramPanel);

        vBox.setPadding(new Insets(10));

        VBox.setVgrow(histogramPanel, Priority.ALWAYS);

        getChildren().add(vBox);
    }
}