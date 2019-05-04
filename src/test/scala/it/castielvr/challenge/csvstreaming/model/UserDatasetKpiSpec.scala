package it.castielvr.challenge.csvstreaming.model

import org.scalatest.FlatSpec
import org.scalatest._
import Matchers._
import scala.collection.immutable.Iterable

class UserDatasetKpiSpec extends FlatSpec {

  "UserDatasetKpi.createStringSequence" should "return the correct iterable of strings" in {
    val udk = UserDatasetKpi(
      BigInt(6666),
      3,
      Map(
        "1" -> new UserKpi(1, 1, 111, 1111),
        "2" -> new UserKpi(2,1,222,2222),
        "3" -> new UserKpi(3,1,333,3333)
      )
    )
    val expectedStrSequence = Seq(
      "6666", "3", "1,1.0,111", "2,2.0,222", "3,3.0,333"
    )

    val output: Iterable[String] = UserDatasetKpi.createStringSequence(udk)

    assert( output.size == expectedStrSequence.size)

    val outputSeq = output.toSeq

    assert(outputSeq.head == expectedStrSequence.head)
    assert(outputSeq(1) == expectedStrSequence(1))
    assert(outputSeq(2) == expectedStrSequence(2))
    assert(outputSeq(3) == expectedStrSequence(3))
    assert(outputSeq(4) == expectedStrSequence(4))
  }

}
