package com.example.my_library

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ValidatorSpinner (context: Context, attrs: AttributeSet?): ConstraintLayout(context, attrs){
    private var atributes: TypedArray =
        context.obtainStyledAttributes(attrs, R.styleable.ValidatorSpinner)
    private val spinner: Spinner
    private val textView: TextView

    init {
        inflate(context, R.layout.validator_spinner, this)

        spinner = findViewById(R.id.spinner)
        textView = findViewById(R.id.text_view)

        val hint = atributes.getString(R.styleable.ValidatorSpinner_hint)
        textView.text = hint
    }

    fun setHint(hint: String) {
        textView.text = hint
    }

    fun setError(msg: String) {
        textView.text = msg
        textView.visibility = View.VISIBLE
        textView.setTextColor(Color.RED)
        textView.setCompoundDrawablesWithIntrinsicBounds(0,
            0,
            R.drawable.ic_baseline_error_24,
            0)
    }

    fun setAdapter(adapter: SpinnerAdapter) {
        spinner.adapter = adapter
        var count = 0
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (count == 0) {
                    textView.visibility = View.VISIBLE
                    count++
                } else {
                    textView.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }


    fun getAdapter(): SpinnerAdapter {
        return spinner.adapter
    }


    fun getSelectedItem(): Any? {
        return if (textView.visibility == View.GONE)
            spinner.selectedItem
        else
            null
    }

    fun getSelectedItemPosition(): Int? {
        return if (textView.visibility == View.GONE)
            spinner.selectedItemPosition
        else
            null
    }

    fun getSelectedItemId(): Long? {
        return if (textView.visibility == View.GONE)
            spinner.selectedItemId
        else
            null
    }

    fun isItemSelected(): Boolean {
        if (textView.visibility == View.VISIBLE){
            setError("< Please select one >")
        }
        return textView.visibility == View.GONE
    }

    fun isSelected(boolean: Boolean) {
        if (boolean) {
            textView.visibility = View.GONE
        } else textView.visibility = View.VISIBLE

    }
}