package com.example.udacitylesson2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.id.message;
import static android.R.id.toggle;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity=0;

     /*
      * This method is called when the order button is clicked.
 */
     public void submitOrder(View view) {
         EditText addName = (EditText) findViewById(R.id.input_name);
         String name = addName.getText().toString();

         CheckBox addNic6 = (CheckBox) findViewById(R.id.add_nic6);
         boolean addNicAnswer = addNic6.isChecked();

         CheckBox addNic3 = (CheckBox) findViewById(R.id.add_nic3);
         boolean addNicAnswer1 = addNic3.isChecked();

         int price = calculatePrice(addNicAnswer, addNicAnswer1);

         Intent intent = new Intent(Intent.ACTION_SENDTO);
         intent.setData(Uri.parse("mailto:")); // only email apps should handle this
         intent.putExtra(Intent.EXTRA_EMAIL, "vape_vkus@gmail.com");
         intent.putExtra(Intent.EXTRA_SUBJECT, "Заказ для: " + name);
         intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(name, price, addNicAnswer,addNicAnswer1));
         if (intent.resolveActivity(getPackageManager()) != null) {
             startActivity(intent);
         }
         displayMessage(createOrderSummary(name, price, addNicAnswer,addNicAnswer1));
     }

//методы увеличения и уменьшения количества заказа
     public void increase(View view){
         if(quantity==100){
             Toast.makeText(this,"Больше заказывать нельзя",Toast.LENGTH_SHORT).show();
         return;}
         quantity = quantity +1;
         display(quantity);
         }
    public void decrease(View view){
        if(quantity<=1){
            Toast.makeText(this,"Вы не можете заказать меньше, чем 1 жидкость",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);}

    //вывод количества товара на экран
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }

    //вывод сообщения на экран
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addNicAnswer, boolean addNicAnswer1) {
        if(addNicAnswer)return quantity * (5+2);
        else if(addNicAnswer1)return quantity * (5+1);
        else return quantity * 5;

    }
    private String createOrderSummary(String name, int price, boolean addNicAns, boolean addNicAns1){
        String priceMessage = "Имя: " + name + "\n" + "Количество: " + quantity + "\n" + "Сумма заказа: $" + price;

        if(addNicAns == true && addNicAns1 == true)priceMessage = priceMessage + "\nвыбрано неверное количество никотина";
        else if(addNicAns == true) priceMessage = priceMessage + "\n6мг. никотина добавлено";
        else if(addNicAns1 == true)priceMessage = priceMessage + "\n3мг. никотина добавлено";
        else priceMessage = priceMessage + "\nБез никотина";
        priceMessage = priceMessage + "\n" + "Спасибо!";
        return priceMessage;
    }



}
