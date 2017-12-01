package ch.fhnw.ima.sliceview.logic.impl;

import ch.fhnw.ima.sliceview.logic.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleHistogrammController implements HistogrammController {
    private int min;
    private int max;
    private int lowestMin;
    private int highestMax;
    private List<HistogrammControllerListener> listeners;
    private ImageModel imageModel;
    private double xStart;
    private double xEnd;


    public SimpleHistogrammController(ImageModel imageModel){
        listeners = new ArrayList<>();
        this.imageModel = imageModel;
        imageModel.addListener(new ImageModelListener() {
            @Override
            public void imageModelChanged() {
                setLowestMin(imageModel.getMin());
                setHighestMax(imageModel.getMax());
            }
        });
    }


    public void setMin(int min) {
        this.min = min;
        setLowestMin(min);
        fireHistogrammChanged();
    }

    public void setMax(int max) {
        this.max = max;
        setHighestMax(max);
        fireHistogrammChanged();
    }
    public void setRange(int min, int max){
        this.min = min;
        this.max = max;
        setHighestMax(max);
        setLowestMin(min);
        fireHistogrammChanged();
    }
    public void setLowestMin(int lowestMin){
        if(lowestMin< this.lowestMin) {
            this.lowestMin = lowestMin;
        }
    }
    public void setHighestMax(int highestMax){
        if(highestMax>this.highestMax) {
            this.highestMax = highestMax;
        }
    }
    private void fireHistogrammChanged() {
        for (HistogrammControllerListener listener : listeners) {
            listener.histogrammChanged();
        }
    }
    public void setStartBorder (double xStart, double windowWidth){
        this.xStart = xStart;
        imageModel.setMin((int)calcValue(xStart,windowWidth));
    }
    public void setEndBorder (double xEnd, double windowWidth){
        this.xEnd = xEnd;
        imageModel.setMax((int)calcValue(xEnd,windowWidth));
    }
    public double calcValue(double cursor, double windowWidth){
        double value = cursor*(highestMax-lowestMin)/windowWidth+lowestMin;
        return value;
    }


    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
    public int getLowestMin(){
        return this.lowestMin;
    }
    public int getHighestMax(){
        return this.highestMax;
    }
    public void addListener(HistogrammControllerListener listener){
        listeners.add(listener);
    }

}
