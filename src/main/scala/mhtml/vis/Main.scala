package mhtml.vis

import mhtml._
import org.scalajs.dom

import scala.scalajs.js
import js.Dynamic.{literal => lit}

object Main extends App {
  js.Dynamic.global.cytoscape(lit(
    container = dom.document.getElementById("cy"),
    layout = lit(
      name = "circle",
      padding = 10
    ),
    elements = lit(
      nodes = js.Array(
        lit(data = lit(id = "a", foo = 3, bar = 5, baz = 7)),
        lit(data = lit(id = "b", foo = 7, bar = 1, baz = 3)),
        lit(data = lit(id = "c", foo = 2, bar = 7, baz = 6)),
        lit(data = lit(id = "d", foo = 9, bar = 5, baz = 2)),
        lit(data = lit(id = "e", foo = 2, bar = 4, baz = 5))
      ),

      edges = js.Array(
        lit(data = lit(id = "ae", weight = 1, source = "a", target = "e")),
        lit(data = lit(id = "ab", weight = 3, source = "a", target = "b")),
        lit(data = lit(id = "be", weight = 4, source = "b", target = "e")),
        lit(data = lit(id = "bc", weight = 5, source = "b", target = "c")),
        lit(data = lit(id = "ce", weight = 6, source = "c", target = "e")),
        lit(data = lit(id = "cd", weight = 2, source = "c", target = "d")),
        lit(data = lit(id = "de", weight = 7, source = "d", target = "e"))
      )
    )
  ))

  def v1 = Var(1)
  def v2 = Var(1)

  def r1 = v1.map(_ * 2)
  def r2 = v2.zip(r1)
  def r3 = r2.flatMap(_ => r2)

  Builder.show(r3)
}
