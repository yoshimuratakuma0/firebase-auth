package com.free.data.models

import com.free.domain.entities.User
import com.google.firebase.auth.FirebaseUser

val FirebaseUser.entity
    get() = User(
        name = displayName ?: "",
        email = email ?: "",
        uid = uid
    )