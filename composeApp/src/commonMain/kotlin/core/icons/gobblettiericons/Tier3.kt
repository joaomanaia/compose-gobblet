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

public val GobbletTierIcons.Tier3: ImageVector
    get() {
        if (_tier3 != null) {
            return _tier3!!
        }
        _tier3 = Builder(
            name = "Tier3",
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
                moveTo(110.725f, 212.764f)
                lineTo(287.501f, 389.541f)
                arcTo(25.0f, 25.0f, 80.236f, false, true, 287.501f, 424.896f)
                lineTo(252.146f, 460.252f)
                arcTo(25.0f, 25.0f, 79.648f, false, true, 216.791f, 460.252f)
                lineTo(40.014f, 283.475f)
                arcTo(25.0f, 25.0f, 111.946f, false, true, 40.014f, 248.12f)
                lineTo(75.369f, 212.764f)
                arcTo(25.0f, 25.0f, 0.0f, false, true, 110.725f, 212.764f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveToRelative(161.612f, 126.257f)
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
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveToRelative(247.702f, 40.209f)
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
        return _tier3!!
    }

private var _tier3: ImageVector? = null
