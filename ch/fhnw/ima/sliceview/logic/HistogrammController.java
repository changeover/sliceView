package ch.fhnw.ima.sliceview.logic;

public interface HistogrammController {

    void setMin(int min);

    void setMax(int max);

    int getMax();

    int getMin();

    void setLowestMin(int min);

    void setHighestMax(int max);

    int getHighestMax();

    int getLowestMin();

    void setStartBorder(double xStart, double windowWidth);

    void setEndBorder (double xEnd, double windowWidth);

    double calcValue(double cursor, double windowWidth);

    void addListener(HistogrammControllerListener listener);
}
