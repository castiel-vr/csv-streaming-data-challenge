package it.castielvr.challenge.csvstreaming.engine

import akka.NotUsed
import akka.stream.scaladsl.Flow
import it.castielvr.challenge.csvstreaming.model.{UserData, UserDatasetKpi, UserKpi}

object KpiEngine {

  def kpiFlow(groupingSize: Int): Flow[UserData, UserDatasetKpi, NotUsed] = {
    Flow[UserData]
      // `grouped` maintains the order in the resulting sequence
      .grouped(groupingSize).map{
      udSeq =>
        val kpiByUser = udSeq
          .groupBy(ud => ud.id)
          .map{
            //For each group of events with the same user id calculate the KPI
            case (id, userDataList) =>
              val userKpiRawList = userDataList.map( ud => UserKpi(ud))

              val userKpi = userKpiRawList
                // KPI are composed in order of processing with foldLeft
                .foldLeft(new UserKpi(0, 0, 0, 0))(
                  (partialKpi, newElem) => {
                    partialKpi.combineWith(newElem)
                    partialKpi
                  }
                )
              id -> userKpi
          }

        val overallFifth: BigInt= kpiByUser.values.map(_.fifthSum).reduceOption(_ + _).getOrElse(0)
        val uniqueUsers = kpiByUser.size

        UserDatasetKpi(
          overallFifth,
          uniqueUsers,
          kpiByUser
        )
    }
  }

}
