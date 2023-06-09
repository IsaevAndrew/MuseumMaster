package edu.example.museummaster.data.data_sourses.category.room.root;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.example.museummaster.data.data_sourses.category.models.NewsElement;
import edu.example.museummaster.data.data_sourses.category.room.dao.NewsDao;

@Database(entities = {NewsElement.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    private static volatile NewsDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract NewsDao newsDao();

    public static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NewsDatabase.class, "news").addCallback(sRoomDatabaseCallback)
                    .build();
            //context.deleteDatabase("news");
        }
        return instance;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                NewsDao dao = instance.newsDao();
                dao.deleteAll();
                dao.insert(new NewsElement("Первая новость", "Сегодня была сделана вкладка новостей", "07.05.23"));
                dao.insert(new NewsElement("Расписание на 9 мая!", "Завтра наш музей работает с 10:00 до 18:00", "08.05.23"));
                dao.insert(new NewsElement("Закрытие!", "В период с 22.05.2023 до 29.05.2023 музей будет закрыт на реконструкцию", "18.05.23"));
            });
        }
    };
}
