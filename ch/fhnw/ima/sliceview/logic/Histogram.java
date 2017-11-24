package ch.fhnw.ima.sliceview.logic;

/**
 * This interface models a histogram.
 */
public interface Histogram {
    /**
     * Returns the number of bins.
     * @return number of bins
     */
    public int getBinCount();

    /**
     * Returns the number of values that fall within a specific bin.
     * @param index index of bin
     * @return number of values in that bin
     */
    public int getBin(int index);

    /**
     * Looks up the bin index into which a value falls.
     * @param value value to lookup
     * @return index of the bin that the value falls into
     */
    public int getBinIndex(int value);

    /**
     * Returns the maximum number of values in any bin.
     * @return maximum number of values
     */
    public int getMaxCount();

}
