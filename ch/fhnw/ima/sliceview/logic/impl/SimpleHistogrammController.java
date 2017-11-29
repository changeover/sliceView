package ch.fhnw.ima.sliceview.logic.impl;

import ch.fhnw.ima.sliceview.logic.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleHistogrammController implements HistogrammController {
    private int min;
    private int max;
    private List<HistogrammControllerListener> listeners;
    private ImageModel imageModel;


    public SimpleHistogrammController(ImageModel imageModel){
        listeners = new ArrayList<>();
    }

    public void setMin(int min) {
        this.min = min;
        fireHistogrammChanged();
    }

    public void setMax(int max) {
        this.max = max;
        fireHistogrammChanged();
    }
    public void setRange(int min, int max){
        this.min = min;
        this.max = max;
        fireHistogrammChanged();
    }
    private void fireHistogrammChanged() {
        for (HistogrammControllerListener listener : listeners) {
            listener.histogrammChanged();
        }
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
    public void addListener(HistogrammControllerListener listener){
        listeners.add(listener);
    }

}
