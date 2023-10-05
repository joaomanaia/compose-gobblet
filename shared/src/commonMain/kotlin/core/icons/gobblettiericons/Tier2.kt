package core.icons.gobblettiericons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import core.icons.GobbletTierIcons

public val GobbletTierIcons.Tier2: ImageVector
    get() {
        if (_tier2 != null) {
            return _tier2!!
        }
        _tier2 = Builder(
            name = "Tier2",
            defaultWidth = 500.0.dp,
            defaultHeight = 500.0.dp,
            viewportWidth = 500.0f,
            viewportHeight = 500.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(146.967f, 176.256f)
                lineTo(323.744f, 353.033f)
                arcTo(25.0f, 25.0f, 112.283f, false, true, 323.744f, 388.388f)
                lineTo(288.388f, 423.744f)
                arcTo(25.0f, 25.0f, 83.771f, false, true, 253.033f, 423.744f)
                lineTo(76.256f, 246.967f)
                arcTo(25.0f, 25.0f, 0.0f, false, true, 76.256f, 211.612f)
                lineTo(111.612f, 176.256f)
                arcTo(25.0f, 25.0f, 0.0f, false, true, 146.967f, 176.256f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveToRelative(212.56f, 75.204f)
                curveToRelative(9.606f, -9.606f, 25.75f, -9.606f, 35.355f, -0.0f)
                lineToRelative(176.777f, 176.777f)
                curveToRelative(9.606f, 9.606f, 9.606f, 25.75f, 0.0f, 35.355f)
                lineToRelative(-35.355f, 35.355f)
                curveToRelative(-9.606f, 9.606f, -25.75f, 9.606f, -35.355f, 0.0f)
                lineToRelative(-176.777f, -176.777f)
                curveToRelative(-9.606f, -9.606f, -9.606f, -25.75f, -0.0f, -35.355f)
                lineToRelative(35.355f, -35.355f)
                close()
            }
        }
            .build()
        return _tier2!!
    }

private var _tier2: ImageVector? = null
