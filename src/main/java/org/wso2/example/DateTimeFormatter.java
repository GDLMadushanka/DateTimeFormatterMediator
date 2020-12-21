package org.wso2.example;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class mediator will change the timeZone of a given timestamp.
 */
public class DateTimeFormatter extends AbstractMediator {

    private static final Log log = LogFactory.getLog(DateTimeFormatter.class);

    private String inputDateTime;
    private String inputDateTimeFormat;
    private String inputTimeZone;
    private String outputDateTimeFormat;
    private String outputTimeZone;
    private String inputFileName;

    public boolean mediate(MessageContext messageContext) {

        if (!StringUtils.isEmpty(inputDateTime) && !StringUtils.isEmpty(inputDateTimeFormat) &&
                !StringUtils.isEmpty(inputTimeZone) && !StringUtils.isEmpty(outputDateTimeFormat) &&
                !StringUtils.isEmpty(outputTimeZone) && !StringUtils.isEmpty(inputFileName)) {
            try {
                SimpleDateFormat inputdf = new SimpleDateFormat(inputDateTimeFormat);
                inputdf.setTimeZone(TimeZone.getTimeZone(inputTimeZone));
                Date inputDate = inputdf.parse(inputDateTime);
                SimpleDateFormat outputdf = new SimpleDateFormat(outputDateTimeFormat);
                outputdf.setTimeZone(TimeZone.getTimeZone(outputTimeZone));
                String outputDate = outputdf.format(inputDate);

                String inputFile = inputFileName.substring(0, inputFileName.length() - 4);
                String fileExtention = inputFileName.substring(inputFileName.length() - 4);
                String outputFileName = inputFile + "_" + outputDate + "_" + fileExtention;
                messageContext.setProperty("OUTPUT_FILE_NAME", outputFileName);
            } catch (ParseException e) {
                log.error("Error occurred while parsing the date", e);
            }
        } else {
            log.error("Required input variables are not set");
        }
        return true;
    }

    public String getInputDateTime() {

        return inputDateTime;
    }

    public void setInputDateTime(String inputDateTime) {

        this.inputDateTime = inputDateTime;
    }

    public String getInputDateTimeFormat() {

        return inputDateTimeFormat;
    }

    public String getInputTimeZone() {

        return inputTimeZone;
    }

    public void setInputTimeZone(String inputTimeZone) {

        this.inputTimeZone = inputTimeZone;
    }

    public String getOutputTimeZone() {

        return outputTimeZone;
    }

    public void setOutputTimeZone(String outputTimeZone) {

        this.outputTimeZone = outputTimeZone;
    }

    public void setInputDateTimeFormat(String inputDateTimeFormat) {

        this.inputDateTimeFormat = inputDateTimeFormat;
    }

    public String getOutputDateTimeFormat() {

        return outputDateTimeFormat;
    }

    public void setOutputDateTimeFormat(String outputDateTimeFormat) {

        this.outputDateTimeFormat = outputDateTimeFormat;
    }

    public String getInputFileName() {

        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {

        this.inputFileName = inputFileName;
    }
}
