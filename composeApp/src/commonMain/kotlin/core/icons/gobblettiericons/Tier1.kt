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

public val GobbletTierIcons.Tier1: ImageVector
    get() {
        if (_tier1 != null) {
            return _tier1!!
        }
        _tier1 = Builder(
            name = "Tier1",
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
                moveTo(196.967f, 126.256f)
                lineTo(373.744f, 303.033f)
                arcTo(25.0f, 25.0f, 113.016f, false, true, 373.744f, 338.388f)
                lineTo(338.388f, 373.744f)
                arcTo(25.0f, 25.0f, 75.89f, false, true, 303.033f, 373.744f)
                lineTo(126.256f, 196.967f)
                arcTo(25.0f, 25.0f, 0.0f, false, true, 126.256f, 161.612f)
                lineTo(161.612f, 126.256f)
                arcTo(25.0f, 25.0f, 0.0f, false, true, 196.967f, 126.256f)
                close()
            }
        }
            .build()
        return _tier1!!
    }

private var _tier1: ImageVector? = null
