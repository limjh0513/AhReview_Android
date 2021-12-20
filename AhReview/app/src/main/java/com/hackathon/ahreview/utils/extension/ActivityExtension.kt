package kr.hs.dgsw.smartschool.morammoram.presentation.extension

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

// FIXME: Activity나 Fragment의 Extension이 동일한데 이럴 경우에는 AppCompatActivity, Fragment로 시작하는 확장함수를 만드는 것보다 이에 대한 정보를 매개변수로 받아 처리하도록 하는게 좋다
// FIXME: 중복된 코드는 합칠려고 노력해야 한다, AppCompatActivity, Fragment만 다르지 다른 것은 동일하다

fun AppCompatActivity.shortToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.shortToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.startActivity(activity: Class<*>) {
    startActivity(Intent(applicationContext, activity).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
}

fun AppCompatActivity.startActivityWithValue(activity: Class<*>, name: String, value: Serializable) {
    startActivity(Intent(applicationContext, activity).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra(name, value))
}

fun AppCompatActivity.startActivityWithFinish(activity: Class<*>) {
    startActivity(Intent(applicationContext, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    this.finish()
}

fun AppCompatActivity.startActivityWithFinishAll(activity: Class<*>) {
    startActivity(Intent(applicationContext, activity).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
    this.finish()
}