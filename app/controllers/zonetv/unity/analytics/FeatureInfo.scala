package controllers.zonetv.unity.analytics

import play.api.libs.json.JsValue

import scala.xml.NodeSeq

/**
  * Created by qichen on 2016-01-12.
  */
class FeatureInfo {
  var screenId: Option[String] = None
  var screenName: Option[String] = None
  var screenPath: Option[String] = None
  var actionType: Option[String] = None
  var actionSource: Option[String] = None
  var actionTarget: Option[String] = None
  var actionValue: Option[String] = None
  var actionZone: Option[String] = None
  var referralId: Option[String] = None
  var referralValue: Option[String] =  None

  def this(json: JsValue) = {
    this()
    this.screenId = (json \ "screenId").asOpt[String]
    this.screenName = (json \ "screenName").asOpt[String]
    this.screenPath = (json \ "screenPath").asOpt[String]
    this.actionType = (json \ "actionType").asOpt[String].map{ v => v.toLowerCase() }
    this.actionSource = (json \ "actionSource").asOpt[String]
    this.actionTarget = (json \ "actionTarget").asOpt[String]
    this.actionValue = (json \ "actionValue").asOpt[String]
    this.actionZone = (json \ "actionZone").asOpt[String].map { v => v.toLowerCase() }
    this.referralId = (json \ "referralId").asOpt[String]
    this.referralValue = (json \ "referralValue").asOpt[String]
  }

  def this(xml: NodeSeq) = {
    this()
    this.screenId = (xml \ "screenId").headOption.map { v => v.text }
    this.screenName = (xml \ "screenName").headOption.map  { v => v.text }
    this.screenPath = (xml \ "screenPath").headOption.map { v => v.text }
    this.actionType = (xml \ "actionType").headOption.map { v => v.text.toLowerCase() }
    this.actionSource = (xml \ "actionSource").headOption.map { v => v.text }
    this.actionTarget = (xml \ "actionTarget").headOption.map { v => v.text }
    this.actionValue = (xml \ "actionValue").headOption.map { v => v.text }
    this.actionZone = (xml \ "actionZone").headOption.map { v => v.text.toLowerCase() }
    this.referralId = (xml \ "referralId").headOption.map { v => v.text }
    this.referralValue = (xml \ "referralValue").headOption.map { v => v.text }
  }
}