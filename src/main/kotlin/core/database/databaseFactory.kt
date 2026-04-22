package com.example.core.database

import com.zaxxer.hikari.HikariConfig
import javax.sql.DataSource

class DatabaseFactory(){

   private lateinit var dataSource: DataSource

    fun init (){
        val config: HikariConfig = HikariConfig()

    }

}