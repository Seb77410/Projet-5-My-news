package com.application.seb.projet5_mynews;

import com.application.seb.projet5_mynews.Utils.FormatDate;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FormatDateTest {


    @Test
    public void convertBusinessDateTest() {
        String dateInput = "2019-09-20T21:58:53+0000";
        String dateOutput = "2019-09-20";
        Assert.assertEquals(dateOutput, FormatDate.convertBusinessDate(dateInput));

        //Assert.assertNotEquals();
    }

    @Test
    public void convertTopStoriesDateTest() {
        String dateInput = "2019-09-21T11:20:42-04:00";
        String dateOutput = "2019-09-21";
        Assert.assertEquals(dateOutput,FormatDate.convertTopStoriesDate(dateInput));
    }

}