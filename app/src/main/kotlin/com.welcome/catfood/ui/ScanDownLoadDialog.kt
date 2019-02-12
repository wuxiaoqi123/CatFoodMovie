package com.welcome.catfood.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Message
import com.welcome.catfood.R
import com.welcome.catfood.manager.SettingManager
import com.welcome.catfood.utils.AppUtils
import com.welcome.catfood.utils.ZxQRUtil
import kotlinx.android.synthetic.main.menu_scan_down.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/02/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ScanDownLoadDialog(context: Context) : BaseMenuDialog(context) {

    private var qrBitmap: Bitmap? = null

    private val mHandler = Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            menu_scan_down_qr_img.setImageBitmap(qrBitmap)
            return true
        }
    })

    override fun getContentView(): Int = R.layout.menu_scan_down

    override fun initViews() {
        val down_url = context.getString(R.string.github_url)
        if (qrBitmap == null) {
            Thread {
                qrBitmap = ZxQRUtil.createQR(
                    down_url,
                    AppUtils.dp2px(200f).toInt(),
                    AppUtils.dp2px(200f).toInt()
                )
                mHandler.sendEmptyMessage(0)
            }.start()
        }
    }

    override fun show() {
        super.show()
        menu_scan_cardview.setCardBackgroundColor(SettingManager.getThemeColor());
    }
}
