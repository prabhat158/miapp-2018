package com.iitbombay.moodi.moodindigo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class EasterEggsActivity extends AppCompatActivity {

    String series, email_id;
    ImageView image;
    String subject, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_eggs);

        image = (ImageView) findViewById(R.id.image);

        series = getIntent().getExtras().getString("series");
        email_id = getIntent().getExtras().getString("email");

        switch (series) {
            case "hp":
                subject = "It's Win-gar-dium Levi-o-sa";
                body = "Did you survive the Avada Kedavra curse? \n Because you're drop dead gorgeous.";
                easterEggs(R.drawable.harry_potter, email_id, subject, body);
                break;
            case "tbbt":
                subject = "You are in my spot";
                body = "On a scale of 1 to America, how free are you tonight? \n Wanna have a cup of coffee?";
                easterEggs(R.drawable.the_big_bang_theory, email_id, subject, body);
                break;
            case "sherlock":
                subject = "Did you miss me?";
                body = "Is this Reichenbach? \n  Because I am falling for you.";
                easterEggs(R.drawable.sherlock, email_id, subject, body);
                break;
            case "house":
                subject = "Reality is almost always wrong";
                body = "Dang boy! Are you my appendix?\n" +
                        "Because I don't understand how you work but this feeling in my stomach makes me wanna take you out";
                easterEggs(R.drawable.house_md, email_id, subject, body);
                break;
            case "hunger":
                subject = "May the odds be ever in your favour";
                body = "They must call you the quarter quell,\n" +
                        "'cause baby, you are changing all the rules";
                easterEggs(R.drawable.hunger_games, email_id, subject, body);
                break;
            case "naruto":
                subject = "I got lost on road of life";
                body = "I address you as Amaterasu\n" +
                        "because you have an ever-burning flame in your heart, which burns every negative emotions to ashes.";
                easterEggs(R.drawable.naruto_itachi, email_id, subject, body);
                break;
            case "got":
                subject = "The things I do for love";
                body = "Did you get sacrificed to the God of fire? \n" +
                        "'Coz you are smoking!!";
                easterEggs(R.drawable.game_of_thrones, email_id, subject, body);
                break;
            case "friends":
                subject = "How you doing?";
                body = "You like that? \n"+
                        "You should hear my phone number!";
                easterEggs(R.drawable.friends, email_id, subject, body);
                break;
            case "myhero":
                subject = "Go Beyond, Plus Ultra";
                body = "If you feel yourself hitting up against your limit remember why you started down this path, and let that memory carry you beyond your limit.";
                easterEggs(R.drawable.myhero, email_id, subject, body);
                break;
        }
    }

    public void easterEggs(int drawable, final String email, final String subject, final String body) {
        image.setImageResource(drawable);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("email", email);
                Intent intent, chooser;
//                intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:"));
//                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
//                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//                intent.putExtra(Intent.EXTRA_TEXT, body);
//                intent.setType("text/plain");
//                chooser = Intent.createChooser(intent, "Send Email");
//                startActivity(chooser);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",email, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        }, 2000);

    }

}
