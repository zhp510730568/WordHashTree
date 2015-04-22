package com.zhp.nlp.hashtree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 提供哈希Tree树的检索和构建方法
 */
public class WordHashTree {
	/**
	 * 内嵌类，保存词相关信息，为哈希树中结点
	 */
	protected class Node {
		// 中文字符
		private char chr;
		// 下一个结点
		private Map<Character, Node> next;
		// 指示到当前位置是否为一个完整的词
		private boolean isComplete = false;
		// 结点信息
		private Word nodeInfo = null;

		/**
		 * 默认构造函数
		 * @return None
		 */
		public Node() {

		}

		/**
		 * 非默认构造函数，根据输入的敏感词属性构建敏感词对象
		 * @param[in] chr 单个字符
		 * @param[in] next 以当前路径组成字符串为开始的词
		 * @return None
		 */
		public Node(char chr, Map<Character, Node> next) {
			this.chr = chr;
			this.next = next;
		}

		/**
		 * 获取nodeInfo值
		 * @return 返回nodeInfo属性值
		 */
		public Word getNodeInfo() {
			return nodeInfo;
		}

		/**
		 * 设置nodeInfo值
		 * @param[in] nodeInfo 设置的值
		 * @return void
		 */
		public void setNodeInfo(Word nodeInfo) {
			this.nodeInfo = nodeInfo;
		}

		/**
		 * 获取Chr值
		 * @return 返回Chr属性值
		 */
		public char getChr() {
			return chr;
		}

		/**
		 * 设置Chr值
		 * @param[in] chr 设置的值
		 * @return void
		 */
		public void setChr(char chr) {
			this.chr = chr;
		}

		/**
		 * 获取Next属性值
		 * @return 返回Next属性值
		 */
		public Map<Character, Node> getNext() {
			return next;
		}

		/**
		 * 设置next值
		 * @param[in] next 设置的值
		 * @return void
		 */
		public void setNext(Map<Character, Node> next) {
			this.next = next;
		}

		/**
		 * 获取isComplete属性值
		 * @return 返回isComplete属性值
		 */
		public boolean isComplete() {
			return isComplete;
		}

		/**
		 * 设置isComplete的值
		 * @param[in] isComplete 设置的值
		 * @return void
		 */
		public void setComplete(boolean isComplete) {
			this.isComplete = isComplete;
		}

		/**
		 * 重写hashCode方法
		 * @return 返回Chr对应的编码值
		 */
		public int hashCode() {
			return chr;
		}

