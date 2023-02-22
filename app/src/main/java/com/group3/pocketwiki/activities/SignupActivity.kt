package com.group3.pocketwiki.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.group3.pocketwiki.R
import org.w3c.dom.Text
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    private lateinit var personName: EditText
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var confirmpass: EditText
    private lateinit var signupBtn: Button
    private lateinit var signinString: TextView

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()

        personName = findViewById(R.id.personName)
        email = findViewById(R.id.email)
        pass = findViewById(R.id.pass)
        confirmpass = findViewById(R.id.confirmpass)
        signupBtn = findViewById(R.id.signupBtn)
        signinString = findViewById(R.id.singninString)

        signinString.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signupBtn.setOnClickListener {
            val email_text = email.text.toString()
            val pass_text = pass.text.toString()
            val confirmpass_text = confirmpass.text.toString()
            val personName_text = personName.text.toString()

            if (pass_text != confirmpass_text) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text) || TextUtils.isEmpty(confirmpass_text) || TextUtils.isEmpty(personName_text)) {
                Toast.makeText(this, "Empty fields not allowed!", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email_text)) {
                Toast.makeText(this, "Email is of invalid format!", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email_text, pass_text).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successfully Signed-Up! You can now login!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Unable to Sign-Up :(", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        var isValid = false
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val inputStr: CharSequence = email
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }
}