package com.group3.backend.io;

import com.group3.backend.model.Course;

import java.io.IOException;
import java.util.List;

public interface AdapterFileReader {
    List<Course> readSchedule() throws IOException;
}
