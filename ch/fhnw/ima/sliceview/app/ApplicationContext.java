package ch.fhnw.ima.sliceview.app;

import ch.fhnw.ima.sliceview.logic.GridData;
import ch.fhnw.ima.sliceview.logic.ImageModel;
import ch.fhnw.ima.sliceview.logic.impl.SimpleGridData;
import ch.fhnw.ima.sliceview.logic.impl.SimpleImageModel;

/**
 * This class is the central container for the main data structures and other information that is
 * used throughout the application. This way, only the application context needs to be passed
 * around the various classes instead of many individual variables.
 */
public class ApplicationContext {
    private String version;
    private String name;
    private GridData gridData;
    private ImageModel imageModel;

    public ApplicationContext(String version, String name) {
        this.version = version;
        this.name = name;
        gridData = new SimpleGridData();
        imageModel = new SimpleImageModel(gridData);
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public GridData getGridData() {
        return gridData;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

}
