package com.example.pracitcingrecievingbtc.UIs;

import java.util.Calendar;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.pracitcingrecievingbtc.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStreamReader;;
import java.util.*;

public class BitcoinPriceFrag extends Fragment {

    private static final String TAG = BitcoinPriceFrag.class.getSimpleName(); // prints class name to for debugging
    ArrayList<Double> bitcoinPrices = new ArrayList<>();
    ArrayList<Calendar> date = new ArrayList<>();
    GraphView graph;
    LineGraphSeries<DataPoint> seriesPoints; // for the co-ordinates
    double y;
    Date x;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_historyof_prices, null, false);
        readCSV(); // reading in the data
        graph = (GraphView) view.findViewById(R.id.graph);
        seriesPoints = new LineGraphSeries<DataPoint>();
        // storing x and y values in the data point array
        for (int i = 0; i < bitcoinPrices.size(); i++) {
            y = bitcoinPrices.get(i); // price
            x = date.get(i).getTime();
            Log.d(TAG, "x = " + x + "b y = " + y);
            seriesPoints.appendData(new DataPoint(x, y), true, bitcoinPrices.size());
        }
        graph.addSeries(seriesPoints);
        settingLabels();
        seriesPoints.setColor(Color.MAGENTA);
        seriesPoints.setThickness(2);
        return view;
    }

        public void settingLabels(){
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
            // as we use dates as labels, the human rounding to nice readable numbers is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Bitcoin prices (USD)");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("\n" + "\n" + "Years (mm/dd/yyyy)");
        graph.getGridLabelRenderer().setTextSize(50);
    }

    public void readCSV() {
        try {
            // put the data in a raw folder in the project from https://www.coindesk.com/price/bitcoin/
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.data)));
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                // string date dd/mm/yyyy
                String splittingDates = nextLine[0];
                // date
                String[] fullDate = splittingDates.split("/");
                int day = Integer.parseInt(fullDate[0]); // correct
                int month = Integer.parseInt(fullDate[1]);
                int year = Integer.parseInt(fullDate[2]);
                // calender
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, day);
                date.add(calendar);
                bitcoinPrices.add(Double.parseDouble(nextLine[1]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
