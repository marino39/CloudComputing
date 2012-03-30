package m69.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
	static byte[] byteArrayToZippedByteArray(byte[] in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry ze = new ZipEntry("temp");
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
	static byte[] ZippedByteArrayToByteArray(byte[] in) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(in);
		ZipInputStream zis = new ZipInputStream(bais);
		zis.getNextEntry();
		int len = zis.available();
		byte[] out = new byte[len];
		zis.read(out, 0, len);
		zis.close();
		return out;
	}
	
}
