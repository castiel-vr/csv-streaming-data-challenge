package it.castielvr.challenge.csvstreaming

import java.io.File

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.alpakka.udp.Datagram
import akka.stream._
import akka.stream.scaladsl._
import com.typesafe.config.{Config, ConfigFactory}
import it.castielvr.challenge.csvstreaming.config.{CliConfig, CsvStreamingConfig}
import it.castielvr.challenge.csvstreaming.engine.KpiEngine
import it.castielvr.challenge.csvstreaming.io.{CsvSink, UdpSource}
import it.castielvr.challenge.csvstreaming.model.{UserData, UserDatasetKpi}
import org.reactivestreams.Subscriber

object CsvStreamingProcessorApp {

  def main(args: Array[String]): Unit = {
    implicit val as: ActorSystem = ActorSystem("ProcessorSystem")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    // Read CLI args
    val cliConfig = CliConfig.parseArgs(args)
    val configFileLocation: String = cliConfig.appConfigPath

    // Read and parse external config
    val configFile = new File(configFileLocation)
    val rawConf: Config = ConfigFactory.parseFile(configFile)
    val appConf = CsvStreamingConfig(rawConf)

    // Create last level of the output dir if it doesn't exist
    val outputDir = new File(appConf.outputPath)
    if(!outputDir.exists()) outputDir.mkdir()

    // Define the source
    val udpSource: Source[UserData, Subscriber[Datagram]] = new UdpSource(appConf.sourceEndpoint).getSource

    // Define a flow to pass from UserData stream to a UserDatasetKpi stream
    val typedData: Flow[UserData, UserDatasetKpi, NotUsed] = KpiEngine.kpiFlow(appConf.eventsGroupingSize)

    // Define the sink to CSV in local filesystem
    val filesystemSink = new CsvSink(appConf.outputPath).getSink

    // Compose the runnable graph
    val mainWorkflow = udpSource.via(typedData).to(filesystemSink)

    mainWorkflow.run

  }
}
