package ru.geekbrains.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.geekbrains.translator.R
import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.translator.utils.convertMeaningsToString
import ru.geekbrains.utils.network.isOnline
import ru.geekbrains.translator.view.description.DescriptionActivity
import ru.geekbrains.historyscreen.view.HistoryActivity
import ru.geekbrains.translator.view.main.adapter.MainAdapter

class MainActivity : ru.geekbrains.core.BaseActivity<AppState>() {

    override lateinit var viewModel: MainViewModel

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val fabClickListener: android.view.View.OnClickListener =
        android.view.View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
            object : MainAdapter.OnListItemClickListener {
                override fun onItemClick(data: DataModel) {
                    startActivity(
                        DescriptionActivity.getIntent(
                            this@MainActivity,
                            data.text!!,
                            convertMeaningsToString(data.meanings!!),
                            data.meanings!![0].imageUrl
                        )
                    )

                }
            }
    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
            object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    isNetworkAvailable = isOnline(applicationContext)
                    if (isNetworkAvailable) {
                        viewModel.getData(searchWord, isNetworkAvailable)
                    } else {
                        showNoInternetConnectionDialog()
                    }
                }
            }

    private val onHistorySearchClickListener: ru.geekbrains.historyscreen.view.SearchHistoryDialogFragment.OnSearchClickListener =
        object : ru.geekbrains.historyscreen.view.SearchHistoryDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                viewModel.getData(searchWord, false)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        check(main_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        val vm: MainViewModel by viewModel()
        viewModel = vm
        viewModel.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })

        search_fab.setOnClickListener(fabClickListener)
        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        main_activity_recyclerview.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            R.id.menu_history_find -> {
                val searchHistoryDialogFragment = ru.geekbrains.historyscreen.view.SearchHistoryDialogFragment.newInstance()
                searchHistoryDialogFragment.setOnSearchClickListener(onHistorySearchClickListener)
                searchHistoryDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}