package ch.fhnw.ima.sliceview.logic.impl;

import ch.fhnw.ima.sliceview.logic.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleSelectionInformation implements SelectionInformation{
    private int xCoordinate;
    private int yCoordinate;
    private double value = 0.00;
    private List<SelectionInformationListener> listeners;
    private GridData gridData;
    private double startValue = 0.00;
    private double endValue = 0.00;

    public SimpleSelectionInformation(GridData gridData){
        this.gridData = gridData;
        listeners = new ArrayList<>();
    }

    public void setCoordinates( int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        setValue();
    }

    public void setXCoordinate(int xCoordinate){
        this.xCoordinate = xCoordinate;
        setValue();
    }
    public void setYCoordinate(int yCoordinate){
        this.yCoordinate = yCoordinate;
        setValue();
    }

    public void setValue(){
        this.value = gridData.getValue(xCoordinate,yCoordinate);
        fireDataChanged();
    }

    public void setValue(double value){
        this.value = value;
        fireDataChanged();
    }

    public double getValue(){
        return this.value;
    }

    public int getXCoordinate(){
        return this.xCoordinate;
    }

    public int getYCoordinate(){
        return this.yCoordinate;
    }

    public List<Integer> getCoordinates(){
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(xCoordinate);
        coordinates.add(yCoordinate);
        return coordinates;
    }
    private void fireDataChanged() {
        for (SelectionInformationListener listener : listeners) {
            listener.selectionInformationChanged();
        }
    }
    public void setRange(double startValue, double endValue){
        this.startValue = startValue;
        this.endValue = endValue;
        for(SelectionInformationListener listener:listeners){
            listener.rangeInformationChanged();
        }
    }
    public double getStartValue(){
        return Math.round(this.startValue);
    }
    public double getEndValue(){
        return Math.round(this.endValue);
    }
    public void addListener(SelectionInformationListener listener){
        listeners.add(listener);
    }
}
