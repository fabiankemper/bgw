/*
 * Copyright 2022 The BoardGameWork Authors
 * SPDX-License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tools.aqua.bgw.net.server.service.websocket

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import tools.aqua.bgw.net.server.entity.tables.KeyValueRepository

/**
 * Checks if the soprasecret HTTP header is matching the current SoPra secret. Checks if the
 * playername HTTP header is set and if its set, assigns it as the playerName attribute to the
 * session.
 *
 * Only allows establishment of web socket session if both checks succeed.
 */
class BGWHandshakeInterceptor(private val keyValueRepository: KeyValueRepository) :
    HandshakeInterceptor {
  private val logger: Logger = LoggerFactory.getLogger(javaClass)

  override fun beforeHandshake(
      request: ServerHttpRequest,
      response: ServerHttpResponse,
      wsHandler: WebSocketHandler,
      attributes: MutableMap<String, Any>
  ): Boolean {
    val secret: String =
        try {
              keyValueRepository.findById("SoPra Secret").get()
            } catch (e: NoSuchElementException) {
              logger.error("SoPra Secret does not exist in database")
              return false
            }
            .value
    var isSuccess = (request.headers.getFirst("soprasecret") == secret)
    with(request.headers.getFirst("playername")) {
      if (this == null) {
        isSuccess = false
      } else {
        attributes["playerName"] = this
      }
    }
    return isSuccess
  }

  /** Does nothing. */
  override fun afterHandshake(
      request: ServerHttpRequest,
      response: ServerHttpResponse,
      wsHandler: WebSocketHandler,
      exception: Exception?
  ) {}
}
