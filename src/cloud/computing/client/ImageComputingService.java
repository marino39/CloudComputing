package cloud.computing.client;

import cloud.computing.algorithms.MatrixFilterType;

public interface ImageComputingService {
	
	public static final int BLACK_AND_WHITE = 0x01;
	
	public int[] makeBlackAndWhite(int[] data);
	
	public byte[] processImage(byte[] image, int effect);
	
	public byte[] applyFilter(byte[] image, int imageX,int imageY, MatrixFilterType type);
	
}
