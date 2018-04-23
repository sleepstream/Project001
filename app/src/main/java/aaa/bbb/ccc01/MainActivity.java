package aaa.bbb.ccc01;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView list_championship;
    private LinearLayoutManager llm;
    private List_championship_adater list_championship_adater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.mainWindowTite);
        setSupportActionBar(toolbar);
        context = this;

        list_championship = findViewById(R.id.list_championship);
        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list_championship.setLayoutManager(llm);
        list_championship_adater = new List_championship_adater(context);
        list_championship.setAdapter(list_championship_adater);

        LoadChampionship loadChampionship = new LoadChampionship();
        loadChampionship.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadChampionship extends AsyncTask<Void, Void, Void>
    {
        private String  urlLigueChemp = "https://www.championat.com/football/_europeleague/2220/calendar/playoff.html";
        private String getUrlLigueEurope = "https://www.championat.com/stat/football/tournament/2220/";

        private String _1Group = "sport__calendar__table__group";
        private String _1Time = "sport__calendar__table__date";
        private String _1Teams = "sport__calendar__table__teams";
        private String _1Status = "sport__calendar__table__result";

        List<Championship> list;
        @Override
        protected void onPostExecute(Void aVoid) {
            if(list!= null) {
                list_championship_adater.setData(list);
                list_championship_adater.notifyDataSetChanged();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            list = new ArrayList<>();
            ServerLoader serverLoader = new ServerLoader();

            serverLoader.setUrlGet(urlLigueChemp);
            try {
                Response response = serverLoader.runGet();
                switch(response.code())
                {
                    case 200:
                        String body = response.body().string().toString();
                        String beginStr = "<table class=\"table b-table-sortlist\">";
                        String endStr = "</table>";
                        int start = 0;
                        start = body.indexOf(beginStr, start);
                        int end = body.indexOf(endStr, start);
                        String str = body.substring(start, end);
                        beginStr = "<tbody>";
                        endStr = "</tbody>";
                        str= str.substring(str.indexOf(beginStr), str.indexOf(endStr));
                        start = 0;
                        while(str.indexOf("<tr>", start)>0)
                        {
                            start = str.indexOf("<tr>", start);
                            end = str.indexOf("</tr>", start);
                            String line = str.substring(start, end);
                            start = end;

                            _1Group = line.substring(line.indexOf(">",line.indexOf("<td class=\"sport__calendar__table__group\"")),line.indexOf("</td>", line.indexOf("<td class=\"sport__calendar__table__group\"")));
                            _1Time = line.substring(line.indexOf(">",line.indexOf("<td class=\"sport__calendar__table__date\"")),line.indexOf("</td>", line.indexOf("<td class=\"sport__calendar__table__date\"")));
                            _1Teams = line.substring(line.indexOf(">",line.indexOf("<td class=\"sport__calendar__table__teams\">")),line.indexOf("</td>", line.indexOf("<td class=\"sport__calendar__table__teams\">")));
                        }
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            for(int i=0; i<100; i++)
            {
                Championship championship = new Championship();
                championship.date_time= i+""+i+":"+"0"+i;
                championship.championshipPosition = getString(R.string.number_of_championship)+" "+i;
                championship.championshipTeam1 = "Team "+i;
                championship.championshipTeam2 = "Team 0"+i;
                championship.result_text = "0:0";

                list.add(championship);

            }
            return null;
        }

    }
}
