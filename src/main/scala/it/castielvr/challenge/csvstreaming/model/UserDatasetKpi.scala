package it.castielvr.challenge.csvstreaming.model
import scala.collection.immutable.Iterable
object UserDatasetKpi {
  def createStringSequence(udk: UserDatasetKpi, sep: String = ","): Iterable[String] = {

    Iterable(udk.overallSum.toString, udk.uniqueUsers.toString) ++
    udk.usersKpi.map {
      case (userId, kpis) =>
        Seq(userId, kpis.thirdAvg, kpis.latestFourth).mkString(sep)
    }
  }
}

case class UserDatasetKpi(
                           overallSum: BigInt,
                           uniqueUsers: Int,
                           usersKpi: Map[String, UserKpi]) {
}