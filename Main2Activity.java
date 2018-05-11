
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    final DBHandler dbHandler  = new DBHandler(this);

    ImageView speaker;
    Button favlistbutton;
    TextView sentence,meaning,word,syn;
    DBHandler2 dbHandler2 = new DBHandler2(this);
    DBHandler3 dbHandler3 = new DBHandler3(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent in = getIntent();
        String tv1= in.getExtras().getString("wordname");

        favlistbutton = (Button) findViewById(R.id.favlistbutton);
        speaker = (ImageView) findViewById(R.id.speaker);

        sentence = findViewById(R.id.sentence);
        meaning = findViewById(R.id.meaning);
        word = findViewById(R.id.wordName);
        syn = findViewById(R.id.syn);

        final String[] data = dbHandler.getAllDetailsOfWord(tv1).split(":");   //getting whole data from database

        word.setText(data[1]);   //word
        meaning.setText(Html.fromHtml("<b>"+": "+"</b>"+data[2]));   //sentence
        sentence.setText(Html.fromHtml("<b>"+" Example : "+"</b>"+data[3]));    //meaning
        syn.setText(Html.fromHtml("<b>"+"Synonyms: "+"</b>"+"fsdfsdf, "+"ewfwefw, "+"efwefwefef,"+"cgdfgdd,"+"gsgsgs,"+"sgsgsgs"));


        favlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if(dbHandler2.wordExist(data[1])!=0){
                            Toast.makeText(getApplicationContext(),"Word already exists in the Fav list",Toast.LENGTH_SHORT).show();
                        }else{
                            dbHandler2.addContact(new Contacts2(0,data[1],"1"));
                            dbHandler.updateStarTo1(data[1]);
                            Toast.makeText(getApplicationContext(),dbHandler.getAllDetailsOfWord(data[1]),Toast.LENGTH_SHORT).show();
                        }
            }
        });

    }
    class ParseURL81 extends AsyncTask<String, Void, Void> {
        public String s, x;
        public String tex12, tex13;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                x = strings[0];
                org.jsoup.nodes.Document doc = Jsoup.connect("http://www.thesaurus.com/browse/"+x+"?s=t").get();
                //  Elements img = doc.select("div.answer_text");
                Elements tex1 = doc.select("span.text");
                tex13 = tex1.toString();
                //tex12 = img.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @TargetApi(24)
        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            org.jsoup.nodes.Document doc2 = Jsoup.parse(tex13);
            String content45 = doc2.body().text();
            syn.setText(content45.substring(0,600).replaceAll(" ",",")+"..and many more");
            // Document doc3 = Jsoup.parse(tex12);
            // String content2 = doc3.body().text();
            //   mapText.setText(content);
            //  qAnswer.setText(" " + content45.substring(0, 1000));
            //Typeface custom61 = Typeface.createFromAsset(getAssets(), "fonts/opensan.ttf");
            //qAnswer.setTypeface(custom61);
        }
    }

    public void mt(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
