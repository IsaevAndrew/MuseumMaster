package edu.example.museummaster.data.data_sourses.category.repositories;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import edu.example.museummaster.ui.viewmodels.AuthState;

public class AuthAppRepository {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<String> errorMessageLiveData;
    private MutableLiveData<AuthState> authStateLiveData;

    public AuthAppRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();
        authStateLiveData = new MutableLiveData<>();
        firebaseAuth.addAuthStateListener(auth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                userLiveData.postValue(user);
                authStateLiveData.postValue(AuthState.AUTHENTICATED);
            } else {
                userLiveData.postValue(null);
                authStateLiveData.postValue(AuthState.UNAUTHENTICATED);
            }
        });
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
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        userLiveData.postValue(user);
                        authStateLiveData.postValue(AuthState.AUTHENTICATED);
                    } else {
                        errorMessageLiveData.postValue(task.getException().getMessage());
                    }
                });
    }

    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        userLiveData.postValue(user);
                        authStateLiveData.postValue(AuthState.AUTHENTICATED);
                    } else {
                        errorMessageLiveData.postValue(task.getException().getMessage());
                    }
                });
    }

    public void logoutUser() {
        firebaseAuth.signOut();
    }

    public void deleteAccount() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(null);
                            authStateLiveData.postValue(AuthState.UNAUTHENTICATED);
                        } else {
                            errorMessageLiveData.postValue(task.getException().getMessage());
                        }
                    });
        }
    }

    public void changePassword(String currentPassword, String newPassword) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(passwordUpdateTask -> {
                                        if (passwordUpdateTask.isSuccessful()) {
                                        } else {
                                            errorMessageLiveData.postValue(passwordUpdateTask.getException().getMessage());
                                        }
                                    });
                        } else {
                            errorMessageLiveData.postValue(task.getException().getMessage());
                        }
                    });
        }
    }
}
