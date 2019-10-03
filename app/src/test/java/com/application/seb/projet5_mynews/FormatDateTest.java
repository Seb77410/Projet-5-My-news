package com.application.seb.projet5_mynews;

import com.application.seb.projet5_mynews.Utils.FormatDate;

import org.junit.Assert;
import org.junit.Test;



public class FormatDateTest {


    @Test
    public void convertBusinessDateTest() {
        String dateInput = "2019-09-20T21:58:53+0000";
        String dateOutput = "2019-09-20";
        Assert.assertEquals(dateOutput, FormatDate.convertBusinessDate(dateInput));

    }

    @Test
    public void convertTopStoriesDateTest() {
        String dateInput = "2019-09-21T11:20:42-04:00";
        String dateOutput = "2019-09-21";
        Assert.assertEquals(dateOutput,FormatDate.convertTopStoriesDate(dateInput));
    }


    @Test
    public void convertSpinnerDateTest() {
        String outPut = "2019/09/21";
        Assert.assertEquals(outPut, FormatDate.convertDateForSpinner(2019,9,21));
    }

    @Test
    public void convertNumberTest() {
        Assert.assertEquals("02", FormatDate.convertNumber(2));
        Assert.assertEquals("23", FormatDate.convertNumber(23));
    }

}