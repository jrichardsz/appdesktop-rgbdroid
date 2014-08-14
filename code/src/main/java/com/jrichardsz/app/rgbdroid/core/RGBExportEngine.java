package com.jrichardsz.app.rgbdroid.core;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;

public class RGBExportEngine{

	private ArrayList<String> pixelStrings=new ArrayList<String>();
	private int widthImage;
	private int heightImage;

	public void execute(String imageFilePath) throws Exception{

		if(pixelStrings!=null && !pixelStrings.isEmpty()){
			pixelStrings.clear();
		}
		
		BufferedImage bufferedImage=null;

		try{

			bufferedImage=ImageIO.read(new File(imageFilePath));

			widthImage=bufferedImage.getWidth();
			heightImage=bufferedImage.getHeight();

			savePixelsAsArrayString(bufferedImage);

		}
		catch(IOException e){
			throw new Exception("Failed to export rgb.",e);
		}

	}

	private void savePixelsAsArrayString(BufferedImage image){

		for(int i=0;i < heightImage;i++){

			String row="";

			for(int j=0;j < widthImage;j++){
				int pixel=image.getRGB(j,i);

				if(j == widthImage - 1){
					row+=getPixelARGBAsString(pixel);
				}
				else{
					row+=getPixelARGBAsString(pixel) + ";";
				}
			}
			
			pixelStrings.add(row);
		}
	}

	private String getPixelARGBAsString(int pixel){

		int alpha=(pixel >> 24) & 0xff;
		int red=(pixel >> 16) & 0xff;
		int green=(pixel >> 8) & 0xff;
		int blue=(pixel) & 0xff;

		return alpha + ", " + red + ", " + green + ", " + blue;
	}

	public ArrayList<String> getPixelStrings(){
		return pixelStrings;
	}

	public void setPixelStrings(ArrayList<String> pixelStrings){
		this.pixelStrings=pixelStrings;
	}

	public int getWidthImage(){
		return widthImage;
	}

	public void setWidthImage(int widthImage){
		this.widthImage=widthImage;
	}

	public int getHeightImage(){
		return heightImage;
	}

	public void setHeightImage(int heightImage){
		this.heightImage=heightImage;
	}

}
