package ru.geekbrains.translator.view.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.search_history_dialog_fragment.*
import ru.geekbrains.translator.R
import ru.geekbrains.translator.utils.getEmptyString

class SearchHistoryDialogFragment : BottomSheetDialogFragment() {

    private lateinit var searchHistoryEditText: TextInputEditText
    private lateinit var searchHistoryclearTextImageView: ImageView
    private lateinit var searchHistoryButton: TextView
    private var onSearchHistoryClickListener: OnSearchClickListener? = null

    private val textWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (searchHistoryEditText.text != null && !searchHistoryEditText.text.toString().isEmpty()) {
                searchHistoryButton.isEnabled = true
                searchHistoryclearTextImageView.visibility = View.VISIBLE
            } else {
                searchHistoryButton.isEnabled = false
                searchHistoryclearTextImageView.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    private val onSearchButtonClickListener =
        View.OnClickListener {
            onSearchHistoryClickListener?.onClick(searchHistoryEditText.text.toString())
            dismiss()
        }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchHistoryClickListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_history_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchHistoryEditText = search_history_edit_text
        searchHistoryclearTextImageView = search_history_clear_text_imageview
        searchHistoryButton = search_history_button_textview

        searchHistoryButton.setOnClickListener(onSearchButtonClickListener)
        searchHistoryEditText.addTextChangedListener(textWatcher)
        addOnClearClickListener()
    }

    override fun onDestroyView() {
        onSearchHistoryClickListener = null
        super.onDestroyView()
    }

    private fun addOnClearClickListener() {
        searchHistoryclearTextImageView.setOnClickListener {
            searchHistoryEditText.setText(String.getEmptyString())
            searchHistoryButton.isEnabled = false
        }
    }

    interface OnSearchClickListener {

        fun onClick(searchWord: String)
    }

    companion object {
        fun newInstance(): SearchHistoryDialogFragment {
            return SearchHistoryDialogFragment()
        }
    }
}
