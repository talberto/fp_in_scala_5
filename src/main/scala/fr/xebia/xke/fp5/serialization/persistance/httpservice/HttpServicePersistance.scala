package fr.xebia.xke.fp5.serialization.persistance.httpservice

import akka.actor.ActorSystem
import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, RequestEntity}
import akka.stream.ActorMaterializer
import fr.xebia.xke.fp5.serialization.persistance.PersistanceLayer

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object HttpServicePersistance {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def httpServicePersistance(client: HttpExt, url: String): PersistanceLayer =
    new PersistanceLayer {

      override def persist(s: String): Unit = {
        val eventualResponse =
          for {
            entity <- Marshal(s).to[RequestEntity]
            request <- client.singleRequest(HttpRequest(method = HttpMethods.PUT, uri = url, entity = entity))
          } yield request

        Await.result(eventualResponse, 10 seconds)
      }

    }

}
