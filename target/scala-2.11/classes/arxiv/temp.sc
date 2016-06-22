import arxiv._
import scala.xml.XML
import java.text.SimpleDateFormat
import java.util.Calendar


val fetcher = new Fetcher("http://export.arxiv.org/api/query")
val m = Map("cat" -> "cond-mat.supr-con", "au" -> "Loss")
val query = SearchQuery(m).sortBy(submittedDate).sortOrder(descending)
val atom = fetcher.fetchXML(query.text)
val node = atom \\ "totalResults"
node.text

val entries = atom \\ "entry"
val entry = entries.toList.head
(entry \ "title").text
(entry \ "link") \@("title")
val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
val date = format.parse("2016-06-20T18:54:28Z".replaceAll("T"," ").replaceAll("Z",""))
val cal = Calendar.getInstance()
cal.setTime(date)
cal.get(Calendar.DAY_OF_YEAR)

fetcher.entries(atom)

import java.util.Calendar

Calendar.getInstance.getTime
1.toString

val threedays = fetcher.fetchByDate("cond-mat.supr-con", arxiv.beforeToday(3))
threedays.map(p => p.published)



