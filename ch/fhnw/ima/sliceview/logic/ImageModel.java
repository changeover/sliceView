package ch.fhnw.ima.sliceview.logic;

import javafx.scene.image.Image;

/**
 * This interface models an image of a grid of data that can be displayed on the screen.
 * It supports windowing, i.e. scaling of a range of the underlying data to the full range of the image intensity.
 */
public interface ImageModel {
    /**
     * Sets the data range to be used for windowing.
     * @param min the lower bound of the range
     * @param max the upper bound of the range
     */
    void setRange(int min, int max);

    /**
     * Sets the lower bound of the range to be used for windowing.
     * @param min the lower bound of the range
     */
    void setMin(int min);

    /**
     * Sets the upper bound of the range to be used for windowing.
     * @param max the upper bound of the range
     */
    void setMax(int max);

    /**
     * Returns the lower bound of the range to be used for windowing.
     * @return the lower bound
     */
    int getMin();

    /**
     * Returns the upper bound of the range to be used for windowing.
     * @return the upper bound
     */
    int getMax();

    /**
     * Returns the image that can be displayed on screen.
     * @return the image
     */
    Image getImage();

    /**
     * Add an observer (listener) that is notified when the image model (e.g. the windowing range) changed.
     * @param listener the listener to add
     */
    void addListener(ImageModelListener listener);
}
