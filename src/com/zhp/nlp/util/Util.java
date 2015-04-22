package com.zhp.nlp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.zhp.nlp.hashtree.WordHashTree;
import com.zhp.nlp.hashtree.Word;

public class Util {
	
	/**
	 * 硬匹配获得文章中词的相关信息
	 * @param[in] hashTree 词hash树
	 * @param[in] article 文章
	 * @return 包含匹配的词信息的链表
	 */
	public static LinkedList<Word> getArtiSensiWordInfo(WordHashTree hashTree, String article) {
		LinkedList<Word> wordsInfo = new LinkedList<Word>();
		String tmpArticle = article.trim();
		int len = tmpArticle.length();
		int pos = 0;
		char chr;
		char[] articleArr = tmpArticle.toCharArray();
		HashMap<String, Word> hashMap = new HashMap<String, Word>();
		Word word;
		int wordlen = 0;
		String wordName;
		while(pos < len) {
			chr = articleArr[pos];
			if(hashTree.existFirstPrefix(chr)) {
				word = hashTree.matchParOfStr(articleArr, pos);
				if(word != null) {
					wordName = word.getWordName();
					wordlen = wordName.length();
					if(hashMap.containsKey(wordName)) {
						hashMap.get(wordName).IncreCount();
					} else {
						word.setCount(1);
						hashMap.put(wordName, word);
					}
					pos += wordlen;
				} else {
					pos ++;
				}
			} else {
				pos ++;
			}
		}
		Iterator<Entry<String, Word>> iter = hashMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Word> entry = (Map.Entry<String, Word>) iter.next();
			Word val = entry.getValue();
			wordsInfo.add(val);
		}
		
		return wordsInfo;
	}
}
