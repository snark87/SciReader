package code
package snippet

import net.liftweb.util.Helpers._
import java.util.Date

import arxiv._
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds.Alert
import net.liftweb.http.js.{JsCmd, JsCmds}
import net.liftweb.http.js.jquery.JqJsCmds

class HelloWorld {

  val fetcher = new Fetcher("http://export.arxiv.org/api/query")
  val recent = fetcher.fetchByDate("cond-mat.supr-con", arxiv.beforeToday(7))

  private def formatNames(names: List[ArxivAuthor]): List[String] = {
    def recursiveFormat(acc: List[String], names: List[ArxivAuthor]): List[String] = names match {
      case head :: tail if tail.isEmpty => acc :+ head.name
      case head :: tail => recursiveFormat(acc :+ head.name + ", ", tail)
      case Nil => acc
    }
    recursiveFormat(List(), names)

  }

  def authors(paper: ArxivEntry) = "#arxivAuthor *" #> formatNames(paper.authors)

  def count = {
    println("count!")
    "#count *" #> recent.length
  }

  def like(arxivId: String): JsCmd = {
    Alert("like:" + arxivId)
    JqJsCmds.JqReplace(arxivId, <b>Like</b>)
  }

  def recentList = ".titles" #>
    ("li #arxivEntry *" #> recent.map(x => ("#arxivTitle *" #> x.title) &
      ("#arxivAbstract *" #> x.summary) &
      ("#arxivAuthors *" #> authors(x)) &
      ("#btnInteresting" #> SHtml.ajaxButton(x.title, () => like(x.id), "id" -> x.id))
    ))

}