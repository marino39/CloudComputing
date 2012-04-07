package cloud.computing.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.taskdefs.Zip;

import cloud.computing.algorithms.MatrixFilter;
import cloud.computing.algorithms.MatrixFilterType;

import m69.utils.Worker;
import m69.utils.WorkerException;
import m69.utils.ZipUtility;


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

	@Override
	public byte[] applyFilter(final byte[] image,final int imageX,final int imageY,final MatrixFilterType type) {
		byte[] unzipedData = null;
		int threads = 10;
		
		try {
			unzipedData = ZipUtility.zippedByteArrayToByteArray(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (unzipedData != null) {
			final int[] img = ZipUtility.byteArrayToIntArray(unzipedData);
			final MatrixFilter filter = new MatrixFilter(type.filter, type.factor, type.bias);
			int[] filteredImg = new int[img.length];/* filter.applyFilter(img, imageX, imageY, 0, 0, imageX, imageY, null);*/
			
			final int part = imageY/threads;
			Worker work = new Worker(true);
			final List<int[]> vec = new ArrayList<int[]>();
			
			for (int i = 0; i < threads; i++) {
				final int s = i;
				try {
					work.addTask(new Runnable() {
						@Override
						public void run() {	
							int res[] = filter.applyFilter(img, imageX, imageY, 0, part*s, imageX, part,  null);
							synchronized (vec) {
								vec.add(res);
							}
						}
					});
				} catch (WorkerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			work.doWork(true);
			
			for (int i = 0; i < vec.size(); i++) {
				int[] res = vec.get(i);
				for(int j = 0; j < filteredImg.length; j++) {
					filteredImg[j] += res[j];
				}
			}
			
			byte[] returnData = null;
			
			try {
				returnData = ZipUtility.byteArrayToZippedByteArray(ZipUtility.intArrayToByteArray(filteredImg));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return returnData;
		}
		
		
		return null;
	}

}
