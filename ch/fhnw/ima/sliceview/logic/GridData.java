package ch.fhnw.ima.sliceview.logic;

/**
 * This interface models a two-dimensional grid of data.
 * The grid can have arbitrary width and height.
 * The data consists of integer values and can be annotated with a name.
 * If the data of the grid changes, through the use of the setData method,
 * then a dataChanged event is sent to registered observers (listeners).
 */
public interface GridData {
    /**
     * Loads data into the grid.
     * @param values a two-dimensional array of integer values that represent the data
     * @param name a name that describes the data
     */
    void setData(int[][] values, String name);

    /**
     * Returns the width of the grid.
     * @return width
     */
    int getWidth();

    /**
     * Returns the hieght of the grid.
     * @return height
     */
    int getHeight();

    /**
     * Returns the data value at a specific position.
     * @param x the column of the data value to return
     * @param y the row of the data value to return
     * @return the data value at the specified position
     */
    int getValue(int x, int y);

    /**
     * Returns the minimum data value of the whole grid.
     * @return the minimum value
     */
    int getMinValue();

    /**
     * Returns the maximum data value of the whole grid.
     * @return the maximum value
     */
    int getMaxValue();

    /**
     * Returns the name that describes this data.
     * @return the name
     */
    String getName();

    /**
     * Register an observer (listener) that is notified if the grid data changes.
     * @param listener an observer
     */
    void addListener(GridDataListener listener);
}
