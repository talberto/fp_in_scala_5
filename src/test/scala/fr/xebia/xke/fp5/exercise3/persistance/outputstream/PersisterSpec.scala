package fr.xebia.xke.fp5.exercise3.persistance.outputstream

import java.io.PrintWriter

import akka.actor.ActorSystem
import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, RequestEntity}
import akka.stream.ActorMaterializer
import fr.xebia.xke.fp5.exercise3.persistance.Persister.persist
import fr.xebia.xke.fp5.exercise3.persistance.httpservice.HttpServicePersistance
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.Mockito.{mock, verify}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class PersisterSpec extends FlatSpec with Matchers with BeforeAndAfter {

  implicit val materializer = ActorMaterializer()(ActorSystem())

  val stringCaptor = ArgumentCaptor.forClass(classOf[String])

  val httpRequestCaptor = ArgumentCaptor.forClass(classOf[HttpRequest])

  var printWriterMock:PrintWriter = null
  var httpClientMock:HttpExt = null

  before {
    printWriterMock = mock(classOf[PrintWriter])
    httpClientMock = mock(classOf[HttpExt])
  }

  "OutputStreamPersister" should "write data to given stream in string format" in {
    // Given
    import fr.xebia.xke.fp5.exercise3.serializer.string.StandardTypeSerializationSupport._
    implicit val mockedOutputStream = OutputStreamPersister.outputStreamPersister(printWriterMock)

    // When
    persist(Option(5))

    // Then
    verify(printWriterMock).println(stringCaptor.capture())
    stringCaptor.getValue should equal("Some(5)")
  }

  it should "write data to given stream in json format" in {
    // Given
    import fr.xebia.xke.fp5.exercise3.serializer.json.StandardTypeSerializationSupport._
    implicit val mockedOutputStream = OutputStreamPersister.outputStreamPersister(printWriterMock)

    // When
    persist(Left("val"): Either[String, String])

    // Then
    verify(printWriterMock).println(stringCaptor.capture())
    stringCaptor.getValue should equal("""{"branch":"left", "value":"val"}""")
  }

  "HttpServicePersistance" should "send data to given service in json format" in {
    // Given
    import fr.xebia.xke.fp5.exercise3.serializer.json.StandardTypeSerializationSupport._
    implicit val mockedOutputStream = HttpServicePersistance.httpServicePersistance(httpClientMock, "http://url/resource")
    given(httpClientMock.singleRequest(httpRequestCaptor.capture(), org.mockito.Matchers.any(), org.mockito.Matchers.any(), org.mockito.Matchers.any())(org.mockito.Matchers.any()))
      .willReturn(Future(HttpResponse()))

    // When
    persist(Left("val"): Either[String, String])

    // Then
    val expectedBody =
      """{"branch":"left", "value":"val"}"""

    httpRequestCaptor.getValue.uri.toString() should equal("http://url/resource")
    httpRequestCaptor.getValue.method should equal(HttpMethods.PUT)
    httpRequestCaptor.getValue.entity should equal(Await.result(Marshal(expectedBody).to[RequestEntity], 10 seconds))
  }

  it should "send data to given service in string format" in {
    // Given
    import fr.xebia.xke.fp5.exercise3.serializer.string.StandardTypeSerializationSupport._
    implicit val mockedOutputStream = HttpServicePersistance.httpServicePersistance(httpClientMock, "http://url/resource")
    given(httpClientMock.singleRequest(httpRequestCaptor.capture(), org.mockito.Matchers.any(), org.mockito.Matchers.any(), org.mockito.Matchers.any())(org.mockito.Matchers.any()))
      .willReturn(Future(HttpResponse()))

    // When
    persist(Left("val"): Either[String, String])

    // Then
    val expectedBody =
      """Left("val")"""

    httpRequestCaptor.getValue.uri.toString() should equal("http://url/resource")
    httpRequestCaptor.getValue.method should equal(HttpMethods.PUT)
    httpRequestCaptor.getValue.entity should equal(Await.result(Marshal(expectedBody).to[RequestEntity], 10 seconds))
  }

}
