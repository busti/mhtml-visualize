package mhtml.vis

import mhtml.Rx._
import mhtml._

import scala.collection.mutable

object Builder {
  implicit def rx2tree(rx: Rx[_]) = show(rx)

  sealed trait RxTree
  final case class Branch(rx: Rx[Any], branches: RxTree *) extends RxTree
  final case class RxBranch(rx: Rx[Any], input: RxTree, branches: Rx[Option[RxTree]]) extends RxTree
  final case class Leaf(rx: Rx[Any]) extends RxTree

  val flatMapStreams = mutable.Map[FlatMap[_, _], Rx[Option[RxTree]]]()

  /**
   * Generates an inversed version of the rx's tree structure.
   * Since flatMap can return an individual Rx for every value in the stream,
   * it is necessary, to have a branching structure that can produce a stream
   * of further rx trees.
   * @param rx The rx that is being analyzed
   * @return A tree strucutre that represents the rx tree in inverse order.
   */
  def show[A](rx: Rx[A]): RxTree = rx match {
    case Map(self, _)          => Branch(rx, self)
    case fm @ FlatMap(self, _) => {
      RxBranch(rx, self, flatMapStreams(fm))
    }
    case Zip(self, other)      => Branch(rx, self, other)
    case DropRep(self)         => Branch(rx, self)
    case Merge(self, other)    => Branch(rx, self, other)
    case Foldp(self, _, _)     => Branch(rx, self)
    case Collect(self, _, _)   => Branch(rx, self)
    case SampleOn(self, other) => Branch(rx, self, other)
    case Imitate(self, other)  => Branch(rx, self, other)
    case Sharing(self)         => Branch(rx, self)
    case leaf: Var[A]          => Leaf(leaf)
  }

  /**
   * Used to wrap a flatMap call, in oder to reliably spy its contents over time.
   * @param rx The wrapped flatmap call.
   * @return The newly generated flatmap that spies its contents.
   */
  def spyRx[A](rx: Rx[A]): Rx[A] = rx match {
    case fm @ FlatMap(r, f: A) => {
      val v = Var[Option[RxTree]](None)
      val g = FlatMap(r, { x: Any =>
        val res = f(x)
          v := Some(res)
        res
      })
      flatMapStreams(g) = v
      g
    }
    case _ => ???
  }
}
