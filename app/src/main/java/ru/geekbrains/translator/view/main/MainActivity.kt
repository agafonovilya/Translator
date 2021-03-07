package ru.geekbrains.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.geekbrains.core.BaseActivity
import ru.geekbrains.translator.R
import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.translator.di.injectDependencies
import ru.geekbrains.translator.utils.convertMeaningsToString
import ru.geekbrains.utils.network.isOnline
import ru.geekbrains.translator.view.description.DescriptionActivity
import ru.geekbrains.translator.view.main.adapter.MainAdapter

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
private const val HISTORY_ACTIVITY_PATH = "ru.geekbrains.historyscreen.view.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyscreen"

class MainActivity : BaseActivity<AppState>() {

    override lateinit var viewModel: MainViewModel
    private lateinit var splitInstallManager: SplitInstallManager
    private lateinit var appUpdateManager: AppUpdateManager

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init ViewModel
        check(main_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        val vm: MainViewModel by viewModel()
        injectDependencies()
        viewModel = vm
        viewModel.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })

        //Init views
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
                // Создаём менеджер
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                // Создаём запрос на создание экрана
                val request =
                    SplitInstallRequest
                        .newBuilder()
                        .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                        .build()

                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }
}