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

        // Добавьте слушатель состояния аутентификации Firebase
        firebaseAuth.addAuthStateListener(auth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                // Пользователь аутентифицирован
                userLiveData.postValue(user);
                authStateLiveData.postValue(AuthState.AUTHENTICATED);
            } else {
                // Пользователь не аутентифицирован
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
                        // Регистрация успешна, пользователь аутентифицирован
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        userLiveData.postValue(user);
                        authStateLiveData.postValue(AuthState.AUTHENTICATED);
                    } else {
                        // Возникла ошибка при регистрации
                        errorMessageLiveData.postValue(task.getException().getMessage());
                    }
                });
    }

    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Авторизация успешна, пользователь аутентифицирован
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        userLiveData.postValue(user);
                        authStateLiveData.postValue(AuthState.AUTHENTICATED);
                    } else {
                        // Возникла ошибка при авторизации
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
                            // Учетная запись успешно удалена
                            userLiveData.postValue(null);
                            authStateLiveData.postValue(AuthState.UNAUTHENTICATED);
                        } else {
                            // Возникла ошибка при удалении учетной записи
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
                                            // Пароль успешно изменен
                                        } else {
                                            errorMessageLiveData.postValue(passwordUpdateTask.getException().getMessage());
                                        }
                                    });
                        } else {
                            // Возникла ошибка при подтверждении аутентификации
                            errorMessageLiveData.postValue(task.getException().getMessage());
                        }
                    });
        }
    }
}
