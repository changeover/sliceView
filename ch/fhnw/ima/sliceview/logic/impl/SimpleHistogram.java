package ch.fhnw.ima.sliceview.logic.impl;

import ch.fhnw.ima.sliceview.logic.GridData;
import ch.fhnw.ima.sliceview.logic.GridDataListener;
import ch.fhnw.ima.sliceview.logic.Histogram;

/**
 * A basic implementation of the histogram interface for grid data.
 * Note that this class registers itself as an observer with the grid data
 * so that it can update itself should the data change. However there is no infrastructure
 * to let users of the histogram class know that the histogram changed. This causes indirection, as
 * the users of the histogram have to observe the data grid instead to be notified of updates.
 * This should be improved!
 */
public class SimpleHistogram implements Histogram {
    private int[] bins;
    private int maxCount;
    private GridData gridData;

    /**
     * Creates a histogram model for the specified image data.
     * @param gridData the image data to be analyzed
     */
    public SimpleHistogram(GridData gridData) {
        this.gridData = gridData;
        computeBins();

        gridData.addListener(new GridDataListener() {
            public void dataChanged() {
                computeBins();
            }
        });
    }

    /**
     * Returns the number of bins.
     * @return number of bins
     */
    public int getBinCount() {
        return bins.length;
    }

    /**
     * Returns the number of values that fall within a specific bin.
     * @param index index of bin
     * @return number of values in that bin
     */
    public int getBin(int index) {
        return bins[index];
    }

    /**
     * Looks up the bin index into which a value falls.
     * @param value value to lookup
     * @return index of the bin that the value falls into
     */
    public int getBinIndex(int value) {
        double dataRange = gridData.getMaxValue() - gridData.getMinValue();
        double normalizedValue = (value - gridData.getMinValue()) / dataRange;
        int index = (int) (normalizedValue * getBinCount());
        index = Math.min(index, getBinCount()-1);
        return index;
    }

    /**
     * Returns the maximum number of values in any bin.
     * @return maximum number of values
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * Distributes the data values into the bins.
     */
    private void computeBins() {
        bins = new int[computeBinCount()];
        maxCount = 0;

        for (int i = 0; i < gridData.getHeight(); i++) {
            for (int j = 0; j < gridData.getWidth(); j++) {
                int index = getBinIndex(gridData.getValue(j, i));
                bins[index]++;
                if (bins[index] > maxCount) maxCount = bins[index];
            }
        }
    }

    private int computeBinCount() {
        int binCount;
        int dataCount = gridData.getHeight() * gridData.getWidth();

        // Two simple ways of computing bin count
//        int binCount = 50;
//        int binCount = (int) Math.sqrt(dataCount);

        // More accurate method that takes distribution of values into account

        double average = 0;
        for (int i = 0; i < gridData.getHeight(); i++) {
            for (int j = 0; j < gridData.getWidth(); j++) {
                average += gridData.getValue(j, i);
            }
        }
        average /= dataCount;

        double sumSquaredDiff = 0;
        for (int i = 0; i < gridData.getHeight(); i++) {
            for (int j = 0; j < gridData.getWidth(); j++) {
                double diff = gridData.getValue(j, i) - average;
                sumSquaredDiff += (diff * diff);
            }
        }
        sumSquaredDiff /= dataCount;
        double standardDeviation = Math.sqrt(sumSquaredDiff);
        double binWidth = 3.94 * standardDeviation * Math.pow(dataCount, -(1.0/3.0));
        binCount = (int) ((gridData.getMaxValue() - gridData.getMinValue()) / binWidth);

        return binCount;
    }
    
    @Override
    public String toString() {
        String text = "# bins " + bins.length + ": (";
        for (int i = 0; i < bins.length; i++) {
            text += bins[i] + " ";
        }
        text += ")";
        return text;
    }

}
