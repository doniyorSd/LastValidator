package com.example.my_library

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout

class DoubleEditText(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var atributes: TypedArray
    private var firstEditText: EditText
    private var secondEditText: EditText
    private var firstPassword: String
    private var secondPassword: String
    private val isPassword: Boolean

    init {
        inflate(context, R.layout.edittext_password, this)
        atributes = context.obtainStyledAttributes(attrs, R.styleable.DoubleEditText)
        firstEditText = findViewById(R.id.first_et)
        secondEditText = findViewById(R.id.second_et)
        firstPassword = firstEditText.text.toString()
        secondPassword = secondEditText.text.toString()
        isPassword =
            atributes.getBoolean(R.styleable.DoubleEditText_passwordEnabled, true)
        setUp()
    }

    private fun setUp() {
        val maxLength = atributes.getInt(R.styleable.DoubleEditText_maxLength, 25)
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = InputFilter.LengthFilter(maxLength)
        firstEditText.filters = fArray
        secondEditText.filters = fArray
        if (!isPassword) {
            firstEditText.inputType = InputType.TYPE_CLASS_TEXT
            secondEditText.inputType = InputType.TYPE_CLASS_TEXT
            firstEditText.hint = "enter text"
            secondEditText.hint = "enter text"
        } else {
            firstEditText.hint = "enter password"
            secondEditText.hint = "re-enter this password"
        }

        secondEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0.toString()
                        .isNotEmpty() && p0.toString().length == firstEditText.text.toString().length
                ) {
                    if (p0.toString() == firstEditText.text.toString() && isPassword) {
                        secondEditText.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_check_circle_24,
                            0
                        )
                    } else if (p0.toString() != firstEditText.text.toString() && isPassword) {
                        secondEditText.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_error_24,
                            0
                        )
                    }
                } else {
                    secondEditText.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }


    fun getFirstText(): String {
        firstPassword = firstEditText.text.toString()
        return firstPassword
    }

    fun getSecondText(): String {
        secondPassword = secondEditText.text.toString()
        return secondPassword
    }

    fun isCompatible(): Boolean {
        return (getFirstText() == getSecondText())
    }

    fun setErrorWithDrawable() {
        if (isPassword)
            secondEditText.error = "Please re-enter password"
        secondEditText.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_baseline_error_24,
            0
        )
    }

    fun setErrorWithDrawable(msg: String) {
        secondEditText.error = msg
        secondEditText.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_baseline_error_24,
            0
        )
    }

    fun clear() {
        firstEditText.text.clear()
        secondEditText.text.clear()
    }

    fun isNotEmpty(): Boolean {
        return (firstEditText.text.isNotEmpty() && secondEditText.text.isNotEmpty())
    }

    fun checkTextAndSetError() {
        if (isPassword && !isCompatible() || firstEditText.text.isEmpty()) {
            setErrorWithDrawable()
        } else if ((firstEditText.text.isEmpty() || secondEditText.text.isEmpty()))
            setErrorWithDrawable("Please re-enter")
        else {
            secondEditText.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_check_circle_24,
                0
            )
        }
    }
}