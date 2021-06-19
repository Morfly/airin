/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.morfly.airin.sample.data.impl.mapping

import org.morfly.airin.sample.data.impl.network.model.PixabayImage
import org.morfly.airin.sample.data.impl.storage.entity.Image


interface DataMapper {

    /**
     * Maps image model from network api to the storage one.
     */
    fun networkToStorage(image: PixabayImage, query: String): Image
}