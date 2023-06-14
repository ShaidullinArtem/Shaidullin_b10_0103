package com.example.shaidullin_b10_0103

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.shaidullin_b10_0103.types.UserType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LoginActivity : AppCompatActivity() {

    private var users = ArrayList<UserType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val navToRegisterBtn = findViewById<Button>(R.id.navToRegisterBtn)

        navToRegisterBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        val username = findViewById<EditText>(R.id.loginUsernameEdit).text
        val password = findViewById<EditText>(R.id.loginPasswordEdit).text

        val loginBtn = findViewById<Button>(R.id.submitLoginBtn);

        loginBtn.setOnClickListener {
            if (username.isNullOrEmpty() || password.isNullOrEmpty())
            {
                Toast.makeText(this, "Enter all data", Toast.LENGTH_LONG).show()
            } else {
                checkUserData("${username}", "${password}")
            }
        }
    }

    private fun checkUserData(username: String, password: String) {

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val gson = Gson()

        val json = sharedPreferences.getString("usersData", null)
        val type = object: TypeToken<ArrayList<UserType>>(){}.type

        users = gson.fromJson(json, type)

        if (users == null) {
            users = ArrayList<UserType>()
        } else {
            for (i in users) {
                if (username == i.username && password == i.password) {
                    Toast.makeText(this, "Successful login", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

}