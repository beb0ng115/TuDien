package com.example.dictionary_merriam_webster;

import java.io.Serializable;

public class wordtest implements Serializable{

	private String word;
	private int ofset;
	private int length;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getOfset() {
		return ofset;
	}
	public void setOfset(int ofset) {
		this.ofset = ofset;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
}
