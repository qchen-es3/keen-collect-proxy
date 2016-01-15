package controllers.zonetv.unity.analytics

import play.api.libs.json.JsValue

import scala.xml.NodeSeq

/**
  * Created by qichen on 2016-01-12.
  */
class DeviceInfo {
  var deviceId: String = _
  var make: Option[String] = None
  var model: Option[String] = None
  var os: Option[String] = None
  var version: Option[String] = None
  var ip: Option[String] = None
  var language: Option[String] = None
  var resolution: Option[String] = None
  var ua: Option[String] = None

  def this(json: JsValue) = {
    this()
    deviceId = (json \ "id").as[String]
    make = (json \ "make").asOpt[String]
    model = (json \ "model").asOpt[String]
    os = (json \ "os").asOpt[String]
    version = (json \ "version").asOpt[String]
    ip = (json \ "ip").asOpt[String]
    language = (json \ "language").asOpt[String]
    resolution = (json \ "resolution").asOpt[String]
    ua = (json \ "ua").asOpt[String]
  }

  def this(xml: NodeSeq) = {
    this()
    deviceId = (xml \ "id").text
    make = (xml \ "make").headOption.map { v => v.text }
    model = (xml \ "model").headOption.map { v => v.text }
    os = (xml \ "os").headOption.map { v => v.text }
    version = (xml \ "version").headOption.map { v => v.text }
    ip = (xml \ "ip").headOption.map { v => v.text }
    language = (xml \ "language").headOption.map { v => v.text }
    resolution = (xml \ "resolution").headOption.map { v => v.text }
    ua = (xml \ "ua").headOption.map { v => v.text }
  }
}