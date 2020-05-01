package com.challenge.ui

import com.challenge.R

class MainActivity : BaseActivity() {

    override fun getLayoutById() = R.layout.activity_main
    private val gifListFragment = ReviewListFragment()

    override fun initUI() {
        supportFragmentManager.beginTransaction().add(R.id.container, gifListFragment).commit()
    }
}
