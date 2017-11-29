package ch.fhnw.ima.sliceview.logic;

public interface HistogrammController {

    void setMin(int min);

    void setMax(int max);

    int getMax();

    int getMin();

    void addListener(HistogrammControllerListener listener);
}
