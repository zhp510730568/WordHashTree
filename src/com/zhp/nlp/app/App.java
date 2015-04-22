package com.zhp.nlp.app;

import java.util.LinkedList;

import com.zhp.nlp.hashtree.Word;
import com.zhp.nlp.hashtree.WordHashTree;
import com.zhp.nlp.util.Util;

public class App {

	public static void main(String[] args) throws Exception {
		String text = "4月21日，国家主席习近平抵达印度尼西亚首都雅加达，应印度尼西亚共和国总统佐科邀请出席亚非领导人会议和万隆会议60周年纪念活动。印度尼西亚内政部长库莫罗和财政部长班邦代表印尼政府到机场迎接习近平主席一行。新华社记者李学仁摄";
		WordHashTree hashTree = WordHashTree.getInstance("./resources/words.txt");
		LinkedList<Word> wordsList = Util.getArtiSensiWordInfo(hashTree, text);
		for(Word word: wordsList) {
			System.out.println(word.getWordName() + ":" + word.getCount());
		}
	}
}