package it.castielvr.challenge.csvstreaming.io

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.stream.alpakka.udp.Datagram
import akka.stream.alpakka.udp.scaladsl.Udp
import akka.stream.scaladsl.{Flow, Source}
import it.castielvr.challenge.csvstreaming.config.SourceEndpoint
import it.castielvr.challenge.csvstreaming.model.UserData
import org.reactivestreams.Subscriber

import scala.concurrent.Future

class UdpSource(endpointConfig: SourceEndpoint)(implicit val as: ActorSystem) extends UserDataStreamSource[Subscriber[Datagram]] {
  override def getSource: Source[UserData, Subscriber[Datagram]] = {
    val sourceBind = new InetSocketAddress(endpointConfig.address, endpointConfig.port)
    val bindFlow: Flow[Datagram, Datagram, Future[InetSocketAddress]] = Udp.bindFlow(sourceBind)
    val udpSource: Source[UserData, Subscriber[Datagram]] = Source.asSubscriber.via(bindFlow).map {
      dg =>
        UserData.fromCsv(dg.data.utf8String)
    }
    udpSource
  }
}
