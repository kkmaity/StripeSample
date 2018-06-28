package com.webskitters.stripesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

public class MainActivity extends AppCompatActivity {
    public Card card;
    private EditText et_month,et_year,et_cvv;
    private String[] month;
    private int[] f;
    private EditText etCardnumber;
    private CardView cardPay;
    private int[] year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String refreshedToken1 = FirebaseInstanceId.getInstance().getToken();
        String refreshedToken2 = FirebaseInstanceId.getInstance().getToken();
        System.out.print("!!!!!!!!!!!!!"+FirebaseInstanceId.getInstance().getToken());
        setContentView(R.layout.activity_main);
        cardPay=(CardView)findViewById(R.id.cardPay);
        etCardnumber=(EditText)findViewById(R.id.etCardnumber);
        et_cvv=(EditText)findViewById(R.id.et_cvv);
        et_month=(EditText)findViewById(R.id.et_month);
        et_year=(EditText)findViewById(R.id.et_year);
        et_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMonth(v);

            }
        });
        et_year.setOnClickListener(new View.OnClickListener() {
            public int year;

            @Override
            public void onClick(View v) {

                getYear(v);
            }
        });
        cardPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCardDetails(etCardnumber.getText().toString(),Integer.parseInt(month[0]),Integer.parseInt(et_year.getText().toString()),et_cvv.getText().toString());
            }
        });
    }

    public void setCardDetails(String cardNumber, int cardExpMonth, int cardExpYear, String cardCVC) {
        card = new Card(
                cardNumber,
                cardExpMonth,
                cardExpYear,
                cardCVC
        );

        card.validateNumber();
        card.validateCVC();
        if (card.validateCard()){
            callCard();
        }
    }

    public void callCard() {
        Stripe stripe = new Stripe(MainActivity.this, "pk_test_RO05KvulEYAuxh3FSZAfDpMR");
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        System.out.println("!!!!!!!!!!"+token);
                        // Send token to your server
                    }

                    public void onError(Exception error) {
                        // Show localized error message

                        ;
                    }
                }
        );
    }

    public void getMonth(View button1) {
        PopupMenu popup = new PopupMenu(MainActivity.this, button1);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_month, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


            public boolean onMenuItemClick(MenuItem item) {
                String title = item.getTitle().toString();

                String[] splited = title.split("\\s+");
                month=splited;
                et_month.setText(month[0]);
             //   Toast.makeText(MainActivity.this, "You Clicked : " +month[0], Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        popup.show();//showing popup menu

    }
    public void getYear(View button1) {

        PopupMenu popup = new PopupMenu(MainActivity.this, button1);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_year, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


            public boolean onMenuItemClick(MenuItem item) {
                String title = item.getTitle().toString();
                String substring = title.substring(Math.max(title.length() - 2, 0));
                et_year.setText(substring);
              //  Toast.makeText(MainActivity.this, "You Clicked : " + year[0], Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();//showing popup menu
    }
}

