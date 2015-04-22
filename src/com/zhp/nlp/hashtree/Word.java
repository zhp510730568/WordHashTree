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
	
	public Word(String wordname, int offset, int count) {
		this.wordName = wordname;
		this.offset = offset;
		this.count = count;
	}
	
	/**
	 * 获取wordName属性值
	 * @return wordName属性值
	 */
	public String getWordName() {
		return wordName;
	}
	
	/**
	 * 设置wordName属性值
	 * @param wordName
	 * @return void
	 */
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	
	/**
	 * 获取offset属性值
	 * @return offset属性值
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * 设置offset属性值
	 * @param offset
	 * @return void
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * 获取offset属性值
	 * @return offset属性值
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置count属性值
	 * @param count
	 * @return void
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * 增加count属性值
	 * @param count
	 * @return void
	 */
	public void IncreCount() {
		this.count++;
	}

	/**
	 * 重写比较方法
	 * @param arg0
	 * @return 结果值
	 */
	@Override
	public int compareTo(Word arg0) {
		if(arg0 == null) {
			return 0;
		}
		if(this.wordName.equals(arg0.wordName) && this.count == arg0.count && this.offset == arg0.offset) {
			return 1;
		}
		
		return 0;
	}
	
	/**
	 * 重写clone方法
	 * 
	 * @return 对象副本
	 */
	@Override
	public Word clone() {
		return new Word(this.wordName, this.offset, this.count);
	}
}
