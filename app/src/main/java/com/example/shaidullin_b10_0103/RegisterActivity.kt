package com.example.shaidullin_b10_0103

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.shaidullin_b10_0103.types.UserType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegisterActivity : AppCompatActivity() {

    private var users = ArrayList<UserType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val navToLoginBtn = findViewById<Button>(R.id.navToLoginBtn)

        navToLoginBtn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        val username = findViewById<EditText>(R.id.registerUsernameEdit).text
        val password = findViewById<EditText>(R.id.registerPasswordEdit).text

        val registerBtn = findViewById<Button>(R.id.submitRegisterBtn)

        registerBtn.setOnClickListener {
            val exist: Boolean = checkUserExist("${username}", "${password}")
            if (username.isNullOrEmpty() || password.isNullOrEmpty() || exist) {
                Toast.makeText(this, "Enter all data", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Success register user ${username}", Toast.LENGTH_LONG).show()
                saveUserData("${username}", "${password}")
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveUserData(username: String, password: String) {

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        users.add(UserType(username, password))
        val json: String = gson.toJson(users)

        editor.putString("usersData", json)
        editor.apply()
    }

    private fun checkUserExist(username: String, password: String): Boolean {

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val gson = Gson()

        val json = sharedPreferences.getString("usersData", null)
        val type = object: TypeToken<ArrayList<UserType>>(){}.type

        users = gson.fromJson(json, type)

        if (users == null) {
            users = ArrayList<UserType>()
            return false
        } else {
            for (i in users) {
                if (username == i.username && password == i.password) {
                    Toast.makeText(this, "User already exist", Toast.LENGTH_SHORT).show()
                    return true
                } else {
                    Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }

        return false
    }
}