package com.example.pracitcingrecievingbtc.View;

import java.util.Calendar;
import android.graphics.Color;
import android.os.Bundle;
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

public class HistoryOfPricesFragment extends Fragment {

    private static final String TAG = "Graph";
    ArrayList<Double> bitcoinPrices = new ArrayList<>();
    ArrayList<Calendar> date = new ArrayList<>(); // changed type to cal
    GraphView graph;
    LineGraphSeries<DataPoint> seriesPoints; // for the co-ordinates
    double y;
    Date x;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_historyof_prices, null, false);

        readCSV(); // reading in the data
        graph = (GraphView) view.findViewById(R.id.graph);
        DataPoint[] dp = new DataPoint[bitcoinPrices.size()]; // empty

        seriesPoints = new LineGraphSeries<DataPoint>();

        // storing x and y values in the data point array
        for (int i = 0; i < bitcoinPrices.size(); i++) {
            y = bitcoinPrices.get(i); // price
            x = date.get(i).getTime();
            seriesPoints.appendData(new DataPoint(x, y), true, bitcoinPrices.size());
        }
        graph.addSeries(seriesPoints);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        // as we use dates as labels, the human rounding to nice readable numbers is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        //      settingXAxisLabels();
        graph.getGridLabelRenderer().setGridColor(Color.BLUE);
        //      settingLabels();
        seriesPoints.setThickness(2);
        return view;
    }

    public void readCSV() {
        try {
            // put the data in a raw folder in the project from https://www.coindesk.com/price/bitcoin/
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.bitcoinpricehistorydatacopy)));
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
            //  System.out.println(date);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}











// old version

//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.fragment.app.Fragment;
//import com.example.pracitcingrecievingbtc.R;
//import com.jjoe64.graphview.DefaultLabelFormatter;
//import com.jjoe64.graphview.GraphView;
//import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
//import com.jjoe64.graphview.helper.StaticLabelsFormatter;
//import com.jjoe64.graphview.series.DataPoint;
//import com.jjoe64.graphview.series.LineGraphSeries;
//import com.opencsv.CSVReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class HistoryOfPricesFragment extends Fragment {
//
//    private static final String TAG = "Graph";
//    List<Double> bitcoinPrices = new ArrayList<>();
//    List<String> date = new ArrayList<>();
//    GraphView graph;
//    LineGraphSeries<DataPoint> seriesPoints; // for the co-ordinates
//    double y;
//    Date x;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.fragment_historyof_prices, null, false);
//
//        readCSV(); // reading in the data
//        graph = (GraphView) view.findViewById(R.id.graph);
//        seriesPoints = new LineGraphSeries<DataPoint>();
//
//        // store each data point
//        for (int i = 0; i < bitcoinPrices.size(); i++) {
//            y = bitcoinPrices.get(i); // price
//
//            System.out.println(x + "," + y);
//
//            //x = x + 0.1; // THIS IS THE ISSUE
//          //  Log.d(TAG, "the price of bitcoin is added to the y axis: " + y);
//            // num of data points is the number of price entries in file
//            seriesPoints.appendData(new DataPoint(x, y), true, bitcoinPrices.size());
//            Log.d(TAG, "plotting point" + x + "," + y);
//        }
//        graph.addSeries(seriesPoints); // adding the points to the graph
//        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//            @Override
//            public String formatLabel(double value, boolean isValueX) {
//                if (isValueX) {
//                    // not working so adding these manually
//                    return "";
//                } else {
//                    // display bitcoin prices in $
//                    return super.formatLabel(value, isValueX) + " $";
//                }
//            }
//        });
//        settingXAxisLabels();
//        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
//        settingLabels();
//        seriesPoints.setThickness(7);
//        return view;
//    }
//
////    public List<Date> iterateThroughDates() throws Exception {
////
////        List<Date> datesInRange = new ArrayList<>();
////
////        for (int i = 0; i < date.size(); i++) {
////
////        // converting from string to date
////        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
////        Date dateObjects = formatter.parse(String.valueOf(date));
////
////        // convert date to calender object
////        Calendar cal = Calendar.getInstance();
////        datesInRange.add(cal.setTime(dateObjects));
////
////
////
////        return datesInRange;
////    }
//
////    public void formattingDates() throws ParseException {
////        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
////        ArrayList<Calendar> c = new ArrayList<Calendar>();
////
////        try {
////            // format strings as dates
////            for (int i = 0; i < date.size(); i++) {
////                Date dateObjects = formatter.parse(date.get(i));
////                // convert date to calender
////                calendar.setTime(dateObjects);
////                System.out.println("Date objects: " + calendar);
////            }
////        } catch (Exception e){
////            e.printStackTrace();
////        }
////    }
//
//
//    public void settingLabels(){
//        graph.getGridLabelRenderer().setVerticalAxisTitle("Bitcoin prices (USD)");
//        graph.getGridLabelRenderer().setHorizontalAxisTitle("Years");
//
//    }
//
//    public void settingXAxisLabels(){
//        graph.getViewport().setXAxisBoundsManual(true);
//        // enable scaling
//        graph.getViewport().setScalable(true);
//        graph.getViewport().setMaxX(bitcoinPrices.size());
//        graph.getGridLabelRenderer().setNumHorizontalLabels(bitcoinPrices.size()); // num lines in file
//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//       staticLabelsFormatter.setHorizontalLabels(new String[] {"2014","2015","2016", "2017","2018","2019","2020","2021","    "});
//        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
//        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
//    }
//
//    public void readCSV() {
//        try {
//            // put the data in a raw folder in the project from https://www.coindesk.com/price/bitcoin/
//            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.bitcoinpricehistorydata)));
//            String [] nextLine;
//            reader.readNext();
//            while ((nextLine = reader.readNext()) != null) {
//                date.add(nextLine[0]);
//                bitcoinPrices.add(Double.parseDouble(nextLine[1]));
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
