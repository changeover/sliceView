package ch.fhnw.ima.sliceview.logic.impl;

import ch.fhnw.ima.sliceview.logic.GridData;
import ch.fhnw.ima.sliceview.logic.GridDataListener;

import java.util.ArrayList;
import java.util.List;

public class SimpleGridData implements GridData {
    private int[][] values;
    private int min;
    private int max;
    private String name;
    private List<GridDataListener> listeners;

    public SimpleGridData() {
        name = "";
        listeners = new ArrayList<>();
    }

    public void setData(int[][] values, String name) {
        this.values = values;
        this.name = name;
        scanMinMax();
        fireDataChanged();
    }

    public int getWidth() {
        if (values == null || values[0] == null) return 0;
        return values[0].length;
    }

    public int getHeight() {
        if (values == null) return 0;
        return values.length;
    }

    public int getValue(int x, int y) {
        return values[y][x];
    }

    public int getMinValue() {
        return min;
    }

    public int getMaxValue() {
        return max;
    }

    public String getName() {
        return name;
    }

    public void addListener(GridDataListener listener) {
        listeners.add(listener);
    }

    private void fireDataChanged() {
        for (GridDataListener listener : listeners) {
            listener.dataChanged();
        }
    }

    private void scanMinMax() {
        min = values[0][0];
        max = values[0][0];

        for (int[] row : values) {
            for (int value : row) {
                if (value > max) max = value;
                if (value < min) min = value;
            }
        }
    }

    @Override
    public String toString() {
        String result = "[GridData] ";
        if (values == null || values[0] == null) return result + "null";

        result += "Name: " + name + ", ";
        result += "Width: " + values[0].length + ", ";
        result += "Height: " + values.length + ", ";
        result += "Min: " + min + ", ";
        result += "Max: " + max;

        return result;
    }
}
