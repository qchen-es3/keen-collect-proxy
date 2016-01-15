package controllers.zonetv.unity.analytics

import play.api.libs.json.JsValue

import scala.xml.NodeSeq

/**
  * Created by qichen on 2016-01-12.
  */
class ContentInfo {
  var contentId: Option[String] = None
  var name: Option[String] = None
  var friendlyName: Option[String] = None
  var url: Option[String] = None
  var contentType: Option[String] = None
  var referralId: Option[String] = None
  var referralValue: Option[String] = None
  var language: Option[String] = None
  var format: Option[String] = None
  var containerName: Option[String] = None
  var containerType: Option[String] = None
  var containerId: Option[String] = None
  var duration: Option[Int] = None
  var engaged: Option[Int] = None

  def this(json: JsValue) = {
    this()
    this.contentId = (json \ "id").asOpt[String]
    this.name = (json \ "name").asOpt[String]
    this.friendlyName = (json \ "friendlyName").asOpt[String]
    this.url = (json \ "url").asOpt[String]
    this.contentType = (json \ "contentType").asOpt[String].map { v => v.toLowerCase() }
    this.referralId = (json \ "referralId").asOpt[String]
    this.referralValue = (json \ "referralValue").asOpt[String]
    this.language = (json \ "language").asOpt[String]
    this.format = (json \ "format").asOpt[String]
    this.containerName = (json \ "containerName").asOpt[String]
    this.containerType = (json \ "containerType").asOpt[String].map { v => v.toLowerCase() }
    this.containerId = (json \ "containerId").asOpt[String]
    this.duration = (json \ "duration").asOpt[Int]
    this.engaged = (json \ "engaged").asOpt[Int]
  }

  def this(xml: NodeSeq) = {
    this()
    this.contentId = (xml \ "contentId").headOption.map { v => v.text }
    this.name = (xml \ "name").headOption.map { v => v.text }
    this.friendlyName = (xml \ "friendlyName").headOption.map { v => v.text }
    this.url = (xml \ "url").headOption.map { v => v.text }
    this.contentType = (xml \ "contentType").headOption.map { v => v.text.toLowerCase() }
    this.referralId = (xml \ "referralId").headOption.map { v => v.text }
    this.referralValue = (xml \ "referralValue").headOption.map { v => v.text }
    this.language = (xml \ "language").headOption.map { v => v.text }
    this.format = (xml \ "format").headOption.map { v => v.text }
    this.containerName = (xml \ "containerName").headOption.map { v => v.text }
    this.containerType = (xml \ "containerType").headOption.map { v => v.text.toLowerCase() }
    this.containerId = (xml \ "containerId").headOption.map { v => v.text }
    this.duration = (xml \ "duration").headOption.map { v => v.text.toInt }
    this.engaged = (xml \ "engaged").headOption.map { v => v.text.toInt }
  }
}