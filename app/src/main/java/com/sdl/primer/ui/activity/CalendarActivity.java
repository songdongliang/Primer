package com.sdl.primer.ui.activity;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.sdl.primer.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

//    TextView mTextCurrentDay;

    TextView mTextMonthDayEnd;

    TextView mTextYearEnd;

    TextView mTextLunarEnd;

    TextView mTextCurrentDayEnd;

    CalendarView mCalendarView;

    private int mYear;
    CalendarLayout mCalendarLayout;

    /**
     * 开始的calendar
     */
    private Calendar beginCalendar;
    /**
     * 结束的calendar
     */
    private Calendar endCalendar;

    private Calendar currentCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initView();
        updateData();

        mCalendarView.setRange(mCalendarView.getCurYear(),mCalendarView.getCurMonth(),mCalendarView.getCurDay()
                                , 2025,12,31);
    }

    protected void initView() {
        mTextMonthDay =  findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
//        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mCalendarView = findViewById(R.id.calendarView);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout(mYear);
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
//        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCalendarView.scrollToCurrent();
//            }
//        });
        mCalendarLayout = (CalendarLayout) findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
//        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }

    protected void updateData() {

        if (beginCalendar == null) {
            beginCalendar = mCalendarView.getSelectedCalendar();
        }
        if (endCalendar == null) {
            endCalendar = beginCalendar;
        }

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(beginCalendar.getYear(), beginCalendar.getMonth(), beginCalendar.getDay(), 0xFFefa245, "").toString(),
                getSchemeCalendar(beginCalendar.getYear(), beginCalendar.getMonth(), beginCalendar.getDay(), 0xFFefa245, ""));
        map.put(getSchemeCalendar(endCalendar.getYear(), endCalendar.getMonth(), endCalendar.getDay(), 0xFF40db25, "").toString(),
                getSchemeCalendar(endCalendar.getYear(), endCalendar.getMonth(), endCalendar.getDay(), 0xFFda6951, ""));
        map.put(getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay(), 0xFFc7c7c7, "").toString(),
                getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay(), 0xFFc7c7c7, ""));

        mCalendarView.setSchemeDate(map);

    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }



    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {

        if (currentCalendar != null && currentCalendar == calendar) {
            return;
        }
        currentCalendar = calendar;
        if (calendar == beginCalendar || calendar == endCalendar) {
            endCalendar = beginCalendar = calendar;
        } else if (calendar.getTimeInMillis() < beginCalendar.getTimeInMillis()) {
            endCalendar = beginCalendar;
            beginCalendar = calendar;
        } else if (calendar.getTimeInMillis() >= beginCalendar.getTimeInMillis()
                && calendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
            beginCalendar = calendar;
        } else {
            endCalendar = calendar;
        }
        updateData();

        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();


    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }


    @Override
    public void onClick(View v) {

    }
}
