package org.javatraining.model;

/**
 * The project name is cms.
 * Created by sergey on 28.06.15 at 17:27.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class Image {
    private byte[] imagebytes;

    public Image(){

    }

    public Image(byte[] imagebytes){
        this.imagebytes = imagebytes;
    }

    public byte[] getImagebytes() {
        return imagebytes;
    }

    public void setImagebytes(byte[] imagebytes) {
        this.imagebytes = imagebytes;
    }
}
