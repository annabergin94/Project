package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.R;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryOfPricesFragment extends Fragment {

    private static final String TAG = "Graph";
    private Button btnCallHistoricalPriceFrag;
    List<Double> bitcoinPrices = new ArrayList<>();
    List<String> date = new ArrayList<>();
    //private static final String PATH_TO_CSV = "/Users/annab/OneDrive/Desktop/raw/data.csv";
    GraphView graph;
    LineGraphSeries<DataPoint> seriesPoints; // for the co-ordinates



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_historyof_prices, null, false);

        btnCallHistoricalPriceFrag = (Button) view.findViewById(R.id.btnCallHistoricalPriceFrag);
        btnCallHistoricalPriceFrag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                double x,y;
                x = 0; // graph to start at 0


                readCSV();
                Log.d(TAG, "Reading the CSV file");

                Log.d(TAG, "initialising the graph view and points array");
                graph = (GraphView) view.findViewById(R.id.graph);
                seriesPoints = new LineGraphSeries<DataPoint>();

                Log.d(TAG, "looping through the data points and storing them");
                for (int i = 0; i < bitcoinPrices.size(); i++) {
                    x = x + 0.1; // going through each date needs to be double for point
                    y = bitcoinPrices.get(i); // price
                    Log.d(TAG, "the price of bitcoin is added to the y axis: " + y);
                    // num of data points is the number of price entries in file
                    seriesPoints.appendData(new DataPoint(x, y), true, bitcoinPrices.size());
                }
                graph.addSeries(seriesPoints); // adding the points to the graph


                // setting title
                graph.setTitle("BTC/USD prices December 2020 - 2021");

                // adding labels
                graph.getGridLabelRenderer().setVerticalAxisTitle("Bitcoin prices (USD)");
                graph.getGridLabelRenderer().setHorizontalAxisTitle("\n" + "Dates in months Dec 20 - Dec 21");

                graph.setTitleTextSize(50);

                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setYAxisBoundsManual(true);


                graph.getGridLabelRenderer().setNumHorizontalLabels(bitcoinPrices.size());

                //         graph.getViewport().setMaxX(20); // max length of horizontal so fits on screen
                graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
                graph.getViewport().setScrollable(true);

                graph.getViewport().setBackgroundColor(Color.WHITE);

                // this increased the number of vertical labels
                graph.getGridLabelRenderer().setNumVerticalLabels(8);
                graph.getGridLabelRenderer().setNumHorizontalLabels(12);

                Log.d(TAG, "setting scroll bar");
                graph.setHorizontalScrollBarEnabled(true);

                graph.getGridLabelRenderer().setGridColor(Color.GRAY);

                Log.d(TAG, "labelling xaxis");
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(new String[] {"   Dec","   Jan","  Feb", "  Mar","  Apr","  May","  Jun","  Jul","  Aug","  Sep","  Oct","  Nov","  Dec","    "});
                graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                seriesPoints.setThickness(7);
            }
        });
        return view;
    }

    public void readCSV() {
        try {
            // put the data in a raw folder in the project from https://www.coindesk.com/price/bitcoin/
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.bitcoinpricehistorydata)));
            String [] nextLine;
            String csvLine = null;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                bitcoinPrices.add(Double.parseDouble(nextLine[1]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("the specified file was not found");
            //         Toast.makeText(this.getContext(), "The specified file was not found", Toast.LENGTH_SHORT).show();
        }
    }
}