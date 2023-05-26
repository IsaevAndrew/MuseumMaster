package edu.example.museummaster.ui.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import edu.example.museummaster.data.data_sourses.category.repositories.AuthAppRepository;

public class AuthViewModel extends ViewModel {
    public AuthAppRepository authAppRepository;
    public MutableLiveData<FirebaseUser> userLiveData;
    public MutableLiveData<String> errorMessageLiveData;
    private MutableLiveData<AuthState> authStateLiveData;

    public AuthViewModel() {
        authAppRepository = new AuthAppRepository();
        userLiveData = authAppRepository.getUserLiveData();
        errorMessageLiveData = authAppRepository.getErrorMessageLiveData();
        authStateLiveData = authAppRepository.getAuthStateLiveData();
    }
    public MutableLiveData<AuthState> getAuthStateLiveData() {
        return authStateLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public void registerUser(String email, String password) {
        authAppRepository.registerUser(email, password);
    }

    public void loginUser(String email, String password) {
        authAppRepository.loginUser(email, password);
    }

    public void logoutUser() {
        authAppRepository.logoutUser();
    }

    public void deleteAccount() {
        authAppRepository.deleteAccount();
    }

    public void changePassword(String currentPassword, String newPassword) {
        authAppRepository.changePassword(currentPassword, newPassword);
    }
}
