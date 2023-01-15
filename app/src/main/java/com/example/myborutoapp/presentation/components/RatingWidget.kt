package com.example.myborutoapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myborutoapp.R
import com.example.myborutoapp.ui.theme.LightGray
import com.example.myborutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 2.7f
) {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starBounds = remember {
        starPath.getBounds()
    }

    FilledStar(starPath = starPath, starPathBounds = starBounds, scaleFactor = scaleFactor)
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        val left = (canvasSize.width / 2f) - (starPathBounds.width / 1.7f)
        val top = (canvasSize.height / 2f) - (starPathBounds.height / 1.7f)

        scale(scale = scaleFactor) {
            translate(left = left, top = top) {
                drawPath(path = starPath, color = StarColor)
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        val left = (canvasSize.width / 2f) - (starPathBounds.width / 1.7f)
        val top = (canvasSize.height / 2f) - (starPathBounds.height / 1.7f)
        val clipWidth = starPathBounds.width / 1.7f
        val clipHeight = clipWidth * 2f

        scale(scale = scaleFactor) {
            translate(left = left, top = top) {
                drawPath(path = starPath, color = LightGray.copy(alpha = 0.5f))
                clipPath(path = starPath) {
                    drawRect(
                        color = StarColor,
                        size = Size(width = clipWidth, height = clipHeight)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        val left = (canvasSize.width / 2f) - (starPathBounds.width / 1.7f)
        val top = (canvasSize.height / 2f) - (starPathBounds.height / 1.7f)

        scale(scale = scaleFactor) {
            translate(left = left, top = top) {
                drawPath(path = starPath, color = LightGray.copy(alpha = 0.5f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilledStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starBounds = remember {
        starPath.getBounds()
    }

    FilledStar(starPath = starPath, starPathBounds = starBounds, scaleFactor = 2.7f)
}

@Preview(showBackground = true)
@Composable
fun HalfFilledStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starBounds = remember {
        starPath.getBounds()
    }

    HalfFilledStar(starPath = starPath, starPathBounds = starBounds, scaleFactor = 2.7f)
}

@Preview(showBackground = true)
@Composable
fun EmptyStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starBounds = remember {
        starPath.getBounds()
    }

    EmptyStar(starPath = starPath, starPathBounds = starBounds, scaleFactor = 2.7f)
}
