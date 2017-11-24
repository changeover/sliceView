package ch.fhnw.ima.sliceview.logic.impl;

import ch.fhnw.ima.sliceview.logic.GridData;
import ch.fhnw.ima.sliceview.logic.GridDataListener;
import ch.fhnw.ima.sliceview.logic.ImageModel;
import ch.fhnw.ima.sliceview.logic.ImageModelListener;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.List;

public class SimpleImageModel implements ImageModel {
    private GridData gridData;
    private int min;
    private int max;
    private Image image;
    private List<ImageModelListener> listeners;

    public SimpleImageModel(final GridData gridData) {
        this.gridData = gridData;

        min = gridData.getMinValue();
        max = gridData.getMaxValue();
        createImage();

        gridData.addListener(new GridDataListener() {
            @Override
            public void dataChanged() {
                min = gridData.getMinValue();
                max = gridData.getMaxValue();
                SimpleImageModel.this.createImage();
                SimpleImageModel.this.fireModelChanged();
            }
        });

        listeners = new ArrayList<>();
    }

    public void setRange(int min, int max) {
        if ((min != this.min) || (max != this.max)) {
            this.min = min;
            this.max = max;
            createImage();
            fireModelChanged();
        }
    }

    public void setMin(int min) {
        if (min != this.min) {
            this.min = min;
            createImage();
            fireModelChanged();
        }
    }

    public void setMax(int max) {
        if (max != this.max) {
            this.max = max;
            createImage();
            fireModelChanged();
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public Image getImage() {
        return image;
    }

    public void addListener(ImageModelListener listener) {
        listeners.add(listener);
    }

    private void fireModelChanged() {
        for (ImageModelListener listener : listeners) {
            listener.imageModelChanged();
        }
    }

    private void createImage() {
        if ((gridData.getWidth() > 0) && (gridData.getHeight() > 0)) {
            WritableImage writableImage = new WritableImage(gridData.getWidth(), gridData.getHeight());
            int[] windowedValue = new int[1];
            for (int row = 0; row < gridData.getHeight(); row++) {
                for (int column = 0; column < gridData.getWidth(); column++) {
                    int value = gridData.getValue(column, row);
                    windowedValue[0] = (int) ((double)(value - min) / (double)(max - min) * 255);
                    windowedValue[0] = Math.min(windowedValue[0], 255);
                    windowedValue[0] = Math.max(windowedValue[0], 0);
                    int argb = (255 << 24) | (windowedValue[0] << 16) | (windowedValue[0] << 8) | windowedValue[0];
                    writableImage.getPixelWriter().setArgb(column, row, argb);
                }
            }
            image = writableImage;
        }
    }
}
