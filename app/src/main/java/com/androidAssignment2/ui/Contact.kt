package com.androidAssignment2.ui

import java.io.Serializable

data class Contact(val imageURL: String = "", val name: String = "", val career: String = "") :
    Serializable
