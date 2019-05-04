package cn.bestlang.pwstore.activity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import cn.bestlang.pwstore.R
import cn.bestlang.pwstore.core.Pw
import cn.bestlang.pwstore.core.PwStore
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_pw.*

class CreatePwActivity : AppCompatActivity() {

    private lateinit var store : PwStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pw)

        initStore()
        initView()
    }

    private fun initStore() {
        store = PwStore.getInstance(applicationContext)
    }

    private fun initView() {
        saveButton.setOnClickListener { saveToPwStore() }
    }

    private fun saveToPwStore() {
        if (notEmpty(accountEditor, pwEditor)) {
            ioThread {
                store.repo.add(Pw(
                    name = nameEditor.text.toString(),
                    account = accountEditor.text.toString(),
                    pw = pwEditor.text.toString(),
                    desc = descEditor.text.toString()
                ))
            }
        }
    }

    private fun notEmpty(vararg editors: EditText): Boolean {
        val editText = editors.find { it.text.trim().isBlank() }
        return if (editText != null) {
            Snackbar.make(editText, "账号密码不能为空", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            false
        } else {
            true
        }
    }


}
