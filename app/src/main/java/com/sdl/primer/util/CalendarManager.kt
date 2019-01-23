package com.sdl.primer.util

import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import java.util.HashMap

class CalendarManager(val mCalendarView: CalendarView):
        CalendarView.OnCalendarSelectListener {

    /**
     * 开始的calendar
     */
    private var beginCalendar: Calendar? = null
    /**
     * 结束的calendar
     */
    private var endCalendar: Calendar? = null

    private var currentCalendar: Calendar? = null

    init {
        mCalendarView.setRange(mCalendarView.curYear, mCalendarView.curMonth, mCalendarView.curDay
                , 2025, 12, 31)
        updateData()
    }

    var calendarSelectListener: ((Calendar?,Calendar?) -> Unit)? = null

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        if (currentCalendar != null && currentCalendar == calendar) {
            return
        }
        currentCalendar = calendar
        if (calendar === beginCalendar || calendar === endCalendar) {
            beginCalendar = calendar
            endCalendar = beginCalendar
        } else if (calendar!!.timeInMillis < beginCalendar!!.timeInMillis) {
            endCalendar = beginCalendar
            beginCalendar = calendar
        } else if (calendar.timeInMillis >= beginCalendar!!.timeInMillis
                && calendar.timeInMillis < endCalendar!!.timeInMillis) {
            beginCalendar = calendar
        } else {
            endCalendar = calendar
        }
        updateData()
        calendarSelectListener?.invoke(beginCalendar,endCalendar)
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {

    }

    private fun updateData() {

        if (beginCalendar == null) {
            beginCalendar = mCalendarView.selectedCalendar
        }
        if (endCalendar == null) {
            endCalendar = beginCalendar
        }

        val map = HashMap<String, Calendar>()
        beginCalendar?.let {
            map[getSchemeCalendar(it.year, it.month, it.day, 0x105dbb, "").toString()] =
                    getSchemeCalendar(it.year, it.month, it.day, 0x105dbb, "")
        }
        endCalendar?.let {
            map[getSchemeCalendar(it.year, it.month, it.day, 0xbf24db, "").toString()] =
                    getSchemeCalendar(it.year, it.month, it.day, 0x2596af, "")
        }
        map[getSchemeCalendar(mCalendarView.curYear, mCalendarView.curMonth, mCalendarView.curDay, 0x383839, "").toString()] =
                getSchemeCalendar(mCalendarView.curYear, mCalendarView.curMonth, mCalendarView.curDay, 0x383839, "")

        mCalendarView.setSchemeDate(map)

    }

    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color: Int, text: String): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color//如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        return calendar
    }
}