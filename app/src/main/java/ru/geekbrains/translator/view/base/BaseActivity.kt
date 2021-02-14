package ru.geekbrains.translator.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.translator.model.data.AppState
import ru.geekbrains.translator.presenter.Presenter

abstract class BaseActivity<T : AppState> : AppCompatActivity(), View {

    protected lateinit var presenter: Presenter<T, View>

    protected abstract fun createPresenter(): Presenter<T, View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}
