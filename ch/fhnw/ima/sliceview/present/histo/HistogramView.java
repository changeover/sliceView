package ch.fhnw.ima.sliceview.present.histo;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import ch.fhnw.ima.sliceview.logic.*;
import ch.fhnw.ima.sliceview.present.DrawingPane;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

class HistogramView extends DrawingPane {
    private static double BORDER_PERCENTAGE = 0.1;

    private ApplicationContext applicationContext;
    private Histogram histogram;
    private boolean isLogarithmicScale;
    private double pointClickX;
    private double pointClickY;
    private double pointReleasedX;
    private double pointReleasedY;
    private Line line = new Line();

    HistogramView(ApplicationContext applicationContext, Histogram histogram) {
        this.applicationContext = applicationContext;
        this.histogram = histogram;

        isLogarithmicScale = true;

        addListeners();
        mouseLine();
    }
    private void mouseLine(){
        Canvas canvas = g.getCanvas();
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pointClickX = event.getX();
                pointClickY = event.getY();
                line.setStartX(pointClickX);
                line.setStartY(pointClickY);
            }
        });
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getX()<=getWidth()&&event.getX()>=pointClickX) {
                    applicationContext.getHistogrammController().setStartBorder(pointClickX, getWidth());
                    applicationContext.getHistogrammController().setEndBorder(event.getX(), getWidth());
                }
            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                repaint();
                    pointReleasedX = event.getX();
                    pointReleasedY = event.getY();
                if(pointClickX>0&&pointReleasedX<=getWidth()&&pointReleasedX>pointClickX) {
                    applicationContext.getHistogrammController().setStartBorder(pointClickX, getWidth());
                    applicationContext.getHistogrammController().setEndBorder(pointReleasedX, getWidth());
                }
            }
        });
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                repaint();
                drawBorders();
                double value = applicationContext.getHistogrammController().calcValue(event.getX(),getWidth());
                int binIndex = histogram.getBinIndex((int)value);
                double dataMin = applicationContext.getGridData().getMinValue();
                double dataMax = applicationContext.getGridData().getMaxValue();
                double binWidth = (dataMax - dataMin)/(double)histogram.getBinCount();
                double startValue = dataMin + (binWidth*binIndex);
                double endValue = dataMin + (binWidth*(binIndex+1));
                if(applicationContext.getImageViewController().getImageView().isVisible()) {
                    applicationContext.getSelectionInformation().setRange(startValue,endValue);
                }
            }
        });
    }

    private void addListeners(){
        GridData gridData = applicationContext.getGridData();
        SelectionInformation selectionInformation = applicationContext.getSelectionInformation();
        HistogrammController histogrammController = applicationContext.getHistogrammController();
        gridData.addListener(new GridDataListener(){
            public void dataChanged() {
                repaint();
            }
        });
        selectionInformation.addListener(new SelectionInformationListener() {
            public void selectionInformationChanged() {
                repaint();
                drawBorders();
                drawBar(histogram.getBinIndex((int)selectionInformation.getValue()),Color.RED,false,true);
            }
            public void rangeInformationChanged(){
                repaint();
                drawBorders();
                drawBar(histogram.getBinIndex((int)selectionInformation.getStartValue()),Color.BLUE,false,true);
            }
        });
        histogrammController.addListener(new HistogrammControllerListener() {
            @Override
            public void histogrammChanged() {
                repaint();
                drawBorders();
            }
        });
    }
    private void drawBorders(){
        drawBar(histogram.getBinIndex(applicationContext.getHistogrammController().getMin()),Color.BLACK,true,true);
        drawBar(histogram.getBinIndex(applicationContext.getHistogrammController().getMax()),Color.BLACK,true,false);
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
                drawBar(i, Color.grayRgb(170),false,true);
            }

        }
    }

    private void drawBar(int index, Paint fill, boolean border, boolean start) {
        double height = getHeight();
        double width = getWidth();
        double binWidth = width / histogram.getBinCount();

        int x0 = (int) (index * binWidth);
        int x1 = (int) ((index +1) * binWidth);
        int columnWidth = 0;
        if(border) {
            columnWidth = 2;
        }
        else{
            columnWidth = Math.max(x1 - x0, 1);
        }

        double count = histogram.getBin(index);
        double maxCount = histogram.getMaxCount();
        g.setFill(fill);
        if (isLogarithmicScale) {
            count = Math.log10(count);
            maxCount = Math.log10(maxCount);
        }
        int columnHeight = 0;
        if(border){
            columnHeight = (int) height;
        }
        else{
            columnHeight = (int) ((count / maxCount) * (height - BORDER_PERCENTAGE * height));
        }
        if(start) {
            g.fillRect(x0, height - columnHeight, columnWidth, columnHeight);
        }
        else{
            g.fillRect(x1-2, height - columnHeight, columnWidth, columnHeight);
        }
    }
}