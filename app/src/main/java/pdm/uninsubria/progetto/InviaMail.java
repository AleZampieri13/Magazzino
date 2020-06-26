package pdm.uninsubria.progetto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class InviaMail extends AsyncTask<Message,String,String> {

    private String miaMail = "zampyteck@gmail.com";
    private String miaPassword = "zampiteck";

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public InviaMail(Context context, String email, String subject, String message){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Message... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Creating a new session
        session = Session.getInstance(props,
                new Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(miaMail, miaPassword);
                    }
                });
        //session.getPasswordAuthentication().getPassword();
        System.out.println(miaMail);
        System.out.println(miaPassword);

        try {
            //Creating MimeMessage object
            Message mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(miaMail));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);

            //Sending email
            //Transport transport = session.getTransport("smtp");
            //transport.connect("smtp.gmail.com" , 587 , miaMail, miaPassword);
            Transport.send(mm);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}