package com.sun.demo.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 * 压缩与解压类
 * 
 * @author zhangyun
 * @date 2015-4-14 对该工具类重写，修复大数据越界问题
 */
public class ZipUtils {
	private static final int ZIP_BUFFER_SIZE = 1024 * 8;

	public static void createZip(OutputStream outStream, List<File> files)
			throws IOException {
		ZipOutputStream out = new ZipOutputStream(outStream);
		byte[] buf = new byte[ZIP_BUFFER_SIZE];
		for (File file : files) {
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
				out.putNextEntry(new ZipEntry(file.getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		out.close();
	}

	/**
	 * 对信息压缩
	 */
	public static byte[] zipData(byte[] data) {
		byte[] ret = null;
		if (null == data) {
			return null;
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		GZIPOutputStream outStream = null;

		try {
			outStream = new GZIPOutputStream(outByteStream);
			outStream.write(data, 0, data.length);
			outStream.finish();
			ret = outByteStream.toByteArray();
		} catch (IOException e) {
		}
		closeStream(outStream);
		closeStream(outByteStream);
		return ret;
	}

	/**
	 * 对数据解压缩
	 */
	public static byte[] unzipData(byte[] data) {
		if (null == data) {
			return null;
		}
		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
		ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();

		GZIPInputStream gzipInput = null;
		DataInputStream dataStream = null;
		byte[] outData = null;
		byte[] tmpBuff = new byte[ZIP_BUFFER_SIZE];

		try {
			gzipInput = new GZIPInputStream(inputStream);
			dataStream = new DataInputStream(gzipInput);
			int readRet = 0;
			while ((readRet = dataStream.read(tmpBuff)) > 0) {
				outPutStream.write(tmpBuff, 0, readRet);
			}
			outData = outPutStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		closeStream(dataStream);
		closeStream(gzipInput);
		closeStream(inputStream);
		closeStream(outPutStream);
		return outData;
	}

	/**
	 * 对多个文件进行压缩
	 */
	public static boolean zipFiles(ArrayList<String> files, String desFile) {
		// 创建输出 目录
		File outFile = new File(desFile);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}

		ZipOutputStream zipOutStream = null;
		boolean ret = false;
		try {
			zipOutStream = new ZipOutputStream(new FileOutputStream(outFile));
			for (String tmpFile : files) {
				ret = zipFile(new File(tmpFile), "", zipOutStream);
				if (!ret) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeStream(zipOutStream);
		if (!ret) {
			outFile.deleteOnExit();
		}
		return ret;
	}

	/**
	 * 对目录进行压缩
	 */
	public static boolean zipPath(String srcDir, String desFile) {
		if (null == srcDir || null == desFile) {
			return false;
		}
		File inputDir = new File(srcDir);
		if (!inputDir.exists()) {
			return false;
		}

		// 创建输出 目录
		File outFile = new File(desFile);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}

		FileOutputStream outFileStream = null;
		ZipOutputStream zipOutStream = null;
		boolean ret = false;
		try {
			outFileStream = new FileOutputStream(outFile);
			zipOutStream = new ZipOutputStream(outFileStream);
			ret = zipFile(inputDir, "", zipOutStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeStream(zipOutStream);
		closeStream(outFileStream);
		if (!ret) {
			outFile.deleteOnExit();
		}
		return ret;
	}

	/**
	 * 对zip文件解压
	 */
	public static boolean unzipFile(String file, String desPath) {
		if (null == file || null == desPath) {
			return false;
		}
		boolean ret = false;
		try {
			ZipFile inputFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> enumeration = inputFile.entries();
			while (enumeration.hasMoreElements()) {
				ZipEntry entry = enumeration.nextElement();
				ret = saveUnzipFile(inputFile, entry, desPath);
				if (!ret) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 对单个文件或目录压缩函数
	 */
	public static boolean zipFile(File file, String path, ZipOutputStream out) {
		// 压缩目录
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			int count = 0;
			if (null != files) {
				count = files.length;
			}
			for (int i = 0; i < count; i++) {
				if (!zipFile(files[i], path + file.getName() + "/", out)) {
					return false;
				}
			}
			return true;
		}

		InputStream inputStram = null;
		byte[] buffer = new byte[ZIP_BUFFER_SIZE];
		ZipEntry zipEntry = null;
		boolean ret = false;
		int total = 0;
		try {
			inputStram = new FileInputStream(file);
			zipEntry = new ZipEntry(path + file.getName());
			out.putNextEntry(zipEntry);
			int length;

			while ((length = inputStram.read(buffer)) > 0) {
				out.write(buffer, 0, length);
				total += length;
			}
			out.closeEntry();
			ret = true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		closeStream(inputStram);
		return ret;
	}

	/**
	 * 保存单个ZIP单元
	 */
	public static boolean saveUnzipFile(ZipFile input, ZipEntry entry, String path) {
		File outFile = new File(path, entry.getName());

		if (entry.isDirectory()) {
			outFile.mkdirs();
			return true;
		}

		FileOutputStream outStream = null;
		InputStream inputStram = null;
		int len;
		int totalLen = 0;
		byte[] buffer = new byte[ZIP_BUFFER_SIZE];
		boolean ret = true;
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		try {
			outStream = new FileOutputStream(outFile);
			inputStram = input.getInputStream(entry);
			while ((len = inputStram.read(buffer)) > 0) {
				outStream.write(buffer, 0, len);
				totalLen += len;
			}
			outStream.flush();
		} catch (IOException e) {
			ret = false;
		}
		closeStream(inputStram);
		closeStream(outStream);
		return ret;
	}

	private static void closeStream(OutputStream output) {
		try {
			if (null != output) {
				output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void closeStream(InputStream input) {
		try {
			if (null != input) {
				input.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 判断文件名是否合法
	 * @param fileName
	 * @return 合法返回true,不合法返回false
	 */
	public static boolean isValidFileName(String fileName) {
		if (fileName == null || fileName.length() > 255)
			return false;
		else
			return !fileName.matches(
					"[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
	}
}