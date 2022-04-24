package com.zaheer.quizbackend.services.interfaces;

public interface CurrentUserService {
    String getLoggedInUserEmail();

    boolean isLoggedInUserAdmin();
}
