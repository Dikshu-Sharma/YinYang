package com.soulharmony.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {

    public static final String PROFILE_PICTURE_INDEX = "0";
    public static final String ID_SEPARATOR = "*_*";
    public static String DEFAULT_IMAGE = "https://firebasestorage.googleapis.com/v0/b/yinyang-9f595.appspot.com/o/userImages%2Fanonymous-woman-black-silhouette-with-question-mark-incognito-female-person-vector-illustration_261737-807.avif?alt=media&token=4b929e14-7a04-4b0a-bf8e-aba70ae05f2b";
    public static User DUMMY_USER = new User(null, null, null, null, null,
            null, null, null, null, null, new HashMap<>(), new ArrayList<>());
}
