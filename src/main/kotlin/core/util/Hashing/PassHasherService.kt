package com.example.core.util.Hashing

import org.mindrot.jbcrypt.BCrypt

class PassHasherService: PassHasher {

    override fun hashPassword(password: String): String{
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override fun verifyPassword(password: String, hashedPassword: String): Boolean{
        return BCrypt.checkpw(password, hashedPassword)
    }
}