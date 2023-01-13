package com.glsoftware.entrenadorrcp.presentation.ScreenElements

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glsoftware.entrenadorrcp.ui.theme.*

@Composable
fun BarIndicator(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    terciaryColor: Color,
    circleRadius:Float,
){
    var barTop by remember {
        mutableStateOf(Offset.Zero)
    }
    var barBot by remember {
        mutableStateOf(Offset.Zero)
    }
    var recTop by remember {
        mutableStateOf(Offset.Zero)
    }
    var barCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(modifier = modifier){
        Canvas(modifier = Modifier
            .fillMaxSize(),
        ){
            val width = size.width
            val height = size.height

            val ancho = circleRadius / 1.5f
            val alto = circleRadius * 2f

            val alto_des = ancho/2.5f

            val esquinas = height/25f

            val barThickness = width/20f

            var ancho_extremos = ancho*1.5f

            var bar_colo: Color = primaryColor
            var top_colo: Color = primaryColor
            var bot_colo: Color = primaryColor

            var valor = initialValue

            barTop = Offset(x = width/2f- ancho_extremos / 2f, y = height/2f - alto/2f)
            barBot = Offset(x = width/2f- ancho_extremos / 2f, y = height/2f + alto/2f - alto_des)
            recTop = Offset(x = width/2f- ancho / 2f, y = height/2f - alto/2f)
            barCenter = Offset(x = width/2f- ancho/2f , y = height/2f -alto/2f +(valor*(alto-(alto_des*0.5f))/50) )

            drawRoundRect(
                size = Size(
                    width = ancho,
                    height = alto
                ),
                topLeft = recTop,
                cornerRadius = CornerRadius(
                    x = esquinas*0.2f.dp.toPx(),
                    y = esquinas*0.2f.dp.toPx()
                ),
                brush = Brush.linearGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.25f)
                    ),
                ),
            )

            if(valor<0){
                valor = 0
            }else if (valor>50){
                valor=50
            }

            if (valor<6 || valor>44){
                bar_colo = hand_green
                if (valor<6){
                    top_colo = hand_green
                }else
                    bot_colo = hand_green
            }

            drawRoundRect(
                color = bar_colo,
                size = Size(
                    width = ancho,
                    height = alto_des*0.5f
                ),
                topLeft = barCenter,
            )

            drawRoundRect(
                color = bot_colo,
                size = Size(
                    width = ancho_extremos,
                    height = alto_des
                ),
                topLeft = barBot,
                cornerRadius = CornerRadius(
                    x = esquinas.dp.toPx(),
                    y = esquinas.dp.toPx()
                )
            )

            drawRoundRect(
                color = top_colo,
                size = Size(
                    width = ancho_extremos,
                    height = alto_des
                ),
                topLeft = barTop,
                cornerRadius = CornerRadius(
                    x = esquinas.dp.toPx(),
                    y = esquinas.dp.toPx()
                )
            )

            drawRoundRect(
                style = Stroke(
                    width = barThickness
                ),
                color = secondaryColor,
                size = Size(
                    width = ancho_extremos,
                    height = alto_des
                ),
                topLeft = barBot,
                cornerRadius = CornerRadius(
                    x = esquinas.dp.toPx(),
                    y = esquinas.dp.toPx()
                )
            )

            drawRoundRect(
                style = Stroke(
                    width = barThickness
                ),
                color = secondaryColor,
                size = Size(
                    width = ancho_extremos,
                    height = alto_des
                ),
                topLeft = barTop,
                cornerRadius = CornerRadius(
                    x = esquinas.dp.toPx(),
                    y = esquinas.dp.toPx()
                )
            )


            /*drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "$initialValue Desp",
                        barCenter.x + ancho / 2f,
                        barCenter.y,
                        Paint().apply {
                            textSize = 30.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = white.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }*/
        }
    }

}

@Preview(showBackground = true)
@Composable
fun Previewarc(){


    BarIndicator(
        modifier = Modifier
            .size(250.dp)
            .background(gray),
        initialValue = 50,
        primaryColor = white,
        secondaryColor = Red200 ,
        terciaryColor = Red700,
        circleRadius = 230f
    )
}