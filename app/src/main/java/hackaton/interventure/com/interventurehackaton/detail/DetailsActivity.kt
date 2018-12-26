/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package hackaton.interventure.com.interventurehackaton.detail

import android.support.v4.app.FragmentActivity

/**
 * Details activity class that loads [DetailsFragment] class.
 */
abstract class DetailsActivity : FragmentActivity() {

    companion object {
        const val SHARED_ELEMENT_NAME = "hero"
        const val ITEM_DATA = "itemData"
        const val MOVIE = "Movie"
    }
}