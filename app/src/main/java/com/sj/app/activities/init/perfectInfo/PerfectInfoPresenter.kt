package com.sj.app.activities.init.perfectInfo

import android.graphics.Bitmap
import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
class PerfectInfoPresenter(private val perfectInfoView: PerfectInfoView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun saveCustInfo(petName: String, sex: String, age: String, headerPhoto: Bitmap) {
        perfectInfoView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.saveCustInfo(petName, sex, age, headerPhoto)
                perfectInfoView.getActivity().runOnUiThread {
                    perfectInfoView.saveCustInfoSuccess("保存成功")
                }
            } catch (ex: TerminalInitError) {
                perfectInfoView.getActivity().runOnUiThread {
                    perfectInfoView.showToast(ex.message!!)
                }
            } finally {
                perfectInfoView.getActivity().runOnUiThread {
                    perfectInfoView.closeProgressDialog()
                }
            }
        }
    }
}