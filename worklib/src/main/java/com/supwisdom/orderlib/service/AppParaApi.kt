package com.supwisdom.orderlib.service

import android.graphics.Bitmap
import com.supwisdom.orderlib.bean.*

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  应用参数api
 */
internal class AppParaApi {
    /**
     * 获取验证码
     */
    fun getVerifyNo(account: String) {
    }

    /**
     * 完善客户资料
     */
    fun saveCustInfo(petName: String, sex: String, age: String, headerPhoto: Bitmap) {
    }

    fun saveCustLabel(labels: String) {
    }

    fun feedBack(advice: String) {

    }

    fun getAdviceData(): List<String>? {
        val dataList = arrayListOf<String>()
        dataList.add("FFFFFFFFFFFF")
        dataList.add("FFFFFFFFFFFF")
        dataList.add("FFFFFFFFFFFF")
        dataList.add("FFFFFFFFFFFF")
        return dataList
    }

    fun getGroupMyJoin() {
    }

    fun getGroupMyStart(): ArrayList<MyStart>? {
        val list = arrayListOf<MyStart>()
        for (i in 0 until 20) {
            val myStart = MyStart()
            myStart.image =
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566583491268&di=4c2de3f6b0a3f597f008e89ab03eea4b&imgtype=0&src=http%3A%2F%2Fpic27.nipic.com%2F20130304%2F7368717_232014122100_2.jpg"
            myStart.petname = "昵称测试"
            myStart.actname = "活动名称"
            myStart.attentioncnt = i + 1
            myStart.joincnt = i + 3
            list.add(myStart)
        }
        return list
    }

    fun getAdviceMyJoin(): ArrayList<MyStart>? {
        val list = arrayListOf<MyStart>()
        for (i in 0 until 20) {
            val myStart = MyStart()
            myStart.image =
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566583491268&di=4c2de3f6b0a3f597f008e89ab03eea4b&imgtype=0&src=http%3A%2F%2Fpic27.nipic.com%2F20130304%2F7368717_232014122100_2.jpg"
            myStart.petname = "昵称测试"
            myStart.actname = "征求名称征求名称"
            myStart.attentioncnt = i + 1
            myStart.joincnt = i + 3
            list.add(myStart)
        }
        return list
    }

    fun getExamineActivities(): List<ExamineData>? {
        val list = arrayListOf<ExamineData>()
        for (i in 0 until 6) {
            val examinActivity = ExamineData()
            examinActivity.title = "标题标题标题标题"
            val details = arrayListOf<Detail>()
            for (j in 0 until 3) {
                val detail = Detail()
                detail.image = "http://img4.imgtn.bdimg.com/it/u=3276179142,1686381254&fm=26&gp=0.jpg"
                detail.petname = "我是昵称"
                detail.status = "1"
                details.add(detail)
            }
            examinActivity.list = details
            list.add(examinActivity)
        }
        return list
    }

    fun getCollectAttentions() {
    }

    fun deleteGroupMyStart() {
    }

    fun getActivityDetails() {
    }

    fun getApplyForGroup(): java.util.ArrayList<ApplyForGroup>? {
        val list = arrayListOf<ApplyForGroup>()
        for (i in 0 until 20) {
            val applyForGroup = ApplyForGroup()
            applyForGroup.applyUser = "张三"
            applyForGroup.applygroup = "大众"
            applyForGroup.status = "1"
            list.add(applyForGroup)
        }
        return list
    }

    fun getMyDynamic(): ArrayList<MyDynamic>? {
        val list = arrayListOf<MyDynamic>()
        for (i in 0 until 20) {
            val applyForGroup = MyDynamic()
            applyForGroup.date = "7月$i"
            applyForGroup.image = "http://img4.imgtn.bdimg.com/it/u=3276179142,1686381254&fm=26&gp=0.jpg"
            applyForGroup.content = "我是内容我是内容我是内容我是内容我是内容我是内容"
            list.add(applyForGroup)
        }
        return list
    }

    fun publishDynamic() {
    }

    fun getMyPublishGroup() {
    }
}