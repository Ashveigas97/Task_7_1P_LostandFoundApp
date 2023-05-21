package com.example.lostandfoundapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "advert.db"

        private const val TABLE_ITEMS = "items"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_LOCATION = "location"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_ITEMS " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TYPE TEXT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_PHONE TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_DATE TEXT, " +
                "$COLUMN_LOCATION TEXT)"

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
        onCreate(db)
    }

    fun insertItem(type: String, name: String, phone: String, description: String, date: String, location: String): Long {
        val values = ContentValues()
        values.put(COLUMN_TYPE, type)
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_PHONE, phone)
        values.put(COLUMN_DESCRIPTION, description)
        values.put(COLUMN_DATE, date)
        values.put(COLUMN_LOCATION, location)

        val db = this.writableDatabase
        val id = db.insert(TABLE_ITEMS, null, values)
        db.close()

        return id
    }

    fun removeItem(name: String) {
        val db = this.writableDatabase
        db.delete(TABLE_ITEMS, "$COLUMN_NAME=?", arrayOf(name))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllItems(): List<String> {
        val itemList = mutableListOf<String>()
        val selectQuery = "SELECT * FROM $TABLE_ITEMS"

        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)

        cursor?.let {
            while (cursor.moveToNext()) {
                val itemName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                itemList.add(itemName)
            }
        }

        cursor?.close()
        db.close()

        return itemList
    }

    @SuppressLint("Range")
    fun getItemByName(name: String): Item? {
        val selectQuery = "SELECT * FROM $TABLE_ITEMS WHERE $COLUMN_NAME = ?"

        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, arrayOf(name))

        var item: Item? = null

        cursor?.let {
            if (cursor.moveToFirst()) {
                val itemId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val itemType = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                val itemName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val itemPhone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
                val itemDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val itemDate = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                val itemLocation = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION))

                item = Item(itemId, itemType, itemName, itemPhone, itemDescription, itemDate, itemLocation)
            }
        }

        cursor?.close()
        db.close()

        return item
    }

    data class Item(
        val id: Long,
        val type: String,
        val name: String,
        val phone: String,
        val description: String,
        val date: String,
        val location: String
    )

}
