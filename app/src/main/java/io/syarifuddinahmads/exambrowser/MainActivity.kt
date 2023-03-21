package io.syarifuddinahmads.exambrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var editTextInputUrl:EditText
    private lateinit var btnSubmit:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initial component
        editTextInputUrl = findViewById(R.id.editText_url_input)
        btnSubmit = findViewById(R.id.btn_submit)

        // action component
        btnSubmit.setOnClickListener {
            if(!editTextInputUrl.text.equals("")){

            }else{
                Toast.makeText(this,"Inputkan URL...",Toast.LENGTH_LONG)
            }
        }

    }

    // set on back pressed
    override fun onBackPressed() {
        // super.onBackPressed()
        dialogConfirmCloseApp()
    }

    // set key down pressed
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_HOME){
            dialogConfirmCloseApp()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    // create dialog confirm for exit app
    private fun dialogConfirmCloseApp(){
        // set and create alert dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Keluar Aplikasi !")
        builder.setMessage("Yakin ingin keluar aplikasi ?")

        // set positive button
        builder.setPositiveButton("Ya") { _, _ ->
            moveTaskToBack(true)
            exitProcess(-1)
        }

        // set negative button
        builder.setNegativeButton("Tidak") { _, _ ->
            null
        }

        // set dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}