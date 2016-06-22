package recommender

import arxiv.ArxivEntry

/**
  * MUTABLE
  *
  * Created by pavel on 6/22/16.
  */

class FreqCounter {
  def addToStatistics(paper: ArxivEntry) = ???
  def addToInteresting(paper: ArxivEntry) = ???
  def predictRating(paper: ArxivEntry): Double = ???
}
