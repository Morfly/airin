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

package org.morfly.sample.imagedata

import kotlinx.coroutines.delay
import javax.inject.Inject


class DefaultImagesRepository @Inject constructor() : ImagesRepository {

    private val images by lazy(LazyThreadSafetyMode.NONE) {
        listOf(
            Image(0, "https://cdn.pixabay.com/photo/2021/06/16/21/46/parrot-6342271_1280.jpg"),
            Image(1, "https://cdn.pixabay.com/photo/2019/08/08/11/42/butterfly-4392802_1280.jpg"),
            Image(2, "https://cdn.pixabay.com/photo/2021/01/28/18/21/beach-5958718_1280.jpg"),
            Image(3, "https://cdn.pixabay.com/photo/2019/11/19/22/23/cat-4638664_1280.jpg"),
            Image(4, "https://cdn.pixabay.com/photo/2021/04/25/23/17/rome-6207755_1280.jpg"),
            Image(5, "https://cdn.pixabay.com/photo/2021/06/17/19/06/sunset-6344387_1280.jpg"),
            Image(6, "https://cdn.pixabay.com/photo/2021/06/04/09/46/poppy-6309354_1280.jpg"),
            Image(7, "https://cdn.pixabay.com/photo/2021/06/07/16/50/woman-6318447_1280.jpg"),
            Image(8, "https://cdn.pixabay.com/photo/2021/07/17/16/42/western-striped-albatros-6473509_1280.jpg"),
            Image(9, "https://cdn.pixabay.com/photo/2021/05/10/05/40/scarlet-mormon-butterfly-6242643_1280.jpg"),
            Image(10, "https://cdn.pixabay.com/photo/2020/11/11/21/20/lamb-5733685_1280.jpg"),
            Image(11, "https://cdn.pixabay.com/photo/2020/08/29/09/26/beach-5526592_1280.jpg"),
            Image(12, "https://cdn.pixabay.com/photo/2020/04/11/00/25/girl-5028253_1280.jpg"),
            Image(13, "https://cdn.pixabay.com/photo/2021/06/18/09/08/leaves-6345603_1280.jpg"),
            Image(14, "https://cdn.pixabay.com/photo/2021/06/13/18/09/roses-6333976_1280.jpg"),
            Image(15, "https://cdn.pixabay.com/photo/2021/06/16/16/07/eagle-6341677_1280.jpg"),
            Image(16, "https://cdn.pixabay.com/photo/2021/06/03/00/06/sheet-music-6305620_1280.jpg"),
            Image(17, "https://cdn.pixabay.com/photo/2021/06/06/18/02/rose-6316005_1280.jpg"),
            Image(18, "https://cdn.pixabay.com/photo/2021/06/20/17/48/blanket-flowers-6351671_1280.jpg"),
            Image(19, "https://cdn.pixabay.com/photo/2021/06/10/10/36/hydrangeas-6325649_1280.jpg"),
            Image(20, "https://cdn.pixabay.com/photo/2021/06/21/03/24/female-6352570_1280.jpg"),
            Image(21, "https://cdn.pixabay.com/photo/2021/06/13/15/17/dog-6333608_1280.jpg"),
            Image(22, "https://cdn.pixabay.com/photo/2021/06/03/15/45/chameleon-6307349_1280.jpg"),
            Image(23, "https://cdn.pixabay.com/photo/2021/06/09/15/29/poppy-6323714_1280.jpg"),
            Image(24, "https://cdn.pixabay.com/photo/2021/06/15/16/11/man-6339003_1280.jpg"),
            Image(25, "https://cdn.pixabay.com/photo/2021/06/04/15/50/skateboarding-6310245_1280.jpg"),
            Image(26, "https://cdn.pixabay.com/photo/2020/05/12/16/16/raspberries-5163781_1280.jpg"),
        )
    }

    override suspend fun loadImages(): List<Image> {
        delay(timeMillis = 1_200)
        return images
    }
}