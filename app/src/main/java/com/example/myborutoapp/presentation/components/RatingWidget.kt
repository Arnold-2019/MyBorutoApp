package com.example.myborutoapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myborutoapp.R
import com.example.myborutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double
) {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starBounds = remember {
        starPath.getBounds()
    }

    FilledStar(starPath = starPath, starBounds = starBounds)
}

@Composable
fun FilledStar(
    starPath: Path,
    starBounds: Rect,
    scaleFactor: Float = 2f
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = this.size
        val left = (canvasSize.width / 2f) - (starBounds.width / 1.7f)
        val top = (canvasSize.height / 2f) - (starBounds.height / 1.7f)

        scale(scale = scaleFactor) {
            translate(left = left, top = top) {
                drawPath(path = starPath, color = StarColor)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilledStarPreview() {
    RatingWidget(modifier = Modifier, rating = 1.0)
}
