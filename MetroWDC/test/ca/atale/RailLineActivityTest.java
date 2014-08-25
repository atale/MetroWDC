package ca.atale;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.atale.metrowdc.R;
import ca.atale.metrowdc.RailLineActivity;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class RailLineActivityTest {

    RailLineActivity activity;

    @Before
    public void setup()
    {
        this.activity = Robolectric.buildActivity(RailLineActivity.class).create().get();
    }

    @Test
    public void shouldHaveHappySmiles() throws Exception 
    {
        String hello = this.activity.getString(R.string.hello_world);
        assertThat(hello, equalTo("Hello world!"));
    }
}