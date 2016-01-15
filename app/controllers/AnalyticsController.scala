package controllers

import controllers.zonetv.unity.analytics.{Payload, KeenAnalyticsProvider}
import play.api._
import play.api.libs.json._
import play.api.mvc._
import play.libs.Akka

import scala.concurrent.{ExecutionContext, Future}
/**
  * Created by qichen on 2016-01-11.
  */
object AnalyticsController extends Controller{

  object Contexts {
    implicit val keenExecutionContext: ExecutionContext = Akka.system.dispatchers.lookup("keen-context")
  }

  def version = Action{ implicit request =>
    Logger.info("Get version")
    Ok("2.5.0")
  }

  def start = Action{ request =>
    this.collect("start", request)
  }

  def end = Action{ request =>
    this.collect("end", request)
  }

  def content = Action{ request =>
    this.collect("content", request)
  }

  def feature = Action{ request =>
    this.collect("feature", request)
  }

  def collect(event: String, request: Request[AnyContent] ): Result = {
    val payload = request.body.asXml
      .map { xml => new Payload(xml) }
      .getOrElse {
        request.body.asJson.map { json => new Payload(json) }
          .orNull
      }
    payload.event = Some(event)
    Future {
      KeenAnalyticsProvider.collect(payload)
    }(Contexts.keenExecutionContext)


    if (request.accepts("application/json"))
      Ok(Json.obj("session" -> payload.sessionId.toString))
    else if (request.accepts("application/xml"))
      Ok(<session>
        {payload.sessionId.toString}
      </session>)
    else
      Ok(payload.sessionId.toString)
  }
}
