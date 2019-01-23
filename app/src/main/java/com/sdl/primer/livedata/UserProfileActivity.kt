package com.sdl.primer.livedata

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sdl.primer.R
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    private var count: Int = 1

    private val mViewModel: UserProfileViewModel by lazy {
        ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        mViewModel.init("1")

        mViewModel.user.observe(this, Observer<User>{ user ->
            mUserProfileTextView.text = user?.username
        })

    }

    fun updateData(view: View) {
        val user = User()
        count++
        user.username = "$count"
        mViewModel.user.value = user
    }

}
