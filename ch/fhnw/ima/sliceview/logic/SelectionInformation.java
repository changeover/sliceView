package ch.fhnw.ima.sliceview.logic;

import java.util.List;

public interface SelectionInformation {

    void setCoordinates(int xCoordinate, int yCoordinate);

    void setXCoordinate(int xCoordinate);

    void setYCoordinate(int yCoordinate);

    void setValue();

    void setValue(double value);

    double getValue();

    int getXCoordinate();

    int getYCoordinate();

    void setRange(double startValue, double endValue);

    List<Integer> getCoordinates();

    double getStartValue();

    double getEndValue();

    void addListener(SelectionInformationListener listener);
}
