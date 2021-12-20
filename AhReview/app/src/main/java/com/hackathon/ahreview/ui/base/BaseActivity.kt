package com.hackathon.ahreview.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hackathon.ahreview.BR
import com.hackathon.ahreview.R
import com.nhn.android.naverlogin.OAuthLogin
import java.lang.reflect.ParameterizedType
import java.util.*


// FIXME: 현재 Base는 도담도담 레거시꺼를 계속 사용하는데 이를 이제는 그만 사용하고 직접 다시 만들어봐라
// FIXME: Base를 왜 사용하는지, 이때 제네릭은 왜 필요한지, Java Style말고 Kotlin 스타일로 코드를 작성해라
// FIXME: Base를 직접만들어보면 코드 공통화 부분에 대해서 많이 배우게 될거다
// FIXME: 코드 복붙 하는 개발자가 되지 말고 직접 설계하고 구현하는 개발자가 되라

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var mBinding: VB
    protected lateinit var mViewModel: VM

    protected abstract val viewModel: VM
    lateinit var mOAuthLoginInstance: OAuthLogin

    protected abstract fun observerViewModel()
/*
    protected open fun onErrorEvent(e: Throwable) {
        e.printStackTrace()
        if(e is TokenException) {
            startActivityWithFinishAll(LoginActivity::class.java)
            shortToast(e.message)
            return
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDataBinding()
/*
        mViewModel.onErrorEvent.observe(this, { exception ->
            onErrorEvent(exception)
        })*/

        observerViewModel()
    }

    private fun performDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, layoutRes())
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
        mBinding.setVariable(BR.vm, mViewModel)
        mBinding.lifecycleOwner = this
        mBinding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mBinding.isInitialized) mBinding.unbind()
        if (::mOAuthLoginInstance.isInitialized) mOAuthLoginInstance.logout(this)
    }

    @LayoutRes
    private fun layoutRes(): Int {
        val split =
            ((Objects.requireNonNull(javaClass.genericSuperclass) as ParameterizedType).actualTypeArguments[0] as Class<*>)
                .simpleName.replace("Binding$".toRegex(), "")
                .split("(?<=.)(?=\\p{Upper})".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()

        val name = StringBuilder()

        for (i in split.indices) {
            name.append(split[i].toLowerCase(Locale.ROOT))
            if (i != split.size - 1)
                name.append("_")
        }

        try {
            return R.layout::class.java.getField(name.toString()).getInt(R.layout::class.java)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        return 0
    }
}