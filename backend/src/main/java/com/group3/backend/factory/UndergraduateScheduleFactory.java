package com.group3.backend.factory;

import com.group3.backend.decorator.BasicScheduler;
import com.group3.backend.decorator.LoggingScheduler;
import com.group3.backend.decorator.Scheduler;
import com.group3.backend.model.Course;
import com.group3.backend.strategy.NoOverlapStrategy;
import com.group3.backend.util.CourseTypeUtil;
import java.util.List;

public class UndergraduateScheduleFactory extends ScheduleFactory {
    @Override
    protected Scheduler createScheduler(List<Course> scheduledCourses) {
        return new LoggingScheduler(
                new BasicScheduler(scheduledCourses, new NoOverlapStrategy()));
    }

    @Override
    protected boolean accept(Course course) {
        return !CourseTypeUtil.isGraduateCourse(course);
    }
}
