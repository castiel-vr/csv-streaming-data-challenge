package it.castielvr.challenge.csvstreaming.engine

import org.scalatest.FlatSpec
import org.scalatest._
import Matchers._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Source}
import akka.stream.testkit.scaladsl.TestSink
import it.castielvr.challenge.csvstreaming.model.{UserData, UserDatasetKpi, UserKpi}

import scala.collection.immutable.Iterable

class KpiEngineSpec extends FlatSpec{

  implicit val system = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()(system)

  "KpiEngine" should "correctly calculate the kpi from a stream of UserData events" in {
    val input = Iterable(
      "0977dca4-9906-3171-bcec-87ec0df9d745,kFFzW4O8gXURgP8ShsZ0gcnNT5E=,0.18715484122922377,982761284,8442009284719321817",
    "5fac6dc8-ea26-3762-8575-f279fe5e5f51,cBKFTwsXHjwypiPkaq3xTr8UoRE=,0.7626710614484215,1005421520,6642446482729493998",
    "0977dca4-9906-3171-bcec-87ec0df9d745,9ZWcYIblJ7ebN5gATdzzi4e8K7Q=,0.9655429720343038,237475359,3923415930816731861",
    "4d968baa-fe56-3ba0-b142-be9f457c9ff4,RnJNTKLYpcUqhjOey+wEIGHC7aw=,0.6532229483547558,1403876285,4756900066502959030",
    "0977dca4-9906-3171-bcec-87ec0df9d745,N0fiZEPBjr3bEHn+AHnpy7I1RWo=,0.8857966322563835,1851028776,6448117095479201352",
    "0977dca4-9906-3171-bcec-87ec0df9d745,P/wNtfFfa8jIn0OyeiS1tFvpORc=,0.8851653165728414,1163597258,8294506528003481004",
    "0977dca4-9906-3171-bcec-87ec0df9d745,Aizem/PgVMKsulLGquCAsLj674U=,0.5869654624020274,1012454779,2450005343631151248",
    "023316ec-c4a6-3e88-a2f3-1ad398172ada,TRQb8nSQEZOA5Ccx8NntYuqDPOw=,0.3790267017026414,652953292,4677453911100967584",
    "023316ec-c4a6-3e88-a2f3-1ad398172ada,UfL8VetarqYZparwV4AJtyXGgFM=,0.26029423666931595,1579431460,5620969177909661735",
    "0977dca4-9906-3171-bcec-87ec0df9d745,uZNIcWQtwst+9mjQgPkV2rvm7QY=,0.039107542861771316,280709214,4450245425875000740"
    ).map(s => UserData.fromCsv(s))

    val expectedKpiData1 = UserDatasetKpi(
      BigInt("30212888860247708058"),
      3,
      Map(
        "0977dca4-9906-3171-bcec-87ec0df9d745" -> new UserKpi(2.0384944455199108f,3, 1851028776, BigInt("18813542311015255030")),
        "5fac6dc8-ea26-3762-8575-f279fe5e5f51" -> new UserKpi(0.7626710614484215f,1, 1005421520, BigInt("6642446482729493998")),
        "4d968baa-fe56-3ba0-b142-be9f457c9ff4" -> new UserKpi(0.6532229483547558f,1, 1403876285, BigInt("4756900066502959030"))
      )

    )
    val expectedKpiData2 = UserDatasetKpi(
      BigInt("25493180386520262311"),
      2,
      Map(
        "0977dca4-9906-3171-bcec-87ec0df9d745" -> new UserKpi(1.51123832183664f,3, 280709214,BigInt("15194757297509632992")),
        "023316ec-c4a6-3e88-a2f3-1ad398172ada" -> new UserKpi(0.6393209383719574f,2,1579431460,BigInt("10298423089010629319"))
    )
    )

    val testSink = TestSink.probe[UserDatasetKpi]

    val kpiFlow = KpiEngine.kpiFlow(5)

    val runRes = Source(input).via(kpiFlow).toMat(testSink)(Keep.both).run()

    runRes._2.request(2)
    val output1 = runRes._2.requestNext
    val output2 = runRes._2.requestNext

    assert(output1 == expectedKpiData1)
    assert(output2 == expectedKpiData2)

    runRes._2.expectComplete()
  }
}
