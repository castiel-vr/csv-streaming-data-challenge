package it.castielvr.challenge.csvstreaming.model

import it.castielvr.challenge.csvstreaming.exceptions.MalformedUserDataCsv

object UserData{

  @throws(classOf[MalformedUserDataCsv])
  def fromCsv(sourceString: String, separator: String = ","): UserData = {
    val splitted: Array[String] = sourceString.split(separator, -1)
    if(splitted.length != 5) {
      throw new MalformedUserDataCsv(s"Resulting elements splitting on ${separator} are ${splitted.length} instead of 5.")
    }

    try{
      UserData(
        splitted(0),
        splitted(1),
        splitted(2).toFloat,
        splitted(3).toLong,
        BigInt(splitted(4).trim)
      )
    } catch {
      case e: Exception =>
        throw new MalformedUserDataCsv(e.getMessage)
    }
  }
}

case class UserData(
                     id: String,
                     encodedData: String,
                     third: Float,
                     fourth: Long,
                     fifth: BigInt
                   )
