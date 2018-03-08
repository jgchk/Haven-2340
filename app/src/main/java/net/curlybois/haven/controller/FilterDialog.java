package net.curlybois.haven.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import net.curlybois.haven.R;
import net.curlybois.haven.model.Shelter;

import static net.curlybois.haven.model.Shelter.AGE;
import static net.curlybois.haven.model.Shelter.GENDER;
import static net.curlybois.haven.model.Shelter.VETERAN;

/**
 * Created by jake on 3/7/18.
 */

public class FilterDialog extends DialogFragment {

    public static final String TAG = FilterDialog.class.getSimpleName();

    public interface FilterDialogListener {
        void setGender(Shelter.Gender gender);

        void setAge(Shelter.Age age);

        void setVeteran(boolean veteran);

        void applyFilters();
    }

    private FilterDialogListener listener;

    public static FilterDialog newInstance(Shelter.Gender gender, Shelter.Age age, boolean veteran) {
        Bundle args = bundleFilters(gender, age, veteran);
        FilterDialog fragment = new FilterDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static Bundle bundleFilters(Shelter.Gender gender, Shelter.Age age, boolean veteran) {
        Bundle args = new Bundle();
        args.putSerializable(GENDER, gender);
        args.putSerializable(AGE, age);
        args.putBoolean(VETERAN, veteran);
        return args;
    }

    private static int genderToId(Shelter.Gender gender) {
        switch (gender) {
            case MEN:
                return R.id.male_gender;
            case WOMEN:
                return R.id.female_gender;
            case NONE:
                return R.id.none_gender;
        }
        return -1;
    }

    private static Shelter.Gender idToGender(int id) {
        switch (id) {
            case R.id.male_gender:
                return Shelter.Gender.MEN;
            case R.id.female_gender:
                return Shelter.Gender.WOMEN;
            case R.id.none_gender:
                return Shelter.Gender.NONE;
        }
        return null;
    }

    private static int ageToId(Shelter.Age age) {
        switch (age) {
            case FAMILIES:
                return R.id.families_age;
            case FAMILIES_NEWBORNS:
                return R.id.newborns_age;
            case CHILDREN:
                return R.id.children_age;
            case YOUNG_ADULTS:
                return R.id.young_adults_age;
            case ANYONE:
                return R.id.anyone_age;
            case NONE:
                return R.id.none_age;
        }
        return -1;
    }

    private static Shelter.Age idToAge(int id) {
        switch (id) {
            case R.id.families_age:
                return Shelter.Age.FAMILIES;
            case R.id.newborns_age:
                return Shelter.Age.FAMILIES_NEWBORNS;
            case R.id.children_age:
                return Shelter.Age.CHILDREN;
            case R.id.young_adults_age:
                return Shelter.Age.YOUNG_ADULTS;
            case R.id.anyone_age:
                return Shelter.Age.ANYONE;
            case R.id.none_age:
                return Shelter.Age.NONE;
        }
        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_filters, null);

        RadioGroup genderGroup = v.findViewById(R.id.gender_group);
        RadioGroup ageGroup = v.findViewById(R.id.age_group);
        CheckBox veteranCheckBox = v.findViewById(R.id.veteran_checkbox);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                listener.setGender(idToGender(checkedId));
            }
        });
        ageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                listener.setAge(idToAge(checkedId));
            }
        });
        veteranCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.setVeteran(isChecked);
            }
        });

        Bundle args = getArguments();
        Shelter.Gender gender = (Shelter.Gender) args.getSerializable(GENDER);
        Shelter.Age age = (Shelter.Age) args.getSerializable(AGE);
        boolean veteran = args.getBoolean(VETERAN);
        genderGroup.check(genderToId(gender));
        ageGroup.check(ageToId(age));
        veteranCheckBox.setChecked(veteran);

        builder.setView(v)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.applyFilters();
                        FilterDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (FilterDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement FilterDialogListener");
        }
    }
}
