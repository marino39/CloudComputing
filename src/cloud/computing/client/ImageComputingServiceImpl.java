package cloud.computing.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class ImageComputingServiceImpl implements ImageComputingService {

	@Override
	public int[] makeBlackAndWhite(int[] data) {
		for (int i = 0; i < data.length; i++) {
			int r = data[i] & 0xFF;
			int g = (data[i] & 0xFF00) >> 8;
			int b = (data[i] & 0xFF0000) >> 16;
			int a = (data[i] & 0xFF000000) >> 24;
			int avg = (r+g+b)/3;
			data[i] = (a << 24) + (avg << 16) + (avg << 8) + avg;
		}
		return data;
	}

	@Override
	public byte[] processImage(byte[] image, int effect) {
		InputStream in = new ByteArrayInputStream(image);
		
		if (effect == this.BLACK_AND_WHITE) {
			//makeBlackAndWhite(new int[] {0});
		}
		return null;
	}

}