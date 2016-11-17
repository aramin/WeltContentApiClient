package de.welt.contentapi.core.models.funkotron

import de.welt.contentapi.core.models.pressed.{ApiChannel, ApiConfiguration}


/**
  * /sport/fussball/ => PresentationChannel
  * @param channel
  * @param configuration
  */
case class ApiPresentationChannel(channel: Option[ApiChannel] = None, configuration: Option[ApiConfiguration] = None)


