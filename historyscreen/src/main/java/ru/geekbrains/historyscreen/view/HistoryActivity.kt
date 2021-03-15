package ru.geekbrains.historyscreen.view

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.scope.currentScope
import ru.geekbrains.core.BaseActivity
import ru.geekbrains.historyscreen.R
import ru.geekbrains.model.data.AppState
import ru.geekbrains.historyscreen.injectDependencies
import ru.geekbrains.model.data.userdata.DataModel


class HistoryActivity : BaseActivity<AppState>() {

    override val layoutRes = R.layout.activity_history
    override lateinit var viewModel: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iniViewModel()
        initViews()
    }
    // Сразу запрашиваем данные из локального репозитория
    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }
    // Вызовется из базовой Activity, когда данные будут готовы
    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (history_activity_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        injectDependencies()
        val vm: HistoryViewModel by currentScope.inject()
        viewModel = vm
        viewModel.subscribe().observe(this@HistoryActivity, Observer<AppState> { renderData(it) })
    }
    // Инициализируем адаптер и передаем его в RecyclerView
    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }
}