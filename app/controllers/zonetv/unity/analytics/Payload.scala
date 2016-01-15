package controllers.zonetv.unity.analytics

import java.util.{Date, Calendar, UUID}

import play.api.libs.json.{Json, Writes, JsValue}

import scala.xml.NodeSeq

/**
  * Created by qichen on 2016-01-12.
  */
class Payload {
  var sessionId: UUID = _
  var accountId: String = _
  var distributor: String = _
  var subscribed: Option[Boolean] = _
  var event: Option[String] = _
  var age: Option[Long] = None
  var app: Option[AppInfo] = None
  var device: Option[DeviceInfo] = None
  var content: Option[ContentInfo] = None
  var feature: Option[FeatureInfo] = None
  var created: Date = Calendar.getInstance().getTime
  var collected: Date = _

  def this(json: JsValue) = {
    this()
    sessionId = UUID.fromString((json \ "sessionId").asOpt[String].getOrElse(UUID.randomUUID().toString))
    accountId = (json \ "accountId").as[String]
    distributor = (json \ "distributor").as[String]
    subscribed = (json \ "subscribed").asOpt[Boolean]
    event = (json \ "event").asOpt[String]
    age = (json \ "age").asOpt[Long]
    app = (json \ "app").asOpt[JsValue].map { v => new AppInfo(v) }
    device = (json \ "device").asOpt[JsValue].map { v => new DeviceInfo(v) }
    content = (json \ "content").asOpt[JsValue].map { v => new ContentInfo(v) }
    feature = (json \ "feature").asOpt[JsValue].map { v => new FeatureInfo(v) }
  }

  def this(xml: NodeSeq) = {
    this()
    sessionId = UUID.fromString((xml \ "sessionId").headOption.map { v => v.text }.getOrElse(UUID.randomUUID().toString))
    accountId = (xml \ "accountId").text
    distributor = (xml \ "distributor").text
    subscribed = (xml \ "subscribed").headOption.map { v => v.text.toBoolean }
    event = (xml \ "event").headOption.map { v => v.text }
    age = (xml \ "age").headOption.map { v => v.text.toLong }
    app = (xml \ "app").headOption.map { v => Some(new AppInfo(v)) }.getOrElse(None)
    device = (xml \ "device").headOption.map { v => Some(new DeviceInfo(v)) }.getOrElse(None)
    content = (xml \ "content").headOption.map { v => Some(new ContentInfo(v)) }.getOrElse(None)
    feature = (xml \ "feature").headOption.map { v => Some(new FeatureInfo(v)) }.getOrElse(None)
  }

  def toJson: JsValue = {

    implicit val isoDateWrites: Writes[Date] = Writes.dateWrites("yyyy-MM-dd'T'HH:mm:ss.S'Z'")

    implicit val appWrites = new Writes[AppInfo] {
      def writes(app: AppInfo) = Json.obj(
        "channel" -> app.channel,
        "name" -> app.name,
        "version" -> app.version,
        "distribute" -> app.distribute)
    }

    implicit val deviceWrites = new Writes[DeviceInfo] {
      def writes(device: DeviceInfo) = Json.obj(
        "id" -> device.deviceId,
        "make" -> device.make,
        "model" -> device.model,
        "os" -> device.os,
        "version" -> device.version,
        "ip" -> device.ip,
        "language" -> device.language,
        "resolution" -> device.resolution,
        "version" -> device.version,
        "version" -> device.version,
        "ua" -> device.ua)
    }

    implicit val contentWrites = new Writes[ContentInfo] {
      def writes(content: ContentInfo) = Json.obj(
        "id" -> content.contentId,
        "name" -> content.name,
        "friendlyName" -> content.friendlyName,
        "url" -> content.url,
        "contentType" -> content.contentType,
        "referralId" -> content.referralId,
        "referralValue" -> content.referralValue,
        "language" -> content.language,
        "format" -> content.format,
        "containerName" -> content.containerName,
        "containerType" -> content.containerType,
        "containerId" -> content.containerId,
        "duration" -> content.duration,
        "engaged" -> content.engaged)
    }

    implicit val featureWrites = new Writes[FeatureInfo] {
      def writes(feature: FeatureInfo) = Json.obj(
        "screenId" -> feature.screenId,
        "screenName" -> feature.screenName,
        "screenPath" -> feature.screenPath,
        "actionType" -> feature.actionType,
        "actionSource" -> feature.actionSource,
        "actionTarget" -> feature.actionTarget,
        "actionValue" -> feature.actionValue,
        "actionZone" -> feature.actionZone,
        "referralId" -> feature.referralId,
        "referralValue" -> feature.referralValue)
    }

    Json.obj(
      "sessionId" -> sessionId.toString,
      "accountId" -> accountId,
      "distributor" -> distributor,
      "subscribed" -> subscribed,
      "event" -> event,
      "age" -> age,
      "app" -> app,
      "device" -> device,
      "content" -> content,
      "feature" -> feature,
      "created" -> created,
      "collected" -> collected
    )
  }
}
