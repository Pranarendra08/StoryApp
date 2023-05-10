package com.example.storyapp.customview

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.example.storyapp.R
import com.example.storyapp.helper.isEmailValid

class EmailEditText: AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val email = p0.toString()
                when {
                    email.isEmpty() -> {
                        error = context.getString(R.string.error_email_blank)
                    }
                    !email.isEmailValid() -> {
                        error = context.getString(R.string.error_email_invalid)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing.
            }

        })
    }

//    fun String.isEmailValid(): Boolean  {
//        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
//    }
}