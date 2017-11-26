package ch.fhnw.ima.sliceview.logic;

import java.util.List;

public interface SelectionInformation {

    void setCoordinates(int xCoordinate, int yCoordinate);

    void setXCoordinate(int xCoordinate);

    void setYCoordinate(int yCoordinate);

    void setValue();

    double getValue();

    int getXCoordinate();

    int getYCoordinate();

    List<Integer> getCoordinates();

    void addListener(SelectionInformationListener listener);
}
