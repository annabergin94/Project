package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
import android.util.Pair;
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
import java.text.DateFormat;
import java.text.ParseException;
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
    private List<Pair<Date, Double>> dataPoints;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_historyof_prices, null, false);


        final SimpleDateFormat sdf = new SimpleDateFormat("yy");
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
                graph.getGridLabelRenderer().setNumHorizontalLabels(8); // for each year
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if(isValueX) {
                                return sdf.format(new Date((short)value));
                            }
                            else {
                                return super.formatLabel(value, isValueX);
                            }}});


                // adding labels
                graph.getGridLabelRenderer().setVerticalAxisTitle("Bitcoin prices (USD)");
                graph.getGridLabelRenderer().setHorizontalAxisTitle("Dates");

                // because x values are dates
                graph.getGridLabelRenderer().setHumanRounding(false);

//                graph.setTitleTextSize(50);

//
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setYAxisBoundsManual(true);
//
//
//                graph.getGridLabelRenderer().setNumHorizontalLabels(bitcoinPrices.size());
//
//                //         graph.getViewport().setMaxX(20); // max length of horizontal so fits on screen
//                graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
//                graph.getViewport().setScrollable(true);
//
//                graph.getViewport().setBackgroundColor(Color.WHITE);
//
//                // this increased the number of vertical labels
////                graph.getGridLabelRenderer().setNumVerticalLabels(8);
////                graph.getGridLabelRenderer().setNumHorizontalLabels(12);
//
//                Log.d(TAG, "setting scroll bar");
//                graph.setHorizontalScrollBarEnabled(true);
//
//                graph.getGridLabelRenderer().setGridColor(Color.GRAY);

                Log.d(TAG, "labelling xaxis");
//                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//                staticLabelsFormatter.setHorizontalLabels(new String[] {"   Dec","   Jan","  Feb", "  Mar","  Apr","  May","  Jun","  Jul","  Aug","  Sep","  Oct","  Nov","  Dec","    "});
//                graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
//                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                seriesPoints.setThickness(7);
  //          }
//        });
        return view;
    }

    public void readCSV() {
        try {
            // put the data in a raw folder in the project from https://www.coindesk.com/price/bitcoin/
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.bitcoinpricehistorydata)));
     //       DateFormat df = new SimpleDateFormat("yyyyMMdd");
      //      String date = null;
            String [] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
        //        df.parse(nextLine[0]);
                bitcoinPrices.add(Double.parseDouble(nextLine[1]));
        //        Log.d(TAG, "X axis " + df.parse(nextLine[0]) + "Y axis " + bitcoinPrices.add(Double.parseDouble(nextLine[1])));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("the specified file was not found");
            //         Toast.makeText(this.getContext(), "The specified file was not found", Toast.LENGTH_SHORT).show();
        }
    }
}