package edu.iastate.graysonc.fastfood.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import edu.iastate.graysonc.fastfood.database.converter.DateConverter;
import edu.iastate.graysonc.fastfood.database.dao.FoodDAO;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.database.dao.UserDAO;

@Database(entities = {User.class, Food.class}, version = 3)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {
    // --- SINGLETON ---
    private static volatile MyDatabase INSTANCE;

    // --- DAO ---
    public abstract UserDAO userDAO();
    public abstract FoodDAO foodDAO();
}