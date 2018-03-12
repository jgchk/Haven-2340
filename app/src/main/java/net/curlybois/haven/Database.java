package net.curlybois.haven;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import net.curlybois.haven.model.Shelter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jake on 3/10/18.
 */

public class Database {

    private static final String TAG = Database.class.getSimpleName();

    public interface DatabaseListener {
        void onSheltersRetrieved();

        void onShelterRetrievalFailed();
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Shelter> shelters = new ArrayList<>();
    private final DatabaseListener listener;

    public Database(final DatabaseListener listener) {
        this.listener = listener;
        db.collection("shelters")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                                shelters.add(dataToShelter(doc.getData()));
                            }
                            listener.onSheltersRetrieved();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            // TODO: show error in UI
                            listener.onShelterRetrievalFailed();
                        }
                    }
                });
    }

    public List<Shelter> getShelters() {
        return shelters;
    }

    private Shelter dataToShelter(Map<String, Object> data) {
        String name = data.get("name").toString();
        int capacity = Integer.valueOf(data.get("capacity").toString());
        String restrictions = data.get("restrictions").toString();
        GeoPoint location = (GeoPoint) data.get("location");
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String address = data.get("address").toString();
        ArrayList<String> notes = (ArrayList<String>) data.get("notes");
        String phone = data.get("phone").toString();
        return new Shelter(name, capacity, restrictions, longitude, latitude, address, notes, phone);
    }
}
