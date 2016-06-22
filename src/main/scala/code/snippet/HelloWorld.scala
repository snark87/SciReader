package code
package snippet

import net.liftweb.util.Helpers._
import java.util.Date
import arxiv._

class HelloWorld {

  val fetcher = new Fetcher("http://export.arxiv.org/api/query")
  val recent = fetcher.fetchByDate("cond-mat.supr-con", arxiv.beforeToday(7))
  def count = "#count *" #> recent.length
  def recentList =".titles" #> ("li #arxivEntry *" #> recent.map(x => ("#arxivTitle *" #> x.title) & ("#arxivAbstract *" #> x.summary) ))

}