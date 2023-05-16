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
                dao.insert(new Exhibit(3, "Пёс Бобка", "Помимо памятника Сусанину, в центре есть «малая архитектурная форма» пожарному псу Бобке. Эта собака в 19 веке жила в пожарной охране и спасала жизни людям. Рядом с памятником расположен шар — копилка, куда каждый желающий может бросить монетку в качестве пожертвования Городскому Центру передержки животных. Загадайте желание и потрите нос Бобке, говорят, оно обязательно сбудется.", "test_image3", "test_audio3"));
                dao.insert(new Exhibit(4, "Историческая часть Костромы", "Историческая часть Костромы имеет радиально-полукольцовую планировку — от Сусанинской площади в разные стороны отходят улицы, словно лучики солнца. Существует легенда, что Екатерина II на вопрос, какой бы она хотела видеть Кострому, развернула свой веер. Так и выстроили улицы, по веерной планировке императрицы. По сей день, если взглянуть на Кострому с высоты, то кажется, что лежит огромный веер.", "test_image4", "test_audio4"));
                dao.insert(new Exhibit(5, "Ипатьевский монастырь", "Свято-Троицкий Ипатьевский монастырь впервые упоминается в летописях в 1432 году. Члены царской семьи почитали это место как свою фамильную святыню. В 1913 году Ипатьевский монастырь широко отмечал 300-летний юбилей династии Романовых. Празднование возглавил император Николай II. Поселили его в деревянном доме, специально построенном за стенами монастыря.", "test_image5", "test_audio5"));
                dao.insert(new Exhibit(6, "Памятник Сусанину", "Согласно легенде, зимой 1613 года в село Домнино был отправлен польско-литовский отряд, чтобы убить молодого царя Михаила Федоровича Романова. По дороге им встретился крестьянин Иван Сусанин, который согласился проводить поляков в Домнино. Вот только повел он их в леса дремучие, в болота непроходимые. А когда враги узнали, что проводник их обманул, они предали Сусанина лютой смерти.", "test_image6", "test_audio6"));
                dao.insert(new Exhibit(7, "Часовня", "На месте дома, где предположительно в деревне Деревеньки проживал Иван Сусанин с семьей, в настоящий момент стоит часовня", "test_image7", "test_audio7"));

            });
        }
    };
}
