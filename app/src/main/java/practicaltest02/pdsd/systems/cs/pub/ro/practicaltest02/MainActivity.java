package practicaltest02.pdsd.systems.cs.pub.ro.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    static Button serverStart;
    static Button get;
    static EditText port;
    static EditText url;
    static TextView urlBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverStart = findViewById(R.id.startserver);
        get = findViewById(R.id.get);
        port = findViewById(R.id.port);
        urlBody = findViewById(R.id.urlview);
        url = findViewById(R.id.url);

        serverStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (port.getText().toString() == null)
                        return;
                    ServerThead st = new ServerThead(Integer.valueOf(port.getText().toString()));
                    new Thread(st).start();
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url.getText().toString() == null)
                    return;

                ClientThread ct = new ClientThread(Integer.valueOf(port.getText().toString()), url.getText().toString());
                new Thread(ct).start();
            }
        });



    }







}
