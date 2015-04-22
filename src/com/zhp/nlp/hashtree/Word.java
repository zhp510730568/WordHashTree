package com.zhp.nlp.hashtree;

/**
 *  词相关信息
 */
public class Word implements Comparable<Word> {
	// 词全名
	private String wordName = "";
	// 文中位置偏移量
	private int offset = -1;
	// 出现数量
	private int count = 0;

	public Word(String wordname) {
		this.wordName = wordname;
	}
	
	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void IncreCount() {
		this.count++;
	}

	@Override
	public int compareTo(Word arg0) {
		if(this.wordName.equals(arg0.wordName)) {
			return 1;
		}
		
		return 0;
	}
	
	@Override
	public Word clone() {
		return new Word(this.wordName);
	}
}