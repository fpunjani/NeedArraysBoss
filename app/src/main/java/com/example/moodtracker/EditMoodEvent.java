package com.example.moodtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * This is an activity that handles editing for existing moods
 */
public class EditMoodEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EmojiEditText et;
    private Spinner feelingSpinner, socialStateSpinner;
    private int index;      // holds the index of the event in the db that is being edited
    private String feeling = "", socialState = "";

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference dataRef;
    private Mood mood;
    private ArrayList<Mood> moodHistory;

    private ArrayList<String> moods;
    private ArrayList<String> socialStates;

    /**
     * Used to create the activity
     * @param  savedInstanceState
     * The savedInstanceState is a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood_event);

        //set up emoji compatibility
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);

        et = findViewById(R.id.reasonET2);
        feelingSpinner = findViewById(R.id.editMoodFeelingSpinner);
        feelingSpinner.setOnItemSelectedListener(this);
        socialStateSpinner = findViewById(R.id.socialStateSpinner2);
        socialStateSpinner.setOnItemSelectedListener(this);
        index = getIntent().getExtras().getInt("index");
        initializeArrays();

        //load data from DB
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        signIn();

    }

    /**
     * Handles sign in for the user
     */
    public void signIn() {
        mAuth.signInWithEmailAndPassword("ahnafon3@gmail.com", "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("sample", "signInWithEmail:success");
                            dataRef = db.getReference("moodEvents");
                            loadDataFromDB();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("sample", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Loads the userdata from the database
     */
    public void loadDataFromDB() {
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Mood>> temp = new GenericTypeIndicator<ArrayList<Mood>>() {};
                moodHistory = dataSnapshot.getValue(temp);
                mood = moodHistory.get(index);

                //initialise spinners and edittexts
                et.setText(mood.getReason());
                feelingSpinner.setSelection(moods.indexOf(mood.getFeeling() + 1));
                socialStateSpinner.setSelection(moods.indexOf(mood.getSocialState() + 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Initializes the arrays for feelings and social states
     */
    public void initializeArrays() {
        moods = new ArrayList<>();
        moods.add("happy");
        moods.add("excited");
        moods.add("hopeful");
        moods.add("satisfied");
        moods.add("sad");
        moods.add("angry");
        moods.add("frustrated");
        moods.add("confused");
        moods.add("annoyed");
        moods.add("hopeless");
        moods.add("lonely");

        socialStates = new ArrayList<>();
        socialStates.add("alone");
        socialStates.add("with one person");
        socialStates.add("with two or more people");
        socialStates.add("with a crowd");
    }

    /**
     * This method is called when the user wants to commit the changes made to the mood event
     * @param  v
     * A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
     */
    public void editMoodEvent(View v) {
        boolean change = false;

        feelingSpinner.setOnItemSelectedListener(this);
        socialStateSpinner.setOnItemSelectedListener(this);
        String reason = et.getText().toString();

        if(!feeling.equals("")) {
            if(!feeling.equals(mood.getFeeling())) {
                change = true;
                mood.setFeeling(feeling);
            }
            if(!reason.equals(mood.getReason())) {
                change = true;
                mood.setReason(reason);
            }
            if(!socialState.equals(mood.getSocialState())) {
                change = true;
                mood.setSocialState(socialState);
            }

            if(change) {
                moodHistory.set(index, mood);
                dataRef.setValue(moodHistory);
                finish();
            }

        } else if (feeling.equals(""))
            Toast.makeText(getApplicationContext(), "Please select how you feel", Toast.LENGTH_LONG).show();
    }

    /**
     * This method is called when the user wants to delete the mood event
     * @param  v
     * A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
     */
    public void deleteMood(View v) {
        moodHistory.remove(index);
        dataRef.setValue(moodHistory);
        finish();
    }

    /**
     * This method is called when the user does not want to commit the changes made to the mood event
     * @param  v
     * A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
     */
    public void cancel(View v) { finish(); }

    /**
     * This method handles both drop down menus when editing the mood event
     * @param  parent
     * The AdapterView where the selection happened
     * @param  view
     * The view within the AdapterView that was clicked
     * @param  position
     * The position of the view in the adapter
     * @param  id
     * The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.editMoodFeelingSpinner)
            feeling = parent.getItemAtPosition(position).toString();
        else if(parent.getId() == R.id.socialStateSpinner2)
            socialState = parent.getItemAtPosition(position).toString();
    }

    /**
     * Default handler for no selection
     * @param  parent
     * The AdapterView where the selection happened
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}