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

import android.os.Build
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter
import android.text.Html
import hackaton.interventure.com.interventurehackaton.ItemData

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: AbstractDetailsDescriptionPresenter.ViewHolder,
        item: Any
    ) {
        val itemData = item as ItemData

        viewHolder.title.text = itemData.name
//        viewHolder.subtitle.text = movie.studio
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.body.text = Html.fromHtml(itemData.desc, Html.FROM_HTML_MODE_COMPACT)
        } else {
            viewHolder.body.text = Html.fromHtml(itemData.desc)
        }
    }
}