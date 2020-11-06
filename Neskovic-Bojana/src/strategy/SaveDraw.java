package strategy;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import view.DrawingFrame;

public class SaveDraw implements SaveOpenFile{
	
	private DrawingFrame frame;
	
	public SaveDraw(DrawingFrame frame) {
		this.frame = frame;
	}

	//https://community.oracle.com/thread/2158176?tstart=0
	//https://stackoverflow.com/questions/4725320/how-to-save-window-contents-as-an-image
	@Override
	public void save(File file) {
		BufferedImage imagebuffer = null;
	    try {
	        imagebuffer = new Robot().createScreenCapture(frame.getView().getBounds());
	        frame.getView().paint(imagebuffer.createGraphics());
	        ImageIO.write(imagebuffer,"jpeg", new File(file + ".jpeg"));
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}

	@Override
	public void open(File file) {
		// TODO Auto-generated method stub
		
	}

}
