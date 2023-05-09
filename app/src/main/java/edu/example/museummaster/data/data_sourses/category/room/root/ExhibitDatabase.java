package edu.example.museummaster.data.data_sourses.category.room.root;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.example.museummaster.data.data_sourses.category.models.Exhibit;
import edu.example.museummaster.data.data_sourses.category.room.dao.ExhibitDao;

@Database(entities = {Exhibit.class}, version = 1)
public abstract class ExhibitDatabase extends RoomDatabase {
    public abstract ExhibitDao exhibitDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile ExhibitDatabase INSTANCE;

    public static ExhibitDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExhibitDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ExhibitDatabase.class, "exhibits-db_test3").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ExhibitDao dao = INSTANCE.exhibitDao();
                dao.insert(new Exhibit(1, "История Костромы","Каменная застройка Костромы проводилась по градостроительному плану, принятому после грандиозного пожара 1773 года, уничтожившего большую часть города. В соответствии с ним улицы спрямили и дома стали возводить по красной линии застройки.", "test_image", "test_audio"));
                dao.insert(new Exhibit(2, "Центр Костромы", "Центр Костромы — это огромная Сусанинская площадь, которая раскинулась по обеим сторонам Советской улицы. Её северо-восточная часть в народе ласково зовется «Сковородкой». В самом центре «Сковородки», по аналогии с другими областными центрами, находится нулевой меридиан.", "test_image2", "test_audio2"));
            });
        }
    };
}
