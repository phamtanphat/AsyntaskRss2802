package khoapham.ptp.phamtanphat.asyntaskrss;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    Button btnReadrss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReadrss = findViewById(R.id.buttonReadRss);

        btnReadrss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XulyRss().execute("https://www.24h.com.vn/upload/rss/trangchu24h.rss");
            }
        });
    }
    class XulyRss extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String noidung = docNoiDung_Tu_URL(strings[0]);
            return noidung;
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser xmldomParser = new XMLDOMParser();
            Document document = xmldomParser.getDocument(s);
            // nhung the giong nhau thi gom lai thanh 1 nodelist
            NodeList nodeList = document.getElementsByTagName("item");
            //Item => Element
            //Nodelist => tat ca the item
            Element element = (Element) nodeList.item(0);
            String title = xmldomParser.getValue(element ,"title");
            Log.d("BBB",title) ;
            super.onPostExecute(s);
        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
