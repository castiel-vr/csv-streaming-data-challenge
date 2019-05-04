package it.castielvr.challenge.csvstreaming.io

import akka.stream.scaladsl.Source
import it.castielvr.challenge.csvstreaming.model.UserData

trait UserDataStreamSource[T] {
  def getSource: Source[UserData, T]
}
