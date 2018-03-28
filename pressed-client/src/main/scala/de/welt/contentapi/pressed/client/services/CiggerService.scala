package de.welt.contentapi.pressed.client.services

import com.google.inject.{ImplementedBy, Inject, Singleton}
import com.kenshoo.play.metrics.Metrics
import de.welt.contentapi.core.client.services.CapiExecutionContext
import de.welt.contentapi.core.client.services.contentapi.AbstractService
import de.welt.contentapi.core.client.services.http.RequestHeaders
import de.welt.contentapi.pressed.models.{ApiPressedContentResponse, PressedReads}
import play.api.Configuration
import play.api.libs.json.{JsLookupResult, JsResult}
import play.api.libs.ws.WSClient

import scala.concurrent.Future

@ImplementedBy(classOf[CiggerServiceImpl])
trait CiggerService {

  def byId(id: String, showRelated: Boolean = false, doEmbed: Boolean = false)(implicit rh: RequestHeaders): Future[ApiPressedContentResponse]

}

@Singleton
class CiggerServiceImpl @Inject()(ws: WSClient, metrics: Metrics, configuration: Configuration, override implicit val capi: CapiExecutionContext)
  extends AbstractService[ApiPressedContentResponse](ws, metrics, configuration, "cigger", capi) with CiggerService {

  override def jsonValidate: JsLookupResult ⇒ JsResult[ApiPressedContentResponse] =
    _.validate[ApiPressedContentResponse](PressedReads.apiPressedContentResponseReads)

  override def byId(id: String, showRelated: Boolean, doEmbed: Boolean)(implicit rh: RequestHeaders): Future[ApiPressedContentResponse] = {

    val parameters = (if (showRelated) Seq("show-related" → "true") else Seq.empty) ++ (if (doEmbed) Seq("embed" → "true") else Seq.empty)

    get(Seq(id), parameters)
  }
}