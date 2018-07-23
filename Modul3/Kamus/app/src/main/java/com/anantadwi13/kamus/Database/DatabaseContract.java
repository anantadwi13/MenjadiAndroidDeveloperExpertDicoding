package com.anantadwi13.kamus.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME_IND_ENG = "ind_eng",
            TABLE_NAME_ENG_IND = "eng_ind";

    static class KamusCol implements BaseColumns{
        static String KATA = "kata",
                TERJEMAHAN = "terjemahan";
    }
}
