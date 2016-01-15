package controllers.zonetv.unity.analytics

import java.util.Calendar

import io.keen.client.scala.{Writer, Client}
import play.api.Logger
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by qichen on 2016-01-12.
  */
object KeenAnalyticsProvider {

  private val _keenClient = new Client with Writer

  def collect(payload: Payload): Future[Unit] = {
    payload.collected = Calendar.getInstance().getTime

    this._keenClient.addEvent("unity_events", payload.toJson.toString()).map { response =>
      response.statusCode match{
        case i if 200 until 400 contains i => Logger.info(payload.event + " has been sent successfully.")
        case _ => Logger.error("Event "+ payload.event.get + " has failed: " + response.statusCode.toString + " " + response.body)
      }
    }
  }
}
