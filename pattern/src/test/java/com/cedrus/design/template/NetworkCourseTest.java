package com.cedrus.design.template;

import com.cedrus.design.template.course.BigDataCourse;
import com.cedrus.design.template.course.JavaCourse;
import com.cedrus.design.template.course.NetworkCourse;


public class NetworkCourseTest {
    public static void main(String[] args) {

        System.out.println("---Java架构师课程---");
        NetworkCourse javaCourse = new JavaCourse();
        javaCourse.createCourse();

        System.out.println("---大数据课程---");
        NetworkCourse bigDataCourse = new BigDataCourse(true);
        bigDataCourse.createCourse();

    }
}
