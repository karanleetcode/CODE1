
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayClass arrayClass = new ArrayClass();

    ImageView greimage;ScrollView r;
    TextView home,favlist,voicelist;
    ListView mainlistlistview,favlistlistview,voicelistview;

    ArrayList<Contacts3> arrayList3 = new ArrayList<>();    // voice arraylist
    ArrayList<Contacts> arrayList1 = new ArrayList<>();    //main arraylist
    ArrayList<Contacts2> arrayList = new ArrayList<>();     //fav list
    ArrayList<Contacts> arr = new ArrayList<>();

    CustomAdapter3 customAdapter3;                          //for voice custom adapter
    CustomAdapter2 customAdapter2;                         // for main list custom adapter
    CustomAdapter customAdapter;                            //fav list custom adapter

    final DBHandler3 dbHandler3 = new DBHandler3(this);    //database for voice list
    final DBHandler2 dbHandler2 = new DBHandler2(this);    //database for star list
    final DBHandler  dbHandler  = new DBHandler (this);      //database for main list

    List<Contacts2> newlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r = findViewById(R.id.r);
        greimage = findViewById(R.id.greimage);
        home      = findViewById(R.id.home);
        favlist   = findViewById(R.id.favlist);
        voicelist = findViewById(R.id.voicelist);

        mainlistlistview = findViewById(R.id.mainlistlistview);
        favlistlistview  = findViewById(R.id.favlistlistview);
        voicelistview    = findViewById(R.id.voicelistview);

       // arrayList1 = arrayClass.returnArraylist();
       // customAdapter2 = new CustomAdapter2(getApplicationContext(),arrayList1);
       // mainlistlistview.setAdapter(customAdapter2);
// starting
       mainlistlistview = findViewById(R.id.mainlistlistview);
        favlistlistview= findViewById(R.id.favlistlistview);
        voicelistview.setVisibility(View.INVISIBLE);
        greimage.setVisibility(View.INVISIBLE);
        r.setVisibility(View.INVISIBLE);

        if (SaveSharedPreferenc.getUserName(MainActivity.this).length() == 0) {
            SaveSharedPreferenc s = new SaveSharedPreferenc();
            s.setUserName(getApplicationContext(), "done");

            arr = arrayClass.returnArraylist();

            for (int i = 0; i <= 7; i++) {
                dbHandler.addContact(new Contacts(arr.get(i).getID(),arr.get(i).getWordName(),
                        arr.get(i).getMean(),    arr.get(i).getSentence(),   arr.get(i).getStar(),    arr.get(i).getVoice()));
                Toast.makeText(getApplicationContext(), arr.get(i).getID() + ":" + arr.get(i).getWordName()+":"+arr.get(i).getSentence(), Toast.LENGTH_SHORT).show();
            }
            customAdapter2 = new CustomAdapter2(getApplicationContext(),arr);
            mainlistlistview.setAdapter(customAdapter2);
        }
        else{
            List<Contacts> contacts3 = dbHandler.getAllContacts();

            ArrayList<Contacts> arr2 = new ArrayList<>();
            arr2.clear();
            for (Contacts cn : contacts3) {
                arr2.add(cn);
            }
            customAdapter2 = new CustomAdapter2(getApplicationContext(), arr2);
            mainlistlistview.setAdapter(customAdapter2);
        }
//HOME BUTTON
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainlistlistview.setVisibility(View.VISIBLE);
                favlistlistview.setVisibility(View.INVISIBLE);
                r.setVisibility(View.INVISIBLE);
                voicelistview.setVisibility(View.INVISIBLE);
                greimage.setVisibility(View.INVISIBLE);

                view.setBackgroundColor(Color.rgb(176,	226,	255));
                favlist.setBackgroundColor(Color.rgb(0	,191,	255	));
                voicelist.setBackgroundColor(Color.rgb(0	,191,	255	));
            }
        });
//FAV LIST BUTTON
        favlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favlistlistview.setAdapter(null);
                List<Contacts2> contacts2 = new ArrayList<>();
                contacts2.clear();
                arrayList = new ArrayList<>();
                contacts2 = dbHandler2.getAllContacts();
                for (Contacts2 cn : contacts2) {
                    arrayList.add(cn);
                }

                customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
                favlistlistview.setAdapter(customAdapter);
                greimage.setVisibility(View.INVISIBLE);
                r.setVisibility(View.INVISIBLE);
                voicelistview.setVisibility(View.INVISIBLE);
                mainlistlistview.setVisibility(View.INVISIBLE);
                favlistlistview.setVisibility(View.VISIBLE);

                view.setBackgroundColor(Color.rgb(176,	226,	255));
                home.setBackgroundColor(Color.rgb(0	,191,	255	));
                voicelist.setBackgroundColor(Color.rgb(0	,191,	255	));

            }
        });
//VOICE BUTTON
        voicelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainlistlistview.setVisibility(View.INVISIBLE);
                favlistlistview.setVisibility(View.INVISIBLE);
                greimage.setVisibility(View.VISIBLE);
                r.setVisibility(View.VISIBLE);

                view.setBackgroundColor(Color.rgb(176,	226,	255));
                home.setBackgroundColor(Color.rgb(0	,191,	255	));
                favlist.setBackgroundColor(Color.rgb(0	,191,	255	));

                mainlistlistview.setVisibility(View.INVISIBLE);
                favlistlistview.setVisibility(View.INVISIBLE);
                voicelistview.setVisibility(View.VISIBLE);
                Intent intent=new Intent(MainActivity.this,Main3Activity.class);
                MainActivity.this.startActivity(intent);
              /*  voicelistview.setAdapter(null);
                List<Contacts3> contacts3 = new ArrayList<>();
                contacts3.clear();
                arrayList3 = new ArrayList<>();
                contacts3 = dbHandler3.getAllContacts();
                for (Contacts3 cn : contacts3) {
                    arrayList3.add(cn);
                }

                customAdapter3 = new CustomAdapter3(getApplicationContext(), arrayList3);
                voicelistview.setAdapter(customAdapter3);*/

            }
        });
    }

}
class SaveSharedPreferenc
{
    static final String PREF_USER_NAME= "username";
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
}
