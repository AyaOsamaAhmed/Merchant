package com.rbt.merchant.presentation.ui

interface ShowPinedComponent {
    fun showNavBottom(check : Boolean)
    fun showNavDrawer(check : Boolean)
    fun showToolBar(check : Boolean)
    fun showFragmentTitle(check : Boolean,titleResource:Int?)
    fun showProfileImage(check : Boolean)
    fun showListImage(check : Boolean)
}