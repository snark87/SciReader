package arxiv

/**
  * Created by pavel on 6/21/16.
  */

import java.util.Date

/*<entry>
<id>http://arxiv.org/abs/1606.06245v1</id>
<updated>2016-06-20T18:54:28Z</updated>
<published>2016-06-20T18:54:28Z</published>
<title>Nodeless multiband superconductivity in stoichiometric single crystalline CaKFe$_4$As$_4$</title>
<summary>
Measurements of the London penetration depth and tunneling conductance in single crystals of the recently discovered stoicheometric, iron - based superconductor, CaKFe$_4$As$_4$ (CaK1144) show nodeless, two effective gap superconductivity with a larger gap of about 6-9 meV and a smaller gap of about 1-4 meV. Having a critical temperature, $T_{c,onset}\approx$35.8 K, this material behaves similar to slightly overdoped Ba$_{1-x}$K$_x$)Fe$_2$As$_2$ (e.g. $x=$0.54, $T_c \approx$ 34 K)---a known multigap $s_{\pm}$ superconductor. We conclude that the superconducting behavior of stoichiometric CaK1144 demonstrates that two-gap $s_{\pm}$ superconductivity is an essential property of high temperature superconductivity in iron - based superconductors, independent of the degree of substitutional disorder.
</summary>
<author>
<author>
<author>
<author>
<author>
<author>
<author>
<author>
<author>
<author>
<author>
<author>
<link href="http://arxiv.org/abs/1606.06245v1" rel="alternate" type="text/html" />
<link title="pdf" href="http://arxiv.org/pdf/1606.06245v1" rel="related" type="application/pdf" />
<arxiv:primary_category xmlns:arxiv="http://arxiv.org/schemas/atom" term="cond-mat.supr-con" scheme="http://arxiv.org/schemas/atom" />
<category term="cond-mat.supr-con" scheme="http://arxiv.org/schemas/atom" />
</entry>
*/

case class ArxivLink(title: String, href: String)

case class ArxivAuthor(name: String)

case class ArxivEntry (id: String, title: String, authors: List[ArxivAuthor], summary: String, links: List[ArxivLink], updated: Date, published: Date)


