package controllers.zonetv.unity.analytics

import play.api.libs.json.JsValue

import scala.xml.NodeSeq

/**
  * Created by qichen on 2016-01-12.
  */
class AppInfo {

  var channel: String = _
  var name: Option[String] = None
  var version: Option[String] = None
  var distribute: Option[String] = None

  def this(json: JsValue) = {
    this()
    this.channel = (json \ "channel").as[String]
    this.name = (json \ "name").asOpt[String]
    this.version = (json \ "version").asOpt[String]
    this.distribute = (json \ "distribute").asOpt[String]
  }

  def this(xml: NodeSeq) = {
    this()
    this.channel = (xml \ "channel").text
    this.name = (xml \ "name").headOption.map { v => v.text }
    this.version = (xml \ "version").headOption.map { v => v.text }
    this.distribute = (xml \ "distribute").headOption.map { v => v.text }
  }
}
