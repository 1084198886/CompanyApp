package com.sj.app.activities.home.my.userCenter

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil
import com.supwisdom.orderlib.entity.DynamicParaRecord
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sj.app.utils.AppCommonUtil
import com.sj.app.view.DialogConstellationSelect
import com.sj.app.view.DialogPhotoSelect
import com.supwisdom.commonlib.utils.CommonUtil
import com.supwisdom.commonlib.utils.DateUtil
import java.io.File
import java.io.IOException

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  个人中心
 */
class UserCenterActivity : BaseActivity(), UserCenterView {
    private val REQ_CODE_OPEN_CAMERA: Int = 100
    private val REQ_CODE_CROP_PHOTO: Int = 200
    private val REQ_CODE_OPEN_ALBUM: Int = 300

    private lateinit var mVHeadPhoto: ImageView
    private lateinit var mVTagContainer: TagContainerLayout
    private lateinit var mVConstellation: TextView
    private lateinit var mVUserid: TextView
    private lateinit var mVSignature: TextView
    private lateinit var mVSex: TextView
    private lateinit var presenter: UserCenterPresenter
    private lateinit var dialogProgress: DialogProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_center)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "个人中心"

        mVHeadPhoto = findViewById<View>(R.id.v_head_photo) as ImageView
        findViewById<View>(R.id.v_modify_headphoto).setOnClickListener {
            showPhotoSelectDialog()
        }
        mVTagContainer = findViewById<View>(R.id.v_tag_container) as TagContainerLayout

        findViewById<View>(R.id.panel_constellation).setOnClickListener {
            showConstellationDialog()
        }
        mVConstellation = findViewById<View>(R.id.v_constellation) as TextView
        mVUserid = findViewById<View>(R.id.v_userid) as TextView

        findViewById<View>(R.id.panel_signature).setOnClickListener {

        }
        mVSignature = findViewById<View>(R.id.v_signature) as TextView
        mVSex = findViewById<View>(R.id.v_sex) as TextView
    }

    private var constellationSelectDialog: DialogConstellationSelect? = null
    private fun showConstellationDialog() {
        if (constellationSelectDialog == null) {
            constellationSelectDialog = DialogConstellationSelect(this)
            constellationSelectDialog!!.itemListener = object : DialogConstellationSelect.ConstellationSelectListener {
                override fun onSelect(positon: Int, data: String) {
                    mVConstellation.text = data
                }
            }
        }
        constellationSelectDialog!!.show()
    }

    private fun initData() {
        presenter = UserCenterPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
        presenter.getUserInfo()
    }

    /**
     * 打开相册
     */
    private fun openSysAlbum() {
        val albumIntent = Intent(Intent.ACTION_PICK)
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(albumIntent, REQ_CODE_OPEN_ALBUM)
    }

    /**
     * 打开相机
     */
    private fun openSysCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var file: File? = null
        val imgUriOri: Uri?
        try {
            file = createOriImageFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (file != null) {
            imgUriOri = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Uri.fromFile(file)
            } else {
                FileProvider.getUriForFile(this, "$packageName.provider", file)
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriOri)
            startActivityForResult(cameraIntent, REQ_CODE_OPEN_CAMERA)
        }
    }


    /**
     * 创建原图像保存的文件
     *
     * @return
     */
    private fun createOriImageFile(): File {
        val imgNameOri = "HomePic_" + DateUtil.getNow3()
        val pictureDirOri =
            File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/OriPicture")
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs()
        }
        return File.createTempFile(imgNameOri, ".jpg", pictureDirOri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_OPEN_CAMERA -> { // 拍照返回
                val tempFile = File(Environment.getExternalStorageDirectory(), "image.png")
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    cropPic(Uri.fromFile(tempFile))
                } else {
                    val uri = getImageContentUri(tempFile)
                    if (uri != null) {
                        cropPic(uri)
                    }
                }
            }
            REQ_CODE_CROP_PHOTO -> {
                if (data != null) {
                    val bundle = data.extras
                    if (bundle != null) {
                        val bitmap = bundle.getParcelable<Bitmap>("data")
                        mVHeadPhoto.setImageBitmap(bitmap)
                        // 把裁剪后的图片保存至本地 返回路径
//                        val urlpath = FileUtil.saveFile(this, "crop.jpg", bitmap)
                    }
                }
            }
            REQ_CODE_OPEN_ALBUM -> { // 相册返回
                if (data != null && data.data != null) {
                    cropPic(data.data!!)
                }
            }
        }
    }

    /**
     * 7.0以上获取裁剪 Uri
     */
    private fun getImageContentUri(imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID),
                MediaStore.Images.Media.DATA + "=? ", arrayOf(filePath), null
            )
            return if (cursor != null && cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val baseUri = Uri.parse("content://media/external/images/media")
                Uri.withAppendedPath(baseUri, "" + id)
            } else {
                if (imageFile.exists()) {
                    val values = ContentValues()
                    values.put(MediaStore.Images.Media.DATA, filePath)
                    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                } else {
                    null
                }
            }
        } finally {
            cursor?.close()
        }
    }

    private var photoSelectDialog: DialogPhotoSelect? = null
    private fun showPhotoSelectDialog() {
        if (photoSelectDialog == null) {
            photoSelectDialog = DialogPhotoSelect(this)
            photoSelectDialog!!.itemListener = object : DialogPhotoSelect.PhotoSelectListener {
                override fun onSelect(photo: DialogPhotoSelect.Photo) {
                    when (photo) {
                        DialogPhotoSelect.Photo.ALBUM -> {
                            openSysAlbum()
                        }
                        DialogPhotoSelect.Photo.CAMERA -> {
                            openSysCamera()
                        }
                    }
                }
            }
        }
        photoSelectDialog!!.show()
    }

    /**
     * 裁剪图片
     * https://blog.csdn.net/wufeng55/article/details/80918749
     */
    private fun cropPic(uri: Uri) {
        val cropIntent = Intent("com.android.camera.action.CROP")
        cropIntent.setDataAndType(uri, "image/*")
        // 开启裁剪
        cropIntent.putExtra("crop", "true")
        // 裁剪宽高比
        cropIntent.putExtra("aspectX", 1)
        cropIntent.putExtra("aspectY", 1)
        // 裁剪输出大小
        cropIntent.putExtra("outputX", 320)
        cropIntent.putExtra("outputY", 320)
        cropIntent.putExtra("scale", true)
        /**
         * return-data
         * 这个属性决定我们在 onActivityResult 中接收到的是什么数据，
         * 如果设置为true 那么data将会以Intent返回一个bitmap
         * 如果设置为false，则会将图片保存到本地并将对应的uri返回，当然这个uri得有我们自己设定。
         * 系统裁剪完成后将会将裁剪完成的图片保存在我们所这设定这个uri地址上。我们只需要在裁剪完成后直接调用该uri来设置图片，就可以了。
         */
        cropIntent.putExtra("return-data", true)
        // 当 return-data 为 false 的时候需要设置这句
//        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        // 图片输出格式
//        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 头像识别 会启动系统的拍照时人脸识别
//        cropIntent.putExtra("noFaceDetection", true)
        startActivityForResult(cropIntent, REQ_CODE_CROP_PHOTO)
    }

    /**
     * 初始化相机相关权限
     * 适配6.0+手机的运行时权限
     */
    fun initPermission() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 之前拒绝了权限，但没有点击 不再询问 这个时候让它继续请求权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//                Toast.makeText(this, "用户曾拒绝打开相机权限", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, permissions, 400);
            } else {
                //注册相机权限
                ActivityCompat.requestPermissions(this, permissions, 400);
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 400) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //成功
                Toast.makeText(this, "用户授权相机权限", Toast.LENGTH_SHORT).show();
            } else {
                // 勾选了不再询问
                Toast.makeText(this, "用户拒绝相机权限", Toast.LENGTH_SHORT).show();
                /**
                 * 跳转到 APP 详情的权限设置页
                 *
                 * 可根据自己的需求定制对话框，点击某个按钮在执行下面的代码
                 */
                val intent = AppCommonUtil.getAppDetailSettingIntent(this)
                startActivity(intent)
            }
        }

    }

    override fun showUserInfo(data: DynamicParaRecord) {
        Glide.with(this)
            .load(data.headphoto)
            .apply(RequestOptions().circleCrop().placeholder(R.drawable.loading_white))
            .into(mVHeadPhoto)

        if (!TextUtils.isEmpty(data.tags)) {
            val tags = presenter.getTagList(data.tags)
            if (tags != null) {
                mVTagContainer.tags = tags
            }
        }
        refreshTagViewStyle()
        mVUserid.text = data.userid ?: ""
        mVConstellation.text = data.constellation ?: ""
        mVSignature.text = data.signature ?: ""
        mVSex.text = data.sex ?: ""
    }

    private fun refreshTagViewStyle() {
        mVTagContainer.borderColor = ContextCompat.getColor(this, R.color.transparent)

        for (i in 0 until mVTagContainer.childCount) {
            val tagView = mVTagContainer.getChildAt(i) as TagView
            tagView.setTextSize(CommonUtil.sp2px(14).toFloat())
            tagView.setTagTextColor(ContextCompat.getColor(this, R.color.black))
            tagView.setHorizontalPadding(CommonUtil.dip2px(5f))
            tagView.setVerticalPadding(CommonUtil.dip2px(5f))

            tagView.setBorderWidth(CommonUtil.dip2px(1f).toFloat())
            tagView.setBorderRadius(CommonUtil.dip2px(5f).toFloat())
            tagView.setTagBorderColor(ContextCompat.getColor(this, R.color.cl_red))
            tagView.setTagBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        }
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun showProgressDialog(msg: String) {
        dialogProgress.setMessage(msg).show()
    }

    override fun closeProgressDialog() {
        if (dialogProgress.isShowing) {
            dialogProgress.dismiss()
        }
    }

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }

}