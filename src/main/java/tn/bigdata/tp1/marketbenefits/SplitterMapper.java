package tn.bigdata.tp1.marketbenefits;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitterMapper extends Mapper<Object, Text, Text, FloatWritable>  {
    private FloatWritable benefits = new FloatWritable();
    private Text market = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        // Regex to retrieve data from irregular structured data
        String patternString = "^([\\d-]+)\\s+([\\d:]+)\\s+(.*?)(?:\\s{2,}(.*?))??\\s+([\\d.]+)\\s+(\\w+)$";
        Pattern pattern = Pattern.compile(patternString);

        // Apply regex pattern to value (current line)
        Matcher matcher = pattern.matcher(value.toString());

        if (matcher.find()) {
            // Setting value for mapper
            market.set(matcher.group(3));
            benefits.set(Float.parseFloat(matcher.group(5))); // Cause of the unstructured data, it can contain the market + product e.g: Wichita Toys
            System.out.println("Market : " + market + " | Benefits : " + benefits);

        } else {
            // If the string don't match the regex pattern
            System.out.println("Pattern doesn't not match for value => " + value.toString());
        }

        // Map
        context.write(market, benefits);
    }
}
