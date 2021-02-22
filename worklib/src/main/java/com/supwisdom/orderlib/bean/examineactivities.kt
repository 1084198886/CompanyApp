package com.supwisdom.orderlib.bean

/**
 * @author gqy
 * @date 2019/8/23 0023.
 * @since 1.0.0
 * @see
 * @desc  TODO
 */

 class ExamineData {
    var title: String? = null
    var list: List<Detail>? = null
}

 class Detail {
    var image: String? = null
    var petname: String? = null
    var status: String? = null
}
