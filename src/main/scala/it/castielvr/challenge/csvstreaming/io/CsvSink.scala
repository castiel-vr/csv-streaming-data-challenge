package it.castielvr.challenge.csvstreaming.io

import java.nio.file.Paths

import akka.NotUsed
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{FileIO, Flow, Sink, Source}
import akka.util.ByteString
import it.castielvr.challenge.csvstreaming.model.UserDatasetKpi

class CsvSink(destinationPath: String)(implicit materializer: ActorMaterializer) extends UserDatasetKpiStreamSink {
  override def getSink: Sink[UserDatasetKpi, NotUsed] = {
    val writeFilesFlow = Flow[UserDatasetKpi]
      .zipWithIndex
      // The flow is splitted to write in different file sinks.
      .mapAsync(1){
        case (udk, index) =>
          val destinationFile = s"$destinationPath/$index.txt"
          val stringSeq = UserDatasetKpi.createStringSequence(udk).map(s => ByteString(s + "\n"))
          Source(stringSeq).runWith(FileIO.toPath(Paths.get(destinationFile)))
      }
    // A fake akka stream sink must be specified, the actual write is defined in the mapAsync
    val uselessSink = Sink.ignore
    Flow[UserDatasetKpi].via(writeFilesFlow).to(uselessSink)
  }
}
