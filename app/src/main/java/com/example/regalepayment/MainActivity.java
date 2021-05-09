package com.example.regalepayment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    Button button;
    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());
        button = findViewById(R.id.button);
        textView3 = findViewById(R.id.textView3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });
    }

    private void makePayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_k7zCiVVf2jAvd2");

        checkout.setImage(R.drawable.logo);


        final Activity activity = this;


        JSONObject options = new JSONObject();
        try {


            options.put("name", "REGALE");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","8512943470");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
    textView3.setText("Succesfull Payment " + s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        textView3.setText("Failed Payment " + s);
    }
}