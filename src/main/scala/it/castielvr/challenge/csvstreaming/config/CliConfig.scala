package it.castielvr.challenge.csvstreaming.config

import org.apache.commons.cli.{BasicParser, Options}

object CliConfig {

  def parseArgs(args: Array[String]): CliConfig = {
    val options = new Options()
    options.addOption("c", "config-path", true, "display current time")

    val parser = new BasicParser
    val cmd = parser.parse(options, args)

    CliConfig(
      cmd.getOptionValue("c")
    )
  }
}

case class CliConfig(
                      appConfigPath: String
                    )