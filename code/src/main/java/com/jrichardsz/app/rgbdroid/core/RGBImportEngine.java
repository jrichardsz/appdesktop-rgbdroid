package com.jrichardsz.app.rgbdroid.core;

import java.awt.image.*;
import java.util.*;

import com.linet.util.file.*;

public class RGBImportEngine{
	
	private BufferedImage bufferedImageCreated;

	public void execute(String pathFile,int width,int height) throws Exception{

		
		ArrayList<String> pixelsAsString=(ArrayList<String>) FileUtil.readFileAsStringCollection(pathFile);

		bufferedImageCreated = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB_PRE);

		for(int row=0;row < height;row++){

			String pixels=pixelsAsString.get(row);

			String[] argbStringArray=pixels.split(";");

			for(int col=0;col < argbStringArray.length;col++){
				String argbString=argbStringArray[col];
				String[] argb=argbString.split(",");
				bufferedImageCreated.setRGB(col,row,convertStringToRGB(argb));
			}

		}
		
		//ImageIO.write(bufferedImageCreated,"jpg",new File("C:\\Users\\RM-RMC\\Desktop\\rgbpattern.jpg")); 

	}

	private int convertStringToRGB(String[] strRGB){
	
		int a = Integer.parseInt(strRGB[0].replace(" ",""));
		int r = Integer.parseInt(strRGB[1].replace(" ",""));
		int g = Integer.parseInt(strRGB[2].replace(" ",""));
		int b = Integer.parseInt(strRGB[3].replace(" ",""));
		int argb = (a << 24) | (r << 16) | (g << 8) | b;
		
		return argb;
	}

	public BufferedImage getBufferedImageCreated(){
		return bufferedImageCreated;
	}
	
}
