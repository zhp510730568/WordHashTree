# WordHashTree
HashTree max match word 最大正向匹配算法
本算法通过构建Hash树的方法实现字符串中词的匹配，也可以用于最大正向匹配算法，算法非常高效。测试时匹配出文章中的特定词，词有四千个，十四万篇文章，总共1.4亿个字符，这些文章保存在文本文件中，每篇占一行，匹配出这些文章中的特定词只需0.2秒。

Hash树数据结构的最大缺点是占用内存比较大，测试时加载了四万个词，大部分是四字，占用内存大致在40M左右，比双数组树占用的内存要多。

欢迎各位大侠们指出不足和改进方法。

