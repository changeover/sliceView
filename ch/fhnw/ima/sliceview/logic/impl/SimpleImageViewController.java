package ch.fhnw.ima.sliceview.logic.impl;

import ch.fhnw.ima.sliceview.app.ApplicationContext;
import ch.fhnw.ima.sliceview.logic.ImageViewController;
import ch.fhnw.ima.sliceview.logic.SelectionInformation;
import ch.fhnw.ima.sliceview.present.image.ImageView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SimpleImageViewController implements ImageViewController{

    private SelectionInformation selectionInformation;
    private ImageView imageView;


    public SimpleImageViewController(ApplicationContext applicationContext, ImageView imageView){
        this.selectionInformation = applicationContext.getSelectionInformation();
        this.imageView = imageView;
        getCoordinates();
    }
    public void getCoordinates(){
        imageView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int xCoordinate = imageView.getDataX((int)event.getX());
                setXCoordinate(xCoordinate);
                int yCoordinate = imageView.getDataY((int)event.getY());
                setYCoordinate(yCoordinate);
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCoordinates(-1);
            }
        });
    }
    public void setXCoordinate(int xCoordinate) {
        selectionInformation.setXCoordinate(xCoordinate);
    }
    public void setYCoordinate(int yCoordinate) {
        selectionInformation.setYCoordinate(yCoordinate);

    }
    public void setCoordinates(int coordinates){
        setXCoordinate(coordinates);
        setYCoordinate(coordinates);

    }
}
