package com.example.quizapp.SingletonClasses;

import com.example.quizapp.Model.Units;

import java.util.ArrayList;
import java.util.HashMap;

public class UnitDataManager {
    private static UnitDataManager instance;

    private HashMap<String, ArrayList<Units>> allCourseUnitsMap;

    private UnitDataManager() {
        allCourseUnitsMap = new HashMap<>();

        ArrayList<Units> osy = new ArrayList<>();
        osy.add(new Units("Overview of Operating System", 8, 1));
        osy.add(new Units("Services and Components of Operating System", 10, 2));
        osy.add(new Units("Process Management", 14, 3));
        osy.add(new Units("CPU Scheduling and Algorithms", 14, 4));
        osy.add(new Units("Memory Management", 14, 5));
        osy.add(new Units("File Management", 10, 6));

        ArrayList<Units> ste = new ArrayList<>();
        ste.add(new Units("Basics of Software Testing and Testing Method", 14, 1));
        ste.add(new Units("Types and Levels of Testing", 18, 2));
        ste.add(new Units("Test Management", 14, 3));
        ste.add(new Units("Defect Management", 10, 4));
        ste.add(new Units("Testing Tools and Measurements", 14, 5));

        ArrayList<Units> css = new ArrayList<>();
        css.add(new Units("Basics of Javascript Programming", 12, 1));
        css.add(new Units("Array,Function and String", 14, 2));
        css.add(new Units("Form and Event Handling", 10, 3));
        css.add(new Units("Cookies and Browser Data", 8, 4));
        css.add(new Units("Regular Expressions,Rollover and Frames", 14, 5));
        css.add(new Units("Menus,Navigation and Web Protection", 12, 6));

        ArrayList<Units> ajp = new ArrayList<>();
        ajp.add(new Units("Abstract Windowing Kit(AWT)", 12, 1));
        ajp.add(new Units("Swings", 10, 2));
        ajp.add(new Units("Event Handling", 12, 3));
        ajp.add(new Units("Network Basics", 10, 4));
        ajp.add(new Units("Interacting with Database", 12, 5));
        ajp.add(new Units("Servlets", 14, 6));

        ArrayList<Units> est = new ArrayList<>();
        est.add(new Units("Environment", 12, 1));
        est.add(new Units("Energy Resources", 10, 2));
        est.add(new Units("EcoSystem and Biodiversity", 12, 3));
        est.add(new Units("Environmental Pollution", 10, 4));
        est.add(new Units("Social Issue and Environmental Education", 12, 5));

        ArrayList<Units> mad = new ArrayList<>();
        mad.add(new Units("Android and its Tools", 4, 1));
        mad.add(new Units("Installation and Configuration of Android", 6, 2));
        mad.add(new Units("Ui Components and Layouts", 8, 3));
        mad.add(new Units("Designing User Interface with View", 12, 4));
        mad.add(new Units("Activity and Multimedia with Databases", 20, 5));
        mad.add(new Units("Security and Android deployment", 20, 6));
        
        ArrayList<Units> pwp = new ArrayList<>();
        pwp.add(new Units("Introduction and Syntax of Python Program", 8, 1));
        pwp.add(new Units("Python Operators and Control Flow statements", 10, 2));
        pwp.add(new Units("Data Structures in Python", 14, 3));
        pwp.add(new Units("Python Functions,Modules,and Packages", 14, 4));
        pwp.add(new Units("Object Oriented Programming in Python", 12, 5));
        pwp.add(new Units("File I/O Handling and Exception Handling", 12, 6));
        
        ArrayList<Units> mgt = new ArrayList<>();
        mgt.add(new Units("Introduction to management concepts and managerial skills", 16, 1));
        mgt.add(new Units("Planning and organizing at supervisory level", 14, 2));
        mgt.add(new Units("Directing and controlling at supervisory level", 14, 3));
        mgt.add(new Units("Safety Management", 14, 4));
        mgt.add(new Units("Legislatiive Acts", 12, 5));

        ArrayList<Units> eti = new ArrayList<>();
        eti.add(new Units("Artificial Intelligence", 6, 1));
        eti.add(new Units("Internet of Things", 18, 2));
        eti.add(new Units("Basics of Digital Forensics", 8, 3));
        eti.add(new Units("Digital Evidence", 10, 4));
        eti.add(new Units("Basics of Hacking", 12, 5));
        eti.add(new Units("Types of Hacking", 16, 5));
        
        ArrayList<Units> wbp = new ArrayList<>();
        wbp.add(new Units("Expressions and Control Statements in PHP", 12, 1));
        wbp.add(new Units("Arrays , Functions and Graphics", 16, 2));
        wbp.add(new Units("Apply Object Oriented Programming in PHP", 16, 3));
        wbp.add(new Units("Creating and validating forms", 12, 4));
        wbp.add(new Units("Database Operations", 14, 5));
    
        ArrayList<Units> nis = new ArrayList<>();
        nis.add(new Units("Introduction to Computer and Information Security", 14, 1));
        nis.add(new Units("User Authentication and Access Control", 10, 2));
        nis.add(new Units("Cryptography", 14, 3));
        nis.add(new Units("Firewall and Intrusion Detection System", 18, 4));
        nis.add(new Units("Network Security Cyber Laws and Compliance Standards", 14, 5));

        allCourseUnitsMap.put("osy", osy);
        allCourseUnitsMap.put("est", est);
        allCourseUnitsMap.put("ajp", ajp);
        allCourseUnitsMap.put("mad", mad);
        allCourseUnitsMap.put("ste", ste);
        allCourseUnitsMap.put("css", css);
        allCourseUnitsMap.put("wbp", wbp);
        allCourseUnitsMap.put("pwp", pwp);
        allCourseUnitsMap.put("mad", mad);
        allCourseUnitsMap.put("eti", eti);
        allCourseUnitsMap.put("mgt", mgt);
    }

    public static synchronized UnitDataManager getInstance() {
        if (instance == null) {
            instance = new UnitDataManager();
        }
        return instance;
    }

    public ArrayList<Units> getUnits(String courseKey) {
        return allCourseUnitsMap.get(courseKey);
    }
}
