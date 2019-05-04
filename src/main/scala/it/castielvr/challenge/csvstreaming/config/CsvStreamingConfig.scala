package it.castielvr.challenge.csvstreaming.config

import com.typesafe.config.Config

object CsvStreamingConfig{
  def apply(config: Config): CsvStreamingConfig ={
    CsvStreamingConfig(
      SourceEndpoint(config.getConfig("source-endpoint")),
      config.getString("output-path"),
      config.getInt("events-grouping-size")
    )
  }
}

case class CsvStreamingConfig(
                               sourceEndpoint: SourceEndpoint,
                               outputPath: String,
                               eventsGroupingSize: Int
                             )

private object SourceEndpoint{
  def apply(config: Config): SourceEndpoint = {
    SourceEndpoint(
      config.getString("address"),
      config.getInt("port")
    )
  }
}

case class SourceEndpoint(
                                  address: String,
                                  port: Int
                                )
