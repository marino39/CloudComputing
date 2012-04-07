package cloud.computing.algorithms;

import cloud.computing.client.ComputingListener;

public class MatrixFilter {
	
	private double[][] filter;
	private double factor;
	private double bias;
	
	public MatrixFilter(double[][] filter, double factor, double bias) {
		this.filter = filter;
		this.factor = factor;
		this.bias = bias;
	}
	
	public int[] applyFilter(int[] image, int w, int h,int startX,int startY,int endX,int endY, ComputingListener l) {
		int result[] = new int[image.length];
		int filterWidth = filter.length;
		int filterHeight = filter[0].length;
		int pixelPerLine = w;
		
		for(int x = startX; x < startX + endX; x++) 
		    for(int y = startY; y < startY + endY; y++) {
		    { 
		        double red = 0.0, green = 0.0, blue = 0.0; 
		         
		        for(int filterX = 0; filterX < filterWidth; filterX++) 
		        for(int filterY = 0; filterY < filterHeight; filterY++) 
		        { 
		            int imageX = (x - filterWidth / 2 + filterX + w) % w; 
		            int imageY = (y - filterHeight / 2 + filterY + h) % h; 
		            
		            red += ((double) getRed(image[imageX + imageY*pixelPerLine])) * filter[filterX][filterY]; 
		            green += ((double) getGreen(image[imageX + imageY*pixelPerLine])) * filter[filterX][filterY]; 
		            blue += ((double) getBlue(image[imageX + imageY*pixelPerLine])) * filter[filterX][filterY]; 
		        } 
		         
		        int pixel = ((int) Math.min(Math.max((int)(factor * red + bias), 0), 255)) << 16;
		        pixel += ((int) Math.min(Math.max((int)(factor * green + bias), 0), 255)) << 8;
		        pixel += ((int) Math.min(Math.max((int)(factor * blue + bias), 0), 255));
		        
		        result[x + y*pixelPerLine] = pixel;
		    }
		}
		return result;
	}
	
	private int getRed(int ARGB) {
		return (ARGB >> 16) & 0xFF;
	}
	
	private int getGreen(int ARGB) {
		return (ARGB >> 8) & 0xFF;
	}
	
	private int getBlue(int ARGB) {
		return (ARGB >> 0) & 0xFF;
	}
	
}
