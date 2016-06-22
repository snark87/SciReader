package arxiv

import scala.xml._
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import arxiv._

/*ti	Title
au	Author
abs	Abstract
co	Comment
jr	Journal Reference
cat	Subject Category
rn	Report Number
id	Id (use id_list instead)*/



/**
  * http://export.arxiv.org/api/query?search_query=cat:cond-mat.supr-con&sortBy=submittedDate&sortOrder=descending
  * Created by pavel on 6/20/16.
  */
class Fetcher(arxivApiUrl: String) {
  val tagTotalResults = "totalResults"
  val tagTitle: String = "title"
  val tagSummary = "summary"
  val tagLink = "link"
  val tagUpdated = "updated"
  val tagPublished = "published"


//todo: merging different cats
  // (possible problem: there are intersections)

  def fetchCatsByDate(categories:List[String], afterFilter: Date => Boolean): Vector[ArxivEntry] = ???
  def merge(vectors: List[Vector[ArxivEntry]]): Vector[ArxivEntry] = ???
  /**
    *
    * @param cat arXiv category e.g. "cond-mat.supr-con"
    * @param afterFilter filter, that filters all dates after given
    * @return arXiv entries for recent papers
    */
  def fetchByDate(cat: String, afterFilter: Date => Boolean): Vector[ArxivEntry] = {
    val query = SearchQuery(Map("cat" -> cat)).sortBy(submittedDate).sortOrder(descending)

    def fetchPage(acc: Vector[ArxivEntry], start: Int, maxResults: Int): Vector[ArxivEntry] = {

      val atom = fetchXML(query.start(start).maxResults(maxResults).text)
      val papers = entries(atom)
      if (papers.isEmpty) papers  //recursion ends if out of papers
      else {
        val lastDate = papers.last.published
        if (afterFilter(lastDate)) {
          fetchPage(acc ++ papers, start + papers.length, maxResults)   //goto next page, tail recursion
        } else
          acc ++ papers.filter(p => afterFilter(p.published)) //todo: optimize - the results are already sorted;
                                                              //recursion ends if last paper is too old
      }
    }
    val resultsPerPage = 10 //magic number
    fetchPage(Vector[ArxivEntry](), 0, resultsPerPage)
  }

  def fetchXML(query: String): Elem =
    XML.load(arxivApiUrl + "?" + query)

  // todo: handle strToInt exception
  def totalResults(atom: Elem): Int =
    (atom \\ tagTotalResults).text.toInt

  def entries(atom: Elem): Vector[ArxivEntry] = (atom \\ "entry").toVector map entry

  //todo: handle exceptions
  def entry(atom: Node): ArxivEntry = {
    val title = (atom \ tagTitle).text
    val summary = (atom \ tagSummary).text
    val links = (atom \ tagLink).toList map link
    val updated = datetime((atom \ tagUpdated).text)
    val published = datetime((atom \ tagPublished).text)
    ArxivEntry(title, summary, links, updated, published)
  }

  def datetime(s: String): Date = {

    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    format.parse(s.replaceAll("T", " ").replaceAll("Z", ""))
  }

  def link(atom: Node): ArxivLink = ArxivLink(atom \@ "title", atom \@ "href")
}
