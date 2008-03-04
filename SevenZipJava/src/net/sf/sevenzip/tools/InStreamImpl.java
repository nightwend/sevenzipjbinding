package net.sf.sevenzip.tools;

import java.io.RandomAccessFile;

import net.sf.sevenzip.IInStream;

public class InStreamImpl implements IInStream {
	public static final int SEEK_SET = 0;
	public static final int SEEK_CUR = 1;
	public static final int SEEK_END = 2;

	private final RandomAccessFile randomAccessFile;

	public InStreamImpl(RandomAccessFile randomAccessFile) {
		this.randomAccessFile = randomAccessFile;
	}

	@Override
	public int seek(long offset, int seekOrigin,
			long[] newPositionOneElementArray) {
		System.out.print("Seek: " + offset + ", origin: " + seekOrigin);

		try {
			switch (seekOrigin) {
			case SEEK_SET:
				randomAccessFile.seek(offset);
				break;

			case SEEK_CUR:
				randomAccessFile.seek(randomAccessFile.getFilePointer()
						+ offset);
				break;

			case SEEK_END:
				randomAccessFile.seek(randomAccessFile.length() - offset);
				break;

			default:
				throw new RuntimeException("Seek: unknown origin");
			}

			newPositionOneElementArray[0] = randomAccessFile.getFilePointer();
			System.out.println(", CurrPos: " + newPositionOneElementArray[0]);
		} catch (Throwable e) {
			e.printStackTrace();
			return 1;
		}

		return 0;
	}

	@Override
	public int read(byte[] data, int[] processedSizeOneElementArray) {
		System.out.print("Reading " + data.length + " bytes, ");
		try {
			int read = randomAccessFile.read(data);
			processedSizeOneElementArray[0] = read;
			System.out.println("was read: " + processedSizeOneElementArray[0]);
		} catch (Throwable e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}

}