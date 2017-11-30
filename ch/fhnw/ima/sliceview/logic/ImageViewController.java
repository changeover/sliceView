package ch.fhnw.ima.sliceview.logic;

import ch.fhnw.ima.sliceview.present.image.ImageView;


public interface ImageViewController {
    void getCoordinates();

    void setCoordinates(int coordinates);

    void setXCoordinate(int xCoordinate);

    void setYCoordinate(int xCoordinate);

    ImageView getImageView();
}