		/**
		 * 重写equals方法
		 * @param[in] obj 和本对象对比的对象
		 * @return 如果obj为null,则返回false；如果不为null并两对象属性Chr相等，返回true，否则返回false
		 */
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			Node other = (Node) obj;
			if (chr != other.chr) {
				return false;
			}
			return true;
		}
	}

	// 根节点
	private Map<Character, Node> root;	
	// 保存创建的单个实例
	private static WordHashTree hashTree = null;
	// 保存上次加载的路径
	private static String loadPath;

	/**
	 * 默认构造函数，构建根类别 
	 * @param[in] config 配置信息
	 * @return None
	 */
	private WordHashTree(String path) throws Exception {
		root = new HashMap<Character, Node>();
		bufferedWordsList = new LinkedList<String>();
		// 加载词
		this.loadSensiWord(path);
	}
	
	/**
	 * 根据配置信息返回唯一实例
	 * @param[in] path 词语路径
	 * @return 哈希树
	 */
	public static WordHashTree getInstance(String path) throws Exception {
		if(hashTree == null) {
			hashTree = new  WordHashTree(path);
		} else {
			if(!loadPath.equals(path)) {
				hashTree = new  WordHashTree(path);
			}
		}
		
		return hashTree;
	}
	
	/**
	 * 从excel文件导出词信息
	 * @param[in] fileName 指定文件路径
	 * @return void
	 */
	private void loadSensiWord(String fileName) throws Exception {
		BufferedReader bw = null;
		try	{
			bw = new BufferedReader(new FileReader(fileName));
			String line = "";
			Word tmpWord;
			while((line = bw.readLine()) != null){
				line = line.trim();
				if(!line.equals("")) {
					String[] wordInfo = line.split("\t");
					tmpWord = new Word(wordInfo[0], -1, 0);
					this.addString(tmpWord);
				}
			}
		} catch (Exception e) {
			System.err.println("导入匹配次出错");
			e.printStackTrace();
		} finally {
			if(bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * 添加字符串到Tree树中 
	 * @param[in] word 词信息
	 * @return word中成功添加的字符数
	 */
	public int addString(Word word) {
		String wordName = word.getWordName();
		char[] tmpStr = wordName.trim().toCharArray();
		int len = tmpStr.length;
		if (len == 0) {
			return 0;
		}
		int result = 0;
		Node node = null;
		Node tmpNode = null;
		Map<Character, Node> tmpMap = root;
		for (int i = 0; i < len; i++, result++) {
			if (tmpMap != null) {
				if (tmpMap.containsKey(tmpStr[i])) {
					node = tmpMap.get(tmpStr[i]);
					tmpMap = node.next;
				} else {
					node = new Node(tmpStr[i], null);
					tmpMap.put(tmpStr[i], node);
					tmpMap = node.next;
				}
			} else {
				tmpMap = new HashMap<Character, Node>();
				tmpNode = new Node(tmpStr[i], null);
				tmpMap.put(tmpStr[i], tmpNode);
				node.next = tmpMap;
				tmpMap = tmpNode.next;
				node = tmpNode;
				tmpMap = null;
			}
		}
		// 到当前节点为一个完整的词
		node.isComplete = true;
		// 保存终止结点信息
		node.nodeInfo = word;

		return result;
	}

	/**
	 * 获得以prefix为前缀的词
	 * @param[in] prefix 要检索的前缀
	 * @return 以prefix为开头的词列表
	 */
	public List<String> wordsStartWithPrefix(String prefix) {

		if (prefix.trim() == lastSearchedPrefix) {
			return bufferedWordsList;
		}
		lastSearchedPrefix = prefix.trim();

		char[] tmpPrefixArr = prefix.trim().toCharArray();
		char chr;
		int len = tmpPrefixArr.length;
		Map<Character, Node> tmpMap = root;
		Node node = null;
		for (int i = 0; i < len; i++) {
			if (tmpMap != null) {
				chr = tmpPrefixArr[i];
				if (tmpMap.containsKey(chr)) {
					node = tmpMap.get(chr);
					tmpMap = node.next;
				}
			} else {
				break;
			}
		}

		if (tmpMap != null) {
			bufferedWordsList.clear();
			getWords(lastSearchedPrefix, tmpMap);
		} else {
			return null;
		}

		return bufferedWordsList;
	}

	// 保存最近一次的搜索前缀
	private String lastSearchedPrefix = "";

	// 保存最近一次的搜索前缀的检索结果
	private List<String> bufferedWordsList;

	/**
	 * 递归获得以prefix开头的字符串 
	 * @param[in] prefix 前缀 
	 * @param[in] map 树根hash表 
	 * @return void
	 */
	private void getWords(String prefix, Map<Character, Node> map) {
		Iterator<Entry<Character, Node>> iter = map.entrySet().iterator();
		String word = "";
		while (iter.hasNext()) {
			Map.Entry<Character, Node> entry = iter.next();
			char key = entry.getKey();
			Node node = entry.getValue();
			word = prefix + key;
			map = node.next;
			if (map != null) {
				if (node.isComplete == false) {
					getWords(word, map);
				} else {
					bufferedWordsList.add(word);
					getWords(word, map);
				}
			} else {
				bufferedWordsList.add(word);
			}
		}
	}

	/**
	 * 判断word是否存在，注意完全匹配
	 * @param[in] word 要匹配的词汇
	 * @return 若存在，返回true，否则，返回false
	 */
	public boolean exist(String word) {
		boolean isExist = true;
		char[] tmpPrefixArr = word.trim().toCharArray();
		char chr;
		int len = tmpPrefixArr.length;
		Map<Character, Node> tmpMap = root;
		Node node = null;
		for (int i = 0; i < len; i++) {
			if (tmpMap != null) {
				chr = tmpPrefixArr[i];
				if (!tmpMap.containsKey(chr)) {
					return false;
				} else {
					node = tmpMap.get(chr);
					tmpMap = node.next;
				}
			} else {
				return false;
			}
		}
		if (node.isComplete == false) {
			isExist = false;
		}

		return isExist;
	}

	/**
	 * 判断字符chr是否词的第一个字符
	 * @param[in] chr 字符
	 * @return 存在返回true，否则，返回false
	 */
	public boolean existFirstPrefix(char chr) {
		if (root.containsKey(chr)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断字符chr是否是一个完整的词
	 * @param[in] chr 字符
	 * @return 如果字符是一个完整的词，返回true，否则，返回false
	 */
	public boolean isCompleteWord(char chr) {
		if (root.containsKey(chr)) {
			Node tmpNode = root.get(chr);
			if (tmpNode.isComplete == true) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 从字符串中指定位置开始匹配词,用于匹配文章中的词
	 * @param[in] str 字符串的数组形式
	 * @param[in] startPos 匹配的开始文职
	 * @return 匹配的结束位置
	 */
	public Word matchParOfStr(char[] str, int startPos) {
		int len = str.length;
		Map<Character, Node> tmpMap = root;
		Node tmpNode = null;
		Node preMatchNode = null;
		int preMatchEndPos = -1;
		char chr;
		for (int i = startPos; i < len; i++) {
			chr = str[i];
			if (tmpMap != null && tmpMap.containsKey(chr)) {
				tmpNode = tmpMap.get(chr);
				tmpMap = tmpNode.next;
				if (tmpNode.isComplete == true) {
					preMatchNode = tmpNode;
					preMatchEndPos = i;
				}
			} else {
				break;
			}
		}
		Word tmpWord = null;
		if (preMatchNode != null) {
			Word word = preMatchNode.nodeInfo;
			tmpWord = word.clone();
			tmpWord.setOffset(preMatchEndPos);
		}

		return tmpWord;
	}
}
