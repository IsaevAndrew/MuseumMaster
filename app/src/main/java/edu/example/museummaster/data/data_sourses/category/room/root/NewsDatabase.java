package edu.example.museummaster.data.data_sourses.category.room.root;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.example.museummaster.data.data_sourses.category.models.NewsElement;
import edu.example.museummaster.data.data_sourses.category.room.dao.NewsElementDao;

@Database(entities = {NewsElement.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    private static volatile NewsDatabase INSTANCE;

    public abstract NewsElementDao newsDao();

    public static final ExecutorService databaseWriteExecutor =
            Executors.newSingleThreadExecutor();

    public static NewsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NewsDatabase.class, "news_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
