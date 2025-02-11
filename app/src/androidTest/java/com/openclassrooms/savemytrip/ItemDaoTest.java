package com.openclassrooms.savemytrip;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.savemytrip.database.SaveMyTripDatabase;
import com.openclassrooms.savemytrip.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ItemDaoTest {

    //FOR DATA
    private SaveMyTripDatabase database;

    //DATA SET FOR TEST
    private static long USER_ID = 1;
    private static User USER_DEMO = new User(USER_ID, "Phillipe", "https://www.google.fr, ");

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        this.database = Room.inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getContext(),
                    SaveMyTripDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertAndGetUser() throws InterruptedException {
        //BEFORE : Adding a new user
        this.database.userDao().createUser(USER_DEMO);
        //TEST
        User user = LiveDataTestUtil.getValue(this.database.userDao().getUser(USER_ID));
        assertTrue(user.getUsername().equals(USER_DEMO.getUsername()) && user.getId() == USER_ID);
    }
}
