package it.castielvr.challenge.csvstreaming.io

import akka.NotUsed
import akka.stream.scaladsl.Sink
import it.castielvr.challenge.csvstreaming.model.UserDatasetKpi


trait UserDatasetKpiStreamSink {
  def getSink: Sink[UserDatasetKpi, NotUsed]
}
