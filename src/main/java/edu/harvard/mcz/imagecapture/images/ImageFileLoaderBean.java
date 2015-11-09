/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.mcz.imagecapture.images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mole
 */
@Stateless
@DeclareRoles("Administrator")
@RolesAllowed("Administrator")
public class ImageFileLoaderBean implements ImageFileLoaderBeanRemote, ImageFileLoaderBeanLocal {

	private File pathToImages;
	private String filename;

	//private static final Log log = LogFactory.getLog(ImageFileLoaderBean.class);

	public ImageFileLoaderBean() {
		pathToImages = new File("/home/mole/stuff/MCZ/mcz/insects/testImages/base/live/");
		filename = "IMG_000053.JPG";
	}
	// Add business logic below. (Right-click in editor and choose
	// "Insert Code > Add Business Method")

	public File getPathToImages() {
		return pathToImages;
	}

	public void setPathToImages(File pathToImages) {
		this.pathToImages = pathToImages;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Image getImage() {
		BufferedImage result = null;
		try {
		      URL url = new URL(pathToImages + filename);
		      result = ImageIO.read(url);
		} catch (MalformedURLException e1) {

		} catch (IOException e) {


		}
		return result;
	}

	public URL getImageURL() {
		URL result = null;
		try {
	             //result = new URL(pathToImages + filename);
	             result = new URL("http://localhost:8086/test/faces/javax.faces.resource/images/IMG_042798_small.JPG");
	        } catch (MalformedURLException e1) {

		} catch (IOException e) {

		}
	       return result;
	}

	public String getAlt() {
		return "an bean image";
	}

}
