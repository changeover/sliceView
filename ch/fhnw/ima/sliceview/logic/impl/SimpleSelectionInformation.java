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
    public void addListener(SelectionInformationListener listener){
        listeners.add(listener);
    }
}
