package com.example.dictionary_merriam_webster;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Vector;

import android.os.Environment;

public class Word {

    public static RandomAccessFile input = null;
    public static int seek;
    public String key;
    public String mean;
    public Vector<Integer> address = new Vector<Integer>();		//array address
    public Vector<Integer> length = new Vector<Integer>();		//array length
    private boolean isLoaded;

    public Word(String key, int address, int length) {  		//index
        this.key = key;
        this.isLoaded = false;

        this.address.add(address);
        this.length.add(length);
    }

    public Word(String key, String mean) {						//map index
        this.key = key;
        this.mean = mean;
        this.isLoaded = true;
    }

    public void addArray(int address, int length) {
        this.address.add(address);
        this.length.add(length);
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getMean() throws IOException {
        if (!isLoaded) {
           // LoadMean();
        }
        return mean;
    }

   // synchronized public static void LoadMean() throws IOException {
    	public static void LoadMean() throws Exception {
        //this.mean = "";
        String means = "";
        int offset = Database.convert64to10("nSJ7");
        int lengths = Database.convert64to10("Zu");
        
        int n = 0;
        //Database.convert64to10(num);
        
        
        File mfile = new File(System.getProperty("user.dir").concat(
				Environment.getExternalStorageDirectory().getAbsolutePath()
						+ "/EnglishVietnamese.dict"));
        try{
        RandomAccessFile fileAccess = new RandomAccessFile(mfile, "r");
        fileAccess.seek(offset);
        byte[] dst = new byte[lengths];
		fileAccess.read(dst , 0, lengths);
		String meansning = new String(dst);//.replaceAll("\0+", "");
		
		n = 2;
        }catch(Exception exc)
        {
        	n = 5;
        }
        // Mỗi lần mình nhập, thì mình tìm 1 lần
        // - > thời gian tìm kiếm lâu, thời gian load nhanh
        // String mean =null
        // dau vo : offset -> length
        // dau ra : Nghia
        // 1. tu offset la leng, truy cap vao file dic voi offset va leng
        // FileAccessRandom
        // Lay nghia ra
        
        /*
        int size = address.size();
        for (int i = 0; i < size; i++) {
            int address = this.address.get(i);
            int len = this.length.get(i);
            byte[] buff = new byte[len];
            input.seek(address);
            input.read(buff, 0, len);
            String mean = new String(buff, "UTF8").replaceAll("\0+", "");
            if (i == 0) {
                this.mean = mean;
            } else {
                if (!this.mean.contains(mean.trim())) {
                    this.mean += '\n' + mean;
                }
            }
        }
        this.isLoaded = true;*/
    }


}
