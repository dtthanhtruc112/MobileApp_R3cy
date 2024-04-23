package com.example.r3cy_mobileapp.Signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.r3cy_mobileapp.R;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPass_Email extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonConfirmEmail;

    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        private String email;
        private String otp;

        public SendEmailTask(String email, String otp) {
            this.email = email;
            this.otp = otp;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                sendEmail(email, otp);
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("ForgotPass_Email", "error: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Show success message on the UI thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ForgotPass_Email.this, "Mã OTP đã được gửi đến email của bạn", Toast.LENGTH_SHORT).show();
                }
            });

            // Navigate to OTP verification activity
            Intent intent = new Intent(ForgotPass_Email.this, ForgotPass_OTPCode.class);
            intent.putExtra("email", String.valueOf(email));
            intent.putExtra("otp", String.valueOf(otp));
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_email);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonConfirmEmail = findViewById(R.id.buttonConfirmEmail);

        buttonConfirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ForgotPass_Email.this, "Vui lòng nhập địa chỉ email", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                // Generate random OTP
                Random random = new Random();
                int otp = random.nextInt(9000) + 1000; // Generate 4-digit OTP

                // Execute AsyncTask to send email
                new SendEmailTask(email, String.valueOf(otp)).execute();
            }
        });
    }
    private void sendEmail(String email, String otp) {
        // Sender's email credentials
        final String username = "anhltt21411@st.uel.edu.vn";
        final String password = "abc123456789@@@";
        Log.v("ForgotPass_Email", "sendEmail đã được kích hoạt");

        // Mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Creating a session
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        Log.v("ForgotPass_Email", "getPasswordAuthentication đã được kích hoạt");
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Creating a MimeMessage object
            Message message = new MimeMessage(session);

            // Setting sender and recipient
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Setting email subject and content
            message.setSubject("R3cy | Mã OTP");
            message.setText("Mã OTP của bạn để đổi mật khẩu tại R3cy là: " + otp);
            Log.v("ForgotPass_Email", "trước hàm sendMessage đã được kích hoạt");

            // Sending the email
            Transport.send(message);
            Log.v("ForgotPass_Email", "sendMessage đã được kích hoạt");
        } catch (MessagingException e) {
            // Show error message
            Toast.makeText(ForgotPass_Email.this, "Lỗi không gửi được mã OTP đến email của bạn", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.v("ForgotPass_Email", "error: " + e.getMessage());
        }
    }
}