package tn.bigdata.tp1.marketbenefits;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

// Converted to Float
public class FloatSumReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
    private FloatWritable result = new FloatWritable(); // Converted to Float

    public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
        float sum = 0; // Converted to Float
        for (FloatWritable val : values) {
            System.out.println("key : " + key + " | value: " + val.get());
            sum += val.get();
        }
        System.out.println("--> Sum = " + sum);
        result.set(sum);
        context.write(key, result);
    }
}