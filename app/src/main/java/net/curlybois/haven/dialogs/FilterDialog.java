package net.curlybois.haven.dialogs;

import android.annotation.SuppressLint;
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
import net.curlybois.haven.adapters.FilterQuery;
import net.curlybois.haven.interfaces.FilterDialogListener;
import net.curlybois.haven.model.Shelter;

import java.io.Serializable;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A dialog for choosing filtering options
 */
public class FilterDialog extends DialogFragment {

    private static final String FILTER_QUERY = "filterQuery";

    @BindView(R.id.gender_rg) private RadioGroup gender_rg;
    @BindView(R.id.age_rg) private RadioGroup age_rg;
    @BindView(R.id.veteran_chb) private CheckBox veteran_chb;

    private FilterDialogListener listener;

    /**
     * Create a new FilterDialog
     * @param filterQuery the current filter query
     * @return the new FilterDialog
     */
    public static FilterDialog newInstance(Serializable filterQuery) {
        Bundle args = bundleArgs(filterQuery);
        FilterDialog fragment = new FilterDialog();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Put the FilterQuery in a Bundle
     * @param filterQuery the current filter query
     * @return the bundled filter query
     */
    public static Bundle bundleArgs(Serializable filterQuery) {
        Bundle args = new Bundle();
        args.putSerializable(FILTER_QUERY, filterQuery);
        return args;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Views setup
        AlertDialog.Builder builder = new AlertDialog.Builder(
                Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.dialog_filters, null);
        ButterKnife.bind(this, v);

        // Listeners setup
        gender_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                listener.setGender(idToGender(checkedId));
            }
        });
        age_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                listener.setAge(idToAge(checkedId));
            }
        });
        veteran_chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.setVeterans(isChecked);
            }
        });

        // Load data from bundle
        Bundle args = getArguments();
        FilterQuery filterQuery = (FilterQuery) Objects.requireNonNull(args)
                .getSerializable(FILTER_QUERY);
        gender_rg.check(genderToId(Objects.requireNonNull(filterQuery).getGender()));
        age_rg.check(ageToId(filterQuery.getAge()));
        veteran_chb.setChecked(filterQuery.isVeterans());

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

    private static int genderToId(Shelter.Gender gender) {
        if (gender == Shelter.Gender.MEN) {
            return R.id.male_rb;
        } else if (gender == Shelter.Gender.WOMEN) {
            return R.id.female_rb;
        }
        return R.id.gender_none_rb;
    }

    private static Shelter.Gender idToGender(int id) {
        if (id == R.id.male_rb) {
            return Shelter.Gender.MEN;
        } else if (id == R.id.female_rb) {
            return Shelter.Gender.WOMEN;
        }
        return null;
    }

    private static int ageToId(Shelter.Age age) {
        switch (age) {
            case FAMILIES:
                return R.id.families_rb;
            case FAMILIES_NEWBORNS:
                return R.id.newborns_rb;
            case CHILDREN:
                return R.id.children_rb;
            case YOUNG_ADULTS:
                return R.id.young_adults_rb;
            case ANYONE:
                return R.id.anyone_rb;
        }
        return R.id.gender_none_rb;
    }

    private static Shelter.Age idToAge(int id) {
        switch (id) {
            case R.id.families_rb:
                return Shelter.Age.FAMILIES;
            case R.id.newborns_rb:
                return Shelter.Age.FAMILIES_NEWBORNS;
            case R.id.children_rb:
                return Shelter.Age.CHILDREN;
            case R.id.young_adults_rb:
                return Shelter.Age.YOUNG_ADULTS;
            case R.id.anyone_rb:
                return Shelter.Age.ANYONE;
        }
        return null;
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
