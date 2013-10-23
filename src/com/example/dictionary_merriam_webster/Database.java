package com.example.dictionary_merriam_webster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import android.os.Environment;

public class Database implements Runnable {

	public File wfile;
	//public final File mfile;
	public Hashtable<String, Word> Hash = new Hashtable<String, Word>();
	public Vector<String> Words = new Vector<String>();

	public Vector<wordtest> listWord = new Vector<wordtest>();

	public Database() {

		wfile = new File(System.getProperty("user.dir").concat(
				Environment.getExternalStorageDirectory().getAbsolutePath()
						+ "/EnglishVietnamese.index"));
		/*mfile = new File(System.getProperty("user.dir").concat(
				Environment.getExternalStorageDirectory().getAbsolutePath()
						+ "/EnglishVietnamese.dict"));*/
		// arrWords();
		// new Thread(this).start();
	}

	public void LoadWord() {

		try {
			StringBuilder builder = new StringBuilder();
			FileInputStream instream = new FileInputStream(wfile);
			byte[] buff = new byte[1024];
			while (instream.read(buff) != -1) {
				builder.append(new String(buff));
			}

			String data = builder.toString();
			StringTokenizer token = new StringTokenizer(data, "\n");
			while (token.hasMoreTokens()) {
				String line = token.nextToken();
				String elements[] = line.split("\t");
				if (elements.length == 3) {
					wordtest temp = new wordtest();
					temp.setWord(elements[0]);
					temp.setOfset(Integer.valueOf(elements[1]));

				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void arrWords() {
		long begin = Calendar.getInstance().getTimeInMillis();
		try {
			StringBuilder builder = new StringBuilder();
			FileInputStream ins = new FileInputStream(wfile);

			byte buffer[] = new byte[1024];
			while (ins.read(buffer) != -1) {
				builder.append(new String(buffer));
			}

			String data = builder.toString();
			StringTokenizer token = new StringTokenizer(data, "\n");
			while (token.hasMoreTokens()) {
				String line = token.nextToken();
				String elements[] = line.split("\t");
				if (elements.length == 3) {
					Word word = Hash.get(elements[0]);
					if (word == null) {
						word = new Word(elements[0],
								convert64to10(elements[1]),
								convert64to10(elements[2]));
						Hash.put(elements[0], word);
						Words.add(elements[0]);
					} else {
						word.addArray(convert64to10(elements[1]),
								convert64to10(elements[2]));
					}

				}
			}
			ins.close();
		} catch (IOException ex) {
			System.out.println("IOException");
		}
		System.out.println("Time load word:  "
				+ (Calendar.getInstance().getTimeInMillis() - begin)
				+ " miliseconds");
	}

	public void LoadFullDictionary() {
		long begin = Calendar.getInstance().getTimeInMillis();
		try {
			Word.input = new RandomAccessFile(mfile, "r");
			Iterator<String> it = Words.iterator();
			while (it.hasNext()) {
				Word word = (Word) Hash.get(it.next());
				// word.LoadMean();
			}
			Word.input.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Full Dictionary:  "
				+ (Calendar.getInstance().getTimeInMillis() - begin)
				+ " miliseconds");
	}

	public String Dictionary(int index, int length) {

		try {
			Word.input = new RandomAccessFile(mfile, "r");
			byte[] buff = new byte[length];
			Word.input.seek(index);
			Word.input.read(buff, 0, length);
			String mean = new String(buff, "UTF8").replaceAll("\0+", "");
			return mean;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public void run() {
		LoadFullDictionary();
	}

	public static int convert64to10(String num) {
		int number = 0;
		int len = num.length();
		String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		for (int i = 0; i < len; i++) {
			number += code.indexOf(num.charAt(i)) * Math.pow(64, len - i - 1);
		}
		return number;
	}

	public static String convert10to64(int num) {
		String number = "";
		String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		while (num != 0) {
			number = code.charAt(num % 64) + number;
			num = num / 64;
		}
		return number;
	}

	// public int addWord(String key, String mean) {
	// int index = 0;
	// Word word = new Word(key, mean);
	// for (Iterator<String> it = Words.iterator(); it.hasNext(); index++) {
	// String element = it.next();
	// if (element.toLowerCase().compareTo(key.toLowerCase()) > 0) {
	// break;
	// }
	// }
	// Words.insertElementAt(key, index);
	// Hash.put(key, word);
	// System.out.println(index);
	// return index;
	// }
}
