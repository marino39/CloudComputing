package m69.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtility {
	
	/**
	 * Zips supplied byte array.
	 * 
	 * @param in byte array to be Zipped.
	 * @return Zipped Byte Array.
	 * @throws IOException
	 */
	public static byte[] byteArrayToZippedByteArray(byte[] in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry ze = new ZipEntry("temp.zip");
        ze.setSize(in.length);
        zos.putNextEntry(ze);
        zos.write(in); 
        zos.closeEntry();
        zos.close();
        
		return baos.toByteArray();
	}
	
	/**
	 * UnZip byte array.
	 * 
	 * @param in byte array to UnZip.
	 * @return UnZiped byte array.
	 * @throws IOException
	 */
	public static byte[] zippedByteArrayToByteArray(byte[] in) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(in);
		ZipInputStream zis = new ZipInputStream(bais);
		ZipEntry e = zis.getNextEntry();
		List<Byte> d = new ArrayList<Byte>();

		while (zis.available() > 0) {
			byte r = (byte) zis.read();
			d.add(r);
		}
		
		byte[] out = new byte[d.size()-1];
		
		for(int i = 0; i < d.size()-1; i++)
		{
			out[i] = d.get(i);
		}
		zis.closeEntry();
		zis.close();
		return out;
	}
	
	public static byte[] intToBytes(int value) {
		return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
	}
	
	public static int bytesToInt(byte[] b) {
		return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
	}
	
	public static byte[] intArrayToByteArray(int[] a) {
		byte[] data = new byte[a.length*4];
		for (int i = 0; i < a.length; i++) {
			byte[] temp = intToBytes(a[i]); 
			data[i*4] = temp[0];
			data[i*4 + 1] = temp[1];
			data[i*4 + 2] = temp[2];
			data[i*4 + 3] = temp[3];
		}
		return data;
	}
	
	public static int[] byteArrayToIntArray(byte[] a) {
		int[] data = new int[a.length/4];
		for (int i = 0; i < a.length/4; i++) {
			byte[] temp = {a[i*4], a[i*4 + 1], a[i*4 + 2], a[i*4 + 3]};
			data[i] = bytesToInt(temp);
		}
		return data;
	}
}
