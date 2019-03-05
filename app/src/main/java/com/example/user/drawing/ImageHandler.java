package com.example.user.opencv_app1.drawing;

/**
 * By Siarhei Charkes in 2016
 * http://privateblog.info
 */
public class ImageHandler {
    private static final int HEIGHT = 320;
    private static final int WIDTH = 240;

    private int[][] currentImage = null; //height:width

    public ImageHandler(){}

    public int[][]getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(int[][]currentImage) {
        this.currentImage = currentImage;
    }
}
