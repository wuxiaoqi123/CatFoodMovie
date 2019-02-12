package com.welcome.catfood.utils

import android.graphics.Bitmap

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

/**
 * Created by wuxiaoqi on 2017/12/19.
 * 二维码工具类
 */

object ZxQRUtil {

    /**
     * 生成一个二维码
     *
     * @param text
     * @return
     */
    fun createQR(text: String, width: Int, height: Int): Bitmap? {
        var bitmap: Bitmap? = null
        var bitMatrix: BitMatrix
        val multiFormatWriter = MultiFormatWriter()
        try {
            bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            bitMatrix = deleteWhite(bitMatrix)
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: Exception) {

        }

        return bitmap
    }

    /**
     * 擦除白边
     *
     * @param matrix
     * @return
     */
    private fun deleteWhite(matrix: BitMatrix): BitMatrix {
        val rec = matrix.enclosingRectangle
        val resWidth = rec[2] + 1
        val resHeight = rec[3] + 1

        val resMatrix = BitMatrix(resWidth, resHeight)
        resMatrix.clear()
        for (i in 0 until resWidth) {
            for (j in 0 until resHeight) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j)
            }
        }
        return resMatrix
    }
}
