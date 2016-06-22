package arxiv

import java.util.Date
/**
  * Created by pavel on 6/21/16.
  */
trait SortByRule {
  val text: String
}

trait SortOrderRule {
  val text: String
}

case object submittedDate extends SortByRule {
  val text = "submittedDate"
}

case object descending extends SortOrderRule {
  val text = "descending"
}

case object ascending extends SortOrderRule {
  val text = "ascending"
}

/**
  * class wrapping search query parameters
  *
  * @param options
  *                  ti	Title
  *                  au	Author
  *                  abs	Abstract
  *                  co	Comment
  *                  jr	Journal Reference
  *                  cat	Subject Category
  *                  rn	Report Number
  *                  id	Id (use id_list instead)*
  * @param sortBy    submittedDate or ...
  * @param sortOrder ascending or descending
  */
case class SearchQuery(options: Map[String, String],
                       sortBy: Option[SortByRule] = None,
                       sortOrder: Option[SortOrderRule] = None,
                       startFrom: Option[Int] = None,
                       maxResults: Option[Int] = None) {
  val queryPrefix = "search_query="
  val sortByOption = (sortBy map (r => "&sortBy=" + r.text)) getOrElse ("")
  val sortOrderOption = (sortOrder map (r => "&sortOrder=" + r.text)) getOrElse ("")
  val startOption = startFrom map ("&start=" + _.toString) getOrElse ""
  val maxResultsOption = maxResults map ("&max_results=" + _.toString) getOrElse ""

  val text: String = {
    val queryStrings = (options map ({
      case (key, value) => key + ":" + value
    })).toList
    queryPrefix + queryStrings.reduce((s1, s2) => s1 + "+AND+" + s2) + sortByOption + sortOrderOption +
      startOption + maxResultsOption
  }

  def sortBy(rule: SortByRule): SearchQuery = copy(sortBy = Some(rule))

  def sortOrder(rule: SortOrderRule): SearchQuery = copy(sortOrder = Some(rule))
  def start(id: Int) = copy(startFrom = Some(id))
  def maxResults(num: Int) = copy(maxResults = Some(num))

  override def toString() = text

}

package object arxiv {

  def beforeToday(daysBefore:Int)(date: Date): Boolean ={
    import java.util.Calendar
    val today = Calendar.getInstance().getTime
    dateToInt(today) - daysBefore <= dateToInt(date)
  }

  def dateToInt(date: Date): Int ={
    //Java-based code
    import java.util.Calendar
    val cal = Calendar.getInstance()
    cal.setTime(date) //side-effect
    cal.get(Calendar.DAY_OF_YEAR) + cal.get(Calendar.YEAR)*366
  }

}

/*
/*
Most used words
(the,1094), (of,649), (a,353), (in,332), (and,306), (to,270), (,224), (we,212), (is,168), (that,151), (with,143),
(for,132), (by,106), (on,99), (at,91), (as,90), (magnetic,76), (this,74), (an,68), (superconducting,66), (be,63),
(are,61), (which,60), (phase,51), (can,47), (from,46), (has,43), (quantum,42), (field,40), (current,40),
(temperature,39), (superconductivity,39), (or,35), (study,35), (order,34), (state,33), (show,30), (gap,30), (fermi,29),
(these,29), (both,29), (high,29), (critical,28), (model,27), (such,27), (our,26), (states,26), (it,25), (two,25),
(properties,25), (between,25), (observed,25), (been,24), (results,24), (also,24), (density,24), (have,23),
(surface,23), (spin,23), (single,23), (low,23), (not,23), (find,22), (electron,20), (here,20), (its,20),
(experimental,19), (present,19), (effect,19), (well,19), (strong,19), (theory,19), (pairing,19), (fluctuations,18),
(superconductors,18), (energy,18), (was,18), (electronic,18), (structure,18), (topological,17), (using,17),
(vortex,17), (one,17), (-,16), (function,16), (dependence,16), (but,15), (=,15), (majorana,15), (transition,15),
(fese,14), (scattering,14), (different,14), (strongly,14), (superconductor,14), (discuss,14), (josephson,14),
(theoretical,14), (charge,14), (cdw,14), (fermions,14), (due,13), (based,13), (effects,13), (recent,13), (band,13),
(doping,13), (flux,13), (where,13), (no,13), (when,13), (potential,13), (used,12), (interaction,12), (up,12),
(measurements,12), (out,12), (transport,12), (presence,12), (near,12), (into,12), (dirac,12), (k,12), (however,,12),
(system,11), (hall,11), (dynamics,11), (demonstrate,11), (provide,11), (films,11), (associated,11), (finite,11),
(iron,11), (even,11), (thus,11), (large,11), (investigate,11), (method,10), (in
the,10), (three,10), (diagram,10), (below,10), (systems,10), (here,,10), (normal,10), (superfluid,10), (found,10),
(bound,10), (while,10), (bias,10), (behavior,10), (coupling,10), (relaxation,10), (value,10), (other,10), (symmetry,10),
(only,10)
Neutral:
the, of, a, in, and, to,, we, is,that,with,for,by,on,at,as,this,an,be,are,which,can,from,has,or,gap,both,such,our,it,
two,been,also,have, low, not, find, here, its, was, one, but, =, due, based, recent, out, near, into, k, however, provide,
even, thus, other, only
 */

 */
