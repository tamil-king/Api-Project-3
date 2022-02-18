package com.my.apiproject.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.my.apiproject.room.entity.ItemsEntity

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(itemsEntities: List<ItemsEntity>): LongArray

    @Query("select * from ItemsEntity")
    fun getDataFromDB(): List<ItemsEntity>

    @Query("select count(*) from ItemsEntity")
    fun getDataCount(): Boolean

    @Query("SELECT * FROM ItemsEntity WHERE name LIKE :query")
    fun getFilterData(query: String):List<ItemsEntity>
}