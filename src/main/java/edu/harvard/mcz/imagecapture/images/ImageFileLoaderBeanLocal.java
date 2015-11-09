/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.images;

import java.awt.Image;
import java.io.File;
import java.net.URL;

/**
 *
 * @author mole
 */
public interface ImageFileLoaderBeanLocal {

	String getAlt();

	String getFilename();

	Image getImage();

	URL getImageURL();

	File getPathToImages();

	void setFilename(String filename);

	void setPathToImages(File pathToImages);

}
