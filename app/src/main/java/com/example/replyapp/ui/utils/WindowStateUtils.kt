package com.example.replyapp.ui.utils

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/*
项目:ReplyApp
包名：com.example.replyapp.ui.utils
作者: bobo
发布日期及时间: 2/8/25  6:45 PM
*/

/*
用关设备姿态的信息
 */
sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(
        val hingePosition: Rect
    ) : DevicePosture

    data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation
    ) : DevicePosture

}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature:FoldingFeature?):Boolean{
    contract { returns(true ) implies (foldFeature!= null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?):Boolean{
    contract { returns(true ) implies (foldFeature!= null) }
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}

/*
 * 不同的设备支持不同的屏幕尺寸。
 */
enum class ReplyNavigationType{
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}
/*
 *导航轨、导航抽屉内的导航内容的位置根据设备尺寸和状态而不同。
 */
enum class ReplyNavigationContentPosition{
    TOP, CENTER
}
/*
 *根据设备尺寸和状态显示的应用程序内容。
 */

enum class ReplyContentType{
    SINGLE_PANE, DUAL_PANE
}