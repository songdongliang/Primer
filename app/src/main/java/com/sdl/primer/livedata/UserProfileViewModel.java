package com.sdl.primer.livedata;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class UserProfileViewModel extends ViewModel {

    private String userId;

    private MutableLiveData<User> user = new MutableLiveData<>();

    public void init(String userId) {
        user.setValue(new User(userId, ""));
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}
