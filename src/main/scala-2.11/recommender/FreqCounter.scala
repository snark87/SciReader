package recommender

import arxiv.ArxivEntry
import collection.mutable

/**
  * MUTABLE
  *
  * Created by pavel on 6/22/16.
  */

class FreqCounter {
  val totalCounts: mutable.AbstractMap[String, Long] = mutable.WeakHashMap() //mutable
  val interestingCounts: mutable.AbstractMap[String, Long] = mutable.WeakHashMap() //mutable
  val reservedWords: Set[String] = Set(
    "the", "of", "a", "in", "and", "to", "we", "is", "that", "with", "for", "by", "on", "at", "as", "this", "an",
    "be", "are", "which", "can", "from", "has", "or", "both", "such", "our", "it", "two", "been", "also", "have",
    "low", "not", "find", "here", "its", "was", "one", "but", "=", "due", "based", "recent", "out", "near", "into",
    "k", "however", "provide", "even", "thus", "other", "only")

  def keywordRating(word: String): Option[Double] = {
    val ic: Long = interestingCounts.getOrElse[Long](word, 0)
      totalCounts.get(word) map {tc: Long => ic.toFloat / tc}
  }

  /**
    * add unique keywords to statistics
    * @param keywords
    */
  def addToStatistics(keywords: Set[String], stat: mutable.AbstractMap[String, Long]) =
  for (word <- keywords) {
        val currentCount = stat.getOrElse[Long](word, 0)
        stat.update(word, currentCount + 1)
      }

  def addToTotal(keywords: Set[String]): Unit = addToStatistics(keywords, totalCounts)

  def addToInteresting(keywords: Set[String]): Unit = addToStatistics(keywords, interestingCounts)

  def extractKeywords(paper: ArxivEntry): Set[String] = {
    val words: Set[String] = (paper.summary.split(" ") map {_.toLowerCase}).toSet
    words &~ reservedWords
  }

  def addToTotal(paper: ArxivEntry): Unit = addToTotal(extractKeywords(paper))
  def addToInteresting(paper: ArxivEntry): Unit = addToInteresting(extractKeywords(paper))
  def predictRating(paper: ArxivEntry): Double = ???
}
