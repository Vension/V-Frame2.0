package com.vension.app.ui.fragments

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.vension.app.R
import com.vension.app.showToast
import com.vension.frame.VFrame
import com.vension.frame.base.VBaseFragment
import com.vension.frame.dialogs.VActionSheetDialog
import com.vension.frame.dialogs.VAlertDialog
import com.vension.frame.utils.VObsoleteUtil
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_tab_2.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/28 10:36
 * 描  述：
 * ========================================================
 */

class TabFragment_2 : VBaseFragment() {


    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.centerTextView.text = "dialog弹窗测试"
        mCommonTitleBar.leftImageButton.visibility = View.GONE
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_tab_2

    override fun initViewAndData(savedInstanceState: Bundle?) {
        btn1_ac_like_ios_dialog.setOnClickListener(this)
        btn2_ac_like_ios_dialog.setOnClickListener(this)
        btn3_ac_like_ios_dialog.setOnClickListener(this)
        btn4_ac_like_ios_dialog.setOnClickListener(this)
        btn5_ac_like_ios_dialog.setOnClickListener(this)
    }

    override fun lazyLoadData() {
    }


    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.btn1_ac_like_ios_dialog-> showBtn1()
            R.id.btn2_ac_like_ios_dialog-> showBtn2()
            R.id.btn3_ac_like_ios_dialog-> showBtn3()
            R.id.btn4_ac_like_ios_dialog-> showBtn4()
            R.id.btn5_ac_like_ios_dialog-> {
                VAlertDialog(context).builder()
                        .setContent("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
                        .setNegativeButton("确定", View.OnClickListener { })
                        .show()
            }
        }
    }

    private fun showBtn1() {
        VActionSheetDialog(context)
                .builder()
                .setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？")
                .setTitleColor(ContextCompat.getColor(VFrame.getContext(), R.color.colorAccent))
                .setTitleSize(16.0f)
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)//设置点击其他地方与返回是否消失 true为消失
                .addSheetItem("清空消息列表", VActionSheetDialog.SheetItemColor.Red
                ) {
                    showToast("清空消息列表")
                }.show()
    }

    private fun showBtn2() {
        VActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("发送给好友", VActionSheetDialog.SheetItemColor.Blue
                ) {
                    showToast("发送给好友")
                }
                .addSheetItem("转载到空间相册", VActionSheetDialog.SheetItemColor.Blue
                ) {
                    showToast("转载到空间相册")
                }
                .addSheetItem("上传到群相册", VActionSheetDialog.SheetItemColor.Blue
                ) {
                    showToast("上传到群相册")
                }
                .addSheetItem("保存到手机", VActionSheetDialog.SheetItemColor.Blue
                ) {
                    showToast("保存到手机")
                }
                .addSheetItem("收藏", VActionSheetDialog.SheetItemColor.Blue
                ) {
                    showToast("收藏")
                }
                .addSheetItem("查看聊天图片", VActionSheetDialog.SheetItemColor.Blue
                ) {
                    showToast("查看聊天图片")
                }
                .show()
    }

    private fun showBtn3() {
        VActionSheetDialog(context)
                .builder()
                .setTitle("请选择操作")
                .setCancelText("取消了")
                .setCancelTextColor(ContextCompat.getColor(VFrame.getContext(), R.color.colorAccent))
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("条目一", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目二", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目三", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目四", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目五", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目六", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目七", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目八", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目九", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }
                .addSheetItem("条目十", VActionSheetDialog.SheetItemColor.Blue
                ) { which ->
                    showToast("item$which")
                }.show()
    }

    private fun showBtn4() {
        VAlertDialog(context).builder().setTitle("退出当前账号")
                .setContent("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                .setPositiveButton("确认退出", VObsoleteUtil.getColor(R.color.Color_ff9734), View.OnClickListener { })
                .setNegativeButton("取消", View.OnClickListener { })
                .show()
    }

}