package com.example.myborutoapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myborutoapp.R
import com.example.myborutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.example.myborutoapp.ui.theme.LightGray
import com.example.myborutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 2.7f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    val result = calculateStars(rating = rating)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        result["filledStars"]?.let {
            repeat(it) {
                FilledStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result["halfFilledStars"]?.let {
            repeat(it) {
                HalfFilledStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result["emptyStars"]?.let {
            repeat(it) {
                EmptyStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
    }
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

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    val maxStars by remember { mutableStateOf(5) }
    var filledStars by remember { mutableStateOf(0) }
    var halfFilledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating.toString()
            .split(".")
            .map { it.toInt() }

        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber
            if (lastNumber in 1..5) {
                halfFilledStars++
            }
            if (lastNumber in 6..9) {
                filledStars++
            }
            if (firstNumber == 5 && lastNumber > 0) {
                emptyStars = 5
                filledStars = 0
                halfFilledStars = 0
            }
        } else {
            Log.d("RatingWidget", "Invalid rating number.")
        }
    }

    emptyStars = maxStars - (filledStars + halfFilledStars)
    return mapOf(
        "filledStars" to filledStars,
        "halfFilledStars" to halfFilledStars,
        "emptyStars" to emptyStars
    )
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
