package com.sj.app.activities.home.my.myDynamic

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我的动态
 */
class MyDynamicPresenter(private val myDynamicView: MyDynamicView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()
    private val allList = arrayListOf<String>()
    private val photoList = arrayListOf<String>()
    private val videoList = arrayListOf<String>()

    fun getMyDynamic() {
        myDynamicView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getMyDynamic()

                allList.clear()
                photoList.clear()
                videoList.clear()
                // TODO

                allList.add("alll1")
                allList.add("alll2")

                photoList.add("photoList1")
                photoList.add("photoList2")

                videoList.add("videoList1")
                videoList.add("videoList2")

                myDynamicView.getActivity().runOnUiThread {
                    myDynamicView.loadSuccess(dataList)
                }
            } catch (ex: TerminalInitError) {
                myDynamicView.getActivity().runOnUiThread {
                    myDynamicView.showToast(ex.message!!)
                }
            } finally {
                myDynamicView.getActivity().runOnUiThread {
                    myDynamicView.closeProgressDialog()
                }
            }
        }
    }

    fun getAll(): List<String> {
        return allList
    }

    fun getPhotoList(): List<String> {
        return photoList
    }

    fun getVideoList(): List<String> {
        return videoList
    }

}