/*
 * Copyright 2021-2022 The BoardGameWork Authors
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

@file:Suppress("unused")

package tools.aqua.bgw.components

import tools.aqua.bgw.core.Scene
import tools.aqua.bgw.visual.Visual

/**
 * The root component in the view hierarchy of a [Scene].
 *
 * @constructor Creates a [RootComponent].
 *
 * @param T Generic [ComponentView].
 * @property scene Scene of this root component.
 */
class RootComponent<T : ComponentView> internal constructor(val scene: Scene<T>) :
    ComponentView(posX = 0, posY = 0, width = 0, height = 0, visual = Visual.EMPTY) {

  /**
   * Removes [component] from the [scene].
   *
   * @param component Child to be removed.
   *
   * @throws IllegalArgumentException If the child's type is incompatible with scene's type.
   */
  override fun removeChild(component: ComponentView) {
    try {
      @Suppress("UNCHECKED_CAST") this.scene.removeComponents(component as T)
    } catch (_: ClassCastException) {
      throw IllegalArgumentException("$component type is incompatible with scene's type.")
    }
  }
}
