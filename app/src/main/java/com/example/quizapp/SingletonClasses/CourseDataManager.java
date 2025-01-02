package com.example.quizapp.SingletonClasses;


import com.example.quizapp.Model.CourseDomain;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseDataManager {
    private static CourseDataManager instance;

    public static final String TYPE_THEORY = "com.example.quizApp.CourseTypes.Theory";
    public static final String TYPE_PR = "com.example.quizApp.CourseTypes.Practical";
    public static final String TYPE_MCQ = "com.example.quizApp.CourseTypes.MultipleChoice";
    private HashMap<String, ArrayList<CourseDomain>> allSemesterCoursesMap;

    private CourseDataManager() {
        allSemesterCoursesMap = new HashMap<>();
        ArrayList<CourseDomain> five = new ArrayList<>();
        five.add(new CourseDomain("Operating System", "22516", "osy", TYPE_THEORY));
        five.add(new CourseDomain("Software Testing", "22518", "ste", TYPE_THEORY));
        five.add(new CourseDomain("Client-Side Scripting", "22519", "css", TYPE_THEORY));
        five.add(new CourseDomain("Advanced Java Programming", "22517", "ajp", TYPE_MCQ));
        five.add(new CourseDomain("Environmental Studies", "22447", "est", TYPE_MCQ));
        five.add(new CourseDomain("Industrial Training", "22057", "itr", TYPE_PR));
        five.add(new CourseDomain("Capstone Project Planning", "22058", "cpp", TYPE_PR));
        five.add(new CourseDomain("Advanced Computer Network", "22020", "acn", TYPE_THEORY));
        five.add(new CourseDomain("Advanced Database Management Systems", "22021", "adn", TYPE_THEORY));
        ArrayList<CourseDomain> six = new ArrayList<>();
        six.add(new CourseDomain("Mobile Application Development", "22617", "mad", TYPE_THEORY));
        six.add(new CourseDomain("Programming With Python", "22616", "pwp", TYPE_THEORY));
        six.add(new CourseDomain("Web Based Application Development Using PHP", "22619", "wbp", TYPE_THEORY));
        six.add(new CourseDomain("Emerging Trends in Information Technology", "22618", "eti", TYPE_MCQ));
        six.add(new CourseDomain("Management", "22509", "mgt", TYPE_MCQ));
        six.add(new CourseDomain("Entrepreneurship Development", "22032", "ede", TYPE_PR));
        six.add(new CourseDomain("Capstone Project Execution", "22060", "cpe", TYPE_PR));

        allSemesterCoursesMap.put("five", five);
        allSemesterCoursesMap.put("six", six);
    }

    public static synchronized CourseDataManager getInstance(){
        if(instance == null){
            instance = new CourseDataManager();
        }
        return instance;
    }

    public ArrayList<CourseDomain> getCourses(String courseKey) {
        return allSemesterCoursesMap.get(courseKey);
    }
}