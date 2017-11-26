package ch.fhnw.ima.sliceview.present.histo;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import ch.fhnw.ima.sliceview.logic.GridDataListener;
import ch.fhnw.ima.sliceview.logic.Histogram;
import ch.fhnw.ima.sliceview.logic.ImageModelListener;
import ch.fhnw.ima.sliceview.logic.SelectionInformationListener;
import ch.fhnw.ima.sliceview.present.DrawingPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

class HistogramView extends DrawingPane {
    private static double BORDER_PERCENTAGE = 0.1;

    private ApplicationContext applicationContext;
    private Histogram histogram;
    private boolean isLogarithmicScale;

    HistogramView(ApplicationContext applicationContext, Histogram histogram) {
        this.applicationContext = applicationContext;
        this.histogram = histogram;

        isLogarithmicScale = true;

        applicationContext.getGridData().addListener(new GridDataListener(){
            public void dataChanged() {
                repaint();
            }
        });
        applicationContext.getSelectionInformation().addListener(new SelectionInformationListener() {
            @Override
            public void selectionInformationChanged() {
                repaint();
                drawBar(histogram.getBinIndex((int)applicationContext.getSelectionInformation().getValue()),Color.rgb(255,0,0));
            }
        });
    }

    public boolean isLogarithmicScale() {
        return isLogarithmicScale;
    }

    public void setLogarithmicScale(boolean logarithmicScale) {
        isLogarithmicScale = logarithmicScale;
        repaint();
    }

    @Override
    protected void paint() {
        if (histogram.getBinCount() > 0) {
            for (int i = 0; i < histogram.getBinCount(); i++) {
                drawBar(i, Color.grayRgb(170));
            }

        }
    }

    private void drawBar(int index, Paint fill) {
        double height = getHeight();
        double width = getWidth();
        double binWidth = width / histogram.getBinCount();

        int x0 = (int) (index * binWidth);
        int x1 = (int) ((index +1) * binWidth);
        int columnWidth = Math.max(x1-x0, 1);

        double count = histogram.getBin(index);
        double maxCount = histogram.getMaxCount();
        g.setFill(fill);
        if (isLogarithmicScale) {
            count = Math.log10(count);
            maxCount = Math.log10(maxCount);
        }

        int columnHeight = (int) ((count / maxCount) * (height - BORDER_PERCENTAGE * height));

        g.fillRect(x0, height-columnHeight, columnWidth, columnHeight);
    }
}