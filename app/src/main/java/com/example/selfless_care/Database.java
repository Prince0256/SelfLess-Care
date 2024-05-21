package com.example.selfless_care;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Database {

    public FirebaseFirestore db;
    int login_result=0;

    public Database(Context context) {
        FirebaseApp.initializeApp(context);
        db=FirebaseFirestore.getInstance();
        Log.e("instance done","hello");

    }


    public void register(String username, String email, String password, String mobilenumber){
        Map<String, Object> user = new HashMap<>();
        user.put("Username", username);
        user.put("Email", email);
        user.put("Password", password);
        user.put("MobileNumber", mobilenumber);

        db.collection("USERS").document(username)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public CompletableFuture<QuerySnapshot> getData(String collection) {
        CompletableFuture<QuerySnapshot> future;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        } else {
            future = null;
        }

        db.collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            if (document!=null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    future.complete(document);
                                }
                                Log.i("data", document.toString());
                            }
//                            Log.i("data", document.getDocuments().toString());
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                future.completeExceptionally(task.getException());
                            }
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return future;
    }


    public CompletableFuture<Integer> login(String DB,String username, String password) {
        CompletableFuture<Integer> future;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        } else {
            future = null;
        }

        DocumentReference docRef = db.collection(DB).document(username);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && password.equals(document.get("Password"))) {
                    Log.d("TAG", document.get("Password") + "DocumentSnapshot data: " + password.equals(document.get("Password")));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        future.complete(1);
                    }
                } else {
                    Log.d("TAG", "No such document");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        future.complete(0);
                    }
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    future.complete(2);
                }
            }
        });

        return future;
    }

    public CompletableFuture<Integer> checkDetails(String DB,String username, String email,String mobileNumber) {
        CompletableFuture<Integer> future;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        } else {
            future = null;
        }

        DocumentReference docRef = db.collection(DB).document(username);
        Log.i("data",email+username+mobileNumber);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && email.equals(document.get("Email")) && mobileNumber.equals(document.get("MobileNumber")) ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        future.complete(1);
                    }
                } else {
                    Log.d("TAG", "No such document");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        future.complete(0);
                    }
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    future.complete(2);
                }
            }
        });

        return future;
    }
    public void Reset(String username, String password) {
        DocumentReference userDocumentRef = db.collection("USERS").document(username);

// Create a map to represent the field you want to update
        Map<String, Object> updates = new HashMap<>();
        updates.put("Password", password);

// Update the specific field in the document
        userDocumentRef.update(updates)
                .addOnSuccessListener(aVoid -> {
                    // Update successful
                    Log.d("TAG", "DocumentSnapshot successfully updated!");
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    Log.w("TAG", "Error updating document", e);
                });

    }

    public void Docregister(String username, String email, String password, String mobilenumber, String license, String degreecertificate) {
        Map<String, Object> user = new HashMap<>();
        user.put("Username", username);
        user.put("Email", email);
        user.put("Password", password);
        user.put("MobileNumber", mobilenumber);
        user.put("license",license);
        user.put("degreecertificate",degreecertificate);

        db.collection("DOC USERS").document(username)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}
