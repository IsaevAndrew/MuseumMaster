package edu.example.museummaster.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import edu.example.museummaster.R;

public class Ticket extends Fragment {
    private Context context;
    Fragment fragment14;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
        bottomNavigationView.setSelectedItemId(R.id.ticket);
        Button btnBuyTicket = view.findViewById(R.id.purchaseButton);
        Spinner spinner = view.findViewById(R.id.tariffSpinner);
        // Создаем объект DatePicker и находим его элемент в макете
        DatePicker datePicker = view.findViewById(R.id.datePicker);

// Устанавливаем минимальную дату, которую может выбрать пользователь, на сегодняшний день
        datePicker.setMinDate(Calendar.getInstance().getTimeInMillis());

        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем выбранный тариф билета и дату из выбирающего круга и датапикера
                String tariff = spinner.getSelectedItem().toString();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                new AlertDialog.Builder(getActivity())
                        .setTitle("Билет куплен")
                        .setMessage(String.format("Тариф: %s\nДата: %d.%d.%d", tariff, day, month+1, year))
                        .setPositiveButton("Ок!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Обработка нажатия кнопки "Закрыть"
                                spinner.setSelection(0);
                                // Сброс выбранной даты
                                datePicker.updateDate(Calendar.getInstance().get(Calendar.YEAR),
                                        Calendar.getInstance().get(Calendar.MONTH),
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    fragment14 = new Home();
                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                    transaction1.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction1.commit();
                    return true;
                case R.id.search:
                    fragment14 = new Search();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction2.commit();
                    return true;
                case R.id.ticket:
                    return true;
                case R.id.scanner:
                    fragment14 = new Scanner();
                    FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                    transaction4.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction4.commit();
                    return true;
                case R.id.profile:
                    fragment14 = new Profile();
                    FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                    transaction5.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction5.commit();
                    return true;
            }
            return false;
        });
        return view;
    }
    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            requireActivity().moveTaskToBack(true);
        }
    };
}
